package CommandDrivenInMemoryDB;

import java.util.HashMap;

public class CommandOrchestrator {
    private Database database;
    public CommandOrchestrator(Database database) {
        this.database = database;
    }
    public void performCommand(Command command) {
        if (command instanceof SetCommand) {
            boolean didPerform = command.perform();
            if (didPerform && !database.transactions.isEmpty()) {
                database.transactions.getLast().addOperation(command);
            }
        }
        else if (command instanceof GetCommand) {
            command.perform();
        }
        else if (command instanceof DeleteCommand) {
            boolean didPerform = command.perform();
            if (didPerform && !database.transactions.isEmpty()) {
                database.transactions.getLast().addOperation(command);
            }
        }
        else if (command instanceof CountCommand) {
            command.perform();
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
}
