package ca.mcmaster.se2aa4.mazerunner;

class Marker { // Marker class which walks through the maze

    protected String path = "";
    protected boolean[][] mapValues;
    protected int currentRow;
    protected int currentCol;
    protected int eastRow;
    protected int eastCol;
    protected int endRow;
    protected int endCol;
    protected String direction = "E";

    public Marker(int row, int col, boolean[][] mapValues, int endRow, int endCol) {
        currentRow = row;
        currentCol = col;
        this.endRow = endRow;
        this.endCol = endCol;
        this.mapValues = mapValues;
    }

    public boolean isValid(int row, int col) { // Checks if potion is valid
        return row >= 0 && row < mapValues.length
                && col >= 0 && col < mapValues[0].length
                && mapValues[row][col];
    }

    public String returnCanonicalPath() { // Returns canonized path
        return path;
    }

    public String returnFactorizedPath() { // Returns factorized path
        path = factorize();
        return path;
    }

    public String factorize() { // Factorizes the path
        if (path == null || path.length() == 0) {
            return "";
        }

        char[] splitPath = path.toCharArray();
        char previous = splitPath[0];
        String newFactorized = "";
        int counter = 0;

        for (char chars : splitPath) {
            if (previous != chars) {
                newFactorized = (counter == 1) ? newFactorized.concat(String.valueOf(previous + " "))
                        : newFactorized.concat(String.valueOf(counter)).concat(String.valueOf(previous + " "));
                previous = chars;
                counter = 1;
            } else {
                counter++;
            }
        }
        newFactorized = (counter == 1) ? newFactorized.concat(String.valueOf(previous + " "))
                : newFactorized.concat(String.valueOf(counter)).concat(String.valueOf(previous + " "));
        return newFactorized;
    }

    public String fac2Canonical(String userInput) { // Turns factorized user input to canonical form for searching
                                                    // purposes
        String[] token = userInput.split("");
        String currentNum = "";
        StringBuilder canonicalInput = new StringBuilder();
        for (String tokens : token) {
            if (Character.isDigit(tokens.charAt(0))) {
                currentNum += tokens;
            } else {
                if (currentNum.isEmpty()) {
                    canonicalInput.append(tokens);
                } else {
                    canonicalInput.append(tokens.repeat(Integer.parseInt(currentNum)));
                    currentNum = "";
                }
            }
        }
        return canonicalInput.toString();
    }

    public String checkPath(String userInput) { // Manager method for west to east and east to west checks
        eastCol = currentCol;
        eastRow = currentRow;

        // Start from east entrance
        if (west2east(userInput)) {
            return "valid";
        } else if (east2west(userInput)) {
            return "valid";
        } else {
            return "not valid";
        }
    }

    public boolean west2east(String userInput) { // West to east check
        char[] userArrInput = userInput.toCharArray();
        direction = "E";
        for (char chars : userArrInput) {
            switch (direction) {
                case "E": // When Facing East
                    if (String.valueOf(chars).equals("F")) {
                        currentCol++;
                        if (isValid(currentRow, currentCol)) {
                            continue;
                        } else {
                            return false;
                        }
                    } else if (String.valueOf(chars).equals("R")) {
                        direction = "S";
                    } else if (String.valueOf(chars).equals("L")) {
                        direction = "N";
                    }
                    break;

                case "N": // When Facing NORTH
                    if (String.valueOf(chars).equals("F")) {
                        currentRow--;
                        if (isValid(currentRow, currentCol)) {
                            continue;
                        } else {
                            return false;
                        }
                    } else if (String.valueOf(chars).equals("R")) {
                        direction = "E";
                    } else if (String.valueOf(chars).equals("L")) {
                        direction = "W";
                    }
                    break;

                case "S": // When Facing SOUTH
                    if (String.valueOf(chars).equals("F")) {
                        currentRow++;
                        if (isValid(currentRow, currentCol)) {
                            continue;
                        } else {
                            return false;
                        }
                    } else if (String.valueOf(chars).equals("R")) {
                        direction = "W";
                    } else if (String.valueOf(chars).equals("L")) {
                        direction = "E";
                    }
                    break;

                case "W": // When Facing WEST
                    if (String.valueOf(chars).equals("F")) {
                        currentCol--;
                        if (isValid(currentRow, currentCol)) {
                            continue;
                        } else {
                            return false;
                        }
                    } else if (String.valueOf(chars).equals("R")) {
                        direction = "N";
                    } else if (String.valueOf(chars).equals("L")) {
                        direction = "S";
                    }
                    break;
            }
        }
        if (currentCol == endCol && currentRow == endRow) {
            return true;
        } else {
            return false;
        }
    }

