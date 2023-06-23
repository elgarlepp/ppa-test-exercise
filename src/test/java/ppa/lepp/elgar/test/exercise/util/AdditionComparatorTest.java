package ppa.lepp.elgar.test.exercise.util;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ppa.lepp.elgar.test.exercise.model.Addition;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class AdditionComparatorTest {

    private final int RANDOM_SEED = 123456;

    private List<Addition> additions = new ArrayList<>();
    private List<Integer> sums = new ArrayList<>();

    @BeforeEach
    public void createunsortedLists() {
        Random generator = new Random(RANDOM_SEED);
        additions.clear();
        sums.clear();

        for(int i = 0; i < 1000; i++) {
            Addition addition = new Addition(generator.nextInt(), generator.nextInt());
            additions.add(addition);
            sums.add(addition.getSum());
        }
    }

    @Test
    public void testAscendingSort() {
        additions = additions.stream()
                .sorted(new AdditionComparator(SortDirection.ASCENDING))
                .toList();
        Collections.sort(sums);

        for(int i = 0; i < additions.size(); i++) {
            assertEquals(sums.get(i), additions.get(i).getSum());
        }
    }

    @Test
    public void testDescendingSort() {
        additions = additions.stream()
                .sorted(new AdditionComparator(SortDirection.DESCENDING))
                .toList();
        Collections.sort(sums);
        Collections.reverse(sums);

        for(int i = 0; i < additions.size(); i++) {
            assertEquals(sums.get(i), additions.get(i).getSum());
        }
    }
}
