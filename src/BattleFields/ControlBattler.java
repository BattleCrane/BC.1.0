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
 * а.) Проверка территории на пустоту;
 * 3.) Удаление юнитов с поля боя;
 * 4.) Создание нового хода:
 * а.) Смена игрока;
 * б.) Отображение активных строений;
 * в.) Отображение активных боевых единиц;
 * г.) Отображение возможных построек;
 * д.) Отображение возможных автоматчиков;
 * е.) Отображение возможных танков.
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
    private Unity headquaters = new Unity(2, 2, "h");
    //Бараки:
    private Unity barracksVertical = new Unity(2, 1, "b");
    private Unity barracksHorisontal = new Unity(1, 2, "b");
    //Генератор:
    private Unity generator = new Unity(2, 2, "g");
    private boolean isConstructedGenerator;
    //Завод:
    private Unity factoryVertical = new Unity(3, 2, "f");
    private Unity factoryHorisontal = new Unity(2, 3, "f");
    //Турель:
    private Unity turret = new Unity(1, 1, "t");
    //Стена:
    private Unity wall = new Unity(1, 1, "w");

    //Армия:
    private int howICanProductArmy;
    private int howICanProductTanks;
    private Unity gunner = new Unity(1, 1, "G");
    private Unity tank = new Unity(1, 1, "T");

    public ControlBattler(BattleField battleField) {
        this.battleField = battleField;
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    public void initializeField() {
        //Размещение штабов:
        putUnity(playerRed, new Point(0, 0), headquaters);
        putUnity(playerBlue, new Point(14, 14), headquaters);

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
                    battleField.getMatrix().get(i).set(j, " " + player.getColorType() + unity.getId());
                    if (i == point.getX() && j == point.getY()) {
                        battleField.getMatrix().get(i).set(j, player.getColorType() + unity.getId() + "'");
                        System.out.println(i + " " + j);
                    }
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
                if (!this.battleField.getMatrix().get(i).get(j).equals("  0")) {
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

    public boolean upgradeBuilding(Point point, Player player){ //Здесь я остановился:
        boolean isUpgraded = false;
        List<String> list = battleField.getMatrix().get(point.getX());
        if (isFindUnity(point) && list.get(point.getY()).contains(player.getColorType())){
            switch (list.get(point.getY()).charAt(list.size() - 2)){
                case 'g': //Улучшение генератора:
                    isUpgraded = true;
                    break;
                case 'b': //Улучшение бараков:
                    isUpgraded = true;
                    break;
                case 'f': //Улучшение завода:
                    isUpgraded = true;
                    break;
                case 't': //Улучшение туррели:
                    isUpgraded = true;
                    break;
            }
        }
        return isUpgraded;
    }

    //Проверка MouseClicked на Unity
    private boolean isFindUnity(Point point){
        return battleField.getMatrix().get(point.getX()).get(point.getY()).contains("'");
    }


    public void deleteUnity(Point point, Unity unity) {

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
        for (int i = 0; i < 16; i++) {
            for (int j = 0; j < 16; j++) {
                if (this.battleField.getMatrix().get(i).get(j).equals(player.getColorType() + "G") || this.battleField.getMatrix().get(i).get(j).equals(player.getColorType() + "T") ||
                        this.battleField.getMatrix().get(i).get(j).equals(player.getColorType() + "t")) {
                    this.battleField.getMatrix().get(i).set(j, "!" + this.battleField.getMatrix().get(i).get(j));
                }
            }
        }
    }

    private void showReadyBuilding(Player player) {
        for (int i = 0; i < 16; i++) {
            for (int j = 0; j < 16; j++) {
                if (this.battleField.getMatrix().get(i).get(j).equals(player.getColorType() + "b'") || this.battleField.getMatrix().get(i).get(j).equals(player.getColorType() + "f'")) {
                    this.battleField.getMatrix().get(i).set(j, "!" + this.battleField.getMatrix().get(i).get(j));
                }
            }
        }
    }

    private void getHowCanBuild(Player player) {
        howICanBuild = 1;
        for (int i = 0; i < 16; i++) {
            for (int j = 0; j < 16; j++) {
                if (this.battleField.getMatrix().get(i).get(j).equals(player.getColorType() + "g'")) {
                    howICanBuild++;
                }
            }
        }
    }

    private void getHowCanProductArmy(Player player) {
        howICanProductArmy = 0;
        for (int i = 0; i < 16; i++) {
            for (int j = 0; j < 16; j++) {
                if (this.battleField.getMatrix().get(i).get(j).equals("!" + player.getColorType() + "b'")) {
                    howICanProductArmy++;
                }
            }
        }
    }

    private void getHowCanProductTanks(Player player) {
        howICanProductTanks = 0;
        for (int i = 0; i < 16; i++) {
            for (int j = 0; j < 16; j++) {
                if (this.battleField.getMatrix().get(i).get(j).equals("!" + player.getColorType() + "f'")) {
                    howICanProductTanks++;
                }
            }
        }
    }

    public int getHowCanBuildFactories() {
        return howICanProductArmy - howICanProductTanks;
    }


    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
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

    public Unity getHeadquaters() {
        return headquaters;
    }

    public void setHeadquaters(Unity headquaters) {
        this.headquaters = headquaters;
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

//    public void constructBuilding(Player player, Point point, Unity unit){
//
//        int startX = point.getX();
//        int startY = point.getY();
//        int finishX = point.getX() + unit.getWidth();
//        int finishY = point.getY() + unit.getHeight();
//        boolean canBuild = false;
//
//        if (point.getX() - 1 < 0){
//            startX = point.getX();
//        }
//        if (point.getY() - 1 < 0){
//            startY = point.getY();
//        }
//        if (point.getX() + unit.getWidth() > 16){
//            finishX = 16;
//        }
//        if (point.getY() + unit.getHeight() > 16){
//            finishY = 16;
//        }
//
//        for (int i = startX; i < finishX; i++){
//            if (battleField.getMatrix().get(i).get(startY).contains(player.getColorType())){
//                canBuild = true;
//            }
//        }
//        for (int j = startY; j < finishY; j++){
//            if (battleField.getMatrix().get(finishX - 1).get(j).contains(player.getColorType())){
//                canBuild = true;
//            }
//        }
//        for (int i = finishX - 1; i > startX - 1; i--){
//            if (battleField.getMatrix().get(i).get(finishY - 1).contains(player.getColorType())){
//                canBuild = true;
//            }
//        }
//        for (int j = finishY - 1; j > startY - 1; j--){
//            if (battleField.getMatrix().get(startX).get(j).contains(player.getColorType())){
//                canBuild = true;
//            }
//        }
//        if (canBuild){
//            putUnity(player, point, unit);
//        }
//    }
//
//
//
//


//    public void correctTerritory() {
//
//    }


//        for (int i = startPointX; i < point.getX() + unity.getWidth() + 1; i++) {
//            if (i >= 0 && i < 16 && startPointY >= 0 && startPointY < 16) {
//                if (battleField.getMatrix().get(i).get(startPointY).contains(player.getColorType()) &&
//                        (!battleField.getMatrix().get(i).get(startPointY).contains("G") || !battleField.getMatrix().get(i).get(j).contains("T"))) {
//                    return true;
//                }
//            }
//            startPointX = i;
//        }
//        for (int j = startPointY; j < point.getY() + unity.getHeight() + 1; j++) {
//            if (battleField.getMatrix().get(startPointX).get(j).contains(player.getColorType()) &&
//                    (!battleField.getMatrix().get(startPointX).get(j).contains("G") || !battleField.getMatrix().get(i).get(j).contains("T"))) {
//                return true;
//            }
//        }
//        for (; sta > point.getX() + unity.getWidth() + 1; i--) {
//            if (battleField.getMatrix().get(i).get(j).contains(player.getColorType()) &&
//                    (!battleField.getMatrix().get(i).get(j).contains("G") || !battleField.getMatrix().get(i).get(j).contains("T"))) {
//                return true;
//            }
//        }
//        for (; j > point.getY() + unity.getHeight() + 1; j--) {
//            if (battleField.getMatrix().get(i).get(j).contains(player.getColorType()) &&
//                    (!battleField.getMatrix().get(i).get(j).contains("G") || !battleField.getMatrix().get(i).get(j).contains("T"))) {
//                return true;
//            }
//        }