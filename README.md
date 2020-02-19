Этот проект является вступительным заданием на JAVA-интенсив в Voronezh IT Academy (VITA).
<br>Это базовая версия консольной игры «Поймай меня, если сможешь!»

ЗАДАЧА. Реализовать в виде консольного приложения следующую игру.
На поле следующего вида:
<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; [&nbsp;&nbsp;]
<br>&nbsp; [&nbsp;&nbsp;] [&nbsp;&nbsp;] [&nbsp;&nbsp;]
<br>&nbsp; [&nbsp;&nbsp;] [\*] [&nbsp;&nbsp;]
<br>&nbsp; [X] [&nbsp;&nbsp;] [X]
<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; [X]
<br> расположены 4 фигуры: 3 фигуры 1-го игрока (обозначены “X”) и одна фигура 2-го игрока (обозначена “*”).
Задача второго игрока добраться до самой нижней клетки игрового поля, а первого не допустить этого. При этом:
1) Фигуры первого игрока могут двигаться только вверх (с двух верхних боковых клеток по диагонали)
2) Фигурка второго игрока может двигаться вверх-вниз-влево-вправо и с самой верхней позиции по диагонали
3) Нельзя делать ход на уже занятую клетку
4) Первым всегда ходит первый игрок
5) Игра заканчивается, если фигура второго игрока окажется ниже всех фигур первого игрока, либо у него не будет возможности сделать ход.<br>