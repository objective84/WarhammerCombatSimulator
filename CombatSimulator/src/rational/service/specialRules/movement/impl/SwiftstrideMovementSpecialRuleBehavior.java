package rational.service.specialRules.movement.impl;

import rational.model.Dice;
import rational.model.Unit;
import rational.service.specialRules.movement.SpecialMovementBehavior;

import java.util.Arrays;
import java.util.List;

public class SwiftstrideMovementSpecialRuleBehavior extends SpecialMovementBehavior {


    public SwiftstrideMovementSpecialRuleBehavior(Unit unit) {
        super(unit);
    }

    @Override
    public int getSpecialMovement() {
        Dice d6 = Dice.getD6();
        List<Integer> rolls = d6.rollSeparateDice(3);
        int lowest = 7;
        int index = 0;
        for(Integer roll : rolls){
            if(roll < lowest){
                index = rolls.indexOf(roll);
                lowest = roll;
            }
        }
        unit.getCombatService().appendLog(Arrays.toString(rolls.toArray()), false);
        rolls.remove(index);
        return rolls.get(0) + rolls.get(1);
    }
}
