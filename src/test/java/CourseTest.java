import org.junit.jupiter.api.Test;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

import static org.junit.jupiter.api.Assertions.*;

class CourseTest {

    @Test
    void localTimeTest() {
        DateTimeFormatter ampmFormatter = DateTimeFormatter.ofPattern("hh:mm:ss a");
        LocalTime s = LocalTime.parse("12:00:00 PM", ampmFormatter);
    }

    @Test
    void timeRangesOverlap() {
        DateTimeFormatter ampmFormatter = DateTimeFormatter.ofPattern("hh:mm:ss a");
        LocalTime start1 = LocalTime.parse("12:00:00 PM", ampmFormatter);
        LocalTime end1 = LocalTime.parse("12:50:00 PM", ampmFormatter);
        LocalTime start2 = LocalTime.parse("01:00:00 PM", ampmFormatter);
        LocalTime end2 = LocalTime.parse("01:50:00 PM", ampmFormatter);

        boolean overlap1 = Course.timeRangesOverlap(start1, end1, start2, end2);
        System.out.println(overlap1);
    }
}