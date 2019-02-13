package sample;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;

public class SmtpSender extends Thread {
    private final Mail mail;
    private final SmtpSocket sock;

    SmtpSender(SmtpSocket sock, Mail mail){
        this.mail = mail;
        this.sock = sock;
    }

    @Override
    public void run() {
        String ip;
        try {
            ip =  InetAddress.getLocalHost().getHostAddress();
        } catch (UnknownHostException e) {
            e.printStackTrace();
            return;
        }

        try {
            sock.connect("smtp.gmail.com");
            sock.send("EHLO " + ip);
            sock.send("AUTH LOGIN");
            sock.sendBase64(mail.getTo());
            sock.sendBase64(mail.getPassword());
            sock.send("MAIL FROM:<" + mail.getTo() + ">");
            sock.send("RCPT TO:<" + mail.getFrom() + ">");
            sock.send("DATA");
            sock.sendData(mail.getLetter());
            sock.send("\r\n.");
            sock.send("QUIT");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
