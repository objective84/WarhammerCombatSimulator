package rational;

import rational.model.UnitModel;

/**
 * Created by awest on 11/14/14.
 */
public interface ToHitCombatService {
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

    public int getNumberOfHits(UnitModel attacker, UnitModel defender);

}