package ppa.lepp.elgar.test.exercise.model;

public class Addition {
    private int addable1;

    private int addable2;

    private int sum;

    public Addition(int addable1, int addable2) {
        this.addable1 = addable1;
        this.addable2 = addable2;
        this.sum = addable1 + addable2;
    }

    public boolean contains(int value) {
        return addable1 == value ||
                addable2 == value ||
                sum == value;
    }

    public int getAddable1() {
        return addable1;
    }

    public int getAddable2() {
        return addable2;
    }

    public int getSum() {
        return sum;
    }
}
