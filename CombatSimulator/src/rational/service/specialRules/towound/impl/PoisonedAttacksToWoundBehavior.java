package rational.service.specialRules.towound.impl;

import rational.model.UnitModel;
import rational.service.specialRules.towound.ToWoundBehavior;

import java.util.ArrayList;
import java.util.List;

public class PoisonedAttacksToWoundBehavior extends ToWoundBehavior {
    public PoisonedAttacksToWoundBehavior(UnitModel model) {
        super(model);
    }

    @Override
    public int rollToWound(UnitModel attacker, UnitModel defender, List<Integer> hits, int attackStrength, boolean noArmorSave, boolean noWardSave) {
        int autoWounds = 0;
        List<Integer> temp = new ArrayList<>(hits);
        for (Integer hit : temp) {
            if (hit == 6) {
                autoWounds++;
                hits.remove(hit);
            }
        }

        return super.rollToWound(attacker, defender, hits, attackStrength, noArmorSave, noWardSave) + autoWounds;
    }
}
