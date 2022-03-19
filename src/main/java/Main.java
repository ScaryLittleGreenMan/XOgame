import java.util.Random;
import java.util.Scanner;

public class Main {

    public static char[][] map;
    public static final int SIZE = 5;
    public static final int DOTS_TO_WIN = 3;
    public static final char DOT_X = 'X';
    public static final char DOT_O = 'O';
    public static final char DOT_EMPTY = '*';

    public static Scanner scanner = new Scanner(System.in);
    public static Random random = new Random();

    public static void main(String[] args) {
        System.out.println("IT'S TIME TO PLAY A GAME!");
        initMap();
        printMap();
        while (true) {
            humanTurn();
            printMap();
            if (checkWinLines(DOT_X)) {
                System.out.println("Кожаный мешок победил");
                break;
            }
            if (isMapFull()) {
                System.out.println("Ничья");
                break;
            }
            aiTurn();
            printMap();
            if (checkWinLines(DOT_O)) {
                System.out.println("Великая компутера победила");
                break;
            }
            if (isMapFull()) {
                System.out.println("Ничья");
                break;
            }
        }
        System.out.println("GAME OVER");

    }

    public static void initMap() { //инициализация карты
        map = new char[SIZE][SIZE];
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                map[i][j] = DOT_EMPTY;
            }
        }
    }

    public static void printMap() { //печать карты
        for (int i = 0; i <= SIZE; i++) {
            System.out.print(i + " ");
        }
        System.out.println();
        for (int i = 0; i < SIZE; i++) {
            System.out.print((i + 1) + " ");
            for (int j = 0; j < SIZE; j++) {
                System.out.print(map[i][j] + " ");
            }
            System.out.println();
        }
        System.out.println();
    }

    public static void humanTurn() { //ход кожаного мешка
        int x;
        int y;
        do {
            System.out.println("Кожаный мешок, введи координаты сначала: столбец, потом строка");
            x = scanner.nextInt() - 1;
            y = scanner.nextInt() - 1;
        } while (!isCellValid(x, y)); // while(isCellValid(x, y) == false)
        map[y][x] = DOT_X;
    }

    public static void aiTurn() { //ai turn
        int x;
        int y;
        //попытка победить самому
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                if (isCellValid(i, j)) {
                    map[j][i] = DOT_O;
                    if (checkWinLines(DOT_O)) {
                        return;
                    }
                    map[j][i] = DOT_EMPTY;
                }
            }
        }

        //сбить победную линию противника, если остался 1 ход до победы
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                if (isCellValid(i, j)) {
                    map[j][i] = DOT_X;
                    if (checkWinLines(DOT_X)) {
                        map[j][i] = DOT_O;
                        return;
                    }
                    map[j][i] = DOT_EMPTY;
                }
            }
        }

        //сбить ход противника, если осталось 2 хода до победы
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                if (isCellValid(i, j)) {
                    map[j][i] = DOT_X;
                    if (checkWinLines(DOT_X, DOTS_TO_WIN - 1) && Math.random() < 0.5) {// вход в перегруженный метод+ рандом для случайности действий компа
                        map[j][i] = DOT_O;
                        return;
                    }
                    map[j][i] = DOT_EMPTY;
                }
            }
        }

        //ход в рандомную точку, сюда поидее не должен дойти
        do {
            x = random.nextInt(SIZE);
            y = random.nextInt(SIZE);
        } while (!isCellValid(x, y));
        map[y][x] = DOT_O;
    }

    public static boolean isCellValid(int x, int y) {
        if (x < 0 || x >= SIZE || y < 0 || y >= SIZE) return false;
        if (map[y][x] == DOT_EMPTY) return true;
        return false;
    }

    public static boolean isMapFull() {
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                if (map[i][j] == DOT_EMPTY) return false;
            }
        }
        return true;
    }

    static boolean checkWinLines(char dot) { //win method
        return checkWinLines(dot, DOTS_TO_WIN);
    }

    static boolean checkLine(int cy, int cx, int vy, int vx, char dot, int dotsToWin) { //overload check line method
        if (cx + vx * (dotsToWin - 1) > SIZE - 1 || cy + vy * (dotsToWin - 1) > SIZE - 1 || cy + vy * (dotsToWin - 1) < 0) {
            return false;
        }
        for (int i = 0; i < dotsToWin; i++) {
            if (map[cy + i * vy][cx + i * vx] != dot) {
                return false;
            }
        }
        return true;
    }

    static boolean checkWinLines(char dot, int dotsToWin) { //overload win method
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                if (checkLine(i, j, 0, 1, dot, dotsToWin) || checkLine(i, j, 1, 0, dot, dotsToWin) || checkLine(i, j, 1, 1, dot, dotsToWin) || checkLine(i, j, -1, 1, dot, dotsToWin)) {
                    return true;
                }
            }
        }
        return false;
    }
}
