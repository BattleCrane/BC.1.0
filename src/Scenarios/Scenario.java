package Scenarios;

import BattleFields.BattleManager;
import Bots.Bot;

public interface Scenario {

    void initializeScenario(BattleManager battleManager);

    Bot getBot();
}
