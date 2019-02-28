package sample;

class TemporaryException extends Exception {
    TemporaryException(){
        super("Сервер временно недоступен, повторите позже");
    }
}
