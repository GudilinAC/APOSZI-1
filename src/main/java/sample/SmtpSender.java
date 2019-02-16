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
        try {
            String ip = getIp();
            connect();
            hello(ip);
            login();
            routs();
            mail();
            quit();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (TemporaryExeption e){
            e.printStackTrace();
        } catch (CodeExeption e) {
            e.printStackTrace();
        }
    }

    private String getIp() throws UnknownHostException {
        return InetAddress.getLocalHost().getHostAddress();
    }

    private void connect() throws IOException, TemporaryExeption, CodeExeption {
        checkCode(sock.connect("smtp.gmail.com"));
    }

    private void hello(String ip) throws IOException, TemporaryExeption, CodeExeption {
        checkCode(sock.send("EHLO " + ip).end());
    }

    private void login() throws IOException, TemporaryExeption, CodeExeption {
        checkCode(sock.send("AUTH LOGIN").end());
        checkCode(sock.base64().send(mail.getTo()).end());
        checkCode(sock.base64().send(mail.getPassword()).end());
    }

    private void routs() throws IOException, TemporaryExeption, CodeExeption {
        checkCode(sock.send("MAIL FROM:<" + mail.getTo() + ">").end());
        checkCode(sock.send("RCPT TO:<" + mail.getFrom() + ">").end());
    }

    private void mail() throws IOException, TemporaryExeption, CodeExeption {
        checkCode(sock.send("DATA").end());
        checkCode(sock.send(mail.getLetter() + "\r\n.").end());
    }

    private void quit() throws IOException {
        sock.send("QUIT").end();
    }

    private void checkCode(int code) throws TemporaryExeption, CodeExeption{
        if (code >= 200 && code <= 399)
            return;
        if (code >= 400 && code <= 499)
            throw new TemporaryExeption();
        throw new CodeExeption(code);
    }
}
