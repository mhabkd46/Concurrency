package RobinHood;

import java.util.*;

public class FractionalSystem {

    // [["AAPL","B","42","100"]]
    public List<List<String>> fractionInvent(List<List<String>> orders, List<List<String>> inventory) {
        HashMap<String, Integer> inventoryMap = new HashMap<>();

        for (List<String> item: inventory) {
            String symbol = item.get(0);
            int quantity = Integer.parseInt(item.get(1));
            inventoryMap.put(symbol, inventoryMap.getOrDefault(symbol, 0) + quantity);
        }

        for (List<String> order: orders) {
            String symbol = order.get(0);
            String action = order.get(1);
            String quantity = order.get(2);
            int price = Integer.parseInt(order.get(3));

            int orderShares = 0;
            if (quantity.startsWith("$")) {
                quantity = quantity.substring(1);
                orderShares = Integer.parseInt(quantity) * 100/price;
            }   
            else {
                orderShares = Integer.parseInt(quantity);
            }
            
            int currentShares = inventoryMap.getOrDefault(symbol, 0);
            if (action.equals("B")) {
                int sharesInInventory = currentShares;
                if (sharesInInventory >= orderShares) {
                    sharesInInventory = sharesInInventory - orderShares;
                }
                else{
                    int sharesToBuy = 100 * (int) Math.ceil((orderShares - sharesInInventory)/100);
                    sharesInInventory = sharesInInventory + sharesToBuy - orderShares;
                }

                inventoryMap.put(symbol, sharesInInventory);
            }
            else{
                int newOrderShares = (currentShares + orderShares) % 100;
                inventoryMap.put(symbol, newOrderShares);
            }

        }
        
        List<List<String>> result = new ArrayList<>();
        for (List<String> item: inventory) {
            String symbol = item.get(0);
            Integer quantity = inventoryMap.getOrDefault(symbol, 0);
            if (quantity != 0) {
                List<String> answer=  new ArrayList<>();
                answer.add(symbol);
                answer.add(quantity.toString());
                result.add(answer);
            }
            
        }

        return result;
    }    
}
