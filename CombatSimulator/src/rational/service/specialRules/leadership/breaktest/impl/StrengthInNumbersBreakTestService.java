package rational.service.specialRules.leadership.breaktest.impl;

import rational.model.Unit;
import rational.service.specialRules.leadership.LeadershipTestService;
import rational.service.specialRules.leadership.breaktest.BreakTestService;
import rational.service.specialRules.leadership.impl.DefaultLeadershipTestService;

public class StrengthInNumbersBreakTestService extends BreakTestService {
    public StrengthInNumbersBreakTestService(Unit unit) {
        super(unit);
    }

    @Override
    public void doBreakTest(int result, Unit other) {
        LeadershipTestService leadershipTestService = unit.getLeadershipTestService();
        if (unit.isSteadfast(other)) {
            unit.getCombatService().appendLog("\n" + unit.getUnitModel().getName() + " remains steadfast.");
            result = 0;
        } else {
            leadershipTestService = new DefaultLeadershipTestService(unit);
        }
        if (!leadershipTestService.testLeadership(result, true)) {
            flee(other);
        } else {
            unit.getCombatService().appendLog("\n" + unit.getUnitModel().getName() + " has passed their break test.");
        }
    }
}
