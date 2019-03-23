package sample;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.io.File;
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
        boolean result = false;
        String errorMessage = null;

        MimeMessage message = makeMime();

        try {
            String ip = getIp();
            connect();
            hello(ip);
            login();
            routs();
            mail(message);
            quit();
            result = true;
        } catch (IOException e) {
            errorMessage = "Server is not responding. Check internet connection.";
            e.printStackTrace();
        } catch (SmtpException e) {
            errorMessage = e.getMessage();
            e.printStackTrace();
        }

        view.endSending(result, errorMessage);
    });

    private String getIp() throws UnknownHostException {
        return InetAddress.getLocalHost().getHostAddress();
    }

    private void connect() throws IOException, SmtpException {
        checkCode(sock.connect("smtp.gmail.com"));
    }

    private void hello(String ip) throws IOException, SmtpException {
        checkCode(sock.send("EHLO " + ip));
    }

    private void login() throws IOException, SmtpException {
        checkCode(sock.send("AUTH LOGIN"));
        checkCode(sock.sendBase64(mail.getFrom()));
        checkCode(sock.sendBase64(mail.getPassword()));
    }

    private void routs() throws IOException, SmtpException {
        checkCode(sock.send("MAIL FROM:<" + mail.getFrom() + ">"));
        checkCode(sock.send("RCPT TO:<" + mail.getTo() + ">"));
    }

    private void mail(MimeMessage massage) throws IOException, SmtpException {
        checkCode(sock.send("DATA"));
        checkCode(sock.send(massage));
    }

    private void quit() throws IOException {
        sock.send("QUIT");
    }

    private void checkCode(int code) throws SmtpException {
        if (code >= 200 && code <= 399)
            return;
        if (code >= 400 && code <= 499)
            throw new SmtpException("Server is temporarily unavailable with code " + code + ". Try to send later.");
        throw new SmtpException("Server returned " + code + ". Check if the entered data is correct.");
    }

    private MimeMessage makeMime() {
        MimeMessage mimeMessage = new MimeMessage((Session) null);
        try {
            mimeMessage.setFrom(mail.getFrom());
            mimeMessage.addRecipients(Message.RecipientType.TO, mail.getTo());
            mimeMessage.setSubject(mail.getSubject());

            MimeBodyPart mimeBodyPart = new MimeBodyPart();
            mimeBodyPart.setContent(mail.getLetter(), "text/plain");

            Multipart multipart = new MimeMultipart();
            multipart.addBodyPart(mimeBodyPart);

            if (mail.getAttached() != null)
                for (File file: mail.getAttached()) {
                    mimeBodyPart = new MimeBodyPart();
                    mimeBodyPart.setDataHandler(new DataHandler(new FileDataSource(file)));
                    mimeBodyPart.setFileName(file.getName());
                    multipart.addBodyPart(mimeBodyPart);
                }

            mimeMessage.setContent(multipart);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
        return mimeMessage;
    }
}
