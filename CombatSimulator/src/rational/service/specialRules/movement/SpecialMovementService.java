package rational.service.specialRules.movement;

import rational.model.Dice;
import rational.model.Unit;

import java.util.Arrays;
import java.util.List;

public abstract class SpecialMovementService {

    protected Unit unit;

    public SpecialMovementService(Unit unit) {
        this.unit = unit;
    }

    public int getSpecialMovement(){
        List<Integer> rolls = Dice.getD6().rollSeparateDice(2);
        unit.getCombatService().appendLog(Arrays.toString(rolls.toArray()), false);

        return rolls.get(0) + rolls.get(1);
    }

}
