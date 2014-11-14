package rational;

import rational.enums.SpecialRuleTypeEnum;
import rational.model.Dice;
import rational.model.UnitModel;

import java.util.List;

/**
 * Created by awest on 11/14/14.
 */
public class ToHitDefaultCombatService implements ToHitCombatService{

    @Override
    public int getNumberOfHits(UnitModel attacker, UnitModel defender) {
        int numberOfAttacks = attacker.getAttacks();
        int difficulty = toHitChart[attacker.getWeaponSkill()][defender.getWeaponSkill()];

        List<Integer> attackRolls = Dice.getD6().rollSeparateDice(numberOfAttacks);

        //Predatory Fighter Special Rules
        if(attacker.getSpecialRules().contains(SpecialRuleTypeEnum.PREDATORY_FIGHTER)) {
            int additionalAttacks = 0;
            for (Integer roll : attackRolls) {
                if (roll == 6) {
                    additionalAttacks++;
                }
            }
            attackRolls.addAll(Dice.getD6().rollSeparateDice(additionalAttacks));
            System.out.println("    " + attacker.getName() + " gets " + additionalAttacks + " additional attacks from Predatory Fighter");
        }
        //TODO add attacks for Frenzy, etc
        System.out.println("    " + attackRolls.toString());

        int numberOfHits = getHitRolls(attackRolls, difficulty);

        return numberOfHits + getHitsFromReRolls(attacker, defender, numberOfAttacks - numberOfHits);
    }

    private int getHitRolls(List<Integer> attacksRolls, int difficulty){
        int hits = 0;
        for(Integer roll : attacksRolls) {
            if(roll >= difficulty){
                hits++;
            }
        }
        return hits;
    }

    private int getHitsFromReRolls(UnitModel attacker, UnitModel defender, int misses){
        if(attacker.getSpecialRules().contains(SpecialRuleTypeEnum.ALWAYS_STRIKE_FIRST)
                && attacker.getInitiative() >= defender.getInitiative()
                && misses > 0){
            System.out.println("    Re-rolling " + misses + " misses because of Always Strike First....");
            return getHitRolls(Dice.getD6().rollSeparateDice(misses), toHitChart[attacker.getWeaponSkill()][defender.getWeaponSkill()]);
        }
        return 0;
    }

    //TODO I think there is a spell or something that lets you re-roll ones
}
