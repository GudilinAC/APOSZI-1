package sample;

class CodeExeption extends Exception{
    CodeExeption(int code){
        super(Integer.toString(code));
    }
}
