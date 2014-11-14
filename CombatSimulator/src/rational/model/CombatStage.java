package rational.model;

import rational.enums.AttackDirectionEnum;

import java.util.Comparator;

public class CombatStage implements Comparator<CombatStage>, Comparable<CombatStage> {
    private Unit attacker;
    private Unit defender;
    private int initiative;


    public CombatStage(Unit attacker, Unit defender, int initiative){
        this.attacker = attacker;
        this.defender = defender;
        this.initiative = initiative;
    }

    public CombatStage() {
    }

    public void beginCombat(){
        System.out.println("\n" + attacker.getUnitModel().getName() + " attacks at initiative " + this.getInitiative());
        AttackDirectionEnum direction;
        direction = attacker.isFlankAttack() ? AttackDirectionEnum.FRONT : AttackDirectionEnum.FLANK;
        attacker.attack(defender, direction);

        defender.removeCasualties(defender.getWoundsReceived());
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
