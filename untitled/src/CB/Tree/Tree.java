package CB.Tree;

import java.util.Objects;

enum Length {
    SHORT,
    MEDIUM,
    TALL
}

enum Color {
    GREEN,
    YELLOW,
    RED
}

public class Tree {
    Length length;
    Color color;

    public Tree(Length length, Color color) {
        this.color = color;
        this.length = length;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Tree tree = (Tree) o;
        return length == tree.length && color == tree.color;
    }

    @Override
    public int hashCode() {
        return Objects.hash(length, color);
    }

    @Override
    public String toString() {
        return "Tree{" +
                "length=" + length +
                ", color=" + color +
                '}';
    }
}

