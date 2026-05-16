package CommandDrivenInMemoryDB;

import java.util.ArrayList;
import java.util.List;

public class Transaction {
    private List<Command> commands;

    public Transaction() {
        commands = new ArrayList<>();
    }

    public void addOperation(Command command) {
        this.commands.add(command);
    }

    public void addAllOperation(List<Command> commands) {
        this.commands.addAll(commands);
    }

    public List<Command> getCommands() {
        return this.commands;
    }
}