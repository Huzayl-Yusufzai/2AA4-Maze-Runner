package ca.mcmaster.se2aa4.mazerunner;

public class ForwardCommand extends Command {

    private Marker receiver;

    public ForwardCommand(Marker receiver) {
        this.receiver = receiver;
    }

    @Override
    public void execute() {
        receiver.moveForward();
    }
}
