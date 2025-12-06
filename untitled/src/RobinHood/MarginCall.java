package RobinHood;
import java.util.*;
public class MarginCall {

    //     Our goal is to build a simplified version of a real Robinhood system that reads a customer's trades from a stream, maintains what they own, and rectifies running out of cash (through a process called a "margin call", which we'll define later). We’re looking for clean code, good naming, testing, etc. We're not particularly looking for the most performant solution.

    //     **Step 1 (tests 1-4): Parse trades and build a customer portfolio**

    //     Your input will be a list of trades, each of which is itself a list of strings in the form [timestamp, symbol, B/S (for buy/sell), quantity, price], e.g.

    //     [["1", "AAPL", "B", "10", "10"], ["3", "GOOG", "B", "20", "5"], ["10", "AAPL", "S", "5", "15"]]

    //     is equivalent to buying 10 shares (i.e. units) of AAPL for 5 each at timestamp 3, and selling 5 shares of AAPL for $15 at timestamp 10.

    //     **Input assumptions:**

    //     - The input is sorted by timestamp
    //     - All numerical values are nonnegative integers
    //     - Trades will always be valid (i.e. a customer will never sell more of a stock than they own).

    //     From the provided list of trades, our goal is to maintain the customer's resulting portfolio (meaning everything they own), **assuming they begin with $1000**. For instance, in the above example, the customer would end up with $875, 5 shares of AAPL, and 20 shares of GOOG. You should return a list representing this portfolio, formatting each individual position as a list of strings in the form [symbol, quantity], using 'CASH' as the symbol for cash and sorting the remaining stocks alphabetically based on symbol. For instance, the above portfolio would be represented as

    //     [["CASH", "875"], ["AAPL", "5"], ["GOOG", "20"]]
    // PART 1:  maintain the customer's resulting portfolio
    public static List<List<String>> buildPortfolio(List<List<String>> trades) {
        int cash = 1000;
        // user's holding, sort alphabetically
        TreeMap<String, Integer> holdings = new TreeMap<>();

        for (List<String> trade : trades) {
            String symbol = trade.get(1);
            String operation = trade.get(2);
            int quantity = Integer.parseInt(trade.get(3));
            int price = Integer.parseInt(trade.get(4));

            if (operation.equals("B")) {
                holdings.put(symbol, holdings.getOrDefault(symbol, 0) + quantity);
                cash -= price * quantity;
            } else {
                holdings.put(symbol, holdings.get(symbol) - quantity);
                cash += price * quantity;
            }
        }

        // build result
        List<List<String>> res = new ArrayList<>();
        res.add(new ArrayList<>(Arrays.asList("CASH", cash + "")));
        for (String s : holdings.keySet()) {
            int quantity = holdings.get(s);
            // APPL, 0 - 不显示
            if (quantity != 0) {
                res.add(new ArrayList<>(Arrays.asList(s, quantity + "")));
            }
        }

        return res;
    }

    //    **Step 2 (tests 5-7): Margin calls**

    //     If the customer ever ends up with a negative amount of cash **after a buy**, they then enter a process known as a **margin call** to correct the situation. In this process, we forcefully sell stocks in the customer's portfolio (sometimes including the shares we just bought) until their cash becomes non-negative again.

    //     We sell shares from the most expensive to least expensive shares (based on each symbol's most-recently-traded price) with ties broken by preferring the alphabetically earliest symbol. Assume we're able to sell any number of shares in a symbol at that symbol's most-recently-traded price.

    //     For example, for this input:

    //     ```
    //     [["1", "AAPL", "B", "10", "100"],
    //     ["2", "AAPL", "S", "2", "80"],
    //     ["3", "GOOG", "B", "15", "20"]]

    //     ```

    //     The customer would be left with 8 AAPL shares, 15 GOOG shares, and 80 a share) to cover the deficit. Afterwards, they would have 6 shares of AAPL, 15 shares of GOOG, and a cash balance of $20.

    //     The expected output would be

    //     [["CASH", "20"], ["AAPL", "6"], ["GOOG", "15"]]

    // PART 2: margin call
    public static List<List<String>> buildPortfolio2(List<List<String>> trades) {
        int cash = 1000;

        // sort alphabetically
        TreeMap<String, Integer> holdings = new TreeMap<>();

        // recent trading price
        Map<String, Integer> lastPrice = new HashMap<>();

        for (List<String> trade : trades) {
            String symbol = trade.get(1);
            String operation = trade.get(2);
            int quantity = Integer.parseInt(trade.get(3));
            int price = Integer.parseInt(trade.get(4));

            // update recent price
            lastPrice.put(symbol, price);

            if (operation.equals("B")) {
                holdings.put(symbol, holdings.getOrDefault(symbol, 0) + quantity);
                cash -= price * quantity;

                if (cash < 0) {
                    // determine which stock to sell to cover the gap
                    List<String> symbols = new ArrayList<>(holdings.keySet());

                    // sorting logic 1.most exp recent price 2. tiebreak-alphabetic
                    symbols.sort((a, b) -> {
                        if (lastPrice.get(a) != lastPrice.get(b)) {
                            return lastPrice.get(b) - lastPrice.get(a);
                        } else {
                            return a.compareTo(b);
                        }
                    });

                    // chose the ones to sell
                    for (String s : symbols) {
                        int qty = holdings.get(s);
                        int p = lastPrice.get(s);

                        while (qty > 0 && cash < 0) {
                            cash += p;
                            qty--;
                        }

                        if (qty == 0) {
                            holdings.remove(s);
                        } else {
                            holdings.put(s, qty);
                        }

                        if (cash >= 0) {
                            break;
                        }
                    }

                }
            } else { // remain same
                holdings.put(symbol, holdings.get(symbol) - quantity);
                cash += price * quantity;
            }
        }

        // build result, remain same
        List<List<String>> res = new ArrayList<>();
        res.add(new ArrayList<>(Arrays.asList("CASH", cash + "")));
        for (String s : holdings.keySet()) {
            int quantity = holdings.get(s);
            // APPL, 0 - 不显示
            if (quantity != 0) {
                res.add(new ArrayList<>(Arrays.asList(s, quantity + "")));
            }
        }

        return res;
    }

    // PART 3 ????????????????????????????????

}
