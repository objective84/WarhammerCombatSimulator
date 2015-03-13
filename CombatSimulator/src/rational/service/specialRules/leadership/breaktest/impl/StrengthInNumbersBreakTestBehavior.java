package rational.service.specialRules.leadership.breaktest.impl;

import rational.model.Unit;
import rational.service.specialRules.leadership.LeadershipTestBehavior;
import rational.service.specialRules.leadership.breaktest.BreakTestBehavior;
import rational.service.specialRules.leadership.impl.DefaultLeadershipTestBehavior;

public class StrengthInNumbersBreakTestBehavior extends BreakTestBehavior {
    public StrengthInNumbersBreakTestBehavior(Unit unit) {
        super(unit);
    }

    @Override
    public void doBreakTest(int result, Unit other) {
        LeadershipTestBehavior leadershipTestBehavior = unit.getLeadershipTestBehavior();
        if (unit.isSteadfast(other)) {
            unit.getCombatService().appendLog("\n" + unit.getUnitModel().getName() + " remains steadfast.");
            result = 0;
        } else {
            leadershipTestBehavior = new DefaultLeadershipTestBehavior(unit);
        }
        if (!leadershipTestBehavior.testLeadership(result, true)) {
            flee(other);
        } else {
            unit.getCombatService().appendLog("\n" + unit.getUnitModel().getName() + " has passed their break test.");
        }
    }
}
