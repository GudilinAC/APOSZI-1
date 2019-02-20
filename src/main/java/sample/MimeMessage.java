package sample;

import java.util.Date;
import javax.mail.internet.MailDateFormat;

class MimeMessage {
    static String getMessage(Mail mail) {
        return "Date: " + date() + "\r\n" +
                "From: " + mail.getFrom() + "\r\n" +
                "To: " + mail.getTo() + "\r\n" +
                "Subject: " + mail.getSubject() + "\r\n" +
                "MIME-Version: 1.0\r\n" +
                "Content-Type: text/plain; charset=UTF-8\r\n\r\n" +
                mail.getLetter() + "\r\n";
    }

    private static String date(){
        return new MailDateFormat().format(new Date());
    }
}
