package rational.service.specialRules.towound;

import rational.enums.SpecialRuleTypeEnum;
import rational.model.Dice;
import rational.model.UnitModel;
import rational.service.closecombat.CloseCombatService;

import java.util.Arrays;
import java.util.List;

public class ToWoundBehavior {

    public static int[][] toWoundChart = {
            {},
            {0,4,5,6,6,6,6,6,6,6,6},
            {0,3,4,5,6,6,6,6,6,6,6},
            {0,2,3,4,5,6,6,6,6,6,6},
            {0,2,2,3,4,5,6,6,6,6,6},
            {0,2,2,2,3,4,5,6,6,6,6},
            {0,2,2,2,2,3,4,5,6,6,6},
            {0,2,2,2,2,2,3,4,5,6,6},
            {0,2,2,2,2,2,2,3,4,5,6},
            {0,2,2,2,2,2,2,2,3,4,5},
            {0,2,2,2,2,2,2,2,2,3,4},
    };

    protected UnitModel model;
    protected CloseCombatService combatService;

    public ToWoundBehavior(UnitModel model) {
        this.model = model;
        combatService = model.getCombatService();
    }

    public int rollToWound(UnitModel attacker, UnitModel defender, List<Integer> hits, int attackStrength, boolean noArmorSave, boolean noWardSave){
        combatService.appendLog( "    Rolling for wounds....");
        int difficulty = toWoundChart[attackStrength][model.getToughness()];
        combatService.appendLog( "    " + attacker.getName() + " wounds on " + difficulty + "+");
        int wounds = rollDice(hits.size(), difficulty, true);
        if(noArmorSave){
            combatService.appendLog( "    this attack prevents armor saves.");
        }else {
            wounds = rollArmorSaves(wounds, attackStrength, defender);
        }
        if(noWardSave) {
            combatService.appendLog( "    this attack prevents ward saves.");
        }else{
            wounds = rollWardSave(wounds, defender);
        }
        return wounds;
    }

    protected int rollArmorSaves(int wounds, int attackStrength, UnitModel defender){
        if (null != defender.getModifiedArmorSave()) {
            int save = defender.getModifiedArmorSave() + Math.abs(3 - attackStrength);
            if(model.getSpecialRules().contains(SpecialRuleTypeEnum.ARMOR_PIERCING)){
                save++;
            }
            if (save <= 6) {
                if (wounds > 0) {
                    combatService.appendLog( "    " + defender.getName() + " have a " + save + "+ armor save...");
                    wounds = rollSave(wounds, save);
                }
            }
        }
        return wounds;
    }

    protected int rollWardSave(int wounds, UnitModel defender){
        if (null != defender.getModifiedWardSave()) {
            int save = defender.getModifiedWardSave();
            if (save <= 6) {
                if (wounds > 0) {
                    combatService.appendLog( "    " + defender.getName() + " have a " + save + "+ ward save...");
                    wounds = rollSave(wounds, save);
                }
            }
        }
        return wounds;
    }

    public int rollDice(Integer amt, int difficulty, boolean reRoll) {
        Dice d6 = Dice.getD6();
        List<Integer> attacks = d6.rollSeparateDice(amt);
        combatService.appendLog( "    " + Arrays.toString(attacks.toArray()));

        int hits = 0;
        for(int i=0; i<amt; i++) {
            if(attacks.get(i) >= difficulty){
                hits++;
            }
        }

        return hits;
    }

    private int rollSave(int amt, int save){
        Dice d6 = Dice.getD6();
        int wounds = amt;
        List<Integer> saves = d6.rollSeparateDice(amt);
        combatService.appendLog( "    " + Arrays.toString(saves.toArray()));
        for(int i=0; i<amt; i++) {
            if(saves.get(i) >= save){
                wounds--;
            }
        }
        return wounds;
    }
}
