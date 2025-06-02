package RobinHood;

import java.util.*;

public class ReferralCount {
    public String[] referralCount(String[] rh_users, String[] new_users) {
        Map<String, ArrayList<String>> graph = new HashMap<>();
        Set<String> existingUser = new HashSet<>();
        for (int i= 0; i < rh_users.length; i++) {
            
            if (!existingUser.contains(rh_users[i]) && !existingUser.contains(new_users[i])) {
                graph.putIfAbsent(rh_users[i], new ArrayList<>());  
                graph.get(rh_users[i]).add(new_users[i]);
            }
            
            existingUser.add(rh_users[i]);
            existingUser.add(new_users[i]);
            
        }

        Set<String> rh_users_set = graph.keySet();
        HashMap<String, Integer> counter = new HashMap<>();
        for (String user: rh_users) {
            if (counter.containsKey(user)) continue;
            dfs(graph, user, counter);
        }

        PriorityQueue<Map.Entry<String, Integer>> pq = new PriorityQueue<>((a, b) -> {
            if (a.getValue() == b.getValue()) {
                return a.getKey().compareTo(b.getKey());
            }

            return b.getValue().compareTo(a.getValue());
        });

        for (Map.Entry<String, Integer> entry: counter.entrySet()) {
            pq.offer(entry);
        }
        
        int k = 0;
        String[] result = new String[3];
        while (!pq.isEmpty() && k != 3) {
            Map.Entry<String, Integer> entry = pq.poll();
            if (!rh_users_set.contains(entry.getKey())) continue;
            result[k] = entry.getKey() + " " + entry.getValue();
            k++;
        }

        return result;
        
    }

    private int dfs(Map<String, ArrayList<String>> graph, String node, HashMap<String, Integer> counter) {
        if (!graph.containsKey(node)) return 0;

        int count = 0;

        for (String child: graph.get(node)){
            count += dfs(graph, child, counter);
        }
        
        counter.putIfAbsent(node, count + 1);

        return count + 1;


    }
}
