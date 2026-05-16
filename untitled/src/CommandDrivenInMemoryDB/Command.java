package CommandDrivenInMemoryDB;

import java.util.List;

public interface Command {
    void perform();

}
interface DBCommand extends Command {
    void perform(Database database);
}


class SetCommand implements DBCommand{
    private String key;
    private String value;
    private Database database;

    public SetCommand(Database database, String key, String value) {
        this.database = database;
        this.key = key;
        this.value = value;
    }
    public void perform() {
        perform(this.database);
    }

    public void perform(Database database) {
        String currentValue = database.cache.get(this.key);
        if (currentValue != null) {
            database.valueFrequency.put(currentValue, Math.max(0, database.valueFrequency.getOrDefault(currentValue, 0) - 1));
        }
        database.cache.put(this.key, this.value);
        database.valueFrequency.put(this.value, Math.max(0, database.valueFrequency.getOrDefault(this.value, 0) + 1));
    }

    @Override
    public String toString() {
        return "SET COMMAND - KEY: " + key + " ; VALUE: " + value;
    }
}

class GetCommand implements DBCommand{
    private String key;
    private Database database;

    public GetCommand(Database database, String key) {
        this.key = key;
        this.database = database;
    }

    public String getKey() {
        return key;
    }

    public void perform() {
        perform(this.database);
    }

    public void perform(Database database) {
        String value = database.cache.get(this.key);
        if (value == null) {
            System.out.println("Key does not exist in the DB");
        }
        System.out.println("GET: " + this.key + ": " + value);
    }

    public String toString() {
        return "GET COMMAND - KEY: " + key;
    }
}

class CountCommand implements DBCommand{
    private String value;
    private Database database;
    public CountCommand(Database database, String value) {
        this.value = value;
        this.database = database;
    }

    public String getValue() {
        return value;
    }
    public void perform() {
        perform(database);
    }

    public void perform(Database database) {
        Integer freq = database.valueFrequency.get(this.value);
        System.out.println("COUNT: " + this.value + ": " + freq);
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
    public void perform() {
        database.transactions.add(new Transaction());
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

    public void perform() {
        if (database.transactions.isEmpty()) {
            System.out.println("NO TRANSACTION");
            return;
        }

        database.transactions.removeLast();
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

    public void perform() {
        if (database.transactions.isEmpty()) {
            System.out.println("NO TRANSACTION");
            return;
        }

        Transaction lastTransaction = database.transactions.removeLast();
        if (database.transactions.isEmpty()) {
            applyToDb(lastTransaction.getCommands());
        }
        else {
            database.transactions.getLast().addAllOperation(lastTransaction.getCommands());
        }
    }

    private void applyToDb(List<Command> commands) {
        for (Command command: commands) {
            command.perform();
        }
    }

    public String toString() {
        return "COMMIT COMMAND";
    }
}

class DeleteCommand implements DBCommand {
    private Database database;
    String key;
    public DeleteCommand(Database database, String key) {
        this.key = key;
        this.database = database;
    }

    public void perform() {
        perform(database);
    }

    public void perform(Database database) {
        String currentValue = database.cache.get(this.key);
        if (currentValue != null) {
            database.valueFrequency.put(currentValue, Math.max(0, database.valueFrequency.getOrDefault(currentValue, 0) - 1));
        }
        database.cache.remove(this.key);
    }

    public String toString() {
        return "DELETE COMMAND - KEY: " + key;
    }
}