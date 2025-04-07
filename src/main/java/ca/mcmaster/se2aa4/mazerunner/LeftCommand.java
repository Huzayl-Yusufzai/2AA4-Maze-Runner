package ca.mcmaster.se2aa4.mazerunner;

public class LeftCommand extends Command {
    private Marker receiver;

    public LeftCommand(Marker receiver) {
        this.receiver = receiver;
    }

    @Override
    public void execute() {
        receiver.turnLeft();
    }
}
