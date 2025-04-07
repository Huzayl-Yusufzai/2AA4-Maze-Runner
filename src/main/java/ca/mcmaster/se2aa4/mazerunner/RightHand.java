package ca.mcmaster.se2aa4.mazerunner;

class RightHand implements MazeAlg {

    private Marker marker;

    public RightHand(Marker marker) {
        this.marker = marker;
    }

    @Override
    public void solve() {
        while (!marker.atEnd()) {
            Command command = null;
            int currRow = marker.getCurrentRow();
            int currCol = marker.getCurrentCol();
            String direction = marker.getDirection();

            switch (direction) {
                case "E":
                    if (marker.isValid(currRow + 1, currCol)) { // Check SOUTH
                        command = new RightCommand(marker);
                        command.execute();
                        command = new ForwardCommand(marker);
                        command.execute();
                    } else if (marker.isValid(currRow, currCol + 1)) { // Check EAST
                        command = new ForwardCommand(marker);
                        command.execute();
                    } else if (marker.isValid(currRow - 1, currCol)) { // Check NORTH
                        command = new LeftCommand(marker);
                        command.execute();
                        command = new ForwardCommand(marker);
                        command.execute();
                    } else { // Go WEST
                        command = new RightCommand(marker);
                        command.execute();
                        command = new RightCommand(marker);
                        command.execute();
                        command = new ForwardCommand(marker);
                        command.execute();
                    }
                    break;

                case "N":
                    if (marker.isValid(currRow, currCol + 1)) { // Check EAST
                        command = new RightCommand(marker);
                        command.execute();
                        command = new ForwardCommand(marker);
                        command.execute();
                    } else if (marker.isValid(currRow - 1, currCol)) { // Check NORTH
                        command = new ForwardCommand(marker);
                        command.execute();
                    } else if (marker.isValid(currRow, currCol - 1)) { // Check WEST
                        command = new LeftCommand(marker);
                        command.execute();
                        command = new ForwardCommand(marker);
                        command.execute();
                    } else { // Go SOUTH
                        command = new RightCommand(marker);
                        command.execute();
                        command = new RightCommand(marker);
                        command.execute();
                        command = new ForwardCommand(marker);
                        command.execute();
                    }
                    break;

                case "S":
                    if (marker.isValid(currRow, currCol - 1)) { // Check WEST
                        command = new RightCommand(marker);
                        command.execute();
                        command = new ForwardCommand(marker);
                        command.execute();
                    } else if (marker.isValid(currRow + 1, currCol)) { // Check SOUTH
                        command = new ForwardCommand(marker);
                        command.execute();
                    } else if (marker.isValid(currRow, currCol + 1)) { // Check EAST
                        command = new LeftCommand(marker);
                        command.execute();
                        command = new ForwardCommand(marker);
                        command.execute();
                    } else { // Go NORTH
                        command = new RightCommand(marker);
                        command.execute();
                        command = new RightCommand(marker);
                        command.execute();
                        command = new ForwardCommand(marker);
                        command.execute();
                    }
                    break;

                case "W":
                    if (marker.isValid(currRow - 1, currCol)) { // Check NORTH
                        command = new RightCommand(marker);
                        command.execute();
                        command = new ForwardCommand(marker);
                        command.execute();
                    } else if (marker.isValid(currRow, currCol - 1)) { // Check WEST
                        command = new ForwardCommand(marker);
                        command.execute();
                    } else if (marker.isValid(currRow + 1, currCol)) { // Check SOUTH
                        command = new LeftCommand(marker);
                        command.execute();
                        command = new ForwardCommand(marker);
                        command.execute();
                    } else { // Go EAST
                        command = new RightCommand(marker);
                        command.execute();
                        command = new RightCommand(marker);
                        command.execute();
                        command = new ForwardCommand(marker);
                        command.execute();
                    }
                    break;
            }
        }
    }

}