package rational.service.closecombat.impl;

import rational.enums.AttackDirectionEnum;
import rational.model.Army;
import rational.model.Unit;
import rational.model.combatstage.CombatStage;
import rational.model.combatstage.DefaultCombatStage;
import rational.model.combatstage.SimultaneousCombatStage;
import rational.service.closecombat.CloseCombatService;

import java.util.*;

public class DefaultCloseCombatService implements CloseCombatService {
    private boolean average;
    String log = "";

    @Override
    public Unit resolveCombat(Army armyA, Army armyB){
        for(Unit unit : armyA.getUnits()){
            for(Unit engagedUnit : unit.getEngagedUnits().keySet()){
                if(unit.getEngagedUnits().get(engagedUnit).equals(AttackDirectionEnum.FLANK)){
                    unit.setFlankAttack(true);
                }else if(unit.getEngagedUnits().get(engagedUnit).equals(AttackDirectionEnum.REAR)){
                    unit.setRearAttack(true);
                }
                if(engagedUnit.getEngagedUnits().get(unit).equals(AttackDirectionEnum.FLANK)){
                    engagedUnit.setFlankAttack(true);
                }else if(engagedUnit.getEngagedUnits().get(unit).equals(AttackDirectionEnum.REAR)){
                    engagedUnit.setRearAttack(true);
                }
                resolveCombat(unit, engagedUnit);
            }
        }

        Army winner;
        Army loser;

            int result = calculateCombatScore(armyA, armyB) - calculateCombatScore(armyB, armyA);
            winner = result > 0 ? armyA : (result == 0 ? null : armyB);
            if (null == winner) {
                if (armyA.getNumMusicians() > armyB.getNumMusicians()) {
                    winner = armyA;
                } else if (armyA.getNumMusicians() < armyB.getNumMusicians()) {
                    winner = armyB;
                } else {
                    appendLog("\n" + "Draw!");
                    return null;
                }
            }
            result = Math.abs(result);
            loser = winner == armyA ? armyB : armyA;
            appendLog("\n" + winner.getName() + " wins!");
            loser.breakTest(result, winner);

        this.appendLog("*****************************************************************************************************************************" +
                "***************************");

        return null;
    }

    @Override
    public void resolveCombat(Unit unitA, Unit unitB) {
        List<Unit> attackers = new ArrayList<>();
        List<Unit> defenders = new ArrayList<>();
        attackers.add(unitA);
        if(null != unitA.getMounts()){
            attackers.add(unitA.getMounts());
        }
        defenders.add(unitB);
        if(null != unitB.getMounts()){
            defenders.add(unitB.getMounts());
        }
        Queue<CombatStage> initiativeOrder =  establishInitiativeOrder(attackers, defenders);

        while(null != initiativeOrder.peek()){
            initiativeOrder.remove().beginCombat();
        }
//        Unit winner;
//        Unit loser;
//
//        if(unitA.getNumModels() > 0 && unitB.getNumModels() > 0) {
//            int result = calculateCombatScore(unitA, unitB) - calculateCombatScore(unitB, unitA);
//            winner = result > 0 ? unitA : (result == 0 ? null : unitB);
//            if (null == winner) {
//                if (unitA.isHasMusician() && !unitB.isHasMusician()) {
//                    winner = unitA;
//                } else if (unitB.isHasMusician() && !unitA.isHasMusician()) {
//                    winner = unitB;
//                } else {
//                    appendLog("\n" + "Draw!");
//                    return null;
//                }
//            }
//            result = Math.abs(result);
//            loser = winner == unitA ? unitB : unitA;
//            appendLog("\n" + winner.getUnitModel().getName() + " wins!");
//            loser.getBreakTestBehavior().doBreakTest(result, winner);
//        }else{
//            winner = unitA.getNumModels() > 0 ? unitA : unitB;
//            loser = winner.equals(unitA) ? unitB : unitA;
//            appendLog("\n" + loser.getUnitModel().getName() + " has been annihilated!");
//            appendLog("\n" + winner.getUnitModel().getName() + " wins!");
//        }
//        this.appendLog("*****************************************************************************************************************************" +
//                "***************************");
//        return winner;
    }

