package sample;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;

class Controller {
    private View view;
    private SmtpSocket sock;
    private Mail mail;

    Controller(View view, SmtpSocketFactory factory){
        this.view = view;
        sock = factory.getSshSmtpSocket(view::log);
    }

    Controller(View view) {
        this(view, new SmtpSocketFactory());
    }

    void send(Mail mail) {
        this.mail = mail;
        sendingThread.start();
    }

    private final Thread sendingThread = new Thread(() -> {
        boolean result = true;

        MimeMessage message = makeMime();

        try {
            String ip = getIp();
            connect();
            hello(ip);
            login();
            routs();
            mail(message);
            quit();
        } catch (IOException e) {
            result = false;
            e.printStackTrace();
        } catch (TemporaryException e){
            result = false;
            e.printStackTrace();
        } catch (CodeException e) {
            result = false;
            e.printStackTrace();
        }

        view.endSending(result);
    });

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
        checkCode(sock.sendBase64(mail.getFrom()));
        checkCode(sock.sendBase64(mail.getPassword()));
    }

    private void routs() throws IOException, TemporaryException, CodeException {
        checkCode(sock.send("MAIL FROM:<" + mail.getFrom() + ">"));
        checkCode(sock.send("RCPT TO:<" + mail.getTo() + ">"));
    }

    private void mail(MimeMessage massage) throws IOException, TemporaryException, CodeException {
        checkCode(sock.send("DATA"));
        checkCode(sock.send(massage));
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

    private MimeMessage makeMime() {
        MimeMessage mimeMessage = new MimeMessage((Session) null);
        try {
            mimeMessage.setFrom(mail.getFrom());
            mimeMessage.addRecipients(Message.RecipientType.TO, mail.getTo());
            mimeMessage.setSubject(mail.getSubject());
            mimeMessage.setText(mail.getLetter());
        } catch (MessagingException e) {
            e.printStackTrace();
        }
        return mimeMessage;
    }
}
