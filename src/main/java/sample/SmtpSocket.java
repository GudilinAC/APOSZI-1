package sample;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;
import java.io.*;
import java.util.Base64;
import java.util.function.Consumer;

class SmtpSocket {
    private Consumer<String> logger;
    private BufferedReader in;
    private BufferedWriter out;
    private OutputStream os;

    SmtpSocket(Consumer<String> logger) {
        this.logger = logger;
    }

    int connect(String host) throws IOException {
        SSLSocket socket = (SSLSocket) SSLSocketFactory.getDefault().createSocket(host, 465);
        in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        os = socket.getOutputStream();
        out = new BufferedWriter(new OutputStreamWriter(os));
        String answer = in.readLine();
        logger.accept(answer);
        return Integer.parseInt(answer.substring(0, 3));
    }

    int send(String message) throws IOException {
        logger.accept(message);
        out.write(message + "\r\n");
        out.flush();
        String answer = in.readLine();
        logger.accept(answer);
        if (in.ready())
            do {
                logger.accept(in.readLine());
            } while (in.ready());
        return Integer.parseInt(answer.substring(0, 3));
    }

    int send(MimeMessage massage) throws IOException {
        try {
            massage.writeTo(os);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
        os.flush();
        return send("\r\n.");
    }

    int sendBase64(String message) throws IOException{
        return send(Base64.getEncoder().encodeToString(message.getBytes()));
    }


}
