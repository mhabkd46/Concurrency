package RobinHood;

// Given a stream of incoming "buy" and "sell" orders (as lists of limit price, quantity, and side, like
// ["155", "3", "buy"]), determine the total quantity (or number of "shares") executed.

// A "buy" order can be executed if there is a corresponding "sell" order with a price that is less than or
// equal to the price of the "buy" order.
// Similarly, a "sell" order can be executed if there is a corresponding "buy" order with a price that is
// greater than or equal to the price of the "sell" order.
// It is possible that an order does not execute immediately if it isn't paired to a counterparty. In that
// case, you should keep track of that order and execute it at a later time when a pairing order is found.
// You should ensure that orders are filled immediately at the best possible price. That is, an order
// should be executed when it is processed, if possible. Further, "buy" orders should execute at the
// lowest possible price and "sell" orders at the highest possible price at the time the order is handled.

// Note that orders can be partially executed.

// --- Sample Input ---

// orders = [
//   ['150', '5', 'buy'],    # Order A
//   ['190', '1', 'sell'],   # Order B
//   ['200', '1', 'sell'],   # Order C
//   ['100', '9', 'buy'],    # Order D
//   ['140', '8', 'sell'],   # Order E
//   ['210', '4', 'buy'],    # Order F
// ]

// Sample Output
// 9
// define

import java.util.*;

class Order{
    int price;
    int shares;
    // String operation; no need!

    Order(int price, int shares) {
        this.price = price;
        this.shares = shares;
    }
}

public class BuySellOrdersMatch {

    public static int calculateExecutedShares(List<List<String>> orders) {
        // min heap for sell orders
        PriorityQueue<Order> sellQueue = new PriorityQueue<>((a, b) -> (a.price - b.price));
        // max heap for buy orders
        PriorityQueue<Order> buyQueue = new PriorityQueue<>((a, b) -> (b.price - a.price));

        int total = 0;
        // ['150', '5', 'buy']
        for (List<String> list : orders) {
            int price = Integer.parseInt(list.get(0));
            int shares = Integer.parseInt(list.get(1));
            String operation = list.get(2);

            int sharesBefore = shares;
            if (operation.equals("buy")) {
                // find all sell orders
                while (shares > 0 && !sellQueue.isEmpty()) {
                    Order sellOrder = sellQueue.peek();
                    if (price >= sellOrder.price) {
                        // matched amount, take min
                        int count = Math.min(shares, sellOrder.shares);
                        shares -= count;
                        sellOrder.shares -= count;
                        if (sellOrder.shares == 0) {
                            sellQueue.poll(); // sold all
                        }
                    } else {
                        break; // needed!!!
                    }
                }
                if (shares > 0) {
                    buyQueue.offer(new Order(price, shares));
                }
            } else if (operation.equals("sell")) {
                while (shares > 0 && !buyQueue.isEmpty()) {
                    Order buyOrder = buyQueue.peek();
                    if (price <= buyOrder.price) {
                        int count = Math.min(shares, buyOrder.shares);
                        shares -= count;
                        buyOrder.shares -= count;
                        if (buyOrder.shares == 0) {
                            buyQueue.poll();
                        }
                    } else {
                        break;
                    }
                }
                if (shares > 0) {
                    sellQueue.offer(new Order(price, shares));
                }
            }

            total += sharesBefore - shares;
        }

        return total;
    }

}
