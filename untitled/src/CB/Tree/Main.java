package CB.Tree;

import java.util.*;

public class Main {
    public static void main(String[] args) {
        List<Tree> treeList = generateTrees(10);
        Set<Tree> treeSet = new HashSet<>(treeList);

        for (Tree tree: treeList) {
            System.out.println(tree);
        }
        System.out.println("=======================================");
        for (Tree tree: treeSet) {
            System.out.println(tree);
        }
    }

    private static List<Tree> generateTrees(int size) {
        List<Tree> treeList = new ArrayList<>();
        Random random = new Random();
        for(int i = 0; i < size; i++) {
            Length randomGeneratedLength = Length.values()[random.nextInt(Length.values().length)];
            Color randomGeneratedColor = Color.values()[random.nextInt(Color.values().length)];

            Tree randomGeneratedTree = new Tree(randomGeneratedLength, randomGeneratedColor);
            treeList.add(randomGeneratedTree);
        }

        return treeList;
    }
}
