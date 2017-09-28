package BattleFields;


import Players.Player;
import Unities.Unity;
import javafx.scene.layout.Pane;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Класс ControlBattler контролирует внутреннее состояние процесса игры.
 * Он хранит в себе все двух игроков, все типы юнитов.
 * Выполняет методы:
 * 1.) Инициализация (стартовая позиция игроков);
 * 2.) Размещение юнитов на поле боя;
 * --- Проверка территории на пустоту;
 * 3.) Удаление юнитов с поля боя;
 * 4.) Создание нового хода:
 * --- Смена игрока;
 * --- Отображение активных строений;
 * --- Отображение активных боевых единиц;
 * --- Отображение возможных построек;
 * --- Отображение возможных автоматчиков;
 * --- Отображение возможных танков.
 * 5.) Отрисовка территорий
 */
//Контроллер игры, делегат
public class ControlBattler {

    //Основные переменные:
    private Pane paneBattle;
    private BattleField battleField = new BattleField();
    private int turn = 1;

    //Игроки:
    private Player playerBlue = new Player(0, 0, "+");
    private Player playerRed = new Player(1, 1, "-");
    private Player player;
    private Player opponentPlayer;

    //Юниты:
    //Строения:
    private int howICanBuild;
    //Штаб:
    private Unity headquarters = new Unity(2, 2, "h", 8);
    //Бараки:
    private Unity barracksVertical = new Unity(2, 1, "b", 1);
    private Unity barracksHorisontal = new Unity(1, 2, "b", 1);
    //Генератор:
    private Unity generator = new Unity(2, 2, "g", 1);
    private boolean isConstructedGenerator;
    //Завод:
    private Unity factoryVertical = new Unity(3, 2, "f", 1);
    private Unity factoryHorisontal = new Unity(2, 3, "f", 1);
    //Турель:
    private Unity turret = new Unity(1, 1, "t", 1);
    //Стена:
    private Unity wall = new Unity(1, 1, "w", 4);

    //Армия:
    private int howICanProductArmy;
    private int howICanProductTanks;
    private Unity gunner = new Unity(1, 1, "G", 1);
    private Unity tank = new Unity(1, 1, "T", 2);

    public ControlBattler(BattleField battleField) {
        this.battleField = battleField;
    }

