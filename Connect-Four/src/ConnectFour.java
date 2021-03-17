import java.util.*;

/**
 * Harun Özdemir 150116043 * Varol Kocoglu 150116045 * Muhammed Murat Dilmaç 150116017 *
 */

class Node {

    private String[][] state;
    private int depth;
    private int value = 0; // or Evaluation function
    private boolean terminalNode = false; // initial false
    private Node parent;
    private boolean leafNode = false;
    private ArrayList<Node> childs = new ArrayList<>();
    private int alpha = Integer.MIN_VALUE;
    private int beta = Integer.MAX_VALUE;
    private int whereIsMyParent; // her node'u oluştuturekn contructorda
    public Node(String[][] state, Node parent, int whereIsMyParent, boolean leafNode) {
        this.state = state;
        this.parent = parent;
        this.whereIsMyParent = whereIsMyParent;
        this.leafNode = leafNode;
    }
    public int getAlpha() {
        return alpha;
    }
    public void setAlpha(int alpha) {
        this.alpha = alpha;
    }
    public int getBeta() {
        return beta;
    }
    public void setBeta(int beta) {
        this.beta = beta;
    }
    public void setState(String[][] state) {
        this.state = state;
    }
    public int getDepth() {
        return depth;
    }
    public void setDepth(int depth) {
        this.depth = depth;
    }
    public int getValue() {
        return value;
    }
    public void setValue(int value) {
        this.value = value;
    }
    public boolean isTerminalNode() {
        return terminalNode;
    }
    public void setTerminalNode(boolean terminalNode) {
        this.terminalNode = terminalNode;
    }
    public Node getParent() {
        return parent;
    }
    public void setParent(Node parent) {
        this.parent = parent;
    }
    public ArrayList<Node> getChilds() {
        return childs;
    }
    public void setChilds(ArrayList<Node> childs) {
        this.childs = childs;
    }
    public int getWhereIsMyParent() {
        return whereIsMyParent;
    }
    public void setWhereIsMyParent(int whereIsMyParent) {
        this.whereIsMyParent = whereIsMyParent;
    }
    public String[][] getState() {
        return state;
    }
    public boolean isLeafNode() {
        return leafNode;
    }
    public void setLeafNode(boolean leafNode) {
        this.leafNode = leafNode;
    }
}
public class ConnectFour {
    private static String[][] merge2dArrays(String[][] arr1, String[][] arr2) {
        for (int x = 0; x < 6; x++) {
            for (int y = 0; y < 7; y++) {
                arr2[x][y] = arr1[x][y];
            }
        }
        return arr2;
    }
    private static String[][] insertTable(String[][] table, String color, int column) {
        int row = 5;
        String[][] arrayNew = new String[6][7];
        arrayNew = merge2dArrays(table, arrayNew);
        while (true) {
            if ("-".equals(arrayNew[row][column])) {
                arrayNew[row][column] = color;	// column 7 tane, row 6
                break;
            }
            row--;
        }
        return arrayNew;
    }
    private static void printArray(String[][] arr) {
        for (int x = 0; x < 6; x++) {
            for (int y = 0; y < 7; y++) {
                if ("R".equals(arr[x][y]))
                    System.out.print("\u001B[41m" + "  " + "\u001B[0m" + "  ");

                else if ("B".equals(arr[x][y]))
                    System.out.print("\u001B[44m" + "  " + "\u001B[0m" + "  ");

                else
                    System.out.print(arr[x][y] + "   ");
            }
            System.out.println("");
        }
        System.out.println("");
    }

