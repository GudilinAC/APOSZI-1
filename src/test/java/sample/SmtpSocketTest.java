package sample;

import org.junit.Before;

public class SmtpSocketTest {
    private SmtpSocket smtpSocket;

    /*@Test
    public void connect() {
        int result = 0;
        try{
            result = smtpSocket.connect("localhost");
        } catch (IOException e){}
        assertEquals(220, result);
    }

    @Test
    public void send() {
        int result = 0;
        try{
            testServer.start();
            smtpSocket.connect("localhost");
            result = smtpSocket.send("Hi");
            testServer.join();
        } catch (Exception e){
            e.printStackTrace();
        }
        assertEquals(123, result);
    }

    @Test
    public void sendBase64() {
        int result = 0;
        try{
            testServer.start();
            smtpSocket.connect("localhost");
            result = smtpSocket.sendBase64("Hi");
            testServer.join();
        } catch (Exception e){
            e.printStackTrace();
        }
        assertEquals(result, 132);
    }*/

    @Before
    public void setUp() throws Exception {
        smtpSocket = new SmtpSocket(System.out::println);
    }
}