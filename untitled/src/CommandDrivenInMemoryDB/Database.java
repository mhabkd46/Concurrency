package CommandDrivenInMemoryDB;

import java.util.HashMap;
import java.util.LinkedList;

public class Database {
    public HashMap<String, String> cache;
    public HashMap<String, Integer> valueFrequency;
    public LinkedList<Transaction> transactions;

    public Database() {
        this.cache = new HashMap<>();
        this.valueFrequency = new HashMap<>();
        this.transactions = new LinkedList<>();
    }

    public Database(HashMap<String, String> cache, HashMap<String, Integer> valueFrequency) {
        this.cache = cache;
        this.valueFrequency = valueFrequency;
        this.transactions = new LinkedList<>();
    }
}
