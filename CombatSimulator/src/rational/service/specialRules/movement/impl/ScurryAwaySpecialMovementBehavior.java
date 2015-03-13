package rational.service.specialRules.movement.impl;

import rational.model.Unit;
import rational.service.specialRules.movement.SpecialMovementBehavior;

public class ScurryAwaySpecialMovementBehavior extends SpecialMovementBehavior {
    public ScurryAwaySpecialMovementBehavior(Unit unit) {
        super(unit);
    }

    @Override
    public int getSpecialMovement(){
        int movement = super.getSpecialMovement();
        unit.getCombatService().appendLog(" + 1", false);
        return movement + 1;
    }
}