    private Queue<CombatStage> establishInitiativeOrder(List<Unit> attackingArmyUnits, List<Unit> defendingArmyUnits) {
        Queue<CombatStage> order = new LinkedList<>();

        Map<Integer, SimultaneousCombatStage> simultaneousStages = new HashMap<>();
        List<CombatStage> stages = new ArrayList<>();

        for(Unit unit : attackingArmyUnits){
            for (Unit compare : defendingArmyUnits) {
                if((!unitAlreadyAssignedToStage(stages, unit)) && !compare.isMount()) {
//                if((!unitAlreadyAssignedToStage(stages, unit) || compare.isFlankAttack() || compare.isRearAttack()) && !compare.isMount()) {
                    int attackOrder = unit.compareTo(compare);
                    CombatStage stage = null;
                    if (attackOrder == 0) {
                        Map<Unit, Unit> simultaneous = new HashMap<>();
                        for(Unit compare2 : defendingArmyUnits){
                            if(compare2.getUnitInitiative() == unit.getUnitInitiative()){
                                if(!compare2.isMount()){
                                    simultaneous.put(unit, compare2);
                                }
                            }
                        }
                        if(simultaneousStages.get(unit.getUnitInitiative()) == null && simultaneousStages.get(unit.getUnitInitiative()) == null){
                            stage = new SimultaneousCombatStage(simultaneous, unit.getUnitInitiative(), this);
                        }else{
                            simultaneousStages.get(unit.getUnitInitiative()).getCombatants().putAll(simultaneous);
                        }
                    }else if(!unitAlreadyAssignedToStage(stages, unit) && !compare.isMount()){
//                    }else if(!unitAlreadyAssignedToStage(stages, unit) && !compare.isMount() && !compare.isFlankAttack() && !compare.isRearAttack()){
                        stage = new DefaultCombatStage(unit, compare, unit.getUnitInitiative(), this);
                    }
                    if(null != stage){
                        stages.add(stage);
                        if(stage instanceof SimultaneousCombatStage){
                            simultaneousStages.put(stage.getInitiative(), (SimultaneousCombatStage)stage);
                        }
                    }
                }
            }
        }
        for(Unit unit : defendingArmyUnits){
            for (Unit compare : attackingArmyUnits) {
                if((!unitAlreadyAssignedToStage(stages, unit)) && !compare.isMount()) {
//                if((!unitAlreadyAssignedToStage(stages, unit) || compare.isFlankAttack() || compare.isRearAttack()) && !compare.isMount()) {
                    int attackOrder = unit.compareTo(compare);
                    CombatStage stage = null;
                    if (attackOrder == 0) {
                        Map<Unit, Unit> simultaneous = new HashMap<>();
                        for(Unit compare2 : attackingArmyUnits){
                            if(compare2.getUnitInitiative() == unit.getUnitInitiative()){
                                if(!compare2.isMount()){
                                    simultaneous.put(unit, compare2);
                                }
                            }
                        }
                        if(simultaneousStages.get(unit.getUnitInitiative()) == null && simultaneousStages.get(unit.getUnitInitiative()) == null){
                            stage = new SimultaneousCombatStage(simultaneous, unit.getUnitInitiative(), this);
                        }else{
                            simultaneousStages.get(unit.getUnitInitiative()).getCombatants().putAll(simultaneous);
                        }
                    }else if(!unitAlreadyAssignedToStage(stages, unit) && !compare.isMount()){
//                    }else if(!unitAlreadyAssignedToStage(stages, unit) && !compare.isMount() && !compare.isFlankAttack() && !compare.isRearAttack()){
                        stage = new DefaultCombatStage(unit, compare, unit.getUnitInitiative(), this);
                    }
                    if(null != stage){
                        stages.add(stage);
                        if(stage instanceof SimultaneousCombatStage){
                            simultaneousStages.put(stage.getInitiative(), (SimultaneousCombatStage)stage);
                        }
                    }
                }
            }
        }
        Collections.sort(stages);
        order.addAll(stages);

        return order;
    }

    public int calculateCombatScore(Unit unit, Army other){
        String divider = getStartLogDivider(unit.getUnitModel().getName());
        appendLog( "\n" + divider + " " + unit.getUnitModel().getName() + " Combat Score " + divider);
        int score = 0;

        if((other.hasDisruptingUnit(unit))){
            appendLog("    Unit is disrupted and receives no rank bonus");
        }else if(unit.getRanks() > 1){
            int rankBonus = unit.getFullRanks() > 1 ? (unit.getFullRanks() - 1) : 0;
            rankBonus = rankBonus > 3 ? 3 : rankBonus;
            score += rankBonus;
            appendLog( "    Rank bonus: " + rankBonus);
            if(unit.isHasGriffinBattleStandard()){
                score+= rankBonus;
                appendLog( "    Griffin Battle Standard bonus: " + rankBonus);
            }
        }
//        if(unit.isHasBanner()){
//            score ++;
//            appendLog( "    Standard bearer bonus: 1");
//        }
//        if(unit.isFlankAttack()){
//            score ++;
//            appendLog( "    Flank attack bonus: 1");
//        }
//        if(unit.isRearAttack()){
//            score += 2;
//            appendLog( "    Rear attack bonus");
//        }
//        if(unit.isHasHighGround()){
//            score++;
//            appendLog( "    High Ground bonus: 1");
//        }
//        if(unit.isHasBattleStandard()){
//            score++;
//            appendLog( "    Battle Standard bonus: 1");
//        }
//        if(unit.getOverkill() > 0){
//            score += unit.getOverkill();
//            appendLog( "    Challenge overkill bonus: " + unit.getOverkill());
//        }
        other.clearWoundsReceived();
//        appendLog( "\nTotal Score: " + score);
//        appendLog( getEndLogDivider(divider) + "\n");
        unit.setCombatScore(score);
        return score;
    }

