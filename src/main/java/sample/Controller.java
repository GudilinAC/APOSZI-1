package sample;

class Controller {
    private View view;
    private SmtpSocket sock;

    Controller(View view) {
        this.view = view;
        sock = new SmtpSocket(view::log);
    }

    void send(Mail mail) {
        (new SmtpSender(sock, mail)).start();
    }
}
