package RobinHood;
import java.util.*;

import static RobinHood.LoadFactor.getLoadFactor;
import static RobinHood.OffsetCommit.getCommits;

public class Main {
    public static void main(String[] args) {
        CandleSticksTests();
        ReferralCountTests();
        FractionalSystemTests();
    }

    private static void CandleSticksTests() {
        CandleSticks candleSticks = new CandleSticks();
        String input = "1:0,3:10,2:12,4:19,5:35";

        System.out.println(candleSticks.candleStick(input));
    }

    private static void ReferralCountTests() {
        ReferralCount referralCount = new ReferralCount();
        String[] answer = referralCount.referralCount(new String[]{"A", "B", "C"}, new String[]{"B", "C", "D"});

        for (String a: answer) {
            System.out.println(a);
        }
    }

    private static void FractionalSystemTests() {
        List<List<String>> orders = new ArrayList<>();
        orders.add(Arrays.asList("AAPL", "B", "$42", "100"));

        List<List<String>> inventory = new ArrayList<>();
        inventory.add(Arrays.asList("AAPL", "50"));

        FractionalSystem fractionalSystem = new FractionalSystem();

        List<List<String>> result = fractionalSystem.fractionInvent(orders, inventory);
        
        for (List<String> answer: result) {
            System.out.println(answer.get(0) + " " + answer.get(1));
        }

    }

    private static void LoadFactorTests() {
        // foobar will not be in the res. it is "non-existence"(didnt appear before =)
        String[] service_list = {"logging=",
                "user=logging",
                "orders=user,foobar",
                "recommendations=user,orders",
                "dashboard=user,orders,recommendations"};

        String entry = "dashboard";

        String[] res = getLoadFactor(service_list, entry);

        for (String s : res) {
            System.out.println(s);
        }

        String[] test2 = new String[]{
                "A=B",
                "B=C",
                "C=D",
                "A=C",
                "D="
        };
        String entrypoint2 = "A";

        String[] res2 = getLoadFactor(test2, entrypoint2);

        for (String s : res2) {
            System.out.println(s);
        }
    }


    private static void OffsetCommitTests() {
        // int[] input = {2,0,1};
        int[] input = {0,2};
        // int[] input = {0,1,2};
        // int[] input = {5,4,2,0,1};
        // int[] input = {2,1,0,5,4};
        // int[] input = {1,2,0,5,4};
        // int[] input = {0,7,1,2,6,3,5,4};
        // int[] input = {3,1,2,0};
        // int[] input = {5,4,3,2,1};
        // int[] input = {0, 1, 3, 4};
        // int[] input = {0, 2, 1, 4, 3};

        for (int x : getCommits(input)) {
            System.out.print(x + " ");
        }

    }
}
