package sample;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import javax.mail.internet.MimeMessage;
import java.io.IOException;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

public class ControllerTest {
    private Controller controller;
    @Mock private Mail mail = mock(Mail.class);
    @Mock private View view = mock(View.class);
    @Mock private SmtpSocket sock = mock(SmtpSocket.class);

    @Test
    public void send() {
        when(mail.getTo()).thenReturn("a");
        when(mail.getFrom()).thenReturn("b");
        when(mail.getLetter()).thenReturn("c");
        when(mail.getPassword()).thenReturn("d");
        when(mail.getSubject()).thenReturn("e");
        when(mail.getAttached()).thenReturn(null);

        try {
            when(sock.connect("smtp.b", 465)).thenReturn(220);
            when(sock.send("EHLO " + anyString())).thenReturn(250);
            when(sock.send("AUTH LOGIN")).thenReturn(334);
            when(sock.sendBase64("b")).thenReturn(334);
            when(sock.sendBase64("d")).thenReturn(235);
            when(sock.send("MAIL FROM:<b>")).thenReturn(250);
            when(sock.send("RCPT TO:<a>")).thenReturn(250);
            when(sock.send("DATA")).thenReturn(354);
            when(sock.send(any(MimeMessage.class))).thenReturn(250);
            when(sock.send("QUIT")).thenReturn(221);
        } catch (IOException e) {
            e.printStackTrace();
        }

        controller.send(mail);
        verify(view, timeout(5000).times(1)).endSending(eq(true), any());
    }

    @Before
    public void setUp() throws Exception {
        SmtpSocketFactory factory = mock(SmtpSocketFactory.class);
        when(factory.getSshSmtpSocket(any())).thenReturn(sock);
        controller = new Controller(view, factory);
    }
}