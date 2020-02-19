package konstantin_izyurov;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class PlayerTwo extends Player {
    public PlayerTwo(String name) {
        super(name);
    }

    @Override
    boolean canMove(ArrayList<Chip> chips) {
        Chip chip = chips.get(0);   // У игрока 2 всего одна фишка
        boolean result = false;
        if (Map.cellIsFree(chip.getX()-1, chip.getY())) result = true;      // Свободна ли клетка слева
        else if (Map.cellIsFree(chip.getX()+1, chip.getY())) result = true; // Свободна ли клетка справа
        else if (Map.cellIsFree(chip.getX(), chip.getY()-1)) result = true; // Свободна ли клетка сверху
        else if (Map.cellIsFree(chip.getX(), chip.getY()+1)) result = true; // Свободна ли клетка снизу
        else if ((chip.getY() == 0) && (Map.cellIsFree(0, 1) || Map.cellIsFree(2, 1))) result = true; // Если фишка в самом верху, свободны ли клетки по диагонали вниз
        // Если выполнится хотя бы одно из условий, значит у игрока есть ходы
        return result;
    }

    @Override
    void go(ArrayList<Chip> chips) {
        Chip chip = chips.get(0);   // У игрока 2 всего одна фишка
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        boolean exit = false;
        int direction = 0;
        while (!exit) {
            System.out.print("Введите направление движения: 4 - влево, 6 - вправо, 8 - вверх, 2 - вниз, 1 и 3 - вниз по диагонали (только если фишка в самом верху)  ");
            try {
                String inp = reader.readLine();
                if (inp.equals(Game.COMMAND_STOP)) {
                    Game.endGame = true;
                    System.out.println("Выход из игры");
                    System.exit(0);
                }
                direction = Integer.parseInt(inp);
            } catch (IOException e) {
                e.printStackTrace();
            } catch (NumberFormatException e) {
                //
            }
            if (direction < 1 || direction == 5 || direction == 7 || direction > 8) {
                System.out.println("Нужно ввести число 1-4, 6, 8, либо команду " + Game.COMMAND_STOP + " для выхода");
            } else {
                int x = chip.getX();
                int y = chip.getY();
                if (direction == 2) {          // Движение фишки вниз
                    if (Map.cellIsFree(x,y + 1)) {
                        Map.setValue(x, y, Map.VALUE_EMPTY);
                        y++;
                        chip.setY(y);
                        Map.setValue(x, y, Map.VALUE_PLAYER_STAR);
                        exit = true;
                    } else System.out.println("Нельзя туда ходить!");
                } else if (direction == 8) {    // Движение фишки вверх
                    if (Map.cellIsFree(x, y - 1)) {
                        Map.setValue(x, y, Map.VALUE_EMPTY);
                        y--;
                        chip.setY(y);
                        Map.setValue(x, y, Map.VALUE_PLAYER_STAR);
                        exit = true;
                    } else System.out.println("Нельзя туда ходить!");
                } else if (direction == 4) {    // Движение фишки влево
                    if (Map.cellIsFree(x - 1, y)) {
                        Map.setValue(x, y, Map.VALUE_EMPTY);
                        x--;
                        chip.setX(x);
                        Map.setValue(x, y, Map.VALUE_PLAYER_STAR);
                        exit = true;
                    } else System.out.println("Нельзя туда ходить!");
                } else if (direction == 6) {    // Движение фишки вправо
                    if (Map.cellIsFree(x + 1, y)) {
                        Map.setValue(x, y, Map.VALUE_EMPTY);
                        x++;
                        chip.setX(x);
                        Map.setValue(x, y, Map.VALUE_PLAYER_STAR);
                        exit = true;
                    } else System.out.println("Нельзя туда ходить!");
                } else if (direction == 1 && y == 0) {  // Движение фишки по диагонали влево
                    if (Map.cellIsFree(0, 1)) {
                        Map.setValue(x, y, Map.VALUE_EMPTY);
                        x--; y++;
                        chip.setX(x); chip.setY(y);
                        Map.setValue(x, y, Map.VALUE_PLAYER_STAR);
                        exit = true;
                    } else System.out.println("Нельзя туда ходить!");
                } else if (direction == 3 && y == 0) {  // Движение фишки по диагонали вправо
                    if (Map.cellIsFree(2, 1)) {
                        Map.setValue(x, y, Map.VALUE_EMPTY);
                        x++; y++;
                        chip.setX(x); chip.setY(y);
                        Map.setValue(x, y, Map.VALUE_PLAYER_STAR);
                        exit = true;
                    } else System.out.println("Нельзя туда ходить!");
                } else System.out.println("По диагонали можно ходить только с самой  верхней клетки.");
            }
        }
    }
}
