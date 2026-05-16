package CommandDrivenInMemoryDB;

import java.util.*;

public class CommandDrivenInMemoryDBMain {
    public static void main(String[] args) {
        Database database = new Database();
        CommandOrchestrator commandOrchestrator = new CommandOrchestrator(database);
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter commands --");

        while (sc.hasNextLine()) {
            String s = sc.nextLine();
            if (s.equals("EXIT")) {
                break;
            }
            Command command = parseToCommand(database, s);
            if (command != null) {
                commandOrchestrator.performCommand(command);
            }
        }
    }

    private static Command parseToCommand(Database database, String command) {
        String[] split = command.split(" ");
        if (split.length == 0) {
            System.out.println("Invalid Command Syntax");
            return null;
        }

        return switch (split[0]) {
            case "SET" -> {
                if (split.length != 3) {
                    System.out.println("Invalid Command Syntax");
                    yield null;
                }
                yield new SetCommand(database, split[1], split[2]);
            }
            case "GET" -> {
                if (split.length != 2) {
                    System.out.println("Invalid Command Syntax");
                    yield null;
                }
                yield new GetCommand(database, split[1]);
            }
            case "COUNT" -> {
                if (split.length != 2) {
                    System.out.println("Invalid Command Syntax");
                    yield null;
                }
                yield new CountCommand(database, split[1]);
            }
            case "DELETE" -> {
                if (split.length != 2) {
                    System.out.println("Invalid Command Syntax");
                    yield null;
                }
                yield new DeleteCommand(database, split[1]);
            }
            case "BEGIN" -> new BeginCommand(database);
            case "ROLLBACK" -> new RollbackCommand(database);
            case "COMMIT" -> new CommitCommand(database);
            default -> {
                System.out.println("Invalid command.. Ignoring");
                yield null;
            }
        };
    }
}
