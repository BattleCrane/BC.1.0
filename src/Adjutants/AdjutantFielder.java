package Adjutants;

import BattleFields.BattleManager;
import BattleFields.Point;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class AdjutantFielder {

    private class ZoneOfPlayer{
        private List<Point> area;
        private int colorID;

        ZoneOfPlayer(List<Point> area, int colorID) {
            this.area = area;
            this.colorID = colorID;
        }

        List<Point> getArea() {
            return area;
        }

        int getColorID() {
            return colorID;
        }

        void setColorID(int colorID) {
            this.colorID = colorID;
        }
    }

    private List<Point> listPassed = new ArrayList<>();
    private ZoneOfPlayer currentZoneOfPlayer = new ZoneOfPlayer(new ArrayList<>(), 0);
    private boolean isExactlyMediumZone = false;

    public void fillZones(BattleManager battleManager){
        List<ZoneOfPlayer> listOfZones = searchZones(battleManager);
        for (ZoneOfPlayer zone: listOfZones){
            if (zone.getColorID() != 0){
                String colorID = zone.getColorID() == 1 ? "+" : "-";
                for (Point point: zone.getArea()){
                    String currentUnity  = battleManager.getBattleField().getMatrix().get(point.X()).get(point.Y());
                    currentUnity = colorID + currentUnity.substring(1);
                    battleManager.getBattleField().getMatrix().get(point.X()).set(point.Y(), currentUnity);
                }
            }
        }
    }

    private List<ZoneOfPlayer> searchZones(BattleManager battleManager){
        List<ZoneOfPlayer> listOfZones = new ArrayList<>();
        currentZoneOfPlayer = new ZoneOfPlayer(new ArrayList<>(), 0); //Текущая область
        listPassed = new ArrayList<>(); //Лист пройденных точек

        for (int i = 0; i < 16; i++){
            for (int j = 0; j < 16; j++){
                String unity = battleManager.getBattleField().getMatrix().get(i).get(j);
                Point cursorPoint = new Point(i, j);
                if (unity.equals("     0") && !listPassed.contains(cursorPoint)){
                    currentZoneOfPlayer = new ZoneOfPlayer(new ArrayList<>(), 0);
                    isExactlyMediumZone = false;
                    currentZoneOfPlayer.getArea().add(cursorPoint);
                    listPassed.add(cursorPoint);
                        step(battleManager, cursorPoint);
                    listOfZones.add(currentZoneOfPlayer);
                }
            }
        }
        return listOfZones;
    }

    private void step(BattleManager battleManager, Point cursorPoint){
            List<Point> closestPoints = new ArrayList<>();
            Point pointUp = new Point(cursorPoint.X() + 1, cursorPoint.Y());
            Point pointUpRight = new Point(cursorPoint.X() + 1, cursorPoint.Y() + 1);
            Point pointUpLeft = new Point(cursorPoint.X() + 1, cursorPoint.Y() - 1);
            Point pointDown = new Point(cursorPoint.X() - 1, cursorPoint.Y());
            Point pointDownRight = new Point(cursorPoint.X() - 1, cursorPoint.Y() + 1);
            Point pointDownLeft = new Point(cursorPoint.X() - 1, cursorPoint.Y() - 1);
            Point pointLeft = new Point(cursorPoint.X(), cursorPoint.Y() - 1);
            Point pointRight = new Point(cursorPoint.X(), cursorPoint.Y() + 1);

            checkRoad(battleManager, pointUp, closestPoints);
            checkRoad(battleManager, pointUpRight, closestPoints);
            checkRoad(battleManager, pointRight, closestPoints);
            checkRoad(battleManager, pointDownRight, closestPoints);
            checkRoad(battleManager, pointDown, closestPoints);
            checkRoad(battleManager, pointDownLeft, closestPoints);
            checkRoad(battleManager, pointLeft, closestPoints);
            checkRoad(battleManager, pointUpLeft, closestPoints);
            if (closestPoints.size() != 0){
                for (int i = 0; i < closestPoints.size(); i++){
                    currentZoneOfPlayer.getArea().add(closestPoints.get(i));
                    listPassed.add(closestPoints.get(i));
                    step(battleManager, closestPoints.get(i));
                }
            }
    }

    private void checkRoad(BattleManager battleManager, Point point, List<Point> closestPoints){
        boolean inBounds = point.X() >= 0 && point.X() < 16 && point.Y() >= 0 && point.Y() < 16;

        if (inBounds && !listPassed.contains(point)){
            String currentUnity = battleManager.getBattleField().getMatrix().get(point.X()).get(point.Y());
            Pattern pattern = Pattern.compile("[hgbfwtiu]");
            Matcher matcher = pattern.matcher(currentUnity);


            if ((currentUnity.contains("+") && currentZoneOfPlayer.getColorID() == -1) ||
                    (currentUnity.contains("-") && currentZoneOfPlayer.getColorID() == 1)){

                currentZoneOfPlayer.setColorID(0);
                isExactlyMediumZone = true;
            }

            if (!isExactlyMediumZone){
                if (currentUnity.contains("+") && currentZoneOfPlayer.getColorID() == 0){
                    currentZoneOfPlayer.setColorID(1);
                }
                if (currentUnity.contains("-") && currentZoneOfPlayer.getColorID() == 0){
                    currentZoneOfPlayer.setColorID(-1);
                }
            }

            if (currentUnity.equals("     0") || currentUnity.equals("XXXXXX")){
                closestPoints.add(point);
            }
        }
    }
}



