package sample;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class SmtpSenderTest {
    private SmtpSender smtpSender;

    @Mock private SmtpSocket smtpSocket = mock(SmtpSocket.class);
    @Mock private Mail mail = mock(Mail.class);

    @Test
    public void run() {
        smtpSender.run();
    }

    @Before
    public void setUp() throws Exception {
        when(mail.getTo()).thenReturn("a");
        when(mail.getFrom()).thenReturn("b");
        when(mail.getLetter()).thenReturn("c");
        when(mail.getPassword()).thenReturn("d");
        when(mail.getSubject()).thenReturn("e");
        when(mail.getAttached()).thenReturn(null);

        when(smtpSocket.connect("smtp.gmail.com")).thenReturn(220);
        when(smtpSocket.send(anyString())).thenReturn(250).thenReturn(334).thenReturn(250).thenReturn(250)
                .thenReturn(354).thenReturn(250).thenReturn(221).thenReturn(500);
        when(smtpSocket.sendBase64(anyString())).thenReturn(334).thenReturn(235).thenReturn(500);

        smtpSender = new SmtpSender(smtpSocket, mail);
    }
}