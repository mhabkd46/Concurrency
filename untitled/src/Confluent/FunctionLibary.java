package Confluent;
import java.util.*;

class Function {
    String name;
    List<String> argumentTypes;
    boolean isVariadic;

    Function(String name, List<String> argumentTypes, boolean isVariadic) {
        this.name = name;
        this.argumentTypes = argumentTypes;
        this.isVariadic = isVariadic;
    }

    @Override
    public String toString() {
        return name;
    }
}

public class FunctionLibary {
    HashMap<String, List<Function>> nonVariadic;
    HashMap<String, List<Function>> variadic;

    public FunctionLibary() {
        this.nonVariadic = new HashMap<>();
        this.variadic = new HashMap<>();
    }

    public void register(Set<Function> functionSet) {
        // implement this method.66

        for (Function function: functionSet) {
            String key = createKey(function.argumentTypes, function.argumentTypes.size());
            if (function.isVariadic) {
                variadic.putIfAbsent(key, new LinkedList<>());
                variadic.get(key).add(function);
            }
            else{
                nonVariadic.putIfAbsent(key, new LinkedList<>());
                nonVariadic.get(key).add(function);
            }
        }
    }

    public List<Function> findMatches(List<String> argumentTypes) {
        // implement this method.
        List<Function> matches = new LinkedList<>();
        String key = createKey(argumentTypes, argumentTypes.size());

        if (nonVariadic.containsKey(key)) {
            matches.addAll(nonVariadic.get(key));
        }

        if (variadic.containsKey(key)) {
            matches.addAll(variadic.get(key));
        }

        int count = argumentTypes.size();
        for (int i = argumentTypes.size() - 2; i >= 0; i--) {
            if (argumentTypes.get(i).equals(argumentTypes.get(i + 1))) {
                count--;
            }
            else{
                break;
            }

            String k = createKey(argumentTypes, count);
            if (variadic.containsKey(k)) {
                matches.addAll(variadic.get(k));
            }
        }

        return matches;
    }

    private String createKey(List<String> argumentTypes, int n) {
        StringBuilder str = new StringBuilder();

        for (int i = 0; i < n; i++) {
            String argumentType = argumentTypes.get(i);
            str.append(argumentType);
            str.append('+');
        }

        return str.toString();
    }
}
