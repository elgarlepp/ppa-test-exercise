package ppa.lepp.elgar.test.exercise.controller;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jdk.jfr.Description;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ppa.lepp.elgar.test.exercise.model.Addition;
import ppa.lepp.elgar.test.exercise.service.CalculationService;
import ppa.lepp.elgar.test.exercise.util.SortDirection;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/calculation")
@Validated
public class CalculationController {
    @Autowired
    CalculationService calculationService;

    @Description("Adds the two given integer together and returns the given integers with their sum")
    @GetMapping("/add/{addable1}/{addable2}")
    public Addition add(
            @PathVariable("addable1") @Min(0) @Max(100) int addable1,
            @PathVariable("addable2") @Min(0) @Max(100) int addable2
    ) {
        return calculationService.add(addable1, addable2);
    }

    @Description("Lists all the made addition calculations. When filter is given, only additions which include given filter is returned")
    @GetMapping(value = {"/list/add/{direction}"})
    public List<Addition> list(
            @PathVariable("direction") SortDirection direction,
            @RequestParam("filter") Optional<@Min(0) @Max(100) Integer> filter
    ) {
        if(filter.isPresent()) {
            return calculationService.search(direction, filter.get());
        }

        return calculationService.list(direction);
    }
}
