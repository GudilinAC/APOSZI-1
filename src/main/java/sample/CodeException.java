package sample;

class CodeException extends Exception {
    CodeException(int code) {
        super(Integer.toString(code));
    }
}
