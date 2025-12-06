package RobinHood;

import java.util.*;

public class MaximumMultiplierPath {

    public int maxMultiplierProduct(int n, List<List<Integer>> edges, int start, int end) {
        // build graph
        Map<Integer, Map<Integer, Integer>> graph = new HashMap<>();

        for (List<Integer> edge : edges) {
            int from = edge.get(0);
            int to = edge.get(1);
            int weight = edge.get(2);

            if (!graph.containsKey(from)) {
                graph.put(from, new HashMap<>());
            }
            graph.get(from).put(to, weight);
        }

        // dfs to find max product
        // dfs tells 1. if we can find path from start to end
        // 2. if there is a path from start to end, return max prod
        int max = dfs(graph, start, end, new HashSet<>());

        return max == -1 ? -1 : max;
    }

    // returns max product of path going from cur
    public int dfs(Map<Integer, Map<Integer, Integer>> graph,
                   int cur, int end,
                   Set<Integer> visited) {

        if (cur == end) {
            return 1;
        }

        visited.add(cur);

        // Return -1 later if no valid path was found from curr to end
        int max = -1;

        // not reached leaf node
        if (graph.containsKey(cur)) {
            for (Map.Entry<Integer, Integer> entry : graph.get(cur).entrySet()) {
                int adj = entry.getKey();
                int weight = entry.getValue();

                // cant go back!
                if (visited.contains(adj)) {
                    continue;
                }

                int adjProduct = dfs(graph, adj, end, visited);
                // means there is a valid path was found from curr to end
                if (adjProduct != -1) {
                    max = Math.max(max, weight * adjProduct);
                }
            }
        }
        // backtrack
        visited.remove(cur);
        return max;
    }

}
