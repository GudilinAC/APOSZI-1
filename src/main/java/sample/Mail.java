package sample;

import java.io.File;
import java.util.List;

class Mail {
    private String to;
    private String password;
    private String from;
    private String topic;
    private String letter;
    private List<File> attached;

    String getTo() {
        return to;
    }

    String getPassword(){
        return password;
    }

    String getFrom() {
        return from;
    }

    String getTopic() {
        return topic;
    }

    String getLetter() {
        return letter;
    }

    List<File> getAttached() {
        return attached;
    }

    Mail(String to, String password, String from, String topic, String letter, List<File> attached) {
        this.to = to;
        this.password = password;
        this.from = from;
        this.topic = topic;
        this.letter = letter;
        this.attached = attached;
    }
}
