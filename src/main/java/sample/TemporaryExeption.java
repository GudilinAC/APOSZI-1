package sample;

public class TemporaryExeption extends Exception {
    TemporaryExeption(){
        super("Сервер временно недоступен, повторите позже");
    }
}
