package sample;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;

public class SmtpSender extends Thread {
    private final Mail mail;
    private final SmtpSocket sock;

    SmtpSender(SmtpSocket sock, Mail mail) {
        this.mail = mail;
        this.sock = sock;
    }

    @Override
    public void run() {
        String message = MimeMessage.getMessage(mail);

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
        checkCode(sock.send("EHLO " + ip).end());
    }

    private void login() throws IOException, TemporaryException, CodeException {
        checkCode(sock.send("AUTH LOGIN").end());
        checkCode(sock.base64().send(mail.getTo()).end());
        checkCode(sock.base64().send(mail.getPassword()).end());
    }

    private void routs() throws IOException, TemporaryException, CodeException {
        checkCode(sock.send("MAIL FROM:<" + mail.getTo() + ">").end());
        checkCode(sock.send("RCPT TO:<" + mail.getFrom() + ">").end());
    }

    private void mail(String massage) throws IOException, TemporaryException, CodeException {
        checkCode(sock.send("DATA").end());
        checkCode(sock.send(massage).send("\r\n.").end());
    }

    private void quit() throws IOException {
        sock.send("QUIT").end();
    }

    private void checkCode(int code) throws TemporaryException, CodeException {
        if (code >= 200 && code <= 399)
            return;
        if (code >= 400 && code <= 499)
            throw new TemporaryException();
        throw new CodeException(code);
    }
}
