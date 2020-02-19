package konstantin_izyurov;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class PlayerOne extends Player {

    public PlayerOne(String name) {
        super(name);
    }

    @Override
    boolean canMove(ArrayList<Chip> chips) {
        boolean result = false;
        for (int i = 0; i < chips.size(); i++) {
            if (Map.cellIsFree(chips.get(i).getX(), chips.get(i).getY()-1)) result = true;         // Можно ли ходить фишками вверх
            else if (i == 0 && chips.get(0).getY() == 1 && Map.cellIsFree(1, 0)) result = true; // Если левая фишка вверху, можно ли ей сходить по диагонали вправо
            else if (i == 2 && chips.get(2).getY() == 1 && Map.cellIsFree(1, 0)) result = true; // Если правая фишка вверху, можно ли ей сходить по диагонали влево
            // Если выполнится хотя бы одно из условий, значит у игрока есть ходы
        }
        return result;
    }

    @Override
    void go(ArrayList<Chip> chips) {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        int chipNumber = 0;  // номер фишки, которой будет ходить игрок
        boolean exit = false;
        while (!exit) {
            System.out.print("Введите номер фишки, которой нужно ходить (1-3): ");
            try {
                String inp = reader.readLine();
                if (inp.equals(Game.COMMAND_STOP)) {
                    Game.endGame = true;
                    System.out.println("Выход из игры");
                    System.exit(0);
                }
                chipNumber = Integer.parseInt(inp);
            } catch (IOException e) {
                e.printStackTrace();
            } catch (NumberFormatException e) {
                //
            }
            if (1 <= chipNumber && chipNumber <= 3) {
                int x = chips.get(chipNumber-1).getX();
                int y = chips.get(chipNumber-1).getY();
                if (Map.cellIsFree(x,y-1)) {             // Двигам фишку вверх
                    Map.setValue(x, y, Map.VALUE_EMPTY);
                    y--;
                    chips.get(chipNumber-1).setY(y);
                    Map.setValue(x, y, Map.VALUE_PLAYER_X);
                    exit = true;
                }
                else if ((chipNumber == 1 || chipNumber == 3) && y == 1 && Map.cellIsFree(1, 0)) {  // Двигаем фишку по диагонали вверх
                    Map.setValue(x, y, Map.VALUE_EMPTY);
                    chips.get(chipNumber-1).setX(1);
                    chips.get(chipNumber-1).setY(0);
                    Map.setValue(1, 0, Map.VALUE_PLAYER_X);
                    exit = true;
                }
                else System.out.println("Нельзя ходить этой фишкой в данный момент!");
            } else System.out.println("Нужно ввести число от 1 до 3, либо команду " + Game.COMMAND_STOP + " для выхода");
        }
    }
}
