# System Design Interview Preparation — Database Scalability & Query Optimization

## Overview

This document captures our discussion around handling large database tables, query optimization, indexing, fragmentation, denormalization, foreign keys, partitioning, sharding, and local/global indexes.

The goal is to build **interview-ready mental models**, not just memorize definitions.

---

# 1. Handling Lots of Database Tables

## Interview Question

> Suppose you're building an enterprise application that has around 300–500 database tables. How would you design and manage such a database?

### Key ideas

Having hundreds of tables is not inherently a problem. Large enterprise applications naturally have many entities.

The important considerations are:

- Domain separation
- Normalization
- Controlled denormalization
- Indexing
- Foreign keys and referential integrity
- Partitioning
- Sharding
- Archiving
- Read replicas
- Schema migrations
- Monitoring

### Organize tables by domain

Instead of mentally treating 500 tables as one giant schema, group them by business domain.

Example:

```text
User Domain
-----------
users
addresses
roles
permissions
sessions

Order Domain
------------
orders
order_items
payments
refunds
shipments

Inventory Domain
----------------
products
categories
stock
warehouse
supplier

Notification Domain
-------------------
emails
sms
templates
notifications
```

This aligns with Domain-Driven Design (DDD) thinking and helps with ownership and maintainability.

---

# 2. Normalization vs Denormalization

## Normalization

Normalization reduces duplicated data and keeps updates consistent.

Example:

```text
Customers
---------
id
name
email
phone

Orders
------
id
customer_id
```

instead of putting customer information directly into every order.

## Don't over-normalize

Too many joins can make frequently accessed queries expensive.

For example:

```text
Order
  ↓
Customer
  ↓
City
  ↓
State
  ↓
Country
  ↓
Currency
  ↓
Timezone
```

Sometimes denormalization is justified when the read path is important.

---

# 3. Query Optimization Example

Consider:

```sql
SELECT *
FROM orders
WHERE customer_id = 12345
ORDER BY created_at DESC
LIMIT 20;
```

Suppose the `orders` table contains 2 billion rows.

## Initial reasoning

Without a suitable index, the database may need to scan a huge number of rows and then sort matching rows by `created_at`.

However, a senior engineer should **not assume this immediately**.

First ask:

> Do we already have indexes?

Then inspect the execution plan using:

```sql
EXPLAIN
```

or, where supported:

```sql
EXPLAIN ANALYZE
```

---

# 4. Separate Indexes vs Composite Index

## Separate indexes

Suppose we have:

```text
Index 1:
customer_id

Index 2:
created_at
```

These are **not equivalent** to:

```text
(customer_id, created_at)
```

The database may use the `customer_id` index to locate rows and then sort them, or choose another plan. Some databases can perform index intersection/merge, but that is not equivalent to having an index whose ordering directly matches the query.

## Composite index

For:

```sql
WHERE customer_id = ?
ORDER BY created_at DESC
LIMIT 20
```

a natural index is:

```sql
(customer_id, created_at DESC)
```

Conceptually:

```text
customer 101
    Apr
    Mar
    Feb
    Jan

customer 102
    May
    Feb

customer 103
    Jun
    Apr
```

The database can:

1. Find `customer_id = 101`
2. Start at the newest `created_at`
3. Read 20 rows
4. Stop

This can avoid a separate sort.

### Interview takeaway

> A composite index is not the same as multiple single-column indexes. Its ordering can satisfy both filtering and sorting requirements.

---

# 5. Why `DESC` on an Index?

Query:

```sql
ORDER BY created_at DESC
LIMIT 20;
```

An index such as:

```sql
(customer_id, created_at DESC)
```

aligns naturally with retrieving the newest records first.

However, many databases can scan B-tree indexes in reverse, so explicitly specifying `DESC` is not always necessary.

A strong interview answer:

> "I would align the index with the common access pattern. If the database supports reverse B-tree scans, an ascending index may also satisfy the descending query efficiently, so I would verify the actual execution plan."

---

# 6. Partitioning by Date with a Customer Query

Suppose the table is partitioned monthly:

```text
orders_2026_01
orders_2026_02
orders_2026_03
...
orders_2026_12
```

