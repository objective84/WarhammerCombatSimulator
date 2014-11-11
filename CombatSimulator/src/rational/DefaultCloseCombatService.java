package rational;

public class DefaultCloseCombatService implements CloseCombatService {


    @Override
    public Unit resolveCombat(Unit unitA, Unit unitB) {
        Unit strikesFirst = unitA.strikeFirst(unitB);
        AttackDirectionEnum direction;
        if(strikesFirst != null) {
            direction = strikesFirst.isFlankAttack() ? AttackDirectionEnum.FRONT : AttackDirectionEnum.FLANK;
            Unit strikesLast = strikesFirst.equals(unitA) ? unitB : unitA;
            System.out.println("Rolling " + strikesFirst.getName() + " attacks....");
            strikesFirst.attack(strikesLast, direction);
            strikesLast.removeCasualties(strikesLast.getWoundsReceived());

            direction = strikesLast.isFlankAttack() ? AttackDirectionEnum.FRONT : AttackDirectionEnum.FLANK;
            System.out.println("\n\nRolling " + strikesLast.getName() + " attacks....");
            strikesLast.attack(strikesFirst, direction);
            strikesFirst.removeCasualties(strikesFirst.getWoundsReceived());
        }else{
            direction = unitA.isFlankAttack() ? AttackDirectionEnum.FRONT : AttackDirectionEnum.FLANK;
            unitA.attack(unitB, direction);
            direction = unitB.isFlankAttack() ? AttackDirectionEnum.FRONT : AttackDirectionEnum.FLANK;
            unitB.attack(unitA, direction);

            unitA.removeCasualties(unitA.getWoundsReceived());
            unitB.removeCasualties(unitB.getWoundsReceived());
        }


        int result = unitA.calculateCombatScore(unitB) - unitB.calculateCombatScore(unitA);
        Unit winner = result > 0 ? unitA : (result == 0 ? null : unitB);
        if(null == winner){
            if(unitA.isHasMusician() && !unitB.isHasMusician()){
                winner = unitA;
            }else if(unitB.isHasMusician() && !unitA.isHasMusician()){
                winner = unitB;
            }else {
                System.out.println("Draw!");
                return null;
            }
        }
        result = Math.abs(result);
        Unit loser = winner == unitA ? unitB : unitA;
        System.out.println(winner.getUnitModel().getName() + " wins!");

        if(loser.isSteadfast(winner) || loser.getUnitModel().getSpecialRules().contains(SpecialRuleTypeEnum.STUBBORN)){
            System.out.println(loser.getUnitModel().getName() + " is steadfast.");
            result = 0;
        }
        if(!loser.testLeadership(result)){
            System.out.println(loser.getUnitModel().getName() + " fails their break test!");
            Dice d6 = Dice.getD6();
            int flee = d6.roll(2);
            System.out.println(loser.getUnitModel().getName() + " flees " + flee + " inches");
            int pursue = d6.roll(2);
            System.out.println(winner.getUnitModel().getName() + " pursues " + pursue + " inches");
            if(pursue >= flee){
                System.out.println(winner.getUnitModel().getName() + " has caught " + loser.getUnitModel().getName() + "!");
                System.out.println(loser.getUnitModel().getName() + " has been annihilated!");
                loser.setNumModels(0);
                loser.setChampion(null);
                loser.setHero(null);
            }else{
                System.out.println(loser.getUnitModel().getName() + " has escaped!");
            }
        }else{
            System.out.println(loser.getUnitModel().getName() + " has passed their break test.");
        }

        return winner;
    }
}
