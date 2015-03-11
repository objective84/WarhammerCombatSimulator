package rational.service.specialRules.leadership.impl;

import rational.model.Dice;
import rational.model.Unit;
import rational.service.specialRules.leadership.LeadershipTestService;

import java.util.List;

public class ColdBloodedLeadershipTestService extends LeadershipTestService {
    public ColdBloodedLeadershipTestService(Unit unit) {
        super(unit);
    }

    @Override
    public boolean testLeadership(int modifier, boolean reRoll) {
        int modifiedLeadership = unit.getLeadership() - modifier;
        if (unit.getLeadership() - modifier < 0) {
            modifiedLeadership = 0;
        }
        String modified = modifier == 0 ? "" : "modified ";
        unit.getCombatService().appendLog("\n" + unit.getUnitModel().getName() + " rolls for a leadership test with a " + modified + "leadership of " +
                modifiedLeadership + "... ");
        Dice die = Dice.getD6();
        return testForPass(die.rollSeparateDice(3), modifier, reRoll);
    }

    @Override
    protected boolean testForPass(List<Integer> rolls, int modifier, boolean reRoll) {
        int highest = 0;
        for (Integer i : rolls) {
            if (i > highest) {
                highest = rolls.indexOf(i);
            }
        }
        rolls.remove(highest);
        return super.testForPass(rolls, modifier, reRoll);
    }
}
