import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class SearchTest {

    private static CourseDatabase db;
    private static Search search;

    @BeforeAll
    static void testSetup() {
        // set up course database using 2020-2021.csv
        CourseReader cr = new CourseReader();
        cr.parseCsv("src/main/csvs/2020-2021.csv");
        db = cr.getCourseDatabase("F20");
        // create new Search object using that database
        search = new Search(db);
    }

    @Test
    void searchTest() {
        // add no filters and search for "comp"
        search.modifyQuery("comp");
        search.search();
        // print results
        System.out.println(Arrays.toString(search.resultsStrs()));


        // add COMP (department) filter and search for "intro"
        search.addFilter(new Filter(Filter.type.DEPARTMENT, "COMP"));
        search.search();
        // print results
        System.out.println(Arrays.toString(search.resultsStrs()));

        // create new Professor object: Jonathan Hutchins
        Professor jh = new Professor("Jonathan", "Hutchins");
        // add this filter
        search.addFilter(new Filter(Filter.type.PROFESSOR, jh));
        search.search();
        // print results
        System.out.println(Arrays.toString(search.resultsStrs()));

        // clear filters
        search.clearFilters();
        // add filter for 2:00:00 PM - 2:50:00 PM
        Filter f = new Filter(Filter.type.TIMES, "2:00:00 PM","2:50:00 PM");
        search.addFilter(f);
        search.search();
        System.out.println(Arrays.toString(search.resultsStrs()));
    }
}