Query:

```sql
SELECT *
FROM orders
WHERE customer_id = 12345
ORDER BY created_at DESC
LIMIT 20;
```

There is no predicate on the partition key.

Therefore, **do not assume partition pruning will occur**.

The database generally cannot safely say:

> "Only December matters."

A customer's orders could exist in any month.

Depending on the database engine and optimizer, it may have to consider multiple/all partitions and combine their results.

Partitioning can even introduce some overhead for such a query.

## Important rule

> Partitioning improves query performance when the query predicates allow the database to eliminate partitions.

Example:

```sql
WHERE created_at >= '2026-08-01'
```

Now the database can potentially prune older partitions.

---

# 7. Partitioning vs Sharding

## Partitioning

Data is divided into partitions within a database/table.

```text
Database
  ↓
Orders
  ├── Partition 1
  ├── Partition 2
  └── Partition 3
```

## Sharding

Data is distributed across multiple database instances/nodes.

```text
Application
     ↓
Shard Router
     ├── DB Shard 1
     ├── DB Shard 2
     └── DB Shard 3
```

A useful interview distinction:

> Partitioning divides data within a database environment; sharding distributes data across database instances/nodes.

---

# 8. Scaling Further with Sharding

Suppose the dominant query is:

```sql
WHERE customer_id = ?
ORDER BY created_at DESC
LIMIT 20;
```

and the dataset becomes extremely large.

A natural shard key is:

```text
customer_id
```

because it matches the dominant access pattern.

The flow becomes:

```text
Application
    ↓
Shard Router
    ↓
Shard containing customer
    ↓
(customer_id, created_at) index
    ↓
20 rows
```

## Important trade-offs

Sharding introduces:

- Cross-shard queries
- Rebalancing complexity
- Hot shards
- Distributed transactions
- Operational complexity

For example:

```sql
SELECT COUNT(*)
FROM orders;
```

must potentially query every shard and aggregate the results.

---

# 9. How Does a Lookup-Based Shard Router Work?

A lookup service does not magically know the shard.

It **stores the mapping**.

Example:

```text
customer_id → shard

12345 → Shard 7
98765 → Shard 3
```

A possible flow:

```text
Application
    ↓
Local Cache
    ↓
Cache miss?
    ↓
Lookup Service
    ↓
Shard 7
```

The mapping could be based initially on:

- Hashing
- Round robin
- Least-loaded shard
- Geography
- Tenant placement

The important difference is that the mapping is persisted and can later be changed.

## Why not just use modulo?

Example:

```text
customer_id % 4
```

Adding a fifth shard changes the mapping:

```text
customer_id % 5
```

and potentially causes massive data movement.

Consistent hashing or an explicit shard map can make rebalancing easier.

---

# 10. Shard Hotspots

Sharding by customer ID does not automatically guarantee even load.

Suppose:

```text
Customer A → 10 million orders
Everyone else → 10 orders each
```

That customer can create a hot shard.

Possible approaches:

- Split/bucket extremely large customers
- Give large tenants dedicated shards
- Move hot tenants
- Use adaptive shard mapping
- Cache frequently accessed data

---

# 11. Composite Index Exists but Query Still Takes 8 Seconds

Suppose we already have:

```sql
(customer_id, created_at DESC)
```

but the query is still slow.

The first step is:

```sql
EXPLAIN ANALYZE
```

Do not immediately assume partitioning or sharding is required.

## Possible causes

### 1. `SELECT *`

The index may locate the rows quickly, but fetching wide rows can be expensive.

Example:

```text
invoice_pdf
large JSON
large text
metadata
```

If only a few fields are required, select only those fields.

### 2. Very large customer

One customer might have millions of orders.

The index still helps, but the amount of matching data and physical page traversal can matter.

### 3. Poor data distribution / selectivity

If one customer owns a very large fraction of the data, the index may not be highly selective for that value.

### 4. Index fragmentation / bloat

Long-running insert/update/delete workloads can cause page splits and partially filled/scattered pages.

### 5. Stale optimizer statistics

The optimizer may estimate:

```text
Expected rows: 10
```

when reality is:

