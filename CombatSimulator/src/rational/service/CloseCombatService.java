package rational.service;

import rational.model.Unit;

public interface CloseCombatService {

    Unit resolveCombat(Unit unitA, Unit unitB);

    String getLog();

    void appendLog(String text);

    void appendLog(String text, boolean newLine);

    void setAverage(boolean average);
}
