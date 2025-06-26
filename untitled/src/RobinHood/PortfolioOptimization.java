package RobinHood;

import java.util.*;
class Security {
    int currentPrice;
    int futurePrice;
    int maxShares;
    int roi;

    public Security(int currentPrice, int futurePrice, int maxShares) {
        this.currentPrice = currentPrice;
        this.futurePrice = futurePrice;
        this.maxShares = maxShares;
        this.roi = futurePrice - currentPrice;
    }
}

public class PortfolioOptimization {

    public int getOptimizedPortfolio(int initialFund, List<List<String>> securitiesString) {
        List<Security> securities = new ArrayList<>();

        return 0;
    }
}
