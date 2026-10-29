package designpatterns.behavioral.command;

/** Command interface: turns a request into a stand-alone object that can also be undone. */
public interface Command {

    void execute();

    void undo();

    String name();
}
