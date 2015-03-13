package rational.model.combatstage;

import rational.enums.SpecialRuleTypeEnum;
import rational.model.Unit;
import rational.model.UnitModel;
import rational.service.closecombat.CloseCombatService;

import java.util.Comparator;
import java.util.List;

public abstract class CombatStage implements Comparator<CombatStage>, Comparable<CombatStage> {

    protected CloseCombatService combatService;
    protected Unit attacker;
    protected Unit defender;
    protected int initiative;


    public CombatStage(Unit attacker, Unit defender, int initiative, CloseCombatService combatService){
        this.combatService = combatService;
        this.attacker = attacker;
        this.defender = defender;
        this.initiative = initiative;
    }

    public void beginCombat(){
        int defenderInitialWounds = defender.getWoundsReceived();

        if(this.getInitiative() == 0){
            combatService.appendLog("\n" + attacker.getUnitModel().getName() + " attacks last");
        }else if(this.getInitiative() == 11){
            combatService.appendLog("\n" + attacker.getUnitModel().getName() + " attacks first");
        }else {
            combatService.appendLog("\n" + attacker.getUnitModel().getName() + " attacks at initiative " + this.getInitiative());
        }
        attack(attacker, defender);

        defender.removeCasualties(defender.getWoundsReceived() - defenderInitialWounds);
    }

    public void attack(Unit attacker, Unit defender){
        List<Integer> attacks = attacker.getUnitModel().getToHitBehavior().doSpecialRule(attacker.getNumAttacks(defender), defender);
        List<Integer> hits = attacker.getUnitModel().getToHitBehavior().checkForHit(attacks);
        int totalHits = hits.size();
        int wounds = 0;
        if(!hits.isEmpty()){
            wounds += attacker.getUnitModel().getToWoundBehavior().rollToWound(attacker.getUnitModel(), defender.getUnitModel(), hits, attacker.getUnitModel().getStrength(),
                    attacker.getUnitModel().getSpecialRules().contains(SpecialRuleTypeEnum.NO_ARMOR_SAVE),
                    attacker.getUnitModel().getSpecialRules().contains(SpecialRuleTypeEnum.NO_WARD_SAVE));
        }

        if(attacker.getCharacters() != null && !attacker.getCharacters().isEmpty()){
            for(UnitModel character : attacker.getCharacters()) {
                attacks = character.getToHitBehavior().doSpecialRule(character.getAttacks(),defender);
                hits = character.getToHitBehavior().checkForHit(attacks);
                totalHits += hits.size();
                int heroWounds;
                if (!hits.isEmpty()) {
                        heroWounds = character.getToWoundBehavior().rollToWound(character, defender.getUnitModel(), hits, character.getStrength(),
                                character.getSpecialRules().contains(SpecialRuleTypeEnum.NO_ARMOR_SAVE),
                                character.getSpecialRules().contains(SpecialRuleTypeEnum.NO_WARD_SAVE));
                        wounds += heroWounds;
                }
                character.setAttacks(0);
            }
        }
        combatService.appendLog( "\n    " + attacker.getUnitModel().getName() + " hits: " + totalHits);
        combatService.appendLog("    " + attacker.getUnitModel().getName() + " wounds : " + wounds + "\n");
        defender.setWoundsReceived(defender.getWoundsReceived() + wounds);
    }



    public Unit getAttacker() {
        return attacker;
    }

    public void setAttacker(Unit attacker) {
        this.attacker = attacker;
    }

    public Unit getDefender() {
        return defender;
    }

    public void setDefender(Unit defender) {
        this.defender = defender;
    }

    public int getInitiative() {
        return initiative;
    }

    public void setInitiative(int initiative) {
        this.initiative = initiative;
    }

    @Override
    public int compare(CombatStage o1, CombatStage o2) {
        return o1.getInitiative() < o2.getInitiative() ? 1 : (o1.getInitiative() == o2.getInitiative() ? 0 :-1);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CombatStage that = (CombatStage) o;

        if (initiative != that.initiative) return false;
        if (attacker != null ? !attacker.equals(that.attacker) : that.attacker != null) return false;
        if (defender != null ? !defender.equals(that.defender) : that.defender != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = attacker != null ? attacker.hashCode() : 0;
        result = 31 * result + (defender != null ? defender.hashCode() : 0);
        result = 31 * result + initiative;
        return result;
    }

    @Override
    public int compareTo(CombatStage other) {
        return this.getInitiative() < other.getInitiative() ? 1 : (this.getInitiative() == other.getInitiative() ? 0 :-1);
    }
}
