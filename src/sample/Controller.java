package sample;

public class Controller {
    private View view;
    private SmtpSocket sock = new SmtpSocket();

    public void setView(View view) {
        this.view = view;
    }

    public void testSock() {
        //System.out.println(new String (Base64.getDecoder().decode("VXNlcm5hbWU6"), StandardCharsets.UTF_8));

        sock.connect("smtp.gmail.com");
        sock.send("EHLO 192.168.100.12\r\n");
        sock.send("MAIL FROM:<GudilinAC@gmail.com>\r\n");
        sock.send("RCPT TO:<GudilinAC@gmail.com>\r\n");
        sock.send("DATA\r\n");
        sock.send("fdsgdsgdsfds\r\n.\r\n");
        sock.send("QUIT\r\n");

    }
}
