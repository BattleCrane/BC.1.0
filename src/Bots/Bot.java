package Bots;

import BattleFields.Point;
import Players.Player;

import java.util.List;

public interface Bot {
    boolean isWinnerTurn(Player player);

    List<Point> probeSafePoints(Player player);

    int probeMaxStep(Player player);

    int isGeneratorUnderDangerous(Player player);

    List<Integer> probeDangerousFromBonuses(Player player);



}
