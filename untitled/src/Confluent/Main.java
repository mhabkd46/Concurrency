package Confluent;

import java.util.*;

public class Main {
    public static void main(String[] args) {
    //    searchPhraseTestCases();
//        singleWildCardMatchingTestCases();
//        functionLibraryTests();
        // tailTestCase();
//        solveSudokuTests();
        quickSortTests();
    }


    public static void tailTestCase() {
        Tail tail = new Tail();
        String answer = tail.getLastNLines(1);
        System.out.println("Answer - "  + answer);
    }


    public static void searchPhraseTestCases() {
        SearchPhrase searchPhrase = new SearchPhrase();

        searchPhrase.addDocument(1, "Cloud computing is the on-demand availability of computer system resources");
        searchPhrase.addDocument(2, "One integrated service for metrics uptime cloud monitoring dashboards and alerts reduces time spent navigating between systems");
        searchPhrase.addDocument(3, "Monitor entire cloud infrastructure, whether in the cloud computing is or in virtualized data centers");


        System.out.print("cloud -- ");
        searchPhrase.search("cloud").forEach(x -> System.out.print(x + ", "));
        System.out.println();
        System.out.print("cloud monitoring -- ");
        searchPhrase.search("cloud monitoring").forEach(x -> System.out.print(x + ", "));
        System.out.println();
        System.out.print("Cloud computing is -- ");
        searchPhrase.search("Cloud computing is").forEach(x -> System.out.print(x + ", "));
        System.out.println();
        System.out.print("yolo -- ");
        searchPhrase.search("yolo").forEach(x -> System.out.print(x + ", "));
        System.out.println();
        System.out.print("entire cloud infrastructure whether -- ");
        searchPhrase.search("entire cloud infrastructure whether").forEach(x -> System.out.print(x + ", "));
        System.out.println();
        System.out.print("Monitor entire cloud infrastructure, whether in the cloud computing is or in virtualized data centers -- ");
        searchPhrase.search("Monitor entire cloud infrastructure, whether in the cloud computing is or in virtualized data centers").forEach(x -> System.out.print(x + ", "));
        System.out.println();
        System.out.print(",,,, -- ");
        searchPhrase.search(",,,,").forEach(x -> System.out.print(x + ", "));
        System.out.println();
        System.out.print("cloud clout-- ");
        searchPhrase.search("cloud clout").forEach(x -> System.out.print(x + ", "));
        System.out.println();
    }
    public static void singleWildCardMatchingTestCases() {
        SingleWildCardMatching singleWildCardMatching = new SingleWildCardMatching();

        System.out.println("cat , c*t - " + singleWildCardMatching.isMatch("cat", "c*t"));
        System.out.println("cat , c* - " + singleWildCardMatching.isMatch("cat", "c*"));
        System.out.println("cat , *t - " + singleWildCardMatching.isMatch("cat", "*t"));
        System.out.println("dog , c*t - " + singleWildCardMatching.isMatch("dog", "c*t"));
        System.out.println("cat , tac* - " + singleWildCardMatching.isMatch("cat", "tac*"));
    }
    public static void functionLibraryTests() {
        FunctionLibary functionLibary = new FunctionLibary();

        HashSet<Function> functions = new HashSet<>();
        functions.add(new Function("FuncA", Arrays.asList("String", "Integer", "Integer"), false));
        functions.add(new Function("FuncB", Arrays.asList("String", "Integer"), true));
        functions.add(new Function("FuncC", Arrays.asList("Integer"), true));
        functions.add(new Function("FuncD", Arrays.asList("Integer", "Integer"), true));
        functions.add(new Function("FuncE", Arrays.asList("Integer", "Integer", "Integer"), false));
        functions.add(new Function("FuncF", Arrays.asList("String"), false));
        functions.add(new Function("FuncG", Arrays.asList("Integer"), false));

        functionLibary.register(functions);

        System.out.println(functionLibary.findMatches(Arrays.asList("String")));
        System.out.println(functionLibary.findMatches(Arrays.asList("Integer")));
        System.out.println(functionLibary.findMatches(Arrays.asList("Integer", "Integer", "Integer", "Integer")));
        System.out.println(functionLibary.findMatches(Arrays.asList("Integer", "Integer", "Integer")));
        System.out.println(functionLibary.findMatches(Arrays.asList("String", "Integer", "Integer", "Integer")));
        System.out.println(functionLibary.findMatches(Arrays.asList("String", "Integer", "Integer")));
    }
    public static void validSudokuTests() {
        String[][] input1 = {{"5","3",".",".","7",".",".",".","."}
                ,{"6",".",".","1","9","5",".",".","."}
                ,{".","9","8",".",".",".",".","6","."}
                ,{"8",".",".",".","6",".",".",".","3"}
                ,{"4",".",".","8",".","3",".",".","1"}
                ,{"7",".",".",".","2",".",".",".","6"}
                ,{".","6",".",".",".",".","2","8","."}
                ,{".",".",".","4","1","9",".",".","5"}
                ,{".",".",".",".","8",".",".","7","9"}};
        Sudoku sudoku1 = new Sudoku(input1);
        System.out.println(sudoku1.validSudoku());

        String[][] input2 = {{"8","3",".",".","7",".",".",".","."}
                ,{"6",".",".","1","9","5",".",".","."}
                ,{".","9","8",".",".",".",".","6","."}
                ,{"8",".",".",".","6",".",".",".","3"}
                ,{"4",".",".","8",".","3",".",".","1"}
                ,{"7",".",".",".","2",".",".",".","6"}
                ,{".","6",".",".",".",".","2","8","."}
                ,{".",".",".","4","1","9",".",".","5"}
                ,{".",".",".",".","8",".",".","7","9"}};
        Sudoku sudoku2 = new Sudoku(input2);
        System.out.println(sudoku2.validSudoku());

        String[][] input3 = {{"8","3",".",".","7",".",".",".","."},{"6",".",".","1","9","5",".",".","."},{".","9","8",".",".",".",".","6","."},{"8",".",".",".","6",".",".",".","3"},{"4",".",".","8",".","3",".",".","1"},{"7",".",".",".","2",".",".",".","6"},{".","6",".",".",".",".","2","8","."},{".",".",".","4","1","9",".",".","5"},{".",".",".",".","8",".",".","7","9"}};
        Sudoku sudoku3 = new Sudoku(input3);
        System.out.println(sudoku3.validSudoku());
    }

    public static void solveSudokuTests() {
        String[][] input1 = {{"5","3",".",".","7",".",".",".","."},{"6",".",".","1","9","5",".",".","."},{".","9","8",".",".",".",".","6","."},{"8",".",".",".","6",".",".",".","3"},{"4",".",".","8",".","3",".",".","1"},{"7",".",".",".","2",".",".",".","6"},{".","6",".",".",".",".","2","8","."},{".",".",".","4","1","9",".",".","5"},{".",".",".",".","8",".",".","7","9"}};
        Sudoku sudoku1 = new Sudoku(input1);
        sudoku1.solveSudoku();
    }

    public static void quickSortTests() {
        QuickSort quickSort = new QuickSort();
        int[] nums = new int[10];
        Random random = new Random();
        System.out.println("Before - ");
        for (int i = 0; i < nums.length; i++) {
            nums[i] = random.nextInt(100);
            System.out.print(nums[i] + ",");
        }

        System.out.println();
        quickSort.quickSort(nums, 0, nums.length - 1);

        System.out.println("After");
        for (int i = 0; i < nums.length; i++) {
            System.out.print(nums[i] + ",");
        }
    }
}
