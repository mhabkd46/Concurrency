package CommandDrivenInMemoryDB;

public interface Command {
    boolean perform();

}
interface ReversibleCommand extends Command {
    boolean reverse();
}


class SetCommand implements ReversibleCommand {
    private String key;
    private String value;
    private Database database;
    private String prevValue;

    public SetCommand(Database database, String key, String value) {
        this.database = database;
        this.key = key;
        this.value = value;
        this.prevValue = database.cache.get(this.key);
    }
    public boolean perform() {
        String currentValue = database.cache.get(this.key);
        if (currentValue != null) {
            database.valueFrequency.put(currentValue, Math.max(0, database.valueFrequency.getOrDefault(currentValue, 0) - 1));
        }
        database.cache.put(this.key, this.value);
        database.valueFrequency.put(this.value, Math.max(0, database.valueFrequency.getOrDefault(this.value, 0) + 1));
        return true;
    }

    public boolean reverse() {
        if (prevValue == null) {
            database.cache.remove(key);
            database.valueFrequency.put(value, Math.max(0, database.valueFrequency.getOrDefault(value, 0) - 1));
        }
        else {
            database.cache.put(key, prevValue);
            database.valueFrequency.put(this.prevValue, Math.max(0, database.valueFrequency.getOrDefault(this.prevValue, 0) + 1));
            database.valueFrequency.put(value, Math.max(0, database.valueFrequency.getOrDefault(value, 0) - 1));
        }
        return true;
    }

    @Override
    public String toString() {
        return "SET COMMAND - KEY: " + key + " ; VALUE: " + value;
    }
}

class GetCommand implements Command {
    private String key;
    private Database database;

    public GetCommand(Database database, String key) {
        this.key = key;
        this.database = database;
    }

    public boolean perform() {
        String value = database.cache.get(this.key);
        if (value == null) {
            System.out.println("Key does not exist in the DB");
        }
        System.out.println("GET: " + this.key + ": " + value);
        return true;
    }

    public String toString() {
        return "GET COMMAND - KEY: " + key;
    }
}

class CountCommand implements Command {
    private String value;
    private Database database;
    public CountCommand(Database database, String value) {
        this.value = value;
        this.database = database;
    }

    public boolean perform() {
        Integer freq = database.valueFrequency.get(this.value);
        System.out.println("COUNT: " + this.value + ": " + freq);
        return true;
    }

    public String toString() {
        return "COUNT COMMAND - VALUE: " + value;
    }
}

class BeginCommand implements Command{
    private Database database;
    public BeginCommand(Database database) {
        this.database = database;
    }
    public boolean perform() {
        database.transactions.add(new Transaction());
        return true;
    }

    public String toString() {
        return "BEGIN COMMAND";
    }
}

class RollbackCommand implements Command{
    private Database database;
    public RollbackCommand(Database database) {
        this.database = database;
    }

    public boolean perform() {
        if (database.transactions.isEmpty()) {
            System.out.println("NO TRANSACTION");
            return false;
        }

        Transaction lastTransaction = database.transactions.removeLast();

        for (int i = lastTransaction.getCommands().size() - 1; i >= 0; i--) {
            Command command = lastTransaction.getCommands().get(i);
            if (command instanceof ReversibleCommand) {
                ((ReversibleCommand)command).reverse();
            }
        }

        return true;
    }

    public String toString() {
        return "ROLLBACK COMMAND";
    }
}

class CommitCommand implements Command{
    private Database database;
    public CommitCommand(Database database) {
        this.database = database;
    }

    public boolean perform() {
        if (database.transactions.isEmpty()) {
            System.out.println("NO TRANSACTION");
            return false;
        }

        Transaction lastTransaction = database.transactions.removeLast();
        if (!database.transactions.isEmpty()) {
            database.transactions.getLast().addAllOperation(lastTransaction.getCommands());
        }

        return true;
    }

    public String toString() {
        return "COMMIT COMMAND";
    }
}

class DeleteCommand implements ReversibleCommand {
    private Database database;
    private String key;
    private String prevValue;
    public DeleteCommand(Database database, String key) {
        this.key = key;
        this.database = database;
        this.prevValue = database.cache.get(key);
    }

    public boolean perform() {
        if (!database.cache.containsKey(key)) {
            System.out.println("Unable to delete key as the key does not exist");
            return false;
        }
        String currentValue = database.cache.get(this.key);
        if (currentValue != null) {
            database.valueFrequency.put(currentValue, Math.max(0, database.valueFrequency.getOrDefault(currentValue, 0) - 1));
        }
        database.cache.remove(this.key);
        return true;
    }

    public boolean reverse() {
        database.cache.put(key, prevValue);
        database.valueFrequency.put(prevValue, Math.max(0, database.valueFrequency.getOrDefault(prevValue, 0) + 1));

        return true;
    }

    public String toString() {
        return "DELETE COMMAND - KEY: " + key;
    }
}