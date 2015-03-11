package rational.model.combatstage;

import rational.model.Unit;
import rational.service.CloseCombatService;

import java.util.HashMap;
import java.util.Map;

public class SimultaneousCombatStage extends CombatStage {

    public Map<Unit, Unit> combatants = new HashMap<>();

    public SimultaneousCombatStage(Map<Unit, Unit> combatants, int initiative, CloseCombatService combatService) {
        super(null, null, initiative, combatService);
        this.combatants = combatants;
    }

    @Override
    public void beginCombat(){

        combatService.appendLog("\nSimultaneous attacks at initiative " + this.getInitiative() + "for:");
        for(Unit unit : combatants.keySet()){
            combatService.appendLog(unit.getUnitModel().getName());
        }
        for(Unit attacker : combatants.keySet()){
            Unit defender = combatants.get(attacker);

            combatService.appendLog("Rolling " + attacker.getName() + " attacks....");
            attack(attacker, defender);
            attack(defender, attacker);

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
