package TestPolyBot.probes;

import botInterface.probes.Probe;
import game.battleFields.BattleManager;
import game.battleFields.Point;
import botInterface.priority.PriorityUnit;
import TestPolyBot.TestInitializer;
import org.jetbrains.annotations.NotNull;
import org.junit.Test;
import polytech.polyNexus.PolyNexus;
import polytech.polyNexus.probes.PolyUpgradingProbe;

import java.util.logging.Logger;

//Worked!
public final class UpgradingProbeTest implements TestInitializer{
    private final Logger logger = Logger.getLogger(UpgradingProbeTest.class.getName());

    @Test
    public final void probeUpgradeTest(){
        BattleManager manager1  = initBattleManager();
        createTest(manager1, new PolyUpgradingProbe
                .UpgradingParams(manager1.getGenerator(), new Point (14,9))
                , () -> manager1.putUnity(manager1.getPlayerBlue()
                , new Point(14, 9), manager1.getGenerator()), 240.0);

        BattleManager manager2 = initBattleManager();
        createTest(manager2, new PolyUpgradingProbe.UpgradingParams(manager1.getGenerator()
                , new Point (10,10)), 0.0);
    }

    @NotNull
    @Override
    public final Object createTest(BattleManager manager, Probe.Params params) {
        Probe probe = PolyNexus.createUpgradingProbe(manager);
        PriorityUnit priorityUnit = (PriorityUnit) probe.probe(params);
        logger.info(manager.getBattleField().toString());
        logger.info("" + priorityUnit);
        return priorityUnit.getPriority();
    }
}
