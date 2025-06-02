package RobinHood;
import java.util.*;

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
}
