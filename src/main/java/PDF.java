

//TODO: implement

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;

import java.io.File;
import java.io.IOException;


public class PDF  {
    public static void create (Schedule schedule) throws IOException {
        PDDocument doc = new PDDocument();
        PDPage page = new PDPage();
        doc.addPage(page);

        PDPageContentStream contentStream = new PDPageContentStream(doc,page);
        contentStream.beginText();
        contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
        contentStream.setLeading(17.5f);

        contentStream.newLineAtOffset(50,700);
        contentStream.showText(schedule.getTitle());
        contentStream.newLine();
        contentStream.showText(schedule.getTerm());

        contentStream.endText();
        contentStream.close();

        doc.save(new File("src\\main\\PDFs\\simple.pdf"));
        doc.close();
    }

    // public void export(){}

}
