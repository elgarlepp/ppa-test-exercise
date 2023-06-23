package ppa.lepp.elgar.test.exercise.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class AdditionTest {
    @Test
    public void testPositiveNumbers() {
        Addition addition = new Addition(111, 456);

        assertEquals(111, addition.getAddable1());
        assertEquals(456, addition.getAddable2());
        assertEquals(567, addition.getSum());
    }

    @Test
    public void testNegativeNumbers() {
        Addition addition = new Addition(-12, -90);

        assertEquals(-12, addition.getAddable1());
        assertEquals(-90, addition.getAddable2());
        assertEquals(-102, addition.getSum());
    }

    @Test
    public void testOneNegativeAndOnePositiveNumber() {
        Addition addition = new Addition(-80, 100);

        assertEquals(-80, addition.getAddable1());
        assertEquals(100, addition.getAddable2());
        assertEquals(20, addition.getSum());
    }
}
