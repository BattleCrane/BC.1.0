package BattleFields;


import Players.Player;
import Unities.BattleUnit;
import Unities.TargetUnit;
import Unities.Unit;
import Unities.Unity;
import javafx.scene.layout.Pane;

import java.util.ArrayList;
import java.util.List;

//Контроллер игры, делегат
public class ControlBattler {

    //Основные переменные:
    private List<DataUnit> listData = new ArrayList<>();
    private Pane paneBattle;
    private BattleField battleField = new BattleField();
    private int turn = 1;

    //Игроки:
    private Player playerBlue = new Player(0, 0, "+");
    private Player playerRed = new Player(1, 1, "-");
    private Player player;
    private Player opponentPlayer;

    //Юниты:
    //Юнит-цель:
    static TargetUnit targetUnit = new TargetUnit();
    //Строения:
    private int howICanBuild;
    //Штаб:
    private Unit headquaters = new Unit(2, 2, "h", 8);
    //Бараки:
    private Unit barracksVertical = new Unit(2, 1, "b", 1);
    private Unit barracksHorisontal = new Unit(1, 2, "b", 1);
    //Генератор:
    private Unit generator = new Unit(2, 2, "g", 1);
    //Завод:
    private Unit factoryVertical = new Unit(3, 2, "f", 1);
    private Unit factoryHorisontal = new Unit(2, 3, "f", 1);
    //Турель:
    private BattleUnit turret = new BattleUnit(1, 1, "t", 2, 1);
    //Стена:
    private Unit wall = new Unit(1, 1, "w", 4);

    //Армия:
    private int howICanProductArmy;
    private int howICanProductTanks;
    private BattleUnit gunner = new BattleUnit(1, 1, "G", 1, 1);
    private BattleUnit tank = new BattleUnit(1, 1, "T", 2, 2);

    //Второй конструктор для графического контейнера
    public ControlBattler(BattleField battleField, List<DataUnit> listData) {
        this.battleField = battleField;
        this.listData = listData;
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

    public void putUnity(Player player, Point point, Unity unity) {

        if (isEmptyTerritory(point, unity)) {
            for (int i = point.getX(); i < point.getX() + unity.getWidth(); i++) {
                for (int j = point.getY(); j < point.getY() + unity.getHeight(); j++) {
                    battleField.getMatrix().get(i).set(j, " " + player.getColorType() + unity.getId());
                    if (i == point.getX() && j == point.getY()) {
                        battleField.getMatrix().get(i).set(j, player.getColorType() + unity.getId() + "'");
                    }
                }
            }
        }
    }

    public void constructBuilding(Player player, Point point, Unit unit){

        int startX = point.getX();
        int startY = point.getY();
        int finishX = point.getX() + unit.getWidth();
        int finishY = point.getY() + unit.getHeight();
        boolean canBuild = false;

        if (point.getX() - 1 < 0){
            startX = point.getX();
        }
        if (point.getY() - 1 < 0){
            startY = point.getY();
        }
        if (point.getX() + unit.getWidth() > 16){
            finishX = 16;
        }
        if (point.getY() + unit.getHeight() > 16){
            finishY = 16;
        }

        for (int i = startX; i < finishX; i++){
            if (battleField.getMatrix().get(i).get(startY).contains(player.getColorType())){
                canBuild = true;
            }
        }
        for (int j = startY; j < finishY; j++){
            if (battleField.getMatrix().get(finishX - 1).get(j).contains(player.getColorType())){
                canBuild = true;
            }
        }
        for (int i = finishX - 1; i > startX - 1; i--){
            if (battleField.getMatrix().get(i).get(finishY - 1).contains(player.getColorType())){
                canBuild = true;
            }
        }
        for (int j = finishY - 1; j > startY - 1; j--){
            if (battleField.getMatrix().get(startX).get(j).contains(player.getColorType())){
                canBuild = true;
            }
        }
        if (canBuild){
            putUnity(player, point, unit);
        }
    }



    public void deleteUnity(Point point, Unity unity) {
        if (isEmptyTerritory(point, unity)) {
            for (int i = point.getX(); i < point.getX() + unity.getWidth(); i++) {
                for (int j = point.getY(); j < point.getY() + unity.getHeight(); j++) {
                    this.battleField.getMatrix().get(i).set(j, unity.getId());
                }
            }
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


    private void whatTurn() {
        if (turn == playerBlue.getTurn()) {
            player = playerBlue;
            opponentPlayer = playerRed;
        } else {
            player = playerRed;
            opponentPlayer = playerBlue;
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

    //После нажатия закончить ход
    public void nextTurnOfCurrentPlayer() {
        turn = (turn + 1) % 2;
        whatTurn();
        showReadyArmy(player);
        showReadyBuilding(player);
        getHowCanBuild(player);
        getHowCanProductArmy(player);
        getHowCanProductTanks(player);


    }

    public void correctTerritory() {

    }


    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    public BattleField getBattleField() {
        return battleField;
    }

    public void setBattleField(BattleField battleField) {
        this.battleField = battleField;
    }

    public List<DataUnit> getListData() {
        return listData;
    }

    public void setListData(List<DataUnit> listData) {
        this.listData = listData;
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

    public Unit getHeadquaters() {
        return headquaters;
    }

    public void setHeadquaters(Unit headquaters) {
        this.headquaters = headquaters;
    }

    public Unit getBarracksVertical() {
        return barracksVertical;
    }

    public void setBarracksVertical(Unit barracksVertical) {
        this.barracksVertical = barracksVertical;
    }

    public Unit getBarracksHorisontal() {
        return barracksHorisontal;
    }

    public void setBarracksHorisontal(Unit barracksHorisontal) {
        this.barracksHorisontal = barracksHorisontal;
    }

    public Unit getGenerator() {
        return generator;
    }

    public void setGenerator(Unit generator) {
        this.generator = generator;
    }

    public Unit getFactoryVertical() {
        return factoryVertical;
    }

    public void setFactoryVertical(Unit factoryVertical) {
        this.factoryVertical = factoryVertical;
    }

    public Unit getFactoryHorisontal() {
        return factoryHorisontal;
    }

    public void setFactoryHorisontal(Unit factoryHorisontal) {
        this.factoryHorisontal = factoryHorisontal;
    }

    public BattleUnit getTurret() {
        return turret;
    }

    public void setTurret(BattleUnit turret) {
        this.turret = turret;
    }

    public Unit getWall() {
        return wall;
    }

    public void setWall(Unit wall) {
        this.wall = wall;
    }

    public BattleUnit getGunner() {
        return gunner;
    }

    public void setGunner(BattleUnit gunner) {
        this.gunner = gunner;
    }

    public BattleUnit getTank() {
        return tank;
    }

    public void setTank(BattleUnit tank) {
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

    public static TargetUnit getTargetUnit() {
        return targetUnit;
    }

    public static void setTargetUnit(TargetUnit targetUnit) {
        ControlBattler.targetUnit = targetUnit;
    }
}