    private static void insertDash(String[][] arr) {
        for (int x = 0; x < 6; x++) {
            for (int y = 0; y < 7; y++) {
                arr[x][y] = "-";
            }
        }
    }
    private static boolean isTie(String[][] state) {
        int treCount = 0;
        for (int i = 0; i < 7; i++) {
            for(int j = 0; j < 6; j++) {
                if (state[j][i].equals("-"))
                    treCount++;
            }
        }
        if (treCount<=1) {
            return true;
        }else {
            return false;
        }
    }
    private static boolean isValidMove(String[][] state, int action) {
        return state[0][action].equals("-");
    }
    private static boolean isEndOfGame(String[][] arr, String color) {
        int win = 0;
        for (int i = 0; i < 6; i++) {// yan yana
            win = 0;
            for (int j = 0; j < 7; j++) {
                if (arr[i][j].equals(color)) {
                    win++;
                    if (win == 4) {
                        return true;
                    }
                } else
                    win = 0;
            }
        }
        win = 0;
        for (int i = 0; i < 7; i++) {// üst üste
            win = 0;
            for (int j = 0; j < 6; j++) {
                if (arr[j][i].equals(color)) {
                    win++;
                    if (win == 4) {
                        return true;
                    }
                } else
                    win = 0;
            }
        }
        win = 0;
        for (int i = 0; i < 6; i++) {/// çapraz
            win = 0;
            for (int j = 0; j < 7; j++) {
                if (arr[i][j].equals(color)) {
                    int oncekiI = i;
                    int oncekiJ = j;
                    for (int a = 0; a < 4; i--, j++, a++) {// sağ üste
                        if (i >= 0 && j < 7) {
                            if (arr[i][j].equals(color)) {
                                win++;
                                if (win == 4) {
                                    return true;
                                }
                            }
                        } else
                            break;
                    }
                    win = 0;
                    i = oncekiI;
                    j = oncekiJ;
                    for (int a = 0; a < 4; i--, j--, a++) {// sol üste
                        if (i >= 0 && j >= 0) {
                            if (arr[i][j].equals(color)) {
                                win++;
                                if (win == 4) {
                                    return true;
                                }
                            }
                        } else
                            break;
                    }
                    win = 0;
                    i = oncekiI;
                    j = oncekiJ;
                    for (int a = 0; a < 4; i++, j++, a++) {// sağ aşağı
                        if (i < 6 && j < 7) {
                            if (arr[i][j].equals(color)) {
                                win++;
                                if (win == 4) {
                                    return true;
                                }
                            }
                        } else
                            break;
                    }
                    win = 0;
                    i = oncekiI;
                    j = oncekiJ;
                    for (int a = 0; a < 4; i++, j--, a++) {// sol aşağı
                        if (i < 6 && j >= 0) {
                            if (arr[i][j].equals(color)) {
                                win++;
                                if (win == 4) {
                                    return true;
                                }
                            }
                        } else
                            break;
                    }
                    win = 0;
                    i = oncekiI;
                    j = oncekiJ;
                }
            }
        }
        return false;
    }
    private static int choiceChair(int totalScore, int redCount, int emptyCount) {// coiceChair method to add points.
        if (redCount == 3 && emptyCount == 1) {
            return 55;
        } else if (redCount == 2 && emptyCount == 2) {
            return 21;
        } else if (redCount == 1 && emptyCount == 3) {
            return 8;
        }
        return 0;
    }
    private static int hueristicDumb(String[][] arr, String color) {
        boolean bitis = isEndOfGame(arr, "R");
        boolean bitiskarsi = isEndOfGame(arr, "B");
        if (bitiskarsi) {
            return -10000;
        }
        if (bitis) {
            return 10000;
        }
        int toplamdeger = 0;
        int redCount = 0;
        int blueCount = 0;
        int emptyCount = 0;
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 7; j++) {
                if (arr[i][j].equals("R")) {// renk buldugunda bunu çıkartabiliriz.
                    int oncekiI = i;
                    int oncekiJ = j;
                    redCount = 0;
                    blueCount = 0;
                    emptyCount = 0;
                    for (int a = 0; a < 4; j++, a++) { // sağa
                        if (j < 7) {
                            if (arr[i][j].equals("R")) {
                                redCount++;
                            } else if (arr[i][j].equals("-")) {
                                emptyCount++;
                            } else {
                                blueCount++;
                            }
                        } else
                            break;
                    }
                    toplamdeger += choiceChair(toplamdeger, redCount, emptyCount);
                    redCount = 0;
                    blueCount = 0;
                    emptyCount = 0;
                    i = oncekiI;
                    j = oncekiJ;
                    for (int a = 0; a < 4; i--, a++) { // yukarı
                        if (i >= 0) {
                            if (arr[i][j].equals("R")) {
                                redCount++;
                            } else if (arr[i][j].equals("-")) {
                                emptyCount++;
                            } else {
                                blueCount++;
                            }
                        } else
                            break;
                    }
                    toplamdeger += choiceChair(toplamdeger, redCount, emptyCount);
                    i = oncekiI;
                    j = oncekiJ;
                } else if (arr[i][j].equals("B")) {// renk buldugunda bunu çıkartabiliriz.
                    redCount = 0;
                    blueCount = 0;
                    emptyCount = 0;
                    int oncekiI = i;
                    int oncekiJ = j;
                    for (int a = 0; a < 4; j++, a++) { // yukarı
                        if (j < 7) {
                            if (arr[i][j].equals("R")) {
                                redCount++;
                            } else if (arr[i][j].equals("-")) {
                                emptyCount++;
                            } else {
                                blueCount++;
                            }
                        } else
                            break;
                    }
                    toplamdeger = toplamdeger - choiceChair(toplamdeger, blueCount, emptyCount);
                    redCount = 0;
                    blueCount = 0;
                    emptyCount = 0;
                    i = oncekiI;
                    j = oncekiJ;
                    for (int a = 0; a < 4; i--, a++) { // sola
                        if (i >= 0) {
                            if (arr[i][j].equals("R")) {
                                redCount++;
                            } else if (arr[i][j].equals("-")) {
                                emptyCount++;
                            } else {
                                blueCount++;
                            }
                        } else
                            break;
                    }
                    toplamdeger -= choiceChair(toplamdeger, blueCount, emptyCount);
                    redCount = 0;
                    blueCount = 0;
                    emptyCount = 0;
                    i = oncekiI;
                    j = oncekiJ;
                }
            }
        }
        return toplamdeger;
    }
    private static int hueristicDumb2(String[][] arr, String color) {
        /// çapraz
        boolean bitis = isEndOfGame(arr, "R");
        boolean bitiskarsi = isEndOfGame(arr, "B");
        if (bitiskarsi) {
            return -10000;
        }
        if (bitis) {
            return 10000;
        }
        int toplamdeger = 0;
        int redCount = 0;
        int blueCount = 0;
        int emptyCount = 0;

        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 7; j++) {
                if (arr[i][j].equals("R")) {// renk buldugunda bunu çıkartabiliriz.

                    int oncekiI = i;
                    int oncekiJ = j;
                    redCount = 0;
                    blueCount = 0;
                    emptyCount = 0;
                    for (int a = 0; a < 4; j++, a++) { // sağa
                        if (j < 7) {
                            if (arr[i][j].equals("R")) {
                                redCount++;
                            } else if (arr[i][j].equals("-")) {
                                emptyCount++;
                            } else {
                                blueCount++;
                            }

                        } else
                            break;

                    }
                    toplamdeger += choiceChair(toplamdeger, redCount, emptyCount);

                    redCount = 0;
                    blueCount = 0;
                    emptyCount = 0;
                    i = oncekiI;
                    j = oncekiJ;
                    for (int a = 0; a < 4; i--, a++) { // yukarı
                        if (i >= 0) {
                            if (arr[i][j].equals("R")) {
                                redCount++;
                            } else if (arr[i][j].equals("-")) {
                                emptyCount++;
                            } else {
                                blueCount++;
                            }

                        } else
                            break;

                    }
                    toplamdeger += choiceChair(toplamdeger, redCount, emptyCount);
                    i = oncekiI;
                    j = oncekiJ;
                } else if (arr[i][j].equals("B")) {// renk buldugunda bunu çıkartabiliriz.

                    redCount = 0;
                    blueCount = 0;
                    emptyCount = 0;
                    int oncekiI = i;
                    int oncekiJ = j;
                    for (int a = 0; a < 4; j++, a++) { // yukarı
                        if (j < 7) {
                            if (arr[i][j].equals("R")) {
                                redCount++;
                            } else if (arr[i][j].equals("-")) {
                                emptyCount++;
                            } else {
                                blueCount++;
                            }

                        } else
                            break;

                    }
                    toplamdeger = toplamdeger - choiceChair(toplamdeger, blueCount, emptyCount);

                    redCount = 0;
                    blueCount = 0;
                    emptyCount = 0;
                    i = oncekiI;
                    j = oncekiJ;
                    for (int a = 0; a < 4; i--, a++) { // sola
                        if (i >= 0) {
                            if (arr[i][j].equals("R")) {
                                redCount++;
                            } else if (arr[i][j].equals("-")) {
                                emptyCount++;
                            } else {
                                blueCount++;
                            }

                        } else
                            break;

                    }
                    toplamdeger -= choiceChair(toplamdeger, blueCount, emptyCount);

                    redCount = 0;
                    blueCount = 0;
                    emptyCount = 0;
                    i = oncekiI;
                    j = oncekiJ;
                }

            }

        }
        return toplamdeger;
    }
    private static int hueristic(String[][] arr, String color) {
        boolean bitis = isEndOfGame(arr, "R");
        boolean bitiskarsi = isEndOfGame(arr, "B");
        if (bitiskarsi) {
            return -10000;
        }
        if (bitis) {
            return 10000;
        }
        int toplamdeger = 0;
        int redCount = 0;
        int blueCount = 0;
        int emptyCount = 0;
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 7; j++) {
                if (arr[i][j].equals("R")) {// renk buldugunda bunu çıkartabiliriz.
                    int oncekiI = i;
                    int oncekiJ = j;
                    redCount = 0;
                    blueCount = 0;
                    emptyCount = 0;
                    for (int a = 0; a < 4; i--, j--, a++) {// sol yukarı
                        if (i >= 0 && j >= 0) {
                            if (arr[i][j].equals("R")) {
                                redCount++;
                            } else if (arr[i][j].equals("-")) {
                                emptyCount++;
                            } else {
                                blueCount++;
                            }
                        } else
                            break;// bu break dışarı taşmasın diye
                    }
                    toplamdeger += choiceChair(toplamdeger, redCount, emptyCount);
                    redCount = 0;
                    blueCount = 0;
                    emptyCount = 0;
                    i = oncekiI;
                    j = oncekiJ;
                    for (int a = 0; a < 4; i--, j++, a++) {// sağ yukarı
                        if (i >= 0 && j < 7) {
                            if (arr[i][j].equals("R")) {
                                redCount++;
                            } else if (arr[i][j].equals("-")) {
                                emptyCount++;
                            } else {
                                blueCount++;
                            }
                        } else
                            break;
                    }
                    toplamdeger += choiceChair(toplamdeger, redCount, emptyCount);
                    redCount = 0;
                    blueCount = 0;
                    emptyCount = 0;
                    i = oncekiI;
                    j = oncekiJ;
                    for (int a = 0; a < 4; i--, a++) { // yukarı
                        if (i >= 0) {
                            if (arr[i][j].equals("R")) {
                                redCount++;
                            } else if (arr[i][j].equals("-")) {
                                emptyCount++;
                            } else {
                                blueCount++;
                            }
                        } else
                            break;
                    }
                    toplamdeger += choiceChair(toplamdeger, redCount, emptyCount);
                    redCount = 0;
                    blueCount = 0;
                    emptyCount = 0;
                    i = oncekiI;
                    j = oncekiJ;
                    for (int a = 0; a < 4; j++, a++) { // sağa
                        if (j < 7) {
                            if (arr[i][j].equals("R")) {
                                redCount++;
                            } else if (arr[i][j].equals("-")) {
                                emptyCount++;
                            } else {
                                blueCount++;
                            }
                        } else
                            break;
                    }
                    toplamdeger += choiceChair(toplamdeger, redCount, emptyCount);
                    redCount = 0;
                    blueCount = 0;
                    emptyCount = 0;
                    i = oncekiI;
                    j = oncekiJ;
                    for (int a = 0; a < 4; j--, a++) { // sola
                        if (j >= 0) {
                            if (arr[i][j].equals("R")) {
                                redCount++;
                            } else if (arr[i][j].equals("-")) {
                                emptyCount++;
                            } else {
                                blueCount++;
                            }
                        } else
                            break;
                    }
                    toplamdeger += choiceChair(toplamdeger, redCount, emptyCount);
                    i = oncekiI;
                    j = oncekiJ;
                } else if (arr[i][j].equals("B")) {// renk buldugunda bunu çıkartabiliriz.
                    redCount = 0;
                    blueCount = 0;
                    emptyCount = 0;
                    int oncekiI = i;
                    int oncekiJ = j;
                    for (int a = 0; a < 4; i--, j--, a++) {// sol yukarı
                        if (i >= 0 && j >= 0) {
                            if (arr[i][j].equals("R")) {
                                redCount++;
                            } else if (arr[i][j].equals("-")) {
                                emptyCount++;
                            } else {
                                blueCount++;
                            }
                        } else
                            break;// bu break dışarı taşmasın diye
                    }
                    toplamdeger -= choiceChair(toplamdeger, blueCount, emptyCount);
                    redCount = 0;
                    blueCount = 0;
                    emptyCount = 0;
                    i = oncekiI;
                    j = oncekiJ;
                    for (int a = 0; a < 4; i--, j++, a++) {// sağ yukarı
                        if (i >= 0 && j < 7) {
                            if (arr[i][j].equals("R")) {
                                redCount++;
                            } else if (arr[i][j].equals("-")) {
                                emptyCount++;
                            } else {
                                blueCount++;
                            }
                        } else
                            break;
                    }
                    toplamdeger -= choiceChair(toplamdeger, blueCount, emptyCount);
                    redCount = 0;
                    blueCount = 0;
                    emptyCount = 0;
                    i = oncekiI;
                    j = oncekiJ;
                    for (int a = 0; a < 4; i--, a++) { // yukarı
                        if (i >= 0) {
                            if (arr[i][j].equals("R")) {
                                redCount++;
                            } else if (arr[i][j].equals("-")) {
                                emptyCount++;
                            } else {
                                blueCount++;
                            }
                        } else
                            break;
                    }
                    toplamdeger -= choiceChair(toplamdeger, blueCount, emptyCount);
                    redCount = 0;
                    blueCount = 0;
                    emptyCount = 0;
                    i = oncekiI;
                    j = oncekiJ;
                    for (int a = 0; a < 4; j++, a++) { // sağa
                        if (j < 7) {
                            if (arr[i][j].equals("R")) {
                                redCount++;
                            } else if (arr[i][j].equals("-")) {
                                emptyCount++;
                            } else {
                                blueCount++;
                            }
                        } else
                            break;
                    }
                    toplamdeger = toplamdeger - choiceChair(toplamdeger, blueCount, emptyCount);
                    redCount = 0;
                    blueCount = 0;
                    emptyCount = 0;
                    i = oncekiI;
                    j = oncekiJ;
                    for (int a = 0; a < 4; j--, a++) { // sola
                        if (j >= 0) {
                            if (arr[i][j].equals("R")) {
                                redCount++;
                            } else if (arr[i][j].equals("-")) {
                                emptyCount++;
                            } else {
                                blueCount++;
                            }

                        } else
                            break;
                    }
                    toplamdeger -= choiceChair(toplamdeger, blueCount, emptyCount);
                    redCount = 0;
                    blueCount = 0;
                    emptyCount = 0;
                    i = oncekiI;
                    j = oncekiJ;
                }
            }
        }
        return toplamdeger;
    }
    private static int hueristic2(String[][] arr, String color) {
        boolean bitis = isEndOfGame(arr, "R");
        boolean bitiskarsi = isEndOfGame(arr, "B");
        if (bitiskarsi) {
            return -10000;
        }
        if (bitis) {
            return 10000;
        }
        int toplamdeger = 0;
        int redCount = 0;
        int blueCount = 0;
        int emptyCount = 0;
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 7; j++) {
                if (arr[i][j].equals("R")) {// When it founds R it search its direction and count them.
                    int oncekiI = i;
                    int oncekiJ = j;
                    redCount = 0;
                    blueCount = 0;
                    emptyCount = 0;
                    for (int a = 0; a < 4; i--, j--, a++) {// sol yukarı
                        if (i >= 0 && j >= 0) {
                            if (arr[i][j].equals("R")) {
                                redCount++;
                            } else if (arr[i][j].equals("-")) {
                                emptyCount++;
                            } else {
                                blueCount++;
                            }
                        } else
                            break;// bu break dışarı taşmasın diye
                    }
                    toplamdeger += choiceChair(toplamdeger, redCount, emptyCount);//after count we sending coiceChair method to add points.
                    redCount = 0;
                    blueCount = 0;
                    emptyCount = 0;
                    i = oncekiI;
                    j = oncekiJ;
                    for (int a = 0; a < 4; i--, j++, a++) {// sağ yukarı
                        if (i >= 0 && j < 7) {
                            if (arr[i][j].equals("R")) {
                                redCount++;
                            } else if (arr[i][j].equals("-")) {
                                emptyCount++;
                            } else {
                                blueCount++;
                            }

                        } else
                            break;
                    }
                    toplamdeger += choiceChair(toplamdeger, redCount, emptyCount);//after count we sending coiceChair method to add points.
                    redCount = 0;
                    blueCount = 0;
                    emptyCount = 0;
                    i = oncekiI;
                    j = oncekiJ;
                    for (int a = 0; a < 4; i--, a++) { // yukarı
                        if (i >= 0) {
                            if (arr[i][j].equals("R")) {
                                redCount++;
                            } else if (arr[i][j].equals("-")) {
                                emptyCount++;
                            } else {
                                blueCount++;
                            }

                        } else
                            break;
                    }
                    toplamdeger += choiceChair(toplamdeger, redCount, emptyCount);//after count we sending coiceChair method to add points.
                    redCount = 0;
                    blueCount = 0;
                    emptyCount = 0;
                    i = oncekiI;
                    j = oncekiJ;
                    for (int a = 0; a < 4; j++, a++) { // sağa
                        if (j < 7) {
                            if (arr[i][j].equals("R")) {
                                redCount++;
                            } else if (arr[i][j].equals("-")) {
                                emptyCount++;
                            } else {
                                blueCount++;
                            }

                        } else
                            break;
                    }
                    toplamdeger += choiceChair(toplamdeger, redCount, emptyCount);//after count we sending coiceChair method to add points.
                    redCount = 0;
                    blueCount = 0;
                    emptyCount = 0;
                    i = oncekiI;
                    j = oncekiJ;
                    for (int a = 0; a < 4; j--, a++) { // sola
                        if (j >= 0) {
                            if (arr[i][j].equals("R")) {
                                redCount++;
                            } else if (arr[i][j].equals("-")) {
                                emptyCount++;
                            } else {
                                blueCount++;
                            }
                        } else
                            break;
                    }
                    toplamdeger += choiceChair(toplamdeger, redCount, emptyCount);//after count we sending coiceChair method to add points.
                    i = oncekiI;
                    j = oncekiJ;
                } else if (arr[i][j].equals("B")) {// renk buldugunda bunu çıkartabiliriz.
                    redCount = 0;
                    blueCount = 0;
                    emptyCount = 0;
                    int oncekiI = i;
                    int oncekiJ = j;
                    for (int a = 0; a < 4; i--, j--, a++) {// sol yukarı
                        if (i >= 0 && j >= 0) {
                            if (arr[i][j].equals("R")) {
                                redCount++;
                            } else if (arr[i][j].equals("-")) {
                                emptyCount++;
                            } else {
                                blueCount++;
                            }
                        } else
                            break;// bu break dışarı taşmasın diye
                    }
                    toplamdeger -= choiceChair(toplamdeger, blueCount, emptyCount);//after count we sending coiceChair method to add points.
                    redCount = 0;
                    blueCount = 0;
                    emptyCount = 0;
                    i = oncekiI;
                    j = oncekiJ;
                    for (int a = 0; a < 4; i--, j++, a++) {// sağ yukarı
                        if (i >= 0 && j < 7) {
                            if (arr[i][j].equals("R")) {
                                redCount++;
                            } else if (arr[i][j].equals("-")) {
                                emptyCount++;
                            } else {
                                blueCount++;
                            }
                        } else
                            break;
                    }
                    toplamdeger -= choiceChair(toplamdeger, blueCount, emptyCount);//after count we sending coiceChair method to add points.
                    redCount = 0;
                    blueCount = 0;
                    emptyCount = 0;
                    i = oncekiI;
                    j = oncekiJ;
                    for (int a = 0; a < 4; i--, a++) { // yukarı
                        if (i >= 0) {
                            if (arr[i][j].equals("R")) {
                                redCount++;
                            } else if (arr[i][j].equals("-")) {
                                emptyCount++;
                            } else {
                                blueCount++;
                            }
                        } else
                            break;
                    }
                    toplamdeger -= choiceChair(toplamdeger, blueCount, emptyCount);//after count we sending coiceChair method to add points.
                    redCount = 0;
                    blueCount = 0;
                    emptyCount = 0;
                    i = oncekiI;
                    j = oncekiJ;
                    for (int a = 0; a < 4; j++, a++) { // sağa
                        if (j < 7) {
                            if (arr[i][j].equals("R")) {
                                redCount++;
                            } else if (arr[i][j].equals("-")) {
                                emptyCount++;
                            } else {
                                blueCount++;
                            }
                        } else
                            break;
                    }
                    toplamdeger = toplamdeger - choiceChair(toplamdeger, blueCount, emptyCount);
                    redCount = 0;
                    blueCount = 0;
                    emptyCount = 0;
                    i = oncekiI;
                    j = oncekiJ;
                    for (int a = 0; a < 4; j--, a++) { // sola
                        if (j >= 0) {
                            if (arr[i][j].equals("R")) {
                                redCount++;
                            } else if (arr[i][j].equals("-")) {
                                emptyCount++;
                            } else {
                                blueCount++;
                            }
                        } else
                            break;
                    }
                    toplamdeger -= choiceChair(toplamdeger, blueCount, emptyCount);
                    redCount = 0;
                    blueCount = 0;
                    emptyCount = 0;
                    i = oncekiI;
                    j = oncekiJ;
                }
            }
        }
        return toplamdeger;
    }

    private static int randomNumberGen() {
        return (int) (Math.random() * 100 + 1);
    }

    private static void createChildren(Node node, String color) {
        boolean leaf = false;
        for (int i = 0; i < 7; i++) {
            if (isValidMove(node.getState(), i)) {
                if (isEndOfGame((insertTable(node.getState(), color, i)), color))
                    leaf = true;

                node.getChilds().add(new Node(insertTable(node.getState(), color, i), node, i, leaf));
                leaf = false;
            }
        }
    }

    private static int alphaBeta(Node node, int depthLimit, int alpha, int beta, String color, int heuristicSelection) {
        boolean isMax = false;
        if (color.equals("R")) {
            isMax = true;
        }//chosing statements
        if (depthLimit == 0 || isEndOfGame(node.getState(), color) || node.isLeafNode()) {
            if (heuristicSelection == 1) {
                node.setValue(heuNormal(node.getState(), color));
            } else if (heuristicSelection == 2) {
                node.setValue(hueristicDumb(node.getState(), color));
            } else if (heuristicSelection == 3) {
                node.setValue(hueristic(node.getState(), color));
            } else if (heuristicSelection == 4) {
                node.setValue(heuNormal2(node.getState(), color));
            } else if (heuristicSelection == 5) {
                node.setValue(hueristicDumb2(node.getState(), color));
            }else if (heuristicSelection == 6) {
                node.setValue(hueristic2(node.getState(), color));
            }
            return node.getValue();
        }
/////////////////////////////////////////////  MAX PLAYER
        if (isMax) {
            int InfN = Integer.MIN_VALUE;
            // tüm valid çocukları oluşturcaz
            node.setValue(InfN);
            createChildren(node, "R");

            for (Node child : node.getChilds()) {
                // System.out.println("Çocuklar");

                node.setValue(Integer.max(node.getValue(),
                        alphaBeta(child, depthLimit - 1, alpha, beta, "B", heuristicSelection)));
                alpha = (Integer.max(alpha, node.getValue()));
                if (alpha >= beta) {
                    // System.out.println("BREAK");
                    break;
                }
            }

            int ret = 0;
            // System.out.println("MAX");
            // System.out.println("parentın value: " + node.getValue());
            for (Node child : node.getChilds()) {
                // System.out.println("child value: " + child.getValue());

                if (node.getValue() == child.getValue()) {
                    ret = child.getValue();

                }

            }

            return ret;
////////////////////////////////// MIN PLAYER
        } else {
            int InfP = Integer.MAX_VALUE;

            node.setValue(InfP);
            createChildren(node, "B");

            for (Node child : node.getChilds()) {
                // System.out.println("Çocuklar");

                node.setValue(Integer.min(node.getValue(),
                        alphaBeta(child, depthLimit - 1, alpha, beta, "R", heuristicSelection)));
                beta = (Integer.min(beta, node.getValue()));
                if (beta <= alpha)
                    break;
            }
            // System.out.println("MIN");
            int ret = 0;
            // System.out.println("parentın value: " + node.getValue());
            for (Node child : node.getChilds()) {
                // System.out.println("child value: " + child.getValue());
                if (node.getValue() == child.getValue()) {

                    ret = child.getValue();
                }

            }
            return ret;
        }
    }

    private static int heuNormal(String[][] arr, String color) {
        boolean bitis = isEndOfGame(arr, "R");
        boolean bitiskarsi = isEndOfGame(arr, "B");
        if (bitiskarsi) {
            return -10000;
        }
        if (bitis) {
            return 10000;
        }
        return randomNumberGen();
    }

    private static int heuNormal2(String[][] arr, String color) {
        boolean bitis = isEndOfGame(arr, "R");
        boolean bitiskarsi = isEndOfGame(arr, "B");
        if (bitiskarsi) {
            return -10000;
        }
        if (bitis) {
            return 10000;
        }
        return randomNumberGen();
    }

    private static int minimaxDecision(Node node, int depthLimit, String color, int heuristicSelection) {
        boolean isMax = false;
        if (color.equals("R")) {
            isMax = true;
        }
        if (depthLimit == 0 || isEndOfGame(node.getState(), color) || node.isLeafNode()) {
            if (heuristicSelection == 1) {
                node.setValue(heuNormal(node.getState(), color));
            } else if (heuristicSelection == 2) {
                node.setValue(hueristicDumb(node.getState(), color));
            } else if (heuristicSelection == 3) {
                node.setValue(hueristic(node.getState(), color));
            } else if (heuristicSelection == 4) {
                node.setValue(heuNormal2(node.getState(), color));
            } else if (heuristicSelection == 5) {
                node.setValue(hueristicDumb2(node.getState(), color));
            }else if (heuristicSelection == 6) {
                node.setValue(hueristic2(node.getState(), color));
            }
            return node.getValue();
        }
        if (isMax) {
            int InfN = Integer.MIN_VALUE;
            // tüm valid çocukları oluşturcaz
            node.setValue(InfN);
            createChildren(node, "R");
            for (Node child : node.getChilds()) {
                node.setValue(
                        Integer.max(node.getValue(), minimaxDecision(child, depthLimit - 1, "B", heuristicSelection)));
            }
            int ret = 0;
            for (Node child : node.getChilds()) {
                if (node.getValue() == child.getValue()) {
                    ret = child.getValue();
                }
            }
            return ret;
        } else {
            int InfP = Integer.MAX_VALUE;
            node.setValue(InfP);
            createChildren(node, "B");
            for (Node child : node.getChilds()) {
                node.setValue(
                        Integer.min(node.getValue(), minimaxDecision(child, depthLimit - 1, "R", heuristicSelection)));
            }
            int ret = 0;
            for (Node child : node.getChilds()) {
                if (node.getValue() == child.getValue()) {
                    ret = child.getValue();
                }
            }
            return ret;
        }
    }

    public static void main(String[] args) {
        // Game Board
        String[][] table = new String[6][7];
        System.out.println("Plase selecet desired option you want...\n" + "1) Human Player vs Human Player\n"
                + "2) Human Player vs AI Player\n" + "3) AI Player vs AI Player\n");
        Scanner scanWhoVsWho = new Scanner(System.in);//chosing statement
        int whoVsWho = scanWhoVsWho.nextInt();
        if (whoVsWho == 1) {//human player vs human player
            Node root = new Node(table, null, 0, false);
            insertDash(table);
            int gamePlay = 0;
            while (true) {
                if (gamePlay % 2 == 0) {//first player
                    System.out.println("\u001B[31m" + "RED Player's Turn" + "\u001B[0m");
                    Scanner scanner = new Scanner(System.in);
                    int row = scanner.nextInt();
                    table = insertTable(table, "R", row);//movement
                    printArray(table);
                    if (!isEndOfGame(table, "R") && isTie(table)) {
                        System.out.println("Draw !!! Friendship Won!!! Kol Bozuk!!!");
                        return;
                    }
                    if (isEndOfGame(table, "R")) {
                        System.out.println("Red Player Won!");
                        return;
                    }
                    root.setState(table);
                } else {//second player
                    System.out.println("\u001B[34m" + "BLUE Player's Turn" + "\u001B[0m");
                    Scanner scanner = new Scanner(System.in);
                    int row = scanner.nextInt();
                    table = insertTable(table, "B", row);//movement
                    printArray(table);
                    if (!isEndOfGame(table, "B") && isTie(table)) {
                        System.out.println("Draw !!! Friendship Won!!! Kol Bozuk!!!");
                        return;
                    }
                    if (isEndOfGame(table, "B")) {
                        System.out.println("Blue Player Won!");
                        return;
                    }
                    root.setState(table);
                }
                gamePlay++;
            }
        } else if (whoVsWho == 2) {//Human vs AI player


            System.out.println("Please select Algorithm...\n" +
                    "Your options are 1)Minimax Algorithm, 2)AlphaBeta Algorithm");
            Scanner selectionAlg = new Scanner(System.in);
            int selectionAlgorithm = selectionAlg.nextInt();
            System.out.println("Please select AI Player's Heuristic...\n" + "Your options are 1)Easy, 2)Medium, 3)Hard\n");
            Scanner scanHeu1 = new Scanner(System.in);
            int heu1 = scanHeu1.nextInt();

            System.out.println("Please write depth limit...");
            Scanner scanDepth = new Scanner(System.in);
            int depthLimit = scanDepth.nextInt();

            Node root = new Node(table, null, 0, false);
            insertDash(table);
            int gamePlay = 0;
            while (true) {
                if (gamePlay % 2 == 0) {
                    System.out.println("\u001B[31m" + "RED Player's Turn" + "\u001B[0m");
                    int row = 0;
                    if(selectionAlgorithm ==1) {
                        int value = minimaxDecision(root, depthLimit, "R",heu1);
                    }else {
                        int value = alphaBeta(root, depthLimit, Integer.MIN_VALUE, Integer.MAX_VALUE, "R", heu1);
                    }
                    for (Node child : root.getChilds()) {
                        if (root.getValue() == child.getValue()) {
                            row = child.getWhereIsMyParent();
                            break;
                        }
                    }
                    root.getChilds().clear();
                    System.out.println("row = " + row);
                    table = insertTable(table, "R", row);
                    printArray(table);
                    root.setState(table);
                    // is game finish or not and tie
                    if (!isEndOfGame(table, "R") && isTie(table)) {
                        System.out.println("Draw !!! Friendship Won!!! Kol Bozuk!!!");
                        return;
                    }
                    if (isEndOfGame(table, "R")) {
                        System.out.println("Red Player Won!");
                        return;
                    }
                } else {
                    System.out.println("\u001B[34m" + "BLUE Player's Turn" + "\u001B[0m");
                    Scanner scanner = new Scanner(System.in);
                    int row1 = scanner.nextInt();
                    table = insertTable(table, "B", row1);
                    printArray(table);
                    if (!isEndOfGame(table, "B") && isTie(table)) {
                        System.out.println("Draw !!! Friendship Won!!! Kol Bozuk!!!");
                        return;
                    }
                    if (isEndOfGame(table, "B")) {
                        System.out.println("Blue Player Won!");
                        return;
                    }
                    root.setState(table);
                }
                gamePlay++;
            }
        } else if (whoVsWho == 3) {//AI player vs AI player
            System.out.println("Please select Algorithm for 1. Ai Player...\n" +
                    "Your options are 1)Minimax Algorithm, 2)AlphaBeta Algorithm");
            Scanner selectionAlg = new Scanner(System.in);
            int selectionAlgorithm = selectionAlg.nextInt();
            System.out.println("Please select 1. AI Player's Heuristic...\n"
                    + "Your options are 1)Easy, 2)Medium, 3)Hard for each AI Player\n");
            Scanner scanHeu1 = new Scanner(System.in);
            int heu1 = scanHeu1.nextInt();

            System.out.println("Please write  depth limit of 1. AI Player...");
            Scanner scanDepth = new Scanner(System.in);
            int depthLimit = scanDepth.nextInt();


            System.out.println("Please select Algorithm for 2. Ai Player...\n" +
                    "Your options are 1)Minimax Algorithm, 2)AlphaBeta Algorithm");
            Scanner selectionAlg2 = new Scanner(System.in);
            int selectionAlgorithm2 = selectionAlg2.nextInt();
            System.out.println("Please select 2. AI Player's Heuristic...\n"
                    + "Your options are 1)Easy, 2)Medium, 3)Hard for each AI Player\n");

            Scanner scanHeu2 = new Scanner(System.in);
            int heu2 = scanHeu2.nextInt();
            heu2 += 3;

            System.out.println("Please write depth limit of 2. AI Player...");
            Scanner scanDepth2 = new Scanner(System.in);
            int depthLimit2 = scanDepth.nextInt();

            Node root = new Node(table, null, 0, false);

            insertDash(table);
            int gamePlay = 0;
            while (true) {
                if (gamePlay % 2 == 0) {
                    System.out.println("\u001B[31m" + "RED Player's Turn" + "\u001B[0m");
                    int row = 0;
                    if(selectionAlgorithm ==2) {//chosing algorithm one of them is alpha beta one of them is minimax
                        int value = alphaBeta(root, depthLimit, Integer.MIN_VALUE, Integer.MAX_VALUE, "R", heu1);
                    }else {
                        int value = minimaxDecision(root, depthLimit, "R",heu1);
                    }


                    for (Node child : root.getChilds()) {
                        if (root.getValue() == child.getValue()) {
                            row = child.getWhereIsMyParent();
                            break;
                        }
                    }
                    root.getChilds().clear();

                    System.out.println("row = " + row);
                    table = insertTable(table, "R", row);
                    printArray(table);
                    root.setState(table);
                    // oyun bitti mi bitmedi mi?
                    if (!isEndOfGame(table, "R") && isTie(table)) {
                        System.out.println("Draw !!! Friendship Won!!! Kol Bozuk!!!");
                        return;
                    }
                    if (isEndOfGame(table, "R")) {
                        System.out.println("Red Player Won!");
                        return;
                    }
                } else {
                    System.out.println("\u001B[34m" + "BLUE Player's Turn" + "\u001B[0m");
                    int row = 0;
                    if(selectionAlgorithm2 ==2) {//chosing algorithm one of them is alpha beta one of them is minimax
                        int value = alphaBeta(root, depthLimit2, Integer.MIN_VALUE, Integer.MAX_VALUE, "B", heu2);
                    }else {
                        int value = minimaxDecision(root, depthLimit, "B",heu2);
                    }
                    for (Node child : root.getChilds()) {
                        if (root.getValue() == child.getValue()) {//chosing row with tree
                            row = child.getWhereIsMyParent();
                            break;
                        }
                    }

                    root.getChilds().clear();
                    System.out.println("row = " + row);
                    table = insertTable(table, "B", row);
                    printArray(table);
                    root.setState(table);
                    // is game finish or not and tie
                    if (!isEndOfGame(table, "B") && isTie(table)) {
                        System.out.println("Draw !!! Friendship Won!!!");
                        return;
                    }
                    if (isEndOfGame(table, "B")) {
                        System.out.println("Blue Player Won!");
                        return;
                    }

                }
                gamePlay++;

            }
        }
    }

}
