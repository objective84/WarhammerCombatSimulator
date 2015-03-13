package rational.model;

import rational.enums.AttackDirectionEnum;
import rational.enums.SpecialRuleTypeEnum;

import java.util.ArrayList;
import java.util.List;

public class Army {

    private String name;
    private String race;
    private List<Unit> units = new ArrayList<>();
    private int armyPointValue;
    private int combatScore;

    public int getNumMusicians(){
        int musicians = 0;
        for(Unit unit : units){
            if(unit.isHasMusician()){
                musicians++;
            }
        }
        return musicians;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRace() {
        return race;
    }

    public void setRace(String race) {
        this.race = race;
    }

    public List<Unit> getUnits() {
        return units;
    }

    public void setUnits(List<Unit> units) {
        this.units = units;
    }

    public int getArmyPointValue() {
        return armyPointValue;
    }

    public void setArmyPointValue(int armyPointValue) {
        this.armyPointValue = armyPointValue;
    }

    public boolean isHasBanner() {
        for(Unit unit : units){
            if(unit.isHasBanner()){
                return true;
            }
        }
        return false;
    }

    public boolean isFlankAttack() {
        for(Unit unit : units){
            if(unit.isFlankAttack()){
                return true;
            }
        }
        return false;
    }

    public boolean isRearAttack() {
        for(Unit unit : units){
            if(unit.isRearAttack()){
                return true;
            }
        }
        return false;
    }

    public boolean isHasHighGround() {
        for(Unit unit : units){
            if(unit.isHasHighGround()){
                return true;
            }
        }
        return false;
    }

    public boolean isHasBattleStandard() {
        for(Unit unit : units){
            if(unit.isHasBattleStandard()){
                return true;
            }
        }
        return false;
    }

    public int getOverkill() {
        int overkill = 0;
        for(Unit unit : units){
            overkill += unit.getOverkill();
        }
        return overkill;
    }

    public void setCombatScore(int combatScore) {
        this.combatScore = combatScore;
    }

    public int getCombatScore() {
        return combatScore;
    }

    public boolean isCharging() {
        for(Unit unit : units){
            if(unit.isCharging()){
                return true;
            }
        }
        return false;
    }

    public int getWoundsReceived() {
        int wounds = 0;
        for(Unit unit : units){
            wounds += unit.getWoundsReceived();
        }
        return wounds;
    }

    public void clearWoundsReceived() {
        for(Unit unit : units){
            unit.setWoundsReceived(0);
        }
    }

    public boolean hasDisruptingUnit(Unit other) {
        for(Unit unit : units) {
            if (unit.getEngagedUnits().values().contains(AttackDirectionEnum.FLANK) || unit.getEngagedUnits().values().contains(AttackDirectionEnum.REAR)) {
                Unit engaged = null;
                if (unit.getEngagedUnits().keySet().contains(other)) {
                    for (Unit engagedUnit : unit.getEngagedUnits().keySet()) {
                        if (engagedUnit.equals(other) && unit.getEngagedUnits().get(engaged).equals(AttackDirectionEnum.FLANK) ||
                                unit.getEngagedUnits().get(other).equals(AttackDirectionEnum.REAR)) {
                            if (unit.getFullRanks() >= 2 && unit.getFiles() >= 5) {
                                return true;
                            }
                        }
                    }
                }
            }
        }
        return false;
    }

    public void breakTest(int result, Army winner) {
        for(Unit unit : units){
            unit.getBreakTestBehavior().doBreakTest(result, winner.getPursingUnit());
        }
    }

    private Unit getPursingUnit() {
        Unit pursuing = null;
        for(Unit unit : units){
            if(!unit.isPursuing()){
                if(unit.getUnitModel().getSpecialRules().contains(SpecialRuleTypeEnum.SWIFTSTRIDE)){
                    pursuing = unit;
                    break;
                }else{
                    pursuing = unit;
                }
            }
        }
        return pursuing;
    }


}
