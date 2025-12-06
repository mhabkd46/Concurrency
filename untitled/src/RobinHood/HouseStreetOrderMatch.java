package RobinHood;
import java.util.*;

public class HouseStreetOrderMatch {
    // A trade is defined as a string containing the 4 properties given below separated by commas:

    //     Symbol (4 alphabetical characters, left-padded by spaces)
    //     Side (either "B" or "S" for buy or sell)
    //     Quantity (4 digits, left-padded by zeros)
    //     ID (6 alphanumeric characters)
    //     e.g.
    //     "AAPL,B,0100,ABC123"

    //     which represents a trade of a buy of 100 shares of AAPL with ID "ABC123"

    //     Given two lists of trades - called "house" and "street" trades, write code to create groups of matches between trades and return a list of unmatched house and street trades sorted alphabetically. Without any matching, the output list would contain all elements of both house and street trades. There are many ways to match trades, the first and most important way is an exact match:

    //     An exact match is a pair of trades containing exactly one house trade and exactly one street trade with identical symbol, side, quantity, and ID
    //     Note: Trades are distinct but not unique

    //     For example, given the following input:
    //     house_trades:
    //     [ "AAPL,B,0100,ABC123", "AAPL,B,0100,ABC123", "GOOG,S,0050,CDC333" ]
    //     street_trades:
    //     [ " FB,B,0100,GBGGGG", "AAPL,B,0100,ABC123" ]
    //     We would expect the following output:
    //     [ " FB,B,0100,GBGGGG", "AAPL,B,0100,ABC123", "GOOG,S,0050,CDC333" ]

    //     Because the first (or second) house trade and second street trade form an exact match, leaving behind three unmatched trades.

    public List<String> exactTradeMatch(List<String> house, List<String> street) {
        Map<String, Integer> houseFreq = getOrderFreq(house);
        Map<String, Integer> streetFreq = getOrderFreq(street);

        // Remove exact matches
        for (String h : houseFreq.keySet()) {
            if (streetFreq.containsKey(h)) {
                // take min, is matched
                int matched = Math.min(houseFreq.get(h), streetFreq.get(h));
                houseFreq.put(h, houseFreq.get(h) - matched);
                streetFreq.put(h, streetFreq.get(h) - matched);
            }
        }

        // collecting unmatched, add to res
        List<String> unmatched = new ArrayList<>();
        for (String s : houseFreq.keySet()) {
            for (int i = 0; i < houseFreq.get(s); i++) {
                unmatched.add(s); // repeat freq times
            }
        }
        for (String s : streetFreq.keySet()) {
            for (int i = 0; i < streetFreq.get(s); i++) {
                unmatched.add(s); // repeat freq times
            }
        }
        // sort alphabetically
        Collections.sort(unmatched);

        return unmatched;
    }

    // calculate frequency of order [xx,xx,xx]
    public static Map<String, Integer> getOrderFreq(List<String> list) {
        Map<String, Integer> map = new HashMap<>();

        for (String s : list) {
            map.put(s, map.getOrDefault(s, 0) + 1);
        }
        return map;
    }


    // PART 2
    //  (same symbol + side + quantity)

    public List<String> getUnmatchTrades(List<String> house, List<String> street) {
        // Group trades by "symbol + quantity" into houseMap and streetMap
        Map<String, List<String>> houseMap = getTradeMap(house);
        Map<String, List<String>> streetMap = getTradeMap(street);

        // Step 1: Remove exact matches between house and street
        removeMatches(houseMap, streetMap, "exact");

        // Step 2: Remove attribute matches (same symbol + side + quantity) between house and street
        removeMatches(houseMap, streetMap, "attribute");

        // Step 3: Combine all unmatched trades from both maps
        List<String> unmatched = new ArrayList<>();
        for (List<String> trades : houseMap.values()) {
            unmatched.addAll(trades);
        }
        for (List<String> trades : streetMap.values()) {
            unmatched.addAll(trades);
        }

        // Return sorted unmatched trades
        Collections.sort(unmatched);
        return unmatched;
    }


    //* Groups trades into a map where each key is "<symbol> <side><quantity>",
    // * and the value is a list of full trade strings belonging to that key.
    //* This enables grouping trades with the same symbol and quantity.
    //*/
    private Map<String, List<String>> getTradeMap(List<String> trades) {
        Map<String, List<String>> tradeMap = new HashMap<>();
        for (String trade : trades) {
            String[] parts = trade.split(",", 4);  // symbol, side, quantity, id
            String symbol = parts[0];
            String side = parts[1];
            String quantity = parts[2];
            // Now grouping by all 3 fields: symbol + side + quantity
            String key = symbol + "," + side + "," + quantity;
            if (!tradeMap.containsKey(key)) {
                tradeMap.put(key, new ArrayList<>());
            }
            tradeMap.get(key).add(trade);
        }
        return tradeMap;
    }

    // /**
    /// * Performs matching between house and street trades based on match type:
    // * - "exact": match if the entire trade string matches
    /// * - "attribute": match if symbol + side + quantity are equal (ignore ID)
    // * Matched trades are removed from both house and street maps.
    // */
    private void removeMatches(Map<String, List<String>> houseMap,
                               Map<String, List<String>> streetMap,
                               String matchType) {
        // Loop through each group key in houseMap (e.g., "AAPL 0100")
        List<String> keys = new ArrayList<>(houseMap.keySet());
        Collections.sort(keys);  // For deterministic matching order

        for (String key : keys) {
            if (!streetMap.containsKey(key)) continue;

            List<String> hTrades = new ArrayList<>(houseMap.get(key));  // Copy to avoid modifying original while iterating
            List<String> sTrades = new ArrayList<>(streetMap.get(key));
            Collections.sort(hTrades);  // Lexical order priority
            Collections.sort(sTrades);

            // Store indices of trades that will be removed after matching
            List<Integer> hToRemove = new ArrayList<>();
            List<Integer> sToRemove = new ArrayList<>();

            // Try to match each house trade with a street trade
            for (int i = 0; i < hTrades.size(); i++) {
                String hTrade = hTrades.get(i);
                String[] hParts = hTrade.split(",", 4);

                for (int j = 0; j < sTrades.size(); j++) {
                    if (sToRemove.contains(j)) continue;  // Already matched

                    String sTrade = sTrades.get(j);
                    String[] sParts = sTrade.split(",", 4);

                    if ("exact".equals(matchType)) {
                        // Exact match: full string must be equal
                        if (hTrade.equals(sTrade)) {
                            hToRemove.add(i);
                            sToRemove.add(j);
                            break;
                        }
                    } else if ("attribute".equals(matchType)) {
                        // Attribute match: symbol + side + quantity must match (ignore ID)
                        if (hParts[0].equals(sParts[0]) &&
                                hParts[1].equals(sParts[1]) &&
                                hParts[2].equals(sParts[2])) {
                            hToRemove.add(i);
                            sToRemove.add(j);
                            break;
                        }
                    }
                }
            }

            // Update the maps to remove matched trades, keep only unmatched
            houseMap.put(key, getRemaining(hTrades, hToRemove));
            streetMap.put(key, getRemaining(sTrades, sToRemove));
        }
    }

    /**
     * Given a list of trades and a list of indices to remove,
     * returns a new list containing only the trades that were not removed.
     */
    private List<String> getRemaining(List<String> trades, List<Integer> toRemove) {
        List<String> remaining = new ArrayList<>();
        for (int i = 0; i < trades.size(); i++) {
            if (!toRemove.contains(i)) {
                remaining.add(trades.get(i));
            }
        }
        return remaining;
    }

    // PART 3??????????????????????????????????


}
