package ca.mcmaster.se2aa4.mazerunner;

public class RightCommand extends Command {
    private Marker receiver;

    public RightCommand(Marker receiver) {
        this.receiver = receiver;
    }

    @Override
    public void execute() {
        receiver.turnRight();
    }
}
