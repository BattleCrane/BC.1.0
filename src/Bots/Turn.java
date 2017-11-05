package Bots;

import Bonuses.Bonus;

import java.util.List;

public abstract class Turn {
    private List<Bonus> listOfUsingBonuses;
    private List<PriorityUnit> listOfUsingBuildings;
    private List<PriorityUnit> getListOfUsingArmy;
}
