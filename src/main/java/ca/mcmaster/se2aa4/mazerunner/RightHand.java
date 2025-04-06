package ca.mcmaster.se2aa4.mazerunner;

class RightHand extends Marker { // New class which extends marker to perform algorithem

    public RightHand(int row, int col, boolean[][] mapValues, int endRow, int endCol) {
        super(row, col, mapValues, endRow, endCol);
        rightHandRule();

    }

    public void rightHandRule() { // Right hand rule
        while (true) {
            switch (direction) {
                case "E": // When Facing East
                    if (isValid(currentRow + 1, currentCol)) { // Checking SOUTH
                        direction = "S";
                        super.addToPath("RF");
                        currentRow++;
                    } else if (isValid(currentRow, currentCol + 1)) {// Checking EAST
                        direction = "E";
                        super.addToPath("F");
                        currentCol++;
                    } else if (isValid(currentRow - 1, currentCol)) {// Checking NORTH
                        direction = "N";
                        super.addToPath("LF");
                        currentRow--;
                    } else { // GO WEST
                        direction = "W";
                        super.addToPath("RRF");
                        currentCol--;
                    }
                    break;

                case "N": // When Facing NORTH
                    if (isValid(currentRow, currentCol + 1)) { // Checking EAST
                        direction = "E";
                        super.addToPath("RF");
                        currentCol++;
                    } else if (isValid(currentRow - 1, currentCol)) {// Checking NORTH
                        direction = "N";
                        super.addToPath("F");
                        currentRow--;
                    } else if (isValid(currentRow, currentCol - 1)) {// Checking WEST
                        direction = "W";
                        super.addToPath("LF");
                        currentCol--;
                    } else { // GO SOUTH
                        direction = "S";
                        super.addToPath("RRF");
                        currentRow++;
                    }
                    break;

                case "S": // When Facing SOUTH
                    if (isValid(currentRow, currentCol - 1)) {// Checking WEST
                        direction = "W";
                        super.addToPath("RF");
                        currentCol--;
                    } else if (isValid(currentRow + 1, currentCol)) {// Checing SOUTH
                        direction = "S";
                        super.addToPath("F");
                        currentRow++;
                    } else if (isValid(currentRow, currentCol + 1)) { // Checking EAST
                        direction = "E";
                        super.addToPath("LF");
                        currentCol++;
                    } else if (isValid(currentRow - 1, currentCol)) { // GO NORTH
                        direction = "N";
                        super.addToPath("RRF");
                        currentRow--;
                    }
                    break;
                case "W": // When Facing WEST
                    if (isValid(currentRow - 1, currentCol)) {// Checking NORTH
                        direction = "N";
                        super.addToPath("RF");
                        currentRow--;
                    } else if (isValid(currentRow, currentCol - 1)) {// Checking WEST
                        direction = "W";
                        super.addToPath("F");
                        currentCol--;
                    } else if (isValid(currentRow + 1, currentCol)) { // Checking SOUTH
                        direction = "S";
                        super.addToPath("LF");
                        currentRow++;
                    } else { // GO EAST
                        direction = "E";
                        super.addToPath("RRF");
                        currentCol++;
                    }
                    break;

            }
            if (endCol == currentCol && endRow == currentRow) {
                break;
            }
        }
    }
}