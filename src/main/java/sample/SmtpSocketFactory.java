package sample;

import java.util.function.Consumer;

class SmtpSocketFactory {
    SmtpSocket getSshSmtpSocket(Consumer<String> logger)
    {
        return new SmtpSocket(logger);
    }
}
