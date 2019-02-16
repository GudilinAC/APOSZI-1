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
    private boolean inBase64 = false;

    SmtpSocket(Consumer<String> logger) {
        this.logger = logger;
    }

    int connect(String host) throws IOException {
        SSLSocket socket = (SSLSocket) SSLSocketFactory.getDefault().createSocket(host, 465);
        in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
        String answer = in.readLine();
        logger.accept(answer);
        return Integer.parseInt(answer.substring(0, 3));

    }

    SmtpSocket base64(){
        inBase64 = true;
        return this;
    }

    SmtpSocket send(String message) throws IOException {
        logger.accept(message);
        sendData(inBase64 ? Base64.getEncoder().encodeToString(message.getBytes()) : message);
        return this;
    }

    int end() throws IOException{
        sendData("\r\n");
        String answer = in.readLine();
        logger.accept(answer);
        if (in.ready())
            do {
                logger.accept(in.readLine());
            } while (in.ready());
        return Integer.parseInt(answer.substring(0, 3));
    }

    private void sendData(String message) throws IOException {
        out.write(message);
        out.flush();
    }
}