    public boolean east2west(String userInput) { // East to west check
        currentCol = endCol;
        currentRow = endRow;

        char[] userArrInput = userInput.toCharArray();
        direction = "W";
        for (char chars : userArrInput) {
            switch (direction) {
                case "E": // When Facing East
                    if (String.valueOf(chars).equals("F")) {
                        currentCol++;
                        if (isValid(currentRow, currentCol)) {
                            continue;
                        } else {
                            return false;
                        }
                    } else if (String.valueOf(chars).equals("R")) {
                        direction = "S";
                    } else if (String.valueOf(chars).equals("L")) {
                        direction = "N";
                    }
                    break;

                case "N": // When Facing NORTH
                    if (String.valueOf(chars).equals("F")) {
                        currentRow--;
                        if (isValid(currentRow, currentCol)) {
                            continue;
                        } else {
                            return false;
                        }
                    } else if (String.valueOf(chars).equals("R")) {
                        direction = "E";
                    } else if (String.valueOf(chars).equals("L")) {
                        direction = "W";
                    }
                    break;

                case "S": // When Facing SOUTH
                    if (String.valueOf(chars).equals("F")) {
                        currentRow++;
                        if (isValid(currentRow, currentCol)) {
                            continue;
                        } else {
                            return false;
                        }
                    } else if (String.valueOf(chars).equals("R")) {
                        direction = "W";
                    } else if (String.valueOf(chars).equals("L")) {
                        direction = "E";
                    }
                    break;

                case "W": // When Facing WEST
                    if (String.valueOf(chars).equals("F")) {
                        currentCol--;
                        if (isValid(currentRow, currentCol)) {
                            continue;
                        } else {
                            return false;
                        }
                    } else if (String.valueOf(chars).equals("R")) {
                        direction = "N";
                    } else if (String.valueOf(chars).equals("L")) {
                        direction = "S";
                    }
                    break;
            }
        }
        if (currentCol == eastCol && currentRow == eastRow) {
            return true;
        } else {
            return false;
        }
    }

    public int getCurrentRow() {
        return currentRow;
    }

    public int getCurrentCol() {
        return currentCol;
    }

    public String getDirection() {
        return direction;
    }

    public boolean atEnd() {
        return currentRow == endRow && currentCol == endCol;
    }

    public void moveForward() {
        int newRow = currentRow;
        int newCol = currentCol;
        switch (direction) {
            case "E":
                newCol++;
                break;
            case "W":
                newCol--;
                break;
            case "N":
                newRow--;
                break;
            case "S":
                newRow++;
                break;
        }
        if (isValid(newRow, newCol)) {
            currentRow = newRow;
            currentCol = newCol;
            path = path.concat("F");
        }
    }

    public void turnLeft() {
        switch (direction) {
            case "E":
                direction = "N";
                break;
            case "N":
                direction = "W";
                break;
            case "W":
                direction = "S";
                break;
            case "S":
                direction = "E";
                break;
        }
        path = path.concat("L");
    }

    public void turnRight() {
        switch (direction) {
            case "E":
                direction = "S";
                break;
            case "S":
                direction = "W";
                break;
            case "W":
                direction = "N";
                break;
            case "N":
                direction = "E";
                break;
        }
        path = path.concat("R");
    }
}