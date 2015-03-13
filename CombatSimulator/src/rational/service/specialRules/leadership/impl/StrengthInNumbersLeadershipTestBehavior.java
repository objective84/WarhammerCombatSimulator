package rational.service.specialRules.leadership.impl;

import rational.model.Dice;
import rational.model.Unit;
import rational.service.specialRules.leadership.LeadershipTestBehavior;

public class StrengthInNumbersLeadershipTestBehavior extends LeadershipTestBehavior {

    public StrengthInNumbersLeadershipTestBehavior(Unit unit) {
        super(unit);
    }

    @Override
    public boolean testLeadership(int modifier, boolean reRoll) {
        int modifiedLeadership = unit.getLeadership() + unit.getRankBonus() - modifier;
        modifiedLeadership  = modifiedLeadership > 10 ? 10 : modifiedLeadership < 0 ? 0 : modifiedLeadership;
        unit.getCombatService().appendLog(unit.getUnitModel().getName() + "adds its rank bonus of " + unit.getRankBonus() + " to it's leadership score.");

        String modified = modifier == 0 ? "" : "modified ";
        unit.getCombatService().appendLog( "\n" + unit.getUnitModel().getName() + " rolls for a leadership test with a " + modified + "leadership of " +
                modifiedLeadership + "... ");
        Dice die = Dice.getD6();
        return testForPass(die.rollSeparateDice(2), modifier, reRoll);
    }
}
