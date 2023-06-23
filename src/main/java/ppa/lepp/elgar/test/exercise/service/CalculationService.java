package ppa.lepp.elgar.test.exercise.service;

import org.springframework.stereotype.Service;
import ppa.lepp.elgar.test.exercise.model.Addition;
import ppa.lepp.elgar.test.exercise.util.AdditionComparator;
import ppa.lepp.elgar.test.exercise.util.SortDirection;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class CalculationService {
    private List<Addition> additions = Collections.synchronizedList(new ArrayList<>());

    public Addition add(int addable1, int addable2) {
        Addition addition = new Addition(addable1, addable2);
        additions.add(addition);
        return addition;
    }

    public List<Addition> search(SortDirection direction, int filter) {
        List<Addition> filteredAdditions = additions
                .stream()
                .filter(addition -> addition.contains(filter))
                .toList();
        return sortAdditionList(filteredAdditions, direction);
    }

    public List<Addition> list(SortDirection direction) {
        return sortAdditionList(additions, direction);
    }

    private List<Addition> sortAdditionList(List<Addition> additions, SortDirection direction) {
        return additions
                .stream()
                .sorted(new AdditionComparator(direction))
                .toList();
    }
}
