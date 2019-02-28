package sample;

import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class MailTest {
    private Mail mail;
    private List<File> files = new ArrayList<>();

    @Before
    public void setUp() throws Exception {
        mail = new Mail("a", "b", "c", "d", "e", files);
    }

    @Test
    public void getTo() {
        assertEquals("a", mail.getTo());
    }

    @Test
    public void getPassword() {
        assertEquals("b", mail.getPassword());
    }

    @Test
    public void getFrom() {
        assertEquals("c", mail.getFrom());
    }

    @Test
    public void getSubject() {
        assertEquals("d", mail.getSubject());
    }

    @Test
    public void getLetter() {
        assertEquals("e", mail.getLetter());
    }

    @Test
    public void getAttached() {
        assertEquals( files, mail.getAttached());
    }
}