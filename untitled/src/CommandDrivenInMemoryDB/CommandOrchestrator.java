package CommandDrivenInMemoryDB;

import java.util.HashMap;

public class CommandOrchestrator {
    private Database database;
    public CommandOrchestrator(Database database) {
        this.database = database;
    }
    public void performCommand(Command command) {
        if (command instanceof SetCommand) {
            performSetCommand((SetCommand) command);
        }
        else if (command instanceof GetCommand) {
            performGetCommand((GetCommand) command);
        }
        else if (command instanceof DeleteCommand) {
            performDeleteCommand((DeleteCommand) command);
        }
        else if (command instanceof CountCommand) {
            performCountCommand((CountCommand) command);
        }
        else if (command instanceof BeginCommand) {
            command.perform();
        }
        else if (command instanceof RollbackCommand) {
            command.perform();
        }
        else if (command instanceof CommitCommand) {
            command.perform();
        }
    }

    private void performCountCommand(CountCommand command) {
        if (database.transactions.isEmpty()){
            command.perform();
        }
        else {
            HashMap<String, String> localCache = new HashMap<>(database.cache);
            HashMap<String, Integer> localValueFrequency = new HashMap<>(database.valueFrequency);
            Database localDatabase = new Database(localCache, localValueFrequency);

            for (Transaction transaction: database.transactions) {
                for (Command localCommand: transaction.getCommands()) {
                    System.out.println(localCommand);
                    if (localCommand instanceof DBCommand) {
                        ((DBCommand)localCommand).perform(localDatabase);
                    }

                }
            }

            Integer value = localDatabase.valueFrequency.get(command.getValue());
            System.out.println("COUNT: " + command.getValue() + ": " + value);
        }
    }

    private void performDeleteCommand(DeleteCommand command) {
        if (database.transactions.isEmpty()) {
            command.perform();
        }
        else {
            database.transactions.getLast().addOperation(command);
        }
    }

    private void performSetCommand(SetCommand command) {
        if (database.transactions.isEmpty()) {
            command.perform();
        }
        else {
            database.transactions.getLast().addOperation(command);
        }
    }

    private void performGetCommand(GetCommand command) {
        if (database.transactions.isEmpty()){
            command.perform();
        }
        else {
            HashMap<String, String>  localCache = new HashMap<>(database.cache);
            HashMap<String, Integer>  localValueFrequency = new HashMap<>(database.valueFrequency);
            Database localDatabase = new Database(localCache, localValueFrequency);

            for (Transaction transaction: database.transactions) {
                for (Command localCommand: transaction.getCommands()) {
                    System.out.println(localCommand);
                    if (localCommand instanceof DBCommand) {
                        ((DBCommand)localCommand).perform(localDatabase);
                    }
                }
            }

            String value = localDatabase.cache.get(command.getKey());
            System.out.println("GET: " + command.getKey() + ": " + value);
        }
    }
}
