package rational.service.specialRules.leadership.breaktest;

import rational.model.Unit;
import rational.model.UnitModel;

import java.util.ArrayList;

public abstract class BreakTestService {
    protected Unit unit;

    public BreakTestService(Unit unit) {
        this.unit = unit;
    }

    public void doBreakTest(int result, Unit other){
        if (unit.isSteadfast(other)) {
            unit.getCombatService().appendLog("\n" + unit.getUnitModel().getName() + " remains steadfast.");
            result = 0;
        }
        if (!unit.getLeadershipTestService().testLeadership(result, true)) {
            flee(other);
        } else {
            unit.getCombatService().appendLog("\n" + unit.getUnitModel().getName() + " has passed their break test.");
        }
    }

    protected void flee(Unit other){
        unit.setBroken(true);
        unit.getCombatService().appendLog("\n" + unit.getUnitModel().getName() + " fails their break test!");

        unit.getCombatService().appendLog("\n" + unit.getUnitModel().getName() + " flees (", false);
        int flee = unit.getSpecialMovementService().getSpecialMovement();
        unit.getCombatService().appendLog(") = " + flee + " inches", false);

        unit.getCombatService().appendLog("\n" + other.getUnitModel().getName() + " pursues (", true);
        int pursue = other.getSpecialMovementService().getSpecialMovement();
        unit.getCombatService().appendLog(") = " + pursue + " inches", false);
        if (pursue >= flee) {
            unit.getCombatService().appendLog("\n" + other.getUnitModel().getName() + " has caught " + unit.getUnitModel().getName() + "!");
            unit.getCombatService().appendLog("\n" + unit.getUnitModel().getName() + " has been annihilated!");
            unit.setModels(null);
            unit.setCharacters(new ArrayList<UnitModel>());
        } else {
            unit.getCombatService().appendLog("\n" + unit.getUnitModel().getName() + " has escaped!");
        }
    }
}
