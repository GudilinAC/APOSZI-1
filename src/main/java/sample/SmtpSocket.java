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
    protected BufferedReader in;
    protected BufferedWriter out;
    protected OutputStream os;

    SmtpSocket(Consumer<String> logger) {
        this.logger = logger;
    }

    int connect(String host, int port) throws IOException {
        SSLSocket socket = (SSLSocket) SSLSocketFactory.getDefault().createSocket(host, port);
        createStreams(socket);
        String answer = in.readLine();
        logger.accept("Server: " + answer);
        return Integer.parseInt(answer.substring(0, 3));
    }

    protected void createStreams(SSLSocket socket) throws IOException{
        os = socket.getOutputStream();
        in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        out = new BufferedWriter(new OutputStreamWriter(os));
    }

    int send(String message) throws IOException {
        logger.accept("Client: " + message);
        out.write(message + "\r\n");
        out.flush();
        String answer = in.readLine();
        logger.accept("Server: " + answer);
        if (in.ready())
            do {
                logger.accept("Server: " + in.readLine());
            } while (in.ready());
        return Integer.parseInt(answer.substring(0, 3));
    }

    int send(MimeMessage massage) throws IOException {
        try {
            massage.writeTo(os);
            logger.accept("Sending message...");
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
