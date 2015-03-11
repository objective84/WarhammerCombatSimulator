package rational.service.specialRules.tohit.impl;

import rational.enums.SpecialRuleTypeEnum;
import rational.model.Dice;
import rational.model.UnitModel;
import rational.service.specialRules.tohit.ToHitService;

import java.util.Arrays;
import java.util.List;

public class PredatoryStrikeService extends ToHitService {

    public PredatoryStrikeService(UnitModel model) {
        super(model);
    }

    @Override
    public List<Integer> rollToHit(int numAttacks) {
        return rollPredatoryStrike(numAttacks, true);
    }

    public List<Integer> rollPredatoryStrike(int numAttacks, boolean reRollAllowed){
        Dice d6 = Dice.getD6();
        List<Integer> attacks = d6.rollSeparateDice(numAttacks);
        combatService.appendLog("    " + Arrays.toString(attacks.toArray()));

        if(model.getSpecialRules().contains(SpecialRuleTypeEnum.PREDATORY_FIGHTER) && reRollAllowed){
            int additionalAttacks = 0;
            for(int i=0; i<attacks.size(); i++) {
                if(attacks.get(i) == 6){
                    additionalAttacks++;
                }
            }
            if(additionalAttacks > 0) {
                combatService.appendLog("    " + model.getName() + " gets " + additionalAttacks + " additional attacks....");
                attacks.addAll(rollPredatoryStrike(additionalAttacks, false));
            }
        }
        return attacks;

    }
}
