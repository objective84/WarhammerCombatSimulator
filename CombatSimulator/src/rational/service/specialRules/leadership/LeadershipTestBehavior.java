package rational.service.specialRules.leadership;

import rational.model.Dice;
import rational.model.Unit;

import java.util.Arrays;
import java.util.List;

public abstract class LeadershipTestBehavior {
    protected Unit unit;

    public LeadershipTestBehavior(Unit unit) {
        this.unit = unit;
    }

    public boolean testLeadership(int modifier, boolean reRoll){
        int modifiedLeadership = unit.getLeadership() - modifier;
        if(unit.getLeadership() - modifier < 0){
            modifiedLeadership = 0;
        }
        String modified = modifier == 0 ? "" : "modified ";
        unit.getCombatService().appendLog( "\n" + unit.getUnitModel().getName() + " rolls for a leadership test with a " + modified + "leadership of " +
                modifiedLeadership + "... ");
        Dice die = Dice.getD6();
        return testForPass(die.rollSeparateDice(2), modifier, reRoll);
    }

    protected boolean testForPass(List<Integer> rolls, int modifier, boolean reRoll){
        unit.getCombatService().appendLog( "    " + Arrays.toString(rolls.toArray()));

        if(rolls.get(0) == 1 && rolls.get(1) == 1){
            return true;
        }

        boolean passed = (rolls.get(0) + rolls.get(1)) + modifier <= unit.getLeadership();

        if(!passed && unit.isHasBattleStandard() && reRoll){
            unit.getCombatService().appendLog( "\n" + unit.getUnitModel().getName() + " has a battle standard, and re-rolls failed leadership test.");
            passed = testLeadership(modifier, false);
        }
        return passed;    }
}