    public ControlBattler() {

    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    public void initializeField() {
        //Размещение штабов:
        putUnity(playerRed, new Point(0, 0), headquarters);
        putUnity(playerBlue, new Point(14, 14), headquarters);

        //Размещение стен:
        for (int j = 0; j < 5; j++) {
            putUnity(playerRed, new Point(j, 4), wall);
            putUnity(playerRed, new Point(4, j), wall);

            putUnity(playerBlue, new Point(15 - j, 11), wall);
            putUnity(playerBlue, new Point(11, 15 - j), wall);
        }
        nextTurnOfCurrentPlayer();
    }
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    public boolean putUnity(Player player, Point point, Unity unity) {
        if (isEmptyTerritory(point, unity)) {
            for (int i = point.getX(); i < point.getX() + unity.getWidth(); i++) {
                for (int j = point.getY(); j < point.getY() + unity.getHeight(); j++) {
                    String newUnity = unity.getHitPoints() + "^" + "?" + player.getColorType() + unity.getId();
                    if (i == point.getX() && j == point.getY()) {
                        newUnity = newUnity + "'";
                    } else {
                        newUnity = newUnity + ".";
                    }
                    battleField.getMatrix().get(i).set(j, newUnity);
                }
            }
            return true;
        } else {
            return false;
        }
    }

    private Boolean isEmptyTerritory(Point point, Unity unity) {
        if (point.getX() + unity.getWidth() > 16 || point.getY() + unity.getHeight() > 16) {
            return false;
        }
        for (int i = point.getX(); i < point.getX() + unity.getWidth(); i++) {
            for (int j = point.getY(); j < point.getY() + unity.getHeight(); j++) {
                if (!this.battleField.getMatrix().get(i).get(j).equals("     0")) {
                    return false;
                }
            }
        }
        return true;
    }

    //Проверка расположения рядом строения:
    public boolean checkConstructionOfBuilding(Point point, Unity unity, Player player) {
        int startPointX = point.getX() - 1; //Сдвигаем начальную точку в левый верхний угол (Тут ошибка в проектировании осей)
        int startPointY = point.getY() - 1;
        Pattern pattern = Pattern.compile("[hgbfwt]");

        for (int i = startPointX; i <= startPointX + unity.getWidth() + 1; i++) {
            for (int j = startPointY; j <= startPointY + unity.getHeight() + 1; j++) {
                if (i >= 0 && i < 16 && j >= 0 && j < 16) {
                    Matcher matcher = pattern.matcher(battleField.getMatrix().get(i).get(j));
                    if (battleField.getMatrix().get(i).get(j).contains(player.getColorType()) && matcher.find()) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public boolean upgradeBuilding(Point point, Player player) { //Здесь я остановился:
        boolean isUpgraded = false;
        List<String> list = battleField.getMatrix().get(point.getX());
        int y = point.getY();
        Unity unityBuild = new Unity(list.get(y));
        if (isFindUnity(point) && list.get(y).contains(player.getColorType())) {
            System.out.println(list.get(y).substring(4, 5));
            switch (list.get(y).substring(4, 5)) { //Смотрим строение:
                case "g": //Улучшение генератора:
                    if (levelUp(unityBuild)) { //Необходимо переписать матрицу
                        if (unityBuild.getId().contains("<")) {
                            try {
                                int upgradedHitPoints = Integer.parseInt(unityBuild.getId().substring(0, 1)) + 1;
                                unityBuild.setId(upgradedHitPoints + unityBuild.getId().substring(1));
                            } catch (Exception ignored) {
                            }
                        }
                        if (unityBuild.getId().contains(">")) {
                            try {
                                int upgradedHitPoints = Integer.parseInt(unityBuild.getId().substring(0, 1)) + 2;
                                unityBuild.setId(upgradedHitPoints + unityBuild.getId().substring(1));
                            } catch (Exception ignored) {
                            }
                        }
                    }
                    isUpgraded = true;
                    break;
                case "b": //Улучшение бараков:
                    if (levelUp(unityBuild)) {
                        if (unityBuild.getId().contains("<")) {
                            try {
                                int upgradedHitPoints = Integer.parseInt(unityBuild.getId().substring(0, 1)) + 1;
                                unityBuild.setId(upgradedHitPoints + unityBuild.getId().substring(1));
                            } catch (Exception ignored) {
                            }
                        }
                        if (unityBuild.getId().contains(">")) {
                            try {
                                int upgradedHitPoints = Integer.parseInt(unityBuild.getId().substring(0, 1)) + 2;
                                unityBuild.setId(upgradedHitPoints + unityBuild.getId().substring(1));
                            } catch (Exception ignored) {
                            }
                        }
                    }
                    isUpgraded = true;
                    break;
                case "f": //Улучшение завода:
                    if (levelUp(unityBuild)) {
                        if (unityBuild.getId().contains("<")) {
                            try {
                                int upgradedHitPoints = Integer.parseInt(unityBuild.getId().substring(0, 1)) + 3;
                                unityBuild.setId(upgradedHitPoints + unityBuild.getId().substring(1));
                            } catch (Exception ignored) {
                            }
                        }
                        if (unityBuild.getId().contains(">")) {
                            try {
                                int upgradedHitPoints = Integer.parseInt(unityBuild.getId().substring(0, 1)) + 2;
                                unityBuild.setId(upgradedHitPoints + unityBuild.getId().substring(1));
                            } catch (Exception ignored) {
                            }
                        }
                    }
                    isUpgraded = true;
                    break;
                case "t": //Улучшение туррели:
                    if (levelUp(unityBuild) && unityBuild.getId().contains("<")) {
                            try {
                                int upgradedHitPoints = Integer.parseInt(unityBuild.getId().substring(0, 1)) + 1;
                                unityBuild.setId(upgradedHitPoints + unityBuild.getId().substring(1));
                            } catch (Exception ignored) {
                            }
                    }
                    isUpgraded = true;
                    break;
            }
        }
        battleField.getMatrix().get(point.getX()).set(y, unityBuild.getId());

        return isUpgraded;
    }

    //Проверка MouseClicked на Unity
    private boolean isFindUnity(Point point) {
        return battleField.getMatrix().get(point.getX()).get(point.getY()).contains("'");
    }

    public boolean levelUp(Unity unity) {
        boolean isUpgraded = false;
        System.out.println(unity.getId().substring(1, 2));
        switch (unity.getId().substring(1, 2)) {
            case "^":
                unity.setId(unity.getId().substring(0, 1) + '<' + unity.getId().substring(2));
                isUpgraded = true;
                break;
            case "<":
                unity.setId(unity.getId().substring(0, 1) + '>' + unity.getId().substring(2));
                isUpgraded = true;
                break;
        }
        return isUpgraded;
    }


    public void deleteUnity(Point point, Unity unity) {


    }

    public void correctTerritory() {

    }


    //После нажатия закончить ход
    public void nextTurnOfCurrentPlayer() {
        turn = (turn + 1) % 2;
        whatTurn();
        showReadyArmy(player);
        showReadyBuilding(player);
        getHowCanBuild(player);
        getHowCanProductArmy(player);
        getHowCanProductTanks(player);
        isConstructedGenerator = false;
    }

    private void whatTurn() {
        if (turn == playerBlue.getTurn()) {
            player = playerBlue;
            opponentPlayer = playerRed;
        } else {
            player = playerRed;
            opponentPlayer = playerBlue;
        }
    }


    private void showReadyArmy(Player player) {
        Pattern pattern = Pattern.compile("[GTt]");
        for (int i = 0; i < 16; i++) {
            for (int j = 0; j < 16; j++) {
                Matcher matcher = pattern.matcher(battleField.getMatrix().get(i).get(j));
                List<String> list = battleField.getMatrix().get(i);
                if (matcher.find() && list.contains(player.getColorType())) {
                    String readyUnity = list.get(j).substring(0, 2) + "!" + list.get(j).substring(3);
                    battleField.getMatrix().get(i).set(j, readyUnity);
                }
            }
        }
    }

    private void showReadyBuilding(Player player) {
        Pattern pattern = Pattern.compile("[bf]'");
        for (int i = 0; i < 16; i++) {
            for (int j = 0; j < 16; j++) {
                Matcher matcher = pattern.matcher(battleField.getMatrix().get(i).get(j));
                List<String> list = battleField.getMatrix().get(i);
                if (matcher.find() && list.contains(player.getColorType())) {
                    String readyUnity = list.get(j).substring(0, 2) + "!" + list.get(j).substring(3);
                    battleField.getMatrix().get(i).set(j, readyUnity);
                }
            }
        }
    }

    private void getHowCanBuild(Player player) {
        Pattern pattern = Pattern.compile("g'");
        howICanBuild = 1;
        for (int i = 0; i < 16; i++) {
            for (int j = 0; j < 16; j++) {
                List<String> list = battleField.getMatrix().get(i);
                Matcher matcher = pattern.matcher(list.get(j));
                if (matcher.find() && list.get(j).contains(player.getColorType())) {
                    String readyUnity = list.get(j).substring(0, 2) + "!" + list.get(j).substring(3);
                    battleField.getMatrix().get(i).set(j, readyUnity);
                    howICanBuild++;
                }
            }
        }
    }

    private void getHowCanProductArmy(Player player) {
        Pattern pattern = Pattern.compile("[!?][+-]b'");
        howICanProductArmy = 0;
        for (int i = 0; i < 16; i++) {
            for (int j = 0; j < 16; j++) {
                List<String> list = battleField.getMatrix().get(i);
                Matcher matcher = pattern.matcher(list.get(j));
                if (matcher.find() && list.get(j).contains(player.getColorType())) {
                    howICanProductArmy++;
                }
            }
        }
    }

    private void getHowCanProductTanks(Player player) {
        Pattern pattern = Pattern.compile("[!?][+-]f'");
        howICanProductTanks = 0;
        for (int i = 0; i < 16; i++) {
            for (int j = 0; j < 16; j++) {
                List<String> list = battleField.getMatrix().get(i);
                Matcher matcher = pattern.matcher(list.get(j));
                if (matcher.find() && list.get(j).contains(player.getColorType())) {
                    howICanProductTanks++;
                }
            }
        }
    }

    public int getHowCanBuildFactories() {
        return howICanProductArmy - howICanProductTanks;
    }


    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    public BattleField getBattleField() {
        return battleField;
    }

    public void setBattleField(BattleField battleField) {
        this.battleField = battleField;
    }

    public Pane getPaneBattle() {
        return paneBattle;
    }

    public void setPaneBattle(Pane paneBattle) {
        this.paneBattle = paneBattle;
    }

    public int getTurn() {
        return turn;
    }

    public void setTurn(int turn) {
        this.turn = turn;
    }

    public Player getPlayerBlue() {
        return playerBlue;
    }

    public void setPlayerBlue(Player playerBlue) {
        this.playerBlue = playerBlue;
    }

    public Player getPlayerRed() {
        return playerRed;
    }

    public void setPlayerRed(Player playerRed) {
        this.playerRed = playerRed;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public Player getOpponentPlayer() {
        return opponentPlayer;
    }

    public void setOpponentPlayer(Player opponentPlayer) {
        this.opponentPlayer = opponentPlayer;
    }

    public Unity getHeadquarters() {
        return headquarters;
    }

    public void setHeadquarters(Unity headquarters) {
        this.headquarters = headquarters;
    }

    public Unity getBarracksVertical() {
        return barracksVertical;
    }

    public void setBarracksVertical(Unity barracksVertical) {
        this.barracksVertical = barracksVertical;
    }

    public Unity getBarracksHorisontal() {
        return barracksHorisontal;
    }

    public void setBarracksHorisontal(Unity barracksHorisontal) {
        this.barracksHorisontal = barracksHorisontal;
    }

    public Unity getGenerator() {
        return generator;
    }

    public void setGenerator(Unity generator) {
        this.generator = generator;
    }

    public Unity getFactoryVertical() {
        return factoryVertical;
    }

    public void setFactoryVertical(Unity factoryVertical) {
        this.factoryVertical = factoryVertical;
    }

    public Unity getFactoryHorisontal() {
        return factoryHorisontal;
    }

    public void setFactoryHorisontal(Unity factoryHorisontal) {
        this.factoryHorisontal = factoryHorisontal;
    }

    public Unity getTurret() {
        return turret;
    }

    public void setTurret(Unity turret) {
        this.turret = turret;
    }

    public Unity getWall() {
        return wall;
    }

    public void setWall(Unity wall) {
        this.wall = wall;
    }

    public Unity getGunner() {
        return gunner;
    }

    public void setGunner(Unity gunner) {
        this.gunner = gunner;
    }

    public Unity getTank() {
        return tank;
    }

    public void setTank(Unity tank) {
        this.tank = tank;
    }

    public int getHowICanBuild() {
        return howICanBuild;
    }

    public void setHowICanBuild(int howICanBuild) {
        this.howICanBuild = howICanBuild;
    }

    public int getHowICanProductArmy() {
        return howICanProductArmy;
    }

    public void setHowICanProductArmy(int howICanProductArmy) {
        this.howICanProductArmy = howICanProductArmy;
    }

    public int getHowICanProductTanks() {
        return howICanProductTanks;
    }

    public void setHowICanProductTanks(int howICanProductTanks) {
        this.howICanProductTanks = howICanProductTanks;
    }

    public boolean isConstructedGenerator() {
        return isConstructedGenerator;
    }

    public void setConstructedGenerator(boolean constructedGenerator) {
        isConstructedGenerator = constructedGenerator;
    }
}

