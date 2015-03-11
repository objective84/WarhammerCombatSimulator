package rational.service.specialRules.tohit.impl;

import rational.model.UnitModel;
import rational.service.specialRules.tohit.ToHitService;

import java.util.List;

public class StrikeFirstToHitSpecialRuleService extends ToHitService {

    public StrikeFirstToHitSpecialRuleService(UnitModel model) {
        super(model);
    }

    @Override
    public List<Integer> checkForHit(List<Integer> attacks) {
        int numAttacks = attacks.size();
        List<Integer> hits = super.checkForHit(attacks);
        if(hits.size() < numAttacks) {
            int difficulty = toHitChart[model.getWeaponSkill()][defender.getUnitModel().getWeaponSkill()];
            combatService.appendLog("    Re-rolling " + (numAttacks - hits.size()) + " misses....");
            List<Integer> reRolls = super.rollToHit(numAttacks - hits.size());
            hits.addAll(super.checkForHit(reRolls));
        }
        return hits;
    }
}
