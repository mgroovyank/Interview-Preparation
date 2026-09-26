// https://leetcode.com/problems/accounts-merge/

class Solution {
    public List<List<String>> accountsMerge(List<List<String>> accounts) {
        int n = accounts.size();
        DisjointSet ds = new DisjointSet(n);
        Map<String, Integer> emailToAccount = new HashMap<>();
        for(int i=0;i<n;i++){ // i= accountNumber
            List<String> account = accounts.get(i);
            String name = account.get(0);
            int numEmails = account.size()-1;
            for(int j=1;j<account.size();j++){
                String email = account.get(j);
                if(emailToAccount.containsKey(email)){
                    int prevAccount = emailToAccount.get(email);
                    ds.union(prevAccount, i);
                }else{
                    emailToAccount.put(email, i);
                }
            }
        }

        Map<Integer, List<String>> accountToEmails = new HashMap<>();

        for(Map.Entry<String, Integer> entry : emailToAccount.entrySet()){
            String email = entry.getKey();
            int account = entry.getValue();
            int parentAccount = ds.findParent(account);
            if(accountToEmails.containsKey(parentAccount)){
                List<String> emails = accountToEmails.get(parentAccount);
                emails.add(email);
            }else{
                List<String> emails = new ArrayList<>();
                emails.add(email);
                accountToEmails.put(parentAccount, emails);
            }
        }

        List<List<String>> mergedAccounts = new ArrayList<>();

        for(Map.Entry<Integer, List<String>> entry : accountToEmails.entrySet()){
            int account = entry.getKey();
            List<String> emails = entry.getValue();
            emails.sort(null);
            List<String> mergedEmails = new ArrayList<>();
            mergedEmails.add(accounts.get(account).get(0));//name
            mergedEmails.addAll(emails);
            mergedAccounts.add(mergedEmails);
        }

        return mergedAccounts;

    }

    class DisjointSet {
        int V;
        int[] parent;
        int[] rank;
        
        public DisjointSet(int V){
            this.V = V;
            this.parent = new int[V];
            for(int i=0;i<V;i++){
                this.parent[i] = i; // parent is itself
            }
            this.rank = new int[V]; // rank = 0
        }
        
        public void union(int u, int v){
            int pu = findParent(u);
            int pv = findParent(v);
            int ranku = this.rank[pu];
            int rankv = this.rank[pv];
            if(ranku == rankv || ranku > rankv){
                // attach pv to pu
                this.parent[pv] = pu;
                this.rank[pu]++;
            }else {
                // attach pu to pv
                this.parent[pu] = pv;
            }
        }
        
        public int findParent(int u){
            // base case
            if(u == this.parent[u]){
                return u;
            }
            return this.parent[u] = findParent(this.parent[u]); // patch compression
        }
    }
}
