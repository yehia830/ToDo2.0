package tiy.com;

import javax.persistence.*;


@Entity
@Table(name = "todos")
public class ToDo {
    @Id
    @GeneratedValue
    int id;

    @ManyToOne
    User user;

    @Column(nullable = false)
    String text;

    @Column(nullable = false)
    boolean isDone;

    public ToDo() {
    }

    public ToDo(String text, User user) {
        this.text = text;
        this.isDone = false;
        this.user = user;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public boolean isDone() {
        return isDone;
    }

    public void setDone(boolean done) {
        isDone = done;
    }
}