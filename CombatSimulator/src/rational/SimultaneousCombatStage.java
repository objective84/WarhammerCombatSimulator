package rational;

import java.util.HashMap;
import java.util.Map;

public class SimultaneousCombatStage extends CombatStage {

    public Map<Unit, Unit> combatants = new HashMap<>();

    public SimultaneousCombatStage(Map<Unit, Unit> combatants, int initiative) {
        this.combatants = combatants;
        this.setInitiative(initiative);
    }

    public SimultaneousCombatStage(){

    }

    @Override
    public void beginCombat(){
        AttackDirectionEnum defendDirection;
        System.out.println("\nSimultaneous attacks at initiative " + this.getInitiative() + "for:");
        for(Unit unit : combatants.keySet()){
            System.out.println(unit.getUnitModel().getName());
        }
        for(Unit attacker : combatants.keySet()){
            Unit defender = combatants.get(attacker);
            defendDirection = attacker.isFlankAttack() ? (attacker.isRearAttack() ? AttackDirectionEnum.REAR : AttackDirectionEnum.FRONT)
                    : AttackDirectionEnum.FLANK;
            System.out.println("Rolling " + attacker.getName() + " attacks....");
            attacker.attack(defender, AttackDirectionEnum.FRONT);
            defender.attack(attacker, defendDirection);

            defender.removeCasualties(defender.getWoundsReceived());
            attacker.removeCasualties(attacker.getWoundsReceived());
        }
    }

    public Map<Unit, Unit> getCombatants() {
        return combatants;
    }

    public void setCombatants(Map<Unit, Unit> combatants) {
        this.combatants = combatants;
    }
}
