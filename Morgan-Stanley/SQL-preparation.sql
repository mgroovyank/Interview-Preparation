select sysdate from dual;
select to_char(sysdate, 'dd') from dual;
select to_char(sysdate, 'MM') from dual;
select to_char(sysdate, 'MON') from dual;
select to_char(sysdate, 'yy') from dual;
select to_char(sysdate, 'yyyy') from dual;

select extract(MONTH from sysdate);
select extract(DAY from sysdate);
select extract(YEAR from sysdate);

SELECT c.name
FROM customers c
INNER JOIN orders o ON o.customer_id = c.id
WHERE o.orderDate > TO_DATE('01-JAN-23', 'DD-MON-RR');

select e.employee_id, mgr.employee_id, mgr.employee_name from employees e 
left outer JOIN employees mgr
on e.manager_id = mgr.employee_id;

select c.customer_name, NVL(od.order_amount, 0), NVL(od.payment_amount, 0) from customers c
left outer join 
(
    select o.customer_id, sum(o.order_amount) as order_amount,
    sum(p.payment_amount) as payment_amount
    from orders o left join payments p on o.order_id = p.order_id
    where EXTRACT(YEAR FROM o.order_date) = 2023
    group by o.customer_id
) od
on c.customer_id = od.customer_id;

select c.customer_name, sum(order_amount), sum(payment_amount) from orders o 
left join customers c
on o.customer_id = c.customer_id
left join payments p
on o.order_id=p.order_id
where EXTRACT(YEAR FROM o.order_date) = 2023
group by c.customer_name;

select book_id from books b
minus
select book_id from borrowed where
borrow_date > ADD_MONTHS(SYSDATE, -6);

select b.book_id, b.book_name from books b
left join borrowed bo
on b.book_id = bo.book_id
where bo.borrow_date is NULL OR
 bo.borrow_date < ADD_MONTHS(SYSDATE, -6);

select  d.department_id, sum(s.salary) from departments d
left join employees e
on d.department_id = e.department_id
left join salaries s
on e.employee_id = s.employee_id
where s.effective_date = (select max(effective_date) from salaries where employee_id=e.employee_id)
group by d.department_id;

select d.department_id, sum(s.salary) from departments d 
left join employees e
on d.department_id = e.department_id
left join (
    select employee_id, salary from
    (select employee_id, salary, 
    ROW_NUMBER() OVER (PARTITION BY employee_id order by effective_date desc) as rn
    from salaries) 
    where rn = 1
) s
on e.employee_id = s.employee_id
group by d.department_id;

select p.product_id, count(*) from products p
inner join
orders o
on p.product_id = o.product_id
group by p.product_id
having count(*)>5;

select * from products where product_id 
in (select product_id from orders group by product_id having sum(quantity) > 5);

select p.product_id from products p inner join
orders o
on p.product_id = o.product_id
group by p.product_id
having sum(o.quantity)>5;

SELECT e.*
FROM employees e
WHERE e.salary > (
    SELECT AVG(e2.salary)
    FROM employees e2
    WHERE e2.department_id = e.department_id
);

select salesperson_id, sale_amount, sale_date from 
(select salesperson_id, sale_amount, sale_date,
ROW_NUMBER() OVER (PARTITION BY salesperson_id order by sale_amount desc) as rn from sales)
where rn=1;

select salesperson_id, sale_amount, sale_date, 
RANK() OVER (PARTITION BY salesperson_id order by sale_amount desc) as rnk from 
sales;

select *, AVG(amount) over (order by sale_date rows 6 preceding) as moving_avg from
daily_sales;   

select student_id, student_name, score,
CASE 
    WHEN score > 80 THEN 'High'
    WHEN score BETWEEN 50 AND 79 THEN 'Medium'
    ELSE 'Low'
    END as score_category
 from students;

