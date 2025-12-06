package RobinHood;

import java.util.*;
// Represents one security's data
class Security {
    double curPrice;
    double futurePrice;
    double maxShares;
    double roi; // return on investment

    Security(double curPrice, double futurePrice, double maxShares) {
        this.curPrice = curPrice;
        this.futurePrice = futurePrice;
        this.maxShares = maxShares;
        this.roi = futurePrice / curPrice;
    }
}

public class PortfolioOptimization {

    public double portValOptimization(int initialFund, List<List<String>> securities) {
        // A list that holds all the profitable securities
        List<Security> list = new ArrayList<>();

        // Parse input and filter only profitable securities
        for (List<String> sec : securities) {
            double curPrice = Double.parseDouble(sec.get(1));
            double futurePrice = Double.parseDouble(sec.get(2));
            double maxShares = Double.parseDouble(sec.get(3));

            // profitable
            if (futurePrice > curPrice) {
                list.add(new Security(curPrice, futurePrice, maxShares));
            }
        }

        // Sort by ROI in desc
        // this will warn!!! list.sort((a, b) -> (b.roi - a.roi));
        list.sort((a, b) -> Double.compare(b.roi, a.roi));

        // Greedily purchase shares with available funds
        double totalFutureValue = 0;
        double remainingFund = initialFund;

        for (Security sec : list) {
            double affordableShares = remainingFund / sec.curPrice;

            if (affordableShares >= sec.maxShares) {
                // Buy full allowed shares
                totalFutureValue += sec.maxShares * sec.futurePrice;
                remainingFund -= sec.maxShares * sec.curPrice;
            } else {
                // Buy as many fractional shares as we can afford
                totalFutureValue += affordableShares * sec.futurePrice;
                return totalFutureValue;  // budget exhausted
            }
        }

        // Step 4: Add leftover cash as-is (assuming no future growth on cash)
        return totalFutureValue + remainingFund;
    }
}