```text
Actual rows: 5 million
```

This can lead to a poor execution plan.

### 6. Disk I/O bottleneck

The query may be logically efficient but waiting on storage.

### 7. Lock contention

The query may be waiting for another transaction.

### 8. Network / serialization cost

The database may finish quickly, but transferring and serializing large rows may be expensive.

### 9. Index isn't actually being used

Functions, casts, mismatched data types, or other query patterns can prevent efficient index use.

### 10. Index does not match the actual query

For example:

```sql
WHERE customer_id = ?
AND status = 'PAID'
ORDER BY created_at DESC
```

might benefit from an index such as:

```text
(customer_id, status, created_at)
```

depending on the database and workload.

---

# 12. Index Fragmentation

Index fragmentation means the logical ordering of the index remains correct, but its physical storage becomes less efficient.

A B-tree might logically represent:

```text
A → B → C → D → E → F
```

while the physical pages may be scattered:

```text
Page 10 → Page 482 → Page 91 → Page 712
```

This can increase:

- Page reads
- Cache misses
- Random I/O

The B-tree is still balanced, so lookup complexity remains approximately:

```text
O(log N)
```

but the constant cost can increase.

## Causes

- Random inserts
- Deletes
- Updates to indexed columns
- Page splits

UUID/random keys can create more random insertion patterns than sequential keys.

## Maintenance

Depending on the database:

- Index reorganization
- Index rebuild
- Vacuum/bloat management

should be considered based on actual metrics rather than performed blindly.

---

# 13. How to Explain Index Fragmentation in an Interview

> "A B-tree remains logically sorted, but over time random inserts, deletes, and updates can cause page splits and partially filled pages. Logically adjacent entries may end up on physically distant pages, increasing I/O and cache misses. The lookup complexity remains O(log N), but the actual execution time can increase."

---

# 14. Denormalization

Consider:

```text
Customers
---------
customer_id
name
email

Orders
------
order_id
customer_id

OrderItems
----------
order_item_id
order_id
product_id
quantity

Products
--------
product_id
name
price
category
brand
```

An order-history page may need multiple joins.

One possible denormalization is:

```text
OrderItems
----------
order_item_id
order_id
product_id
product_name
purchase_price
```

and potentially:

```text
Orders
------
order_id
customer_id
customer_name
```

## Why?

- Reduce joins
- Improve read performance
- Preserve historical information

## Important business consideration

Historical values often should not change.

If a product cost ₹100 when purchased, an old order should usually continue showing:

```text
purchase_price = ₹100
```

even if the current product price becomes ₹150.

Similarly, invoices often need to preserve historical customer/address information.

## Don't blindly duplicate everything

Only duplicate fields justified by the access pattern or business semantics.

---

# 15. Foreign Keys

Foreign keys enforce referential integrity.

Example:

```text
Customers
---------
id = 101

Orders
------
customer_id = 101
```

The database can prevent invalid references.

## Important correction

A foreign key can reference a primary key or another appropriately constrained unique key, depending on the database.

## When might database-enforced FKs be avoided?

### Microservices

If:

```text
Order Service → Orders DB
Customer Service → Customers DB
```

the database cannot normally enforce a cross-database FK.

Integrity must be handled through application/service-level mechanisms.

### Very high write throughput

FK checks introduce work. Some systems may choose application-level enforcement where the performance/architecture trade-off justifies it.

### Sharded databases

If:

```text
Customer → Shard 1
Order → Shard 7
```

the database cannot usually enforce a normal FK across shards.

### Bulk loading

Large data imports may use staging tables or temporarily manage constraints and validate the data afterward.

## Important distinction

The requirement for referential integrity may still exist.

The question is:

> **Who enforces it — the database or the application/system?**

---

# 16. What Happens When a Table Reaches 5 Billion Rows?

Do not immediately say:

> "Shard it."

First determine what is actually becoming a bottleneck.

## Diagnose

Ask:

- Are reads slow?
- Are writes slow?
- Is storage becoming a problem?
- Are backups too long?
- Are indexes too large?
- Is maintenance becoming difficult?
- Are there lock/contention problems?
- Is this a reporting workload?
- How much of the data is actively accessed?

