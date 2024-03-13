import org.junit.jupiter.api.Test;

import java.io.File;

import static org.junit.jupiter.api.Assertions.*;

class StudentTest {

    @Test
    public void readFileTest() {
        File testFile = new File("test_credential_data.txt"); //TODO: program can't find file
        Credentials creds = Student.readFile(testFile);
        assertEquals(creds.getName(), "Billy Bob");
        assertEquals(creds.getId(),12345);
        assertEquals(creds.getMajor(), "B.S. in Computer Science");
        assertEquals(creds.getPassword(), "pa55word");
        assertEquals(creds.getEmail(), "bobbjXY@gcc.edu");

    }
}