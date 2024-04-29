import org.junit.jupiter.api.Test;

import java.io.IOException;

public class PDFTest {

    @Test
    void pdfHelloWorld() throws IOException {
        Schedule schedule = new Schedule("MySchedule","F20");
        PDF.create(schedule);
    }
}
