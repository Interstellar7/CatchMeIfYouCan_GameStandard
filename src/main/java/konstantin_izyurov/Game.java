package konstantin_izyurov;

import java.io.*;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Scanner;

/**
 * Основной класс игры. Содержит главный цикл.
 */
public class Game {
    public static int turn = 0;             // Номер хода для логгирования
    public static boolean endGame = false;  // Флаг окончания игры для выхода из главного цикла
    PlayerOne player1;      // Игроки
    PlayerTwo player2;
    // Создаём игровые фишки
    static Chip chipX1 = new Chip(0, Map.Y_SIZE-2);
    static Chip chipX2 = new Chip(1, Map.Y_SIZE-1);
    static Chip chipX3 = new Chip(2, Map.Y_SIZE-2);
    static ArrayList<Chip> chipsX = new ArrayList<>(3);     // Массив фишек Игрока 1
    static Chip chipStar = new Chip(1, 2);
    static ArrayList<Chip> chipsStar = new ArrayList<>(1);  // Массив фишек Игрока 2 с одним элементом

    // Основные команды для консоли
    static final String COMMAND_START = "start";
    static final String COMMAND_RULES = "rules";
    static final String COMMAND_STOP = "stop";
    // Имена игроков по умолчанию
    static final String DEFAULT_PLAYER1_NAME = "Игрок1";
    static final String DEFAULT_PLAYER2_NAME = "Игрок2";
    static {
        chipsX.add(chipX1);
        chipsX.add(chipX2);
        chipsX.add(chipX3);
        chipsStar.add(chipStar);
    }

    public Game() {
    }

    // Метод возвращает координату самой нижней фишки игрока 1 (для победы игрока 2)
    public int yCoordOfTheLowestChipX() {
        int max = 0;
        for (Chip x : chipsX) {
            if (x.getY() > max) max = x.getY();
        }
        return max;
    }

    // Основной метод - "контроллер" игры.
    void runGame() {
        System.out.println("Начинаем игру!");
        Map.printMap();
        while(!endGame) {                       // Главный цикл игры
            turn++;
                // Ход Игрока 1
            System.out.println("Ход " + Game.turn + ". Ходит игрок 1 - " + player1.getName());
            if (player1.canMove(chipsX)) {
                player1.go(chipsX);
                Map.printMap();
            }
            else System.out.println(player1.getName() + " пропускает ход, так как некуда ходить");
            if (chipStar.getY() > yCoordOfTheLowestChipX()) {      // Условие победы Игрока 2
                System.out.println("Фишка игрока 2 оказалась ниже всех фишек первого игрока.");
                System.out.println("ПОБЕДИЛ ИГРОК 2 " + player2.getName() + "! ПОЗДРАВЛЯЕМ С ПОБЕДОЙ!!!");
                endGame = true;
            }
                // Ход Игрока 2
            if (!endGame) {
                System.out.println("Ход " + Game.turn + ". Ходит игрок 2 - " + player2.getName());
                if (player2.canMove(chipsStar)) {
                    player2.go(chipsStar);
                    Map.printMap();
                } else {                                      // Условие победы Игрока 1
                    System.out.println("Игроку 2 некуда ходить!");
                    System.out.println("ПОБЕДИЛ ИГРОК 1 " + player1.getName() + "! ПОЗДРАВЛЯЕМ С ПОБЕДОЙ!!!");
                    endGame = true;
                }
                if (chipStar.getY() > yCoordOfTheLowestChipX()) {      // Условие победы Игрока 2
                    System.out.println("Фишка игрока 2 оказалась ниже всех фишек первого игрока.");
                    System.out.println("ПОБЕДИЛ ИГРОК 2 " + player2.getName() + "! ПОЗДРАВЛЯЕМ С ПОБЕДОЙ!!!");
                    endGame = true;
                }
            }
        }
        System.out.println("Конец игры");
    }

    // Инициализация игры и начало
    void initGame() {
        System.out.println("Добро пожаловать в консольную игру \"Поймай меня, если сможешь!\"");
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        String name1 = DEFAULT_PLAYER1_NAME;
        String name2 = DEFAULT_PLAYER2_NAME;
        try {
            System.out.print("Введите имя игрока 1: ");
            name1 = reader.readLine();
            System.out.print("Введите имя игрока 2: ");
            name2 = reader.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
        player1 = new PlayerOne(name1);
        player2 = new PlayerTwo(name2);

        String command = "";
        while (true) {
            System.out.printf("Введите команду %s для начала игры, %s - для вывода правил игры.\n", COMMAND_START, COMMAND_RULES);
            System.out.printf("Игру можно остановить в любой момент командой %s\n", COMMAND_STOP);
            try {
                command = reader.readLine();
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (command.equals(COMMAND_RULES)) {
                // Выводим правила игры из файла
                ClassLoader classLoader = getClass().getClassLoader();
                File file = new File(Objects.requireNonNull(classLoader.getResource("rules.txt")).getFile());
                try {
                    Scanner scanner = new Scanner(file);
                    while (scanner.hasNextLine())  System.out.println(scanner.nextLine());
                } catch (FileNotFoundException e) {
                    System.out.println("Проблемы с загрузкой файла \"rules.txt\"");
                }
                System.out.println();
            }
            if (command.equals(COMMAND_STOP)) {
                System.out.println("Игра остановлена");
                System.exit(0);
            }
            if (command.equals(COMMAND_START)) return;
        }
    }
}
