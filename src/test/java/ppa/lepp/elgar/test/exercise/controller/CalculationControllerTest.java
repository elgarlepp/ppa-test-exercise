package ppa.lepp.elgar.test.exercise.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import ppa.lepp.elgar.test.exercise.model.Addition;
import ppa.lepp.elgar.test.exercise.service.CalculationService;
import ppa.lepp.elgar.test.exercise.util.SortDirection;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CalculationController.class)
public class CalculationControllerTest {

    private final String ADD_URL = "/calculation/add/%d/%d";
    private final String LIST_URL = "/calculation/list/add/%s";
    private final String LIST_URL_WITH_FILTER = LIST_URL + "?filter=%d";

    @Autowired
    MockMvc mockMvc;

    @MockBean
    CalculationService calculationService;

    @Test
    public void testAdd() throws Exception {
        Addition addition = new Addition(3, 4);
        when(calculationService.add(3, 4)).thenReturn(addition);

        mockMvc.perform(get(String.format(ADD_URL, 3, 4)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", notNullValue()))
                .andExpect(jsonPath("$.addable1", is(addition.getAddable1())))
                .andExpect(jsonPath("$.addable2", is(addition.getAddable2())))
                .andExpect(jsonPath("$.sum", is(addition.getSum())));
    }

    @Test
    public void testAddNumberOutOfBoundsException() throws Exception {
        List<Map.Entry<Integer, Integer>> testEntries = new ArrayList<>() {{
            add(new AbstractMap.SimpleImmutableEntry<>(-1, 1));
            add(new AbstractMap.SimpleImmutableEntry<>(1, -1));
            add(new AbstractMap.SimpleImmutableEntry<>(101, 1));
            add(new AbstractMap.SimpleImmutableEntry<>(1, 101));
        }};

        for(Map.Entry<Integer, Integer> testEntry : testEntries) {
            mockMvc.perform(get(String.format(ADD_URL, testEntry.getKey(), testEntry.getValue())))
                    .andExpect(status().isBadRequest())
                    .andExpect(jsonPath("$", notNullValue()))
                    .andExpect(jsonPath("$.error", notNullValue()));
        }
    }

    @Test
    public void testListWithSearch() throws Exception {
        List<Addition> additions = new ArrayList<>(){{
            add(new Addition(1, 1));
            add(new Addition(1, 2));
            add(new Addition(2, 3));
        }};

        when(calculationService.search(SortDirection.ASCENDING, 2)).thenReturn(additions);

        ResultActions actions = mockMvc.perform(get(String.format(LIST_URL_WITH_FILTER, "ASCENDING", 2)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", notNullValue()));

        for(int i = 0; i < additions.size(); i++) {
            Addition current = additions.get(i);
            actions.andExpect(jsonPath(String.format("$[%d].addable1", i), is(current.getAddable1())))
                    .andExpect(jsonPath(String.format("$[%d].addable2", i), is(current.getAddable2())))
                    .andExpect(jsonPath(String.format("$[%d].sum", i), is(current.getSum())));
        }
    }

    @Test
    public void testListWithSearchWithCorrectSortDirections() throws Exception {
        mockMvc.perform(get(String.format(LIST_URL_WITH_FILTER, "ASCENDING", 1)))
                .andExpect(status().isOk());

        mockMvc.perform(get(String.format(LIST_URL_WITH_FILTER, "DESCENDING", 1)))
                .andExpect(status().isOk());
    }

    @Test
    public void testListWithSearchNumberOutOfBoundsException() throws Exception {
        List<Integer> testEntries = new ArrayList<>() {{
            add(-1);
            add(101);
        }};

        for(Integer testEntry : testEntries) {
            mockMvc.perform(get(String.format(LIST_URL_WITH_FILTER, "DESCENDING", testEntry)))
                    .andExpect(status().isBadRequest())
                    .andExpect(jsonPath("$", notNullValue()))
                    .andExpect(jsonPath("$.error", notNullValue()));
        }
    }

    @Test
    public void testList() throws Exception {
        List<Addition> additions = new ArrayList<>(){{
            add(new Addition(20, 10));
            add(new Addition(10, 30));
            add(new Addition(90, 10));
        }};

        when(calculationService.list(SortDirection.ASCENDING)).thenReturn(additions);

        ResultActions actions = mockMvc.perform(get(String.format(LIST_URL, "ASCENDING")))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", notNullValue()));

        for(int i = 0; i < additions.size(); i++) {
            Addition current = additions.get(i);
            actions.andExpect(jsonPath(String.format("$[%d].addable1", i), is(current.getAddable1())))
                    .andExpect(jsonPath(String.format("$[%d].addable2", i), is(current.getAddable2())))
                    .andExpect(jsonPath(String.format("$[%d].sum", i), is(current.getSum())));
        }
    }

    @Test
    public void testListNotFoundOnWrongSortDirection() throws Exception {
        mockMvc.perform(get(String.format(LIST_URL, "desc")))
                .andExpect(status().isBadRequest());

        mockMvc.perform(get(String.format(LIST_URL, "asc")))
                .andExpect(status().isBadRequest());

        mockMvc.perform(get(String.format(LIST_URL, "random")))
                .andExpect(status().isBadRequest());

        mockMvc.perform(get(String.format(LIST_URL, "descending")))
                .andExpect(status().isBadRequest());

        mockMvc.perform(get(String.format(LIST_URL, "ascending")))
                .andExpect(status().isBadRequest());
    }
}
