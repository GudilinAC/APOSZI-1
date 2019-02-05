package sample;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class Controller {
    private View view;
    private SmtpSocket sock = new SmtpSocket();

    public void setView(View view) {
        this.view = view;
    }

    public void testSock() {
        sock.connect("smtp.gmail.com");
        try {
            sock.send("EHLO " + InetAddress.getLocalHost().getHostAddress());
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        sock.send("AUTH LOGIN");
        sock.sendBase64("GudilinAC@gmail.com");
        sock.sendBase64("Andrey01");
        sock.send("MAIL FROM:<GudilinAC@gmail.com>");
        sock.send("RCPT TO:<GudilinAC@gmail.com>");
        sock.send("DATA");
        sock.send("fdsgdsgdsfds\r\n.");
        sock.send("QUIT");
    }
}
