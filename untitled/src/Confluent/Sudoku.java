package Confluent;
import java.util.*;

public class Sudoku {
    private String[][] sudoku;
    private HashMap<Integer, Set<Integer>> rowSet;
    private HashMap<Integer, Set<Integer>> colSet;
    private HashMap<String, Set<Integer>> boxSet;

    public Sudoku(String[][] sudoku) {
        this.sudoku = sudoku;
        this.rowSet = new HashMap<>();
        this.colSet = new HashMap<>();
        this.boxSet = new HashMap<>();

        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                String key = Math.floorDiv(i, 3) + "-" + Math.floorDiv(j, 3);
                rowSet.putIfAbsent(i, new HashSet<>());

                colSet.putIfAbsent(j, new HashSet<>());

                boxSet.putIfAbsent(key, new HashSet<>());
            }
        }
    }

    public boolean validSudoku() {
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if (sudoku[i][j].equals(".")) continue;
                if (containInSets(i, j, sudoku[i][j])) return false;
                addToSets(i, j, sudoku[i][j]);
            }
        }

        return true;
    }

    public void solveSudoku() {

        boolean answer = backtrack(0, 0);
        if (answer) {
            display();
        }
        else {
            System.out.println("Invalid Sudoku");
        }

    }

    private boolean backtrack(int i, int j) {

        if (i == sudoku.length) return true;
        if (j == sudoku[0].length) {
            return backtrack(i + 1, 0);
        }

        if (!sudoku[i][j].equals(".")) {
            return backtrack(i , j + 1);
        }


        for (int x = 1; x <= 9; x++) {
            String xString = String.valueOf(x);
            if (!containInSets(i, j, xString)) {
                addToSets(i, j, xString);
                sudoku[i][j] = xString;
                boolean answer = backtrack(i, j + 1);
                if (answer) {
                    return true;
                }
                sudoku[i][j] = ".";
                removeFromSets(i, j, xString);
            }
        }
        return false;
    }

    private void addToSets(int i, int j, String c) {
        int element = Integer.parseInt(c);
        String key = Math.floorDiv(i, 3) + "-" + Math.floorDiv(j, 3);

        rowSet.get(i).add(element);
        colSet.get(j).add(element);
        boxSet.get(key).add(element);
    }

    private void removeFromSets(int i, int j, String c) {
        int element = Integer.parseInt(c);
        String key = Math.floorDiv(i, 3) + "-" + Math.floorDiv(j, 3);

        rowSet.get(i).remove(element);
        colSet.get(j).remove(element);
        boxSet.get(key).remove(element);
    }

    private boolean containInSets(int i, int j, String c) {
        int element = Integer.parseInt(c);
        String key = Math.floorDiv(i, 3) + "-" + Math.floorDiv(j, 3);

        return rowSet.get(i).contains(element) || colSet.get(j).contains(element) || boxSet.get(key).contains(element);
    }

    private void display() {
        for (int i = 0; i < 9; i++) {
            System.out.print("[");

            for (int j = 0; j < 9; j++) {
                if (j == 8) {
                    System.out.print(sudoku[i][j]);
                }
                else {
                    System.out.print(sudoku[i][j] + ",");
                }
            }

            System.out.print("]");
            System.out.println();
        }
    }
}
