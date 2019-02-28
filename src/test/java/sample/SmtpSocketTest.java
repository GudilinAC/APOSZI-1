package sample;

import org.junit.Before;
import org.junit.Test;

import javax.mail.internet.MimeMessage;
import javax.net.ssl.SSLSocket;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStream;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

public class SmtpSocketTest {
    private SmtpSocket smtpSocket;
    private OutputStream mockOs = mock(OutputStream.class);
    private BufferedReader mockIn = mock(BufferedReader.class);
    private BufferedWriter mockOut = mock(BufferedWriter.class);
    private MimeMessage mockMessage = mock(MimeMessage.class);

    @Before
    public void setUp() throws Exception {
        smtpSocket = new SmtpSocket(System.out::println) {
            @Override protected void createStreams(SSLSocket socket) throws IOException {
                os = mockOs;
                in = mockIn;
                out = mockOut;
            }
        };
        smtpSocket.createStreams(null);
        when(mockIn.ready()).thenReturn(false);
    }

//    @Test
//    public void connect() {
//        int result = 0;
//        try {
//            when(mockIn.readLine()).thenReturn("001");
//            result = smtpSocket.connect("localhost");
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        assertEquals(1,result);
//    }

    @Test
    public void sendStr() {
        int result = 0;
        try {
            when(mockIn.readLine()).thenReturn("002").thenReturn("002");
            when(mockIn.ready()).thenReturn(true).thenReturn(false);
            result =smtpSocket.send("aaa");
        } catch (IOException e) {
            e.printStackTrace();
        }
        assertEquals(2,result);
    }

    @Test
    public void sendMime() {
        int result = 0;
        try {
            when(mockIn.readLine()).thenReturn("003");
            result = smtpSocket.send(mockMessage);
        } catch (IOException e) {
            e.printStackTrace();
        }
        assertEquals(3,result);
    }

    @Test
    public void sendBase64() {
        int result = 0;
        try {
            when(mockIn.readLine()).thenReturn("004");
            result =smtpSocket.sendBase64("eee");
        } catch (IOException e) {
            e.printStackTrace();
        }
        assertEquals(4,result);
    }
}