//    private List<ZoneOfPlayer> searchZones(BattleManager battleManager){
//        List<ZoneOfPlayer> listOfZones = new ArrayList<>();
//        currentZoneOfPlayer = new ZoneOfPlayer(new ArrayList<>(), 0); //Текущая область
//        listPassed = new ArrayList<>(); //Лист пройденных точек
//
//        for (int i = 0; i < 16; i++){
//            for (int j = 0; j < 16; j++){
//                String unity = battleManager.getBattleField().getMatrix().get(i).get(j);
//                Point cursorPoint = new Point(i, j);
//                if (unity.equals("     0") && !listPassed.contains(cursorPoint)){
//                    currentZoneOfPlayer = new ZoneOfPlayer(new ArrayList<>(), 0);
//                    currentZoneOfPlayer.getArea().add(cursorPoint);
//                    listPassed.add(cursorPoint);
//                        step(battleManager, cursorPoint);
//                    listOfZones.add(currentZoneOfPlayer);
//                }
//            }
//        }
//        return listOfZones;
//    }
//
//    private void step(BattleManager battleManager, Point cursorPoint){
//        boolean isNotFullZone = true; //Зона не полностью заполнена
//        while (isNotFullZone){
//            List<Point> closestPoints = new ArrayList<>();
//            Point pointUp = new Point(cursorPoint.X() + 1, cursorPoint.Y());
//            Point pointUpRight = new Point(cursorPoint.X() + 1, cursorPoint.Y() + 1);
//            Point pointUpLeft = new Point(cursorPoint.X() + 1, cursorPoint.Y() - 1);
//            Point pointDown = new Point(cursorPoint.X() - 1, cursorPoint.Y());
//            Point pointDownRight = new Point(cursorPoint.X() - 1, cursorPoint.Y() + 1);
//            Point pointDownLeft = new Point(cursorPoint.X() - 1, cursorPoint.Y() - 1);
//            Point pointLeft = new Point(cursorPoint.X(), cursorPoint.Y() - 1);
//            Point pointRight = new Point(cursorPoint.X(), cursorPoint.Y() + 1);
//
//            checkRoad(battleManager, pointUp, closestPoints);
//            checkRoad(battleManager, pointUpRight, closestPoints);
//            checkRoad(battleManager, pointRight, closestPoints);
//            checkRoad(battleManager, pointDownRight, closestPoints);
//            checkRoad(battleManager, pointDown, closestPoints);
//            checkRoad(battleManager, pointDownLeft, closestPoints);
//            checkRoad(battleManager, pointLeft, closestPoints);
//            checkRoad(battleManager, pointUpLeft, closestPoints);
//            if (closestPoints.size() == 0){
//                isNotFullZone = false;
//            } else {
//                for (int i = 0; i < closestPoints.size() - 1; i++){
//                    currentZoneOfPlayer.getArea().add(closestPoints.get(i));
//                    listPassed.add(closestPoints.get(i));
//                    step(battleManager, closestPoints.get(i));
//                }
//            }
//        }
//    }
