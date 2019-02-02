package sample;

import java.io.*;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

public class SmtpSocket {
    private Socket socket;
    private BufferedReader in;
    private BufferedWriter out;

    public void connect(String host)
    {
        try
        {
            socket = new Socket(host, 25);
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
            out.write(message);
            out.flush();
            System.out.println(in.readLine());
            //System.out.println(new String (Base64.getDecoder().decode(in.readLine()), StandardCharsets.UTF_8));
            if (in.ready())
                do {
                    System.out.println(in.readLine());
                } while (in.ready());
        }
        catch (IOException e) { e.printStackTrace(); }
        return null;
    }
}
