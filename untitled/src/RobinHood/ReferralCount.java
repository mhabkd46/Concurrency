package RobinHood;

import java.util.*;

// Robinhood is famous for its referral program. It’s exciting to see our users spreading the word across their friends and family. One thing that is interesting about the program is the network effect it creates. We would like to build a dashboard to track the status of the program. Specifically, we would like to learn about how people refer others through the chain of referral.

// For the purpose of this question, we consider that a person refers all other people down the referral chain. For example, A refers B, C, and D in a referral chain of A -> B -> C -> D. Please build a leaderboard for the top 3 users who have the most referred users along with the referral count.

// Referral rules:

// A user can only be referred once.
// Once the user is on the RH platform, he/she cannot be referred by other users. For example: if A refers B, no other user can refer A or B since both of them are on the RH platform.
// Referrals in the input will appear in the order they were made.
// Leaderboard rules:

// The user must have at least 1 referral count to be on the leaderboard.
// The leaderboard contains at most 3 users.
// The list should be sorted by the referral count in descending order.
// If there are users with the same referral count, break the ties by the alphabetical order of the user name.
// Input

// rh_users = ["A", "B", "C"]
// | | |
// v v v
// new_users = ["B", "C", "D"]
// Output

// ["A 3", "B 2", "C 1"]
// [execution time limit] 3 seconds (java)

// [memory limit] 1 GB

// [input] array.string rh_users

// A list of referring users.

// [input] array.string new_users

// A list of user that was referred by the users in the referrers array with the same order.

// [output] array.string

// An array of 3 users on the leaderboard. Each of the element here would have the "[user] [referral count]" format. For example, "A 4".

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