## Potential approaches

### Query optimization

- Avoid `SELECT *`
- Use `EXPLAIN ANALYZE`
- Improve indexes
- Optimize joins
- Reduce unnecessary data transfer

### Read replicas

```text
Primary
  ↓
Replica 1
Replica 2
Replica 3
```

Writes go to primary; read traffic can be distributed where consistency requirements permit.

### Caching

Frequently accessed order data/results can be cached.

### Partitioning

Useful when queries align with the partition key and for operational management.

Example:

```text
2024
2025
2026
```

### Archiving

Move old, rarely accessed data to an archive store/database.

Instead of:

```sql
DELETE FROM orders
WHERE created_at < ...
```

on billions of rows, dropping/detaching an old partition can be much more efficient when the schema is designed for it.

### Sharding

Consider when a single database can no longer handle:

- storage requirements
- write throughput
- read throughput
- operational/maintenance requirements

and horizontal scaling is required.

---

# 17. Database Partitioning

## Definition

Database partitioning divides a large logical table into smaller partitions based on a partition key.

The goal is not simply to split rows. It is to improve:

- Query performance when partition pruning is possible
- Data lifecycle management
- Maintenance
- Archiving
- Large-table operations

## Horizontal partitioning

Same columns, different rows.

```text
Orders
  ├── Partition A
  ├── Partition B
  └── Partition C
```

For example:

```text
2024 orders
2025 orders
2026 orders
```

## Vertical partitioning

Different columns are separated.

Example:

```text
CustomerBasic
-------------
id
name
email
```

and:

```text
CustomerProfile
---------------
id
photo
bio
preferences
```

The logical entity remains the same, but frequently and infrequently accessed columns are separated.

---

# 18. Partition Pruning

Partition pruning is the process by which the database determines which partitions cannot contain the required rows and avoids scanning them.

Example:

Partitions:

```text
2024
2025
2026
```

Query:

```sql
WHERE created_at >= '2026-01-01'
```

The database can potentially ignore:

```text
2024
2025
```

and scan only:

```text
2026
```

## Key interview statement

> Partitioning improves query performance when the optimizer can eliminate partitions using predicates on the partition key.

---

# 19. Choosing a Partition Key

A good partition key generally:

- Aligns with common query predicates
- Has a sensible distribution
- Supports data lifecycle operations
- Avoids excessive partition counts

Bad example:

```text
Partition by customer_id
```

if there are tens of millions of customers.

That could imply an impractical number of partitions.

Better choices depend on workload:

- Date/time
- Region
- Tenant
- Hash buckets
- Other bounded/highly useful dimensions

---

# 20. Partitioning Is Also an Operational Tool

Partitioning is not just a query optimization technique.

For example:

```text
orders_2023
orders_2024
orders_2025
orders_2026
```

If 2023 is no longer hot data, it can be archived/dropped as a unit rather than deleting billions of rows individually.

Other benefits can include:

- Smaller per-partition indexes
- Easier maintenance
- Easier archival
- Better lifecycle management
- Potentially more manageable backups

---

# 21. Local vs Global Indexes

Suppose:

```text
Orders
  ├── January partition
  ├── February partition
  └── March partition
```

## Local index

Each partition has its own index.

```text
January
   ↓
January Index

February
   ↓
February Index

March
   ↓
March Index
```

The indexes are independent.

### Benefits

- Smaller indexes
- Easier maintenance
- Partition can be dropped with its index
- Good fit for partition-pruning workloads

### Drawback

A query spanning many partitions may need to search multiple indexes.

---

# 22. Global Index

A global index spans multiple/all partitions.

Conceptually:

```text
January ─┐
February ├──→ Global Index
March ───┘
```

A lookup such as:

```sql
WHERE customer_id = 101
```

can potentially use one index spanning all partitions.

### Benefits

- Efficient for queries that don't filter on the partition key
- One logical index for cross-partition lookups

### Drawbacks

Partition maintenance becomes more expensive.

If a partition is dropped:

```text
January
```

the global index contains entries that reference January rows, so the global index must also be maintained.

---

# 23. Local vs Global Index — Interview Summary

