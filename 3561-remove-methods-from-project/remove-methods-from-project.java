class Solution {

    public List<Integer> remainingMethods(int n, int k, int[][] invocations) {

        // Step 1: Create Graph
        List<Integer>[] graph = new ArrayList[n];

        for (int i = 0; i < n; i++) {
            graph[i] = new ArrayList<>();
        }

        // Step 2: Store all edges in graph
        for (int i = 0; i < invocations.length; i++) {

            int from = invocations[i][0];
            int to = invocations[i][1];

            graph[from].add(to);
        }

        // Step 3: Mark suspicious methods
        boolean[] suspicious = new boolean[n];

        dfs(k, graph, suspicious);

        // Step 4: Check if any normal method calls a suspicious method
        for (int i = 0; i < invocations.length; i++) {

            int from = invocations[i][0];
            int to = invocations[i][1];

            if (!suspicious[from] && suspicious[to]) {

                List<Integer> ans = new ArrayList<>();

                for (int j = 0; j < n; j++) {
                    ans.add(j);
                }

                return ans;
            }
        }

        // Step 5: Return only non-suspicious methods
        List<Integer> ans = new ArrayList<>();

        for (int i = 0; i < n; i++) {

            if (!suspicious[i]) {
                ans.add(i);
            }
        }

        return ans;
    }

    public void dfs(int node, List<Integer>[] graph, boolean[] suspicious) {

        suspicious[node] = true;

        for (int next : graph[node]) {

            if (!suspicious[next]) {

                dfs(next, graph, suspicious);

            }
        }
    }
}