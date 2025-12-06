package RobinHood;

// /*
// You are building an application that consists of many different services that can depend on each other. One of these services is the entrypoint which receives user requests and then makes requests to each of its dependencies, which will in turn call each of their dependencies and so on before returning.
// Given a directed acyclic graph that contains these dependencies, you are tasked with determining the "load factor" for each of these services to handle this load. The load factor of a service is defined as the number of units of load it will receive if the entrypoint receives a 1 unit of load. Note that we are interested in the worst case capacity. For a given downstream service, its load factor is the number of units of load it is required to handle if all upstream services made simultaneous requests. For example, in the following dependency graph where A is the entrypoint:
// Each query to A will generate one query to B which will pass it on to C and from there to D. A will also generate a query to C which will pass it on to D, so the worst case (maximum) load factors for each service is A:1, B:1, C:2, D:2.
// (Important: make sure you've fully understood the above example before proceeding!)


// Problem Details
// service_list: An array of strings of format service_name=dependency1,dependency2. Dependencies can be blank (e.g. dashboard=) and non-existent dependency references should be ignored (e.g. prices=users,foobar and foobar is not a service defined in the graph). Each service is defined only once in the graph.
// entrypoint: An arbitrary service that is guaranteed to exist within the graph
// Output: A list of all services depended by (and including) entrypoint as an array of strings with the format service_name*load_factor sorted by service name.
// Example
// Input:
// service_list = ["logging=",
// "user=logging",
// "orders=user,foobar",
// "recommendations=user,orders",
// "dashboard=user,orders,recommendations"]
// entrypoint = "dashboard"

// A -> C, B -> C, D -> C => C:3

// {db:[user,orders,recom], user:[logging], orders:[user, foobar], recom:[user,orders]}
// [db] {db:1}
// [user,orders,recom] {db:1, user:1, orders:1, recom:1}
// [logging, user, foobar, user, orders] {db:1, logging:1, user:3, foobar:1, order:1}

// // [dashboard] -> db:1
// // [user, orders, recom] -> db:1, user:1, orders:1, recom:1
// // [logging, user, foobar, user, orders] -> db:1, user:3, orders:2, recom:1, logging:1

// Output (note sorted by service name)
// ["dashboard*1",
// "logging*4",
// "orders*2",
// "recommendations*1",
// "user*4"]
// [execution time limit] 3 seconds (cs)
// [input] array.string service_list
// [input] string entrypoint
// [output] array.string
// */


// T(n) = nlogn S(n) = n n: the number of service dependencies (ENlogN)


import java.util.*;

public class LoadFactor {
    public static String[] getLoadFactor(String[] serviceList, String entryPoint) {
        // build graph
        Map<String, List<String>> graph = new HashMap<>();
        // relation: "orders=user,foobar"
        for (String relation : serviceList) {
            String from = relation.split("=")[0];

            graph.putIfAbsent(from, new ArrayList<>());

            // "logging=", no child, but still need to be in map("exist")
            if(relation.split("=").length > 1) {
                String to = relation.split("=")[1];
                for (String s : to.split(",")) {
                    graph.get(from).add(s);
                }
            }

        }

        // sort by key
        TreeMap<String, Integer> loadMap = new TreeMap<>();
        loadMap.put(entryPoint, 1);

        // bfs
        Queue<String> queue = new LinkedList<>();
        queue.offer(entryPoint);

        while (!queue.isEmpty()) {
            String cur = queue.poll();
            // end "to" points dont have adj
            if(graph.containsKey(cur)) {
                for (String adj : graph.get(cur)) {
                    loadMap.put(adj, loadMap.getOrDefault(adj, 0) + 1);
                    queue.offer(adj);
                }
            }
        }

        // return in "logging*4" format
        String[] res = new String[graph.size()];
        int i = 0;
        for(String service : loadMap.keySet()) {
            if (graph.containsKey(service)) { // need to filter out non-exist nodes like "foobar"
                res[i] = service + "*" + loadMap.get(service);
                i++;
            }
        }

        return res;
    }
}
