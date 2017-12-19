package scenarios;

import game.battleFields.BattleManager;
import botInterface.Bot;

public interface Scenario {

    void initializeScenario(BattleManager battleManager);

    Bot getBot();
}
