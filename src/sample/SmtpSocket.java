package sample;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;
import java.io.*;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

public class SmtpSocket {
    private SSLSocket socket;
    private BufferedReader in;
    private BufferedWriter out;

    public void connect(String host)
    {
        try
        {
            socket = (SSLSocket) SSLSocketFactory.getDefault().createSocket("smtp.gmail.com", 465);
           // socket = SSLSocketFactory.getDefault("smtp.gmail.com", 587);
            //socket = new Socket(host, 587);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            System.out.println(in.readLine());
        }
        catch (IOException e) { e.printStackTrace(); }

    }

    public String send(String message)
    {
        try
        {
            System.out.println(message);
            out.write(message + "\r\n");
            out.flush();
            System.out.println(in.readLine());
            if (in.ready())
                do {
                    System.out.println(in.readLine());
                } while (in.ready());
        }
        catch (IOException e) { e.printStackTrace(); }
        return null;
    }

    public String sendBase64(String message)
    {
        try
        {
            System.out.println(message + " (Base64)");
            out.write(Base64.getEncoder().encodeToString(message.getBytes()) + "\r\n");
            out.flush();
            System.out.println(in.readLine());
        }
        catch (IOException e) { e.printStackTrace(); }
        return null;
    }
}
