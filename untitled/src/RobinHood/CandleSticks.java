package RobinHood;
import java.util.*;

public class CandleSticks {
     
    public String candleStick(String input) {
        Map<Integer, ArrayList<Integer>> intervalMap = new HashMap<>();
        Map<Integer, int[]> bucketMinMax = new HashMap<>();
        
        populateIntervalAndBucketMap(intervalMap, bucketMinMax, input);

        List<int[]> aggregated = getAggregatedValues(intervalMap, bucketMinMax);

        return buildStringFromAggregated(aggregated);
    }

    private String buildStringFromAggregated(List<int[]> aggregated) {
        StringBuilder result = new StringBuilder(); 

        for (int[] data: aggregated) {
            result.append("{");
            result.append(data[0]);
            for (int i = 1; i < data.length; i++) {
                result.append(",").append(data[i]);
            }
            result.append("}");
        }

        return result.toString();
    }

    private void populateIntervalAndBucketMap(Map<Integer, ArrayList<Integer>> intervalMap, Map<Integer, int[]> bucketMinMax,  String input) {
        String[] intervals = input.split(",");

        for (String interval: intervals) {
            String[] intervalSplit = interval.split(":");
            int price = Integer.parseInt(intervalSplit[0]);
            int timeStamp = Integer.parseInt(intervalSplit[1]);
            int bucketKey = timeStamp/10;

            intervalMap.putIfAbsent(bucketKey, new ArrayList<>());
            intervalMap.get(bucketKey).add(price);

            bucketMinMax.putIfAbsent(bucketKey, new int[]{Integer.MAX_VALUE, Integer.MIN_VALUE});

            if (bucketMinMax.get(bucketKey)[0] > price) {
                bucketMinMax.put(bucketKey, new int[]{price, bucketMinMax.get(bucketKey)[1]});
            }

            if (bucketMinMax.get(bucketKey)[1] < price) {
                bucketMinMax.put(bucketKey, new int[]{bucketMinMax.get(bucketKey)[0], price});
            }
        }
    }

    private List<int[]>  getAggregatedValues(Map<Integer, ArrayList<Integer>> intervalMap, Map<Integer, int[]> bucketMinMax) {
        int lastBucket = Integer.MIN_VALUE;

        for (int key: intervalMap.keySet()) {
            lastBucket = Math.max(lastBucket, key);
        }


        List<int[]> aggregated = new ArrayList<>();

        for (int start = 0; start < lastBucket + 1; start ++) {
            if (!intervalMap.containsKey(start) && aggregated.size() == 0) {
                continue;
            }

            if (!intervalMap.containsKey(start)) {
                int[] prev = aggregated.get(aggregated.size() - 1);
                int prevLastValue = prev[2];
                aggregated.add(new int[] {start * 10, prevLastValue, prevLastValue, prevLastValue, prevLastValue});
            }
            else{
                ArrayList<Integer> data = intervalMap.get(start);
                aggregated.add(new int[] {start * 10, data.get(0), data.get(data.size() - 1), bucketMinMax.get(start)[0], bucketMinMax.get(start)[1]});
            }
        }

        return aggregated;
    }
}
