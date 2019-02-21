package sample;

import javax.mail.internet.MailDateFormat;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Date;

public class SmtpSender extends Thread {
    private final Mail mail;
    private final SmtpSocket sock;

    SmtpSender(SmtpSocket sock, Mail mail) {
        this.mail = mail;
        this.sock = sock;
    }

    @Override
    public void run() {
        String message = makeMime();

        try {
            String ip = getIp();
            connect();
            hello(ip);
            login();
            routs();
            mail(message);
            quit();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (TemporaryException e){
            e.printStackTrace();
        } catch (CodeException e) {
            e.printStackTrace();
        }
    }

    private String getIp() throws UnknownHostException {
        return InetAddress.getLocalHost().getHostAddress();
    }

    private void connect() throws IOException, TemporaryException, CodeException {
        checkCode(sock.connect("smtp.gmail.com"));
    }

    private void hello(String ip) throws IOException, TemporaryException, CodeException {
        checkCode(sock.send("EHLO " + ip));
    }

    private void login() throws IOException, TemporaryException, CodeException {
        checkCode(sock.send("AUTH LOGIN"));
        checkCode(sock.sendBase64(mail.getTo()));
        checkCode(sock.sendBase64(mail.getPassword()));
    }

    private void routs() throws IOException, TemporaryException, CodeException {
        checkCode(sock.send("MAIL FROM:<" + mail.getTo() + ">"));
        checkCode(sock.send("RCPT TO:<" + mail.getFrom() + ">"));
    }

    private void mail(String massage) throws IOException, TemporaryException, CodeException {
        checkCode(sock.send("DATA"));
        checkCode(sock.send(massage + "\r\n."));
    }

    private void quit() throws IOException {
        sock.send("QUIT");
    }

    private void checkCode(int code) throws TemporaryException, CodeException {
        if (code >= 200 && code <= 399)
            return;
        if (code >= 400 && code <= 499)
            throw new TemporaryException();
        throw new CodeException(code);
    }

    private String makeMime(){
        return "Date: " + date() + "\r\n" +
                "From: " + mail.getFrom() + "\r\n" +
                "To: " + mail.getTo() + "\r\n" +
                "Subject: " + mail.getSubject() + "\r\n" +
                "MIME-Version: 1.0\r\n" +
                "Content-Type: text/plain; charset=UTF-8\r\n\r\n" +
                mail.getLetter() + "\r\n";
    }

    private String date(){
        return new MailDateFormat().format(new Date());
    }
}
