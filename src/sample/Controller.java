package sample;

import javax.naming.ldap.StartTlsRequest;

public class Controller {
    private View view;
    private SmtpSocket sock = new SmtpSocket();

    public void setView(View view) {
        this.view = view;
    }

    public void testSock() {
        //System.out.println(new String (Base64.getDecoder().decode("VXNlcm5hbWU6"), StandardCharsets.UTF_8));

        sock.connect("smtp.gmail.com");
        sock.send("EHLO 192.168.100.12");
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
