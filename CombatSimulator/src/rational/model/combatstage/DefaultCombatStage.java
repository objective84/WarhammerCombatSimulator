package rational.model.combatstage;

import rational.model.Unit;
import rational.service.closecombat.CloseCombatService;

public class DefaultCombatStage extends CombatStage {

    public DefaultCombatStage(Unit attacker, Unit defender, int initiative, CloseCombatService combatService) {
        super(attacker, defender, initiative, combatService);
    }
}
