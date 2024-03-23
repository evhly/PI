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

        start2 = LocalTime.parse("12:30:00 PM", ampmFormatter);
        end2 = LocalTime.parse("01:45:00 PM", ampmFormatter);

        boolean overlap2 = Course.timeRangesOverlap(start1,end1,start2,end2);
        System.out.println(overlap2);

        start2 = LocalTime.parse("11:00:00 AM", ampmFormatter);
        end2 = LocalTime.parse("12:15:00 PM", ampmFormatter);

        boolean overlap3 = Course.timeRangesOverlap(start1,end1,start2,end2);
        System.out.println(overlap3);

        boolean overlap4 = Course.timeRangesOverlap(start1,end1,start1,end1);
        System.out.println(overlap4);

        start2 = LocalTime.parse("12:15:00 PM", ampmFormatter);
        end2 = LocalTime.parse("12:45:00 PM", ampmFormatter);

        boolean overlap5 = Course.timeRangesOverlap(start1,end1,start2,end2);
        System.out.println(overlap5);
    }
}