| | Local Index | Global Index |
|---|---|---|
| Scope | Individual partition | Multiple/all partitions |
| Size | Smaller | Larger |
| Partition maintenance | Easier | More expensive |
| Drop partition | Usually straightforward | Requires index maintenance |
| Cross-partition lookup | Multiple indexes may be searched | One global index |
| Best fit | Partition-key-oriented workload | Cross-partition lookup workload |

### Easy mental model

> **Partitioning splits the table. Local indexes split with the table. Global indexes do not.**

---

# 24. Mock Interview Questions Covered

## Database / Query Optimization

1. Why would you denormalize a database?
2. What are the benefits and drawbacks of denormalization?
3. How do you keep duplicated data consistent?
4. How do you optimize a query joining four large tables?
5. What happens if a table grows to 5 billion rows?
6. What happens when a query is still slow despite a composite index?
7. What is index fragmentation?
8. How do you detect/fix index fragmentation?
9. When would you avoid foreign keys?
10. What is the difference between application-enforced and database-enforced referential integrity?

## Partitioning

11. What is database partitioning?
12. Horizontal vs vertical partitioning?
13. What is partition pruning?
14. How do you choose a partition key?
15. Does partitioning automatically make queries faster?
16. What happens if a query does not contain a predicate on the partition key?
17. Why partition by date?
18. Can you partition by customer ID?
19. What operational problems does partitioning solve?

## Sharding

20. When would you shard?
21. Why use customer ID as a shard key?
22. How does a shard lookup service work?
23. Hashing vs lookup-based routing?
24. What happens when a new shard is added?
25. What are hot shards?
26. What happens to cross-shard queries?

## Indexes

27. Are two single-column indexes equivalent to one composite index?
28. Why use `(customer_id, created_at)`?
29. What is the significance of `DESC` in an index?
30. What is a covering/index-only scan?
31. What causes index fragmentation?
32. Local vs global indexes?

---

# 25. Interview Mental Model

A useful optimization progression is:

```text
Understand Business Requirement
              ↓
Understand Query Pattern
              ↓
EXPLAIN / EXPLAIN ANALYZE
              ↓
Optimize Query
              ↓
Select Only Required Columns
              ↓
Correct Indexes
              ↓
Statistics / I/O / Locks
              ↓
Caching / Read Replicas
              ↓
Denormalization / Materialized Views
              ↓
Partitioning / Archiving
              ↓
Sharding
```

The important principle is:

> **Measure first, then optimize the actual bottleneck.**

Do not jump immediately to partitioning or sharding just because a table is large.

---

# 26. Personal Interview Notes / Key Corrections

A few recurring improvements from the discussion:

### Instead of:

> "The database will scan all 2 billion rows."

Say:

> "If there is no suitable index, the optimizer may choose a full table scan. I would verify this with EXPLAIN."

### Instead of:

> "Billions of rows means we need partitioning."

Say:

> "The size alone doesn't determine the solution. I'd identify whether the bottleneck is reads, writes, storage, maintenance, or data lifecycle."

### Instead of:

> "Partitioning by date will make customer queries faster."

Say:

> "Date partitioning helps customer queries only if the query can use the date predicate for partition pruning. Otherwise multiple partitions may still need to be searched."

### Instead of:

> "Foreign keys aren't needed when referential integrity isn't required."

Say:

> "The business may still require referential integrity; the question is whether it is enforced by the database or by the application/system."

### Instead of:

> "The latest partition will be searched first."

Say:

> "Without a predicate on the partition key, I would not assume partition pruning. Exact behavior depends on the database optimizer."

---

# 27. Recommended Senior-Level Answer Pattern

For database performance questions, use:

```text
1. Clarify the business/query requirement
2. Inspect EXPLAIN / EXPLAIN ANALYZE
3. Check query shape
4. Check indexes
5. Check statistics
6. Check I/O and locks
7. Reduce data returned
8. Consider caching/read replicas
9. Consider denormalization/materialized views
10. Consider partitioning/archiving
11. Consider sharding only when a single DB cannot scale sufficiently
```

This demonstrates systematic problem solving rather than simply listing database technologies.
