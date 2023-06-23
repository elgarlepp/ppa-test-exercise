package ppa.lepp.elgar.test.exercise.util;

import ppa.lepp.elgar.test.exercise.model.Addition;

import java.util.Comparator;

public class AdditionComparator implements Comparator<Addition> {
    private SortDirection direction;

    public AdditionComparator(SortDirection direction) {
        this.direction = direction;
    }

    @Override
    public int compare(Addition addition1, Addition addition2) {
        if(addition1.getSum() == addition2.getSum()) {
            return 0;
        }

        int multiplier = direction.equals(SortDirection.DESCENDING) ? -1 : 1;
        return (addition1.getSum() > addition2.getSum() ? 1 : -1) * multiplier;
    }

    @Override
    public boolean equals(Object obj) {
        return ((AdditionComparator) obj).direction.equals(direction);
    }
}
