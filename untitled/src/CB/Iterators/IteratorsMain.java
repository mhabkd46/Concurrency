package CB.Iterators;

import java.util.Arrays;
import java.util.List;

interface Filter {
    boolean apply(Integer x);
}

interface CustomIterator {
    boolean hasNext();
    Object next();
}

class StepIterator implements CustomIterator {
    int start;
    int end;
    int step;

    public StepIterator(int start, int end, int step) {
        this.start = start;
        this.end = end;
        this.step = step;
    }

    @Override
    public boolean hasNext() {
        return start + step <= end;
    }

    @Override
    public Integer next() {
        start = start + step;
        return start;
    }
}

class IteratorWithFilter implements  CustomIterator {
    List<Integer> list;
    int index = 0;
    Filter filter;
    public IteratorWithFilter(List<Integer> list, Filter filter) {
        this.list = list;
        this.filter = filter;
    }

    @Override
    public boolean hasNext() {
        return hasNextFilter();
    }

    @Override
    public Integer next() {
        return getNextFilter();
    }

    private boolean hasNextFilter() {
        int i = index + 1;
        while (i < list.size()) {
            if (filter.apply(list.get(i))) return true;
            i++;
        }

        return false;
    }

    private Integer getNextFilter() {
        int i = index + 1;
        while (i < list.size()) {
            if (filter.apply(list.get(i))) {
                index = i;
                return list.get(i);
            }
            i++;
        }

        return -1;
    }
}
class BasicIterator implements CustomIterator {
    List<Integer> list;
    int index = 0;
    public BasicIterator(List<Integer> list) {
        this.list = list;
    }
    @Override
    public boolean hasNext() {
        return index < list.size();
    }

    @Override
    public Integer next() {
        return list.get(index++);
    }
}
class InterleavingIterator implements CustomIterator {

    List<CustomIterator> iterators;
    int index = 0;
    public InterleavingIterator(List<CustomIterator> iterators) {
        this.iterators = iterators;
    }
    @Override
    public boolean hasNext() {
        int i = index;
        while (i < iterators.size()) {
            if(iterators.get(i).hasNext()) {
                return true;
            }
            i++;
        }

        i = 0;
        while (i < index) {
            if(iterators.get(i).hasNext()) {
                return true;
            }
            i++;
        }
        return false;
    }


    @Override
    public Integer next() {
        int i = index;
        while (i < iterators.size()) {
            if(iterators.get(i).hasNext()) {
                index = i + 1;
                return (Integer) iterators.get(i).next();
            }
            i++;
        }

        i = 0;
        while (i < index) {
            if(iterators.get(i).hasNext()) {
                index = i + 1;
                return (Integer) iterators.get(i).next();
            }
            i++;
        }
        return -1;
    }
}
public class IteratorsMain {
    public static void main(String[] args) {
        List<Integer> l1 = Arrays.asList(1,2,3,4);
        List<Integer> l2 = Arrays.asList(5,6);
        List<Integer> l3 = Arrays.asList(101,102,103,104);
        List<Integer> l4 = Arrays.asList(105,106);
        Filter evenFilter = (Integer x) -> x%2 == 0;
        Filter oddFilter = (Integer x) -> x%2 != 0;

        CustomIterator basicIterator1 = new BasicIterator(l1);
        CustomIterator basicIterator2 = new BasicIterator(l2);
        CustomIterator basicIterator3 = new BasicIterator(l3);
        CustomIterator basicIterator4 = new BasicIterator(l4);

        CustomIterator stepIterator1 = new StepIterator(1,10, 2);

        CustomIterator interleavingIterator1 = new InterleavingIterator(Arrays.asList(basicIterator1, basicIterator2));
        CustomIterator interleavingIterator2 = new InterleavingIterator(Arrays.asList(basicIterator3, basicIterator4));
        CustomIterator interleavingIterator3 = new InterleavingIterator(Arrays.asList(interleavingIterator1, interleavingIterator2));

        while(stepIterator1.hasNext()){
            System.out.println(stepIterator1.next());
        }
        //1 , 5, 2, 6, 3, 4
        //101,105,102,106,103,104
    }
}
