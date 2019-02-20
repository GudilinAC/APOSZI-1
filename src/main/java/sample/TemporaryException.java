package sample;

public class TemporaryException extends Exception {
    TemporaryException(){
        super("Сервер временно недоступен, повторите позже");
    }
}
