package rational.service.specialRules.movement.impl;

import rational.model.Unit;
import rational.service.specialRules.movement.SpecialMovementService;

public class ScurryAwaySpecialMovementService extends SpecialMovementService {
    public ScurryAwaySpecialMovementService(Unit unit) {
        super(unit);
    }

    @Override
    public int getSpecialMovement(){
        int movement = super.getSpecialMovement();
        unit.getCombatService().appendLog(" + 1", false);
        return movement + 1;
    }
}
