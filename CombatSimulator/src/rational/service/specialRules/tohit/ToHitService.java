package rational.service.specialRules.tohit;

import rational.model.Dice;
import rational.model.Unit;
import rational.model.UnitModel;
import rational.service.CloseCombatService;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public abstract class ToHitService{
    public static int[][] toHitChart = {
            {},
            {0,4,5,5,5,5,5,5,5,5,5},
            {0,3,4,5,5,5,5,5,5,5,5},
            {0,3,3,4,4,5,5,5,5,5,5},
            {0,3,3,3,4,4,4,5,5,5,5},
            {0,3,3,3,3,4,4,4,5,5,5},
            {0,3,3,3,3,3,4,4,4,4,4},
            {0,3,3,3,3,3,3,4,4,4,4},
            {0,3,3,3,3,3,3,3,4,4,4},
            {0,3,3,3,3,3,3,3,3,4,4},
            {0,3,3,3,3,3,3,3,3,3,4}
    };


    protected UnitModel model;
    protected Unit defender;
    protected CloseCombatService combatService;

    public ToHitService(UnitModel model) {
        this.model = model;
        this.combatService = model.getCombatService();
    }

    public List<Integer> doSpecialRule(int numAttacks, Unit defender){
        this.defender = defender;
        combatService.appendLog( "\n    Rolling for " + model.getName() + "....");
        combatService.appendLog( "    " + model.getName() + " has " + numAttacks + " attacks");
        List<Integer> hits = rollToHit(numAttacks);
        return hits;
    }

    protected List<Integer> rollToHit(int numAttacks){
        Dice d6 = Dice.getD6();
        List<Integer> attacks = d6.rollSeparateDice(numAttacks);
        combatService.appendLog("    " + Arrays.toString(attacks.toArray()));

        return attacks;
    }

    public List<Integer> checkForHit(List<Integer> attacks){
        int difficulty = toHitChart[model.getWeaponSkill()][defender.getUnitModel().getWeaponSkill()];
        combatService.appendLog( "    " + model.getName() + " hits on " + difficulty + "+");
        List<Integer> hits = new ArrayList<>();
        for(Integer attack : attacks){
            if(attack >= difficulty){
                hits.add(attack);
            }
        }
        combatService.appendLog( "    " + hits.size() + " attacks hit");
        return hits;
    }
}
