package RobinHood;

import java.util.*;

public class CourseScheduleMid {

    // ** PART 1
    // Each course at our university has at most one prerequisite that must be taken first.
    // No two courses share a prerequisite. There is only one path through the program.
    // Write a function that takes a list of (prerequisite, course) pairs,
    //  and returns the name  of the course that the student will be taking when they are halfway through their program.
    //  (If a track has an even number of courses, and therefore has two "middle" courses,   you should return the first one.)
    public static String findMiddleCourse(List<List<String>> relations) {
        Map<String, String> map = new HashMap<>();
        // stores "to" courses that has prerequisits
        Set<String> dest = new HashSet<>();

        for (List<String> r : relations) {
            map.put(r.get(0), r.get(1));
            dest.add(r.get(1));
        }

        // a->b b->c c->e e->z
        // find start, which is the course with 0 prereq
        String start = "";
        for (List<String> r : relations) {
            if (!dest.contains(r.get(0))) {
                start = r.get(0);
                break;
            }
        }

        int totalCourses = map.size() + 1;
        int midCourseIndex = (totalCourses - 1) / 2;

        // a-b-c   a-b-c-d
        String cur = start;
        for (int i = 0; i < midCourseIndex; i++) {
            cur = map.get(cur);
        }

        return cur;
    }

    // PART 2:
    // Not a linked list, a graph.
    // multiple track, multiple path, multiple start course, return all the 'middle' courses

    public static List<String> findAllMiddleCourses(List<List<String>> relations) {
        Map<String, List<String>> graph = new HashMap<>();
        Map<String, Integer> inDegree = new HashMap<>();

        for (List<String> r : relations) {
            String from = r.get(0), to = r.get(1);
            graph.putIfAbsent(from, new ArrayList<>());
            graph.get(from).add(to);
            inDegree.put(to, inDegree.getOrDefault(to, 0) + 1);
            inDegree.putIfAbsent(from, 0); // make sure add this
        }

        Set<String> res = new HashSet<>();
        // find source nodes, indegree = 0
        for (String s : inDegree.keySet()) {
            if (inDegree.get(s) == 0) {
                // s is a start course, do dfs
                dfs1(graph, new ArrayList<>(), s, res);
            }
        }
        return new ArrayList<>(res);
    }

    // res: no redundant
    public static void dfs1(Map<String, List<String>> graph, List<String> path, String cur, Set<String> res) {
        path.add(cur);

        // cur is the end node
        if (!graph.containsKey(cur)) {
            String mid = path.get((path.size() - 1) / 2);
            res.add(mid);
            return;
        }

        // copied a new path
        for (String adj : graph.get(cur)) {
            dfs1(graph, new ArrayList<>(path), adj, res);
        }

    }

    // PART 3
    // finds the longest path and returns the course in the middle of that path
    public String findLongestPathMiddleCourse(List<List<String>> pairs) {
        // Build the graph (adjacency list) and compute in-degrees.
        Map<String, List<String>> graph = new HashMap<>();
        Map<String, Integer> indegree = new HashMap<>();


        for (List<String> pair : pairs) {
            String from = pair.get(0);
            String to = pair.get(1);
            graph.computeIfAbsent(from, k -> new ArrayList<>()).add(to);
            indegree.put(to, indegree.getOrDefault(to, 0) + 1);
            indegree.putIfAbsent(from, 0);
        }

        // Identify source nodes (with zero in-degree).
        List<String> sources = new ArrayList<>();
        for (String node : indegree.keySet()) {
            if (indegree.getOrDefault(node, 0) == 0) {
                sources.add(node);
            }
        }

        // Use memoization to store the longest path starting from a node.
        Map<String, List<String>> map = new HashMap<>();

        // Find the longest path among all sources.
        List<String> longestPath = new ArrayList<>();
        for (String src : sources) {
            List<String> path = dfs2(src, graph, map);
            if (path.size() > longestPath.size()) {
                longestPath = path;
            }
        }
        // Determine the middle course.
        int n = longestPath.size();
        int midIndex;
        if (n % 2 == 1) {
            midIndex = n / 2;
        } else {
            midIndex = n / 2 - 1;
        }
        return longestPath.get(midIndex);
    }

    // memoization is used to cache the longest path starting from each node, ensuring that each node’s result is computed only once. For each node:

    // If it has no children (i.e., it’s a sink), the DFS returns a list containing just that node.
    // Otherwise, the DFS computes the longest path among its children and prepends the current node to this path, effectively building the longest path from that node onward.

    // dfs
    private List<String> dfs2(String node, Map<String, List<String>> graph, Map<String, List<String>> map) {
        if (map.containsKey(node)) {
            return map.get(node);
        }
        // If no children, the longest path is just [node].
        if (!graph.containsKey(node) || graph.get(node).isEmpty()) {
            List<String> list = new ArrayList<>();
            list.add(node);
            map.put(node, list);
            return list;
        }

        List<String> maxPath = new ArrayList<>();
        for (String neighbor : graph.get(node)) {
            List<String> path = dfs2(neighbor, graph, map);
            if (path.size() > maxPath.size()) {
                maxPath = path;
            }
        }
        List<String> result = new ArrayList<>();
        result.add(node);
        result.addAll(maxPath);
        map.put(node, result);
        return result;
    }

}
