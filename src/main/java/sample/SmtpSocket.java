package sample;

import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;
import java.io.*;
import java.util.Base64;
import java.util.function.Consumer;

class SmtpSocket {
    private Consumer<String> logger;
    private BufferedReader in;
    private BufferedWriter out;

    public SmtpSocket(Consumer<String> logger) {
        this.logger = logger;
    }

    void connect(String host) {
        try {
            SSLSocket socket = (SSLSocket) SSLSocketFactory.getDefault().createSocket(host, 465);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            logger.accept(in.readLine());
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    String send(String message) {
        try {
            logger.accept(message);
            out.write(message + "\r\n");
            out.flush();
            logger.accept(in.readLine());
            if (in.ready())
                do {
                    logger.accept(in.readLine());
                } while (in.ready());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    String sendBase64(String message) {
        try {
            logger.accept(message + " (Base64)");
            out.write(Base64.getEncoder().encodeToString(message.getBytes()) + "\r\n");
            out.flush();
            logger.accept(in.readLine());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