    public int calculateCombatScore(Army army, Army other){
        String divider = getStartLogDivider(army.getName());
        appendLog( "\n" + divider + " " + army.getName() + " Combat Score " + divider);

        int score = (other.getWoundsReceived());
        appendLog( "    Wounds dealt: " + (other.getWoundsReceived()));

        for(Unit unit : army.getUnits()) {
            score += calculateCombatScore(unit, other);
        }

        if(army.isCharging()){
            score++;
            appendLog( "    Charge bonus: 1");
        }

        if(army.isHasBanner()){
            score ++;
            appendLog( "    Standard bearer bonus: 1");
        }
        if(army.isFlankAttack()){
            score ++;
            appendLog( "    Flank attack bonus: 1");
        }
        if(army.isRearAttack()){
            score += 2;
            appendLog( "    Rear attack bonus");
        }
        if(army.isHasHighGround()){
            score++;
            appendLog( "    High Ground bonus: 1");
        }
        if(army.isHasBattleStandard()){
            score++;
            appendLog( "    Battle Standard bonus: 1");
        }
        if(army.getOverkill() > 0){
            score += army.getOverkill();
            appendLog( "    Challenge overkill bonus: " + army.getOverkill());
        }
        appendLog( "\nTotal Score: " + score);
        appendLog( getEndLogDivider(divider) + "\n");
        army.setCombatScore(score);
        return score;
    }

    private String getStartLogDivider(String name){
        int numDividers = (61 - name.length()) / 2;
        String divider = "";
        for(int i=0; i< numDividers; i++){
            divider += "*";
        }
        return divider;
    }

    private String getEndLogDivider(String startDivider){
        String divider = "";
        for(int i=0; i< 76; i++){
            divider += "*";
        }
        return divider;
    }


    public Set<CombatStage> createCombatStages(List<Unit> attackingArmyUnits, List<Unit> defendingArmyUnits){
        Set<CombatStage> stages = new HashSet<>();

        for(Unit unit : attackingArmyUnits){
            for (Unit compare : defendingArmyUnits) {
                if(!unitAlreadyAssignedToStage(stages, unit) && !compare.isMount() && !compare.isFlankAttack() && !compare.isRearAttack()) {
                    int attackOrder = unit.compareTo(compare);
                    CombatStage stage = null;
                    if (attackOrder == 1) {
                        stage = new DefaultCombatStage(unit, compare, unit.getUnitInitiative(), this);
                    }else if(attackOrder == -1){
                        stage = new DefaultCombatStage(compare, unit, compare.getUnitInitiative(), this);
                    }else if (attackOrder == 0) {
                        Map<Unit, Unit> simultaneous = new HashMap<>();
                        for(Unit compare2 : defendingArmyUnits){
                            if(compare2.getUnitInitiative() == unit.getUnitInitiative()){
                                if(!compare2.isMount()){
                                    simultaneous.put(unit, compare2);
                                }
                                if(!unitAlreadyAssignedToStage(stages, compare2) && !unit.isMount() && !unit.isFlankAttack() && !unit.isRearAttack()){
                                    simultaneous.put(compare2, unit);
                                }
                            }
                        }
                    }
                }
            }
        }
        return stages;
    }


    private boolean unitAlreadyAssignedToStage(Collection<CombatStage> stages, Unit unit) {
        for (CombatStage stage : stages) {
            if (stage instanceof SimultaneousCombatStage) {
                SimultaneousCombatStage simultaneous = (SimultaneousCombatStage) stage;
                for (Unit attacker : simultaneous.getCombatants().keySet()) {
                    return attacker.equals(unit);
                }
            } else {
                return stage.getAttacker().equals(unit);
            }
        }
        return false;
    }

    @Override
    public String getLog(){
        return this.log;
    }

    @Override
    public void appendLog(String text){
        if(!average)this.log = this.log.concat("\n" +text);
    }

    @Override
    public void appendLog(String text, boolean newLine){
        if(!average)this.log = this.log.concat(text);
    }

    @Override
    public void setAverage(boolean average){
        this.average = average;
    }
}
