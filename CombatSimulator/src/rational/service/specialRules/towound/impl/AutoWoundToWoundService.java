package rational.service.specialRules.towound.impl;

import rational.model.UnitModel;
import rational.service.specialRules.towound.ToWoundService;

import java.util.List;

public class AutoWoundToWoundService extends ToWoundService {
    public AutoWoundToWoundService(UnitModel model) {
        super(model);
    }

    @Override
    public int rollToWound(UnitModel attacker, UnitModel defender, List<Integer> hits, int attackStrength, boolean noArmorSave, boolean noWardSave) {
        combatService.appendLog( "    " + model.getName() + "'s weapon wounds automatically.");
        return hits.size();
    }
}
