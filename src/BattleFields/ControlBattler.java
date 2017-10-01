package BattleFields;


import Players.Player;
import Unities.Unity;
import javafx.scene.layout.Pane;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

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
    private IdentificationField identificationField = new IdentificationField();
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
    private Unity turret = new Unity(1, 1, "t", 2);
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
                    identificationField.getMatrix().get(i).set(j, "" + identificationField.getNumberUnity());
                    battleField.getMatrix().get(i).set(j, newUnity);
                }
            }
            identificationField.setNumberUnity(identificationField.getNumberUnity() + 1);
            return true;
        } else {
            return false;
        }
    }
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    public boolean putDoubleWall(Player player, Point point, Unity unity) {
        Point pointShiftRight = new Point(point.getX(), point.getY());
        pointShiftRight.setY(pointShiftRight.getY() + 1);
        if (isEmptyTerritory(point, unity) && isEmptyTerritory(pointShiftRight, unity)) {
            for (int i = point.getX(); i < point.getX() + unity.getWidth(); i++) {
                for (int j = point.getY(); j < point.getY() + unity.getHeight(); j++) {
                    String newUnity = unity.getHitPoints() + "^" + "?" + player.getColorType() + unity.getId();
                    if (i == point.getX() && j == point.getY()) {
                        newUnity = newUnity + "'";
                    }
                    identificationField.getMatrix().get(i).set(j, "" + identificationField.getNumberUnity());
                    identificationField.setNumberUnity(identificationField.getNumberUnity() + 1);
                    identificationField.getMatrix().get(i).set(j + 1, "" + identificationField.getNumberUnity());
                    identificationField.setNumberUnity(identificationField.getNumberUnity() + 1);
                    battleField.getMatrix().get(i).set(j, newUnity);
                    battleField.getMatrix().get(i).set(j + 1, newUnity);
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
        if (battleField.getMatrix().get(point.getX()).get(point.getY()).contains(player.getColorType())) {
            for (int i = 0; i < 16; i++){
                for (int j = 0; j < 16; j++){
                    String unityBuild = battleField.getMatrix().get(i).get(j);
                    if (identificationField.getMatrix().get(i).get(j).equals(String.valueOf(getIdentification(point)))){
                        try {
                            switch (battleField.getMatrix().get(i).get(j).substring(4, 5)) { //Смотрим строение:
                                case "g": //Улучшение генератора:
                                    if (!unityBuild.contains(">")){
                                        unityBuild = levelUp(unityBuild);
                                        if (unityBuild.contains("<")) {
                                            unityBuild = changeHitPoints(unityBuild, 1);
                                        }
                                        if (unityBuild.contains(">")) {
                                            unityBuild = changeHitPoints(unityBuild, 2);
                                        }
                                        isUpgraded = true;
                                    }
                                    break;
                                case "b": //Улучшение бараков:
                                    if (!unityBuild.contains(">")) {
                                        unityBuild = levelUp(unityBuild);
                                        if (unityBuild.contains("<")) {
                                            unityBuild = changeHitPoints(unityBuild, 1);
                                        }
                                        if (unityBuild.contains(">")) {
                                            unityBuild = changeHitPoints(unityBuild, 2);
                                        }
                                        isUpgraded = true;
                                    }
                                    break;
                                case "f": //Улучшение завода:
                                    if (!unityBuild.contains(">")) {
                                        unityBuild = levelUp(unityBuild);
                                        if (unityBuild.contains("<")) {
                                            unityBuild = changeHitPoints(unityBuild, 3);
                                        }
                                        if (unityBuild.contains(">")) {
                                            unityBuild = changeHitPoints(unityBuild, 2);
                                        }
                                        isUpgraded = true;
                                    }
                                    break;
                                case "t": //Улучшение туррели:
                                    if (unityBuild.contains("^")) {
                                    unityBuild = levelUp(unityBuild);
                                        unityBuild = changeHitPoints(unityBuild, 2);
                                    }
                                    isUpgraded = true;
                                    break;
                            }
                        } catch (Exception ignored) {
                        }
                    }
                    battleField.getMatrix().get(i).set(j, unityBuild);
                }
                }
            }

        return isUpgraded;
    }

    private int getIdentification(Point point){
        return Integer.parseInt(identificationField.getMatrix().get(point.getX()).get(point.getY()));
    }

    @NotNull
    private String changeHitPoints(String unityBuild, int temp) {
        int upgradedHitPoints = Integer.parseInt(unityBuild.substring(0, 1)) + temp;
        return upgradedHitPoints + unityBuild.substring(1);
    }



    @Nullable
    public String levelUp(String unity) {
        System.out.println(unity.substring(1, 2));
        switch (unity.substring(1, 2)) {
            case "^":
                System.out.println("UP");
                return unity.substring(0, 1) + '<' + unity.substring(2);
            case "<":
                return unity.substring(0, 1) + '>' + unity.substring(2);
        }
        return unity;
    }

    public void checkDestroyedUnities() {
        for (int i = 0; i < 16; i++){
            for (int j = 0; j < 16; j++){
                if( battleField.getMatrix().get(i).get(j).substring(0, 1).equals("0")){
                    battleField.getMatrix().get(i).set(j, "XXXXXX");
                }
            }
        }



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
                if (matcher.find() && list.get(j).contains(player.getColorType())) {
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
                if (matcher.find() && list.get(j).contains(player.getColorType())) {
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

    public IdentificationField getIdentificationField() {
        return identificationField;
    }
}

//Проверка MouseClicked на Unity
//    private Pair isFindUnity(Point point) {
//        String clickedElement = battleField.getMatrix().get(point.getX()).get(point.getY());
//        if (clickedElement.contains("'")) {
//            return new Pair(point, true);
//        }
//        if (clickedElement.contains(".")) {
//            System.out.println("не корень:");
//            String currentUnity = clickedElement.substring(4, 5);
//            for (int i = point.getX() - 1; i < point.getX() + 3; i++) {
//                for (int j = point.getY() - 2; i < point.getY() + 5; j++) {
//                    if (i >= 0 && i < 16 && j >= 0 && j < 16) {
//                        System.out.println("i: " + i + "     " + "j: " + j);
//                        String currentElement = battleField.getMatrix().get(j).get(i);
//                        System.out.println(currentElement);
//                        if (currentElement.contains("'") && currentElement.contains(currentUnity)) {
//                            switch (currentUnity) {
//                                case "g":
//                                    for (int k = j; k < j + 2; k++) {
//                                        for (int l = i; l < i + 2; l++) {
//                                            System.out.println("inner");
//                                            if (battleField.getMatrix().get(l).get(k).equals(clickedElement)) {
//                                                return new Pair(new Point(i, j), true);
//                                            }
//                                        }
//                                    }
//                                    break;
//                                case "f":
//                                    for (int k = j; k < j + 3; k++) {
//                                        for (int l = i; l < i + 2; l++) {
//                                            if (battleField.getMatrix().get(l).get(k).equals(clickedElement)) {
//                                                return new Pair(new Point(i, j), true);
//                                            }
//                                        }
//                                    }
//                                    break;
//                                case "b":
//                                    for (int k = j; k < j + 2; k++) {
//                                            if (battleField.getMatrix().get(j).get(k).equals(clickedElement)) {
//                                                return new Pair(new Point(i, j), true);
//                                            }
//                                    }
//                                    break;
//                            }
//                        }
//                    }
//                }
//            }
//        }
//        return new Pair(null, false);
//    }