package rational.service;

import rational.model.UnitModel;

/**
 * Created by awest on 11/14/14.
 */
public interface ToHitCombatBehavior {
    public int getNumberOfHits(UnitModel attacker, UnitModel defender);
}
