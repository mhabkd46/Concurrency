package Rippling;
import java.util.*;

class Transaction {
    public HashMap<String, String> cache;

    public Transaction(HashMap<String, String> cache){
        this.cache = cache;
    }
}
public class KVStoreTransactions {

    private HashMap<String, String> globalCache;
    private LinkedList<Transaction> transactions;

    public KVStoreTransactions() {
        this.globalCache = new HashMap<>();
        this.transactions = new LinkedList<>();
    }

    public void begin() {
        HashMap<String, String> localCache = null;
        if (transactions.isEmpty()) {
            localCache = new HashMap<>(globalCache);
        }
        else{
            Transaction lastTransaction = transactions.getLast();
            localCache = new HashMap<>(lastTransaction.cache);
        }

        transactions.add(new Transaction(localCache));

    }

    public void commit() {
        Transaction lastTransaction = transactions.removeLast();
        HashMap<String, String> localCache = lastTransaction.cache;
        if (transactions.isEmpty()) {
            globalCache = new HashMap<>(localCache);
        }
        else {
            Transaction prevTransaction = transactions.getLast();
            prevTransaction.cache = new HashMap<>(localCache);
        }
    }

    public void rollback() {
        if (!transactions.isEmpty()) {
            transactions.removeLast();
        }
    }

    public String get(String key) {
        if (!transactions.isEmpty()) {
            Transaction lastTransaction = transactions.getLast();
            return lastTransaction.cache.get(key);
        }

        return globalCache.get(key);
    }

    public void set(String key, String value) {
        if (!transactions.isEmpty()) {
            Transaction lastTransaction = transactions.getLast();
            lastTransaction.cache.put(key, value);
            return;
        }

        globalCache.put(key, value);
    }

    public void delete(String key) {
        if (!transactions.isEmpty()) {
            Transaction lastTransaction = transactions.getLast();
            lastTransaction.cache.remove(key);
            return;
        }

        globalCache.remove(key);
    }

    public void display() {

    }
}
