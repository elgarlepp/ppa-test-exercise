package ppa.lepp.elgar.test.exercise.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ppa.lepp.elgar.test.exercise.model.Addition;
import ppa.lepp.elgar.test.exercise.util.SortDirection;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CalculationServiceTest {

    private CalculationService service;

    @BeforeEach
    public void createService() {
        service = new CalculationService();
    }

    @Test
    public void testAdd() {
        Addition addition = service.add(2, 5);

        assertEquals(2, addition.getAddable1());
        assertEquals(5, addition.getAddable2());
        assertEquals(7, addition.getSum());
    }

    @Test
    public void testAddEdgeCases() {
        Addition addition = service.add(0, 100);

        assertEquals(0, addition.getAddable1());
        assertEquals(100, addition.getAddable2());

        addition = service.add(100, 0);

        assertEquals(100, addition.getAddable1());
        assertEquals(0, addition.getAddable2());
    }

    @Test
    public void testAscendingList() {
        service.add(3, 4);
        service.add(1, 2);
        service.add(2, 3);

        List<Addition> additions = service.list(SortDirection.ASCENDING);
        assertEquals(3, additions.size());

        Addition addition = additions.get(0);
        assertEquals(1, addition.getAddable1());
        assertEquals(2, addition.getAddable2());
        assertEquals(3, addition.getSum());

        addition = additions.get(1);
        assertEquals(2, addition.getAddable1());
        assertEquals(3, addition.getAddable2());
        assertEquals(5, addition.getSum());

        addition = additions.get(2);
        assertEquals(3, addition.getAddable1());
        assertEquals(4, addition.getAddable2());
        assertEquals(7, addition.getSum());
    }

    @Test
    public void testDescendingList() {
        service.add(2, 1);
        service.add(6, 4);
        service.add(4, 3);

        List<Addition> additions = service.list(SortDirection.DESCENDING);
        assertEquals(3, additions.size());

        Addition addition = additions.get(0);
        assertEquals(6, addition.getAddable1());
        assertEquals(4, addition.getAddable2());
        assertEquals(10, addition.getSum());

        addition = additions.get(1);
        assertEquals(4, addition.getAddable1());
        assertEquals(3, addition.getAddable2());
        assertEquals(7, addition.getSum());

        addition = additions.get(2);
        assertEquals(2, addition.getAddable1());
        assertEquals(1, addition.getAddable2());
        assertEquals(3, addition.getSum());
    }

    @Test
    public void testAscendingSearch() {
        service.add(7, 5);
        service.add(10, 2);
        service.add(5, 1);
        service.add(2, 3);

        List<Addition> additions = service.search(SortDirection.ASCENDING, 5);
        assertEquals(3, additions.size());

        Addition addition = additions.get(0);
        assertEquals(2, addition.getAddable1());
        assertEquals(3, addition.getAddable2());
        assertEquals(5, addition.getSum());

        addition = additions.get(1);
        assertEquals(5, addition.getAddable1());
        assertEquals(1, addition.getAddable2());
        assertEquals(6, addition.getSum());

        addition = additions.get(2);
        assertEquals(7, addition.getAddable1());
        assertEquals(5, addition.getAddable2());
        assertEquals(12, addition.getSum());
    }

    @Test
    public void testDescendingSearch() {
        service.add(0, 19);
        service.add(5, 6);
        service.add(6, 1);
        service.add(3, 3);

        List<Addition> additions = service.search(SortDirection.DESCENDING, 6);
        assertEquals(3, additions.size());

        Addition addition = additions.get(0);
        assertEquals(5, addition.getAddable1());
        assertEquals(6, addition.getAddable2());
        assertEquals(11, addition.getSum());

        addition = additions.get(1);
        assertEquals(6, addition.getAddable1());
        assertEquals(1, addition.getAddable2());
        assertEquals(7, addition.getSum());

        addition = additions.get(2);
        assertEquals(3, addition.getAddable1());
        assertEquals(3, addition.getAddable2());
        assertEquals(6, addition.getSum());
    }

    @Test
    public void testSearchEdgeCases() {
        service.add(0, 8);
        service.add(7, 100);

        List<Addition> additions = service.search(SortDirection.ASCENDING, 0);
        Addition zeroAddition = additions.get(0);
        assertEquals(1, additions.size());
        assertEquals(0, zeroAddition.getAddable1());
        assertEquals(8, zeroAddition.getAddable2());

        additions = service.search(SortDirection.ASCENDING, 100);
        Addition hundredAddition = additions.get(0);
        assertEquals(1, additions.size());
        assertEquals(7, hundredAddition.getAddable1());
        assertEquals(100, hundredAddition.getAddable2());
    }
}
