package rational.service.specialRules;

import rational.model.Dice;

import java.util.ArrayList;
import java.util.List;

public class PredatoryStrikeSpecialRuleService implements ToHitSpecialRuleService {

    @Override
    public List<Integer> doSpecialRule(List<Integer> toHitRolls) {
        Dice d6 = Dice.getD6();
        List<Integer> temp = new ArrayList<>(toHitRolls);
        for(Integer hit : temp){
            if(hit == 6){
                toHitRolls.add(d6.roll(1));
            }
        }

        return toHitRolls;
    }
}
