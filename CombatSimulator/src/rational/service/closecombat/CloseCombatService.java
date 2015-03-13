package rational.service.closecombat;

import rational.model.Army;
import rational.model.Unit;

public interface CloseCombatService {

    Unit resolveCombat(Army armyA, Army armyB);

    void resolveCombat(Unit unitA, Unit unitB);

    String getLog();

    void appendLog(String text);

    void appendLog(String text, boolean newLine);

    void setAverage(boolean average);
}
