package rational;

import java.util.*;

public class DefaultCloseCombatService implements CloseCombatService {


    @Override
    public Unit resolveCombat(Unit unitA, Unit unitB) {


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
        Unit winner;
        Unit loser;
        if(unitA.getNumModels() > 0 && unitB.getNumModels() > 0) {
            int result = unitA.calculateCombatScore(unitB) - unitB.calculateCombatScore(unitA);
            winner = result > 0 ? unitA : (result == 0 ? null : unitB);
            if (null == winner) {
                if (unitA.isHasMusician() && !unitB.isHasMusician()) {
                    winner = unitA;
                } else if (unitB.isHasMusician() && !unitA.isHasMusician()) {
                    winner = unitB;
                } else {
                    System.out.println("Draw!");
                    return null;
                }
            }
            result = Math.abs(result);
            loser = winner == unitA ? unitB : unitA;
            System.out.println(winner.getUnitModel().getName() + " wins!");

            if (loser.isSteadfast(winner) || loser.getUnitModel().getSpecialRules().contains(SpecialRuleTypeEnum.STUBBORN)) {
                System.out.println(loser.getUnitModel().getName() + " remains steadfast.");
                result = 0;
            }
            if (!loser.testLeadership(result)) {
                System.out.println(loser.getUnitModel().getName() + " fails their break test!");
                Dice d6 = Dice.getD6();
                int flee = d6.roll(2);
                System.out.println(loser.getUnitModel().getName() + " flees " + flee + " inches");
                int pursue = d6.roll(2);
                System.out.println(winner.getUnitModel().getName() + " pursues " + pursue + " inches");
                if (pursue >= flee) {
                    System.out.println(winner.getUnitModel().getName() + " has caught " + loser.getUnitModel().getName() + "!");
                    System.out.println(loser.getUnitModel().getName() + " has been annihilated!");
                    loser.setNumModels(0);
                    loser.setChampion(null);
                    loser.setHero(null);
                } else {
                    System.out.println(loser.getUnitModel().getName() + " has escaped!");
                }
            } else {
                System.out.println(loser.getUnitModel().getName() + " has passed their break test.");
            }
        }else{
            winner = unitA.getNumModels() > 0 ? unitA : unitB;
            loser = winner.equals(unitA) ? unitB : unitA;
            System.out.println(loser.getUnitModel().getName() + " has been annihilated!");
            System.out.println(winner.getUnitModel().getName() + " wins!");
        }

        return winner;
    }

    private Queue<CombatStage> establishInitiativeOrder(List<Unit> attackingArmyUnits, List<Unit> defendingArmyUnits) {
        Queue<CombatStage> order = new LinkedList<>();

        List<Unit> attackFirst = new ArrayList<>();
        List<Unit> attackLast = new ArrayList<>();
        Map<Integer, SimultaneousCombatStage> simultaneousStages = new HashMap<>();
        List<CombatStage> stages = new ArrayList<>();

        for(Unit unit : attackingArmyUnits){
            if(unit.getUnitModel().getSpecialRules().contains(SpecialRuleTypeEnum.ALWAYS_STRIKE_FIRST)){
                if(!unit.getUnitModel().getSpecialRules().contains(SpecialRuleTypeEnum.ALWAYS_STRIKE_LAST)){
                    attackFirst.add(unit);
                }
            }else if(unit.getUnitModel().getSpecialRules().contains(SpecialRuleTypeEnum.ALWAYS_STRIKE_LAST)){
                attackLast.add(unit);
            }
        }

        for(Unit unit : defendingArmyUnits){
            if(unit.getUnitModel().getSpecialRules().contains(SpecialRuleTypeEnum.ALWAYS_STRIKE_FIRST)){
                if(!unit.getUnitModel().getSpecialRules().contains(SpecialRuleTypeEnum.ALWAYS_STRIKE_LAST)){
                    attackFirst.add(unit);
                }
            }else if(unit.getUnitModel().getSpecialRules().contains(SpecialRuleTypeEnum.ALWAYS_STRIKE_LAST)){
                attackLast.add(unit);
            }
        }

        if(!attackFirst.isEmpty()) {
            CombatStage firstStage;
            if (attackFirst.size() > 1) {
                Map<Unit, Unit> simultaneous = new HashMap<>();
                for(Unit unit : attackFirst) {
                    for(Unit compare : attackFirst){
                        if(!unit.equals(compare) && !compare.isMount()){
                            simultaneous.put(unit, compare);
                        }
                    }
                }
                firstStage = new SimultaneousCombatStage(simultaneous, 0);
                stages.add(firstStage);
            }else{
                firstStage = null;
                for(Unit defender : defendingArmyUnits){
                    if(!defender.isMount()){
                        firstStage = new CombatStage(attackFirst.get(0), defender, attackFirst.get(0).getUnitInitiative());
                        stages.add(firstStage);
                    }
                }
            }
        }



//        stages.addAll(createCombatStages(attackingArmyUnits, defendingArmyUnits));
//        stages.addAll(createCombatStages(defendingArmyUnits, attackingArmyUnits));

        for(Unit unit : attackingArmyUnits){
            for (Unit compare : defendingArmyUnits) {
                if((!unitAlreadyAssignedToStage(stages, unit) || compare.isFlankAttack() || compare.isRearAttack()) && !compare.isMount()) {
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
                            stage = new SimultaneousCombatStage(simultaneous, unit.getUnitInitiative());
                        }else{
                            simultaneousStages.get(unit.getUnitInitiative()).getCombatants().putAll(simultaneous);
                        }
                    }else if(!unitAlreadyAssignedToStage(stages, unit) && !compare.isMount() && !compare.isFlankAttack() && !compare.isRearAttack()){
                        stage = new CombatStage(unit, compare, unit.getUnitInitiative());
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
                if((!unitAlreadyAssignedToStage(stages, unit) || compare.isFlankAttack() || compare.isRearAttack()) && !compare.isMount()) {
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
                            stage = new SimultaneousCombatStage(simultaneous, unit.getUnitInitiative());
                        }else{
                            simultaneousStages.get(unit.getUnitInitiative()).getCombatants().putAll(simultaneous);
                        }
                    }else if(!unitAlreadyAssignedToStage(stages, unit) && !compare.isMount() && !compare.isFlankAttack() && !compare.isRearAttack()){
                        stage = new CombatStage(unit, compare, unit.getUnitInitiative());
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


    public Set<CombatStage> createCombatStages(List<Unit> attackingArmyUnits, List<Unit> defendingArmyUnits){
        Set<CombatStage> stages = new HashSet<>();

        for(Unit unit : attackingArmyUnits){
            for (Unit compare : defendingArmyUnits) {
                if(!unitAlreadyAssignedToStage(stages, unit) && !compare.isMount() && !compare.isFlankAttack() && !compare.isRearAttack()) {
                    int attackOrder = unit.compareTo(compare);
                    CombatStage stage = null;
                    if (attackOrder == 1) {
                        stage = new CombatStage(unit, compare, unit.getUnitInitiative());
                    }else if(attackOrder == -1){
                        stage = new CombatStage(compare, unit, compare.getUnitInitiative());
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


    private boolean unitAlreadyAssignedToStage(Collection<CombatStage> stages, Unit unit){
        for(CombatStage stage : stages){
            if(stage instanceof SimultaneousCombatStage){
                SimultaneousCombatStage simultaneous = (SimultaneousCombatStage)stage;
                for(Unit attacker : simultaneous.getCombatants().keySet()){
                    return attacker.equals(unit);
                }
            }else{
                return stage.getAttacker().equals(unit);
            }
        }
        return false;
    }
}
