package rational;

public class DefaultCloseCombatService implements CloseCombatService {


    @Override
    public Unit resolveCombat(Unit unitA, Unit unitB, AttackDirectionEnum direction) {
        Unit strikesFirst = unitA.strikeFirst(unitB);
        if(strikesFirst != null) {
            Unit strikesLast = strikesFirst.equals(unitA) ? unitB : unitA;
            System.out.println("Rolling " + strikesFirst.getName() + " attacks....");
            strikesFirst.attack(strikesLast, direction);
            strikesLast.setNumModels(strikesLast.getNumModels() - strikesLast.getWoundsReceived());

            System.out.println("\n\nRolling " + strikesLast.getName() + " attacks....");
            strikesLast.attack(strikesFirst, direction);
            strikesFirst.setNumModels(strikesFirst.getNumModels() - strikesFirst.getWoundsReceived());
        }else{
            unitA.attack(unitB, direction);
            unitB.attack(unitA, direction);

            unitB.setNumModels(unitB.getNumModels() - unitB.getWoundsReceived());
            unitA.setNumModels(unitA.getNumModels() - unitA.getWoundsReceived());
        }


        int result = unitA.calculateCombatScore(unitB) - unitB.calculateCombatScore(unitA);
        Unit winner = result > 0 ? unitA : (result == 0 ? null : unitB);
        if(null == winner){
            if(unitA.isHasMusician() && !unitB.isHasMusician()){
                winner = unitA;
            }else if(unitB.isHasMusician() && !unitA.isHasMusician()){
                winner = unitB;
            }
            System.out.println("Draw!");
            return null;
        }
        result = Math.abs(result);
        Unit loser = winner == unitA ? unitB : unitA;
        if(loser.getRanks() > winner.getRanks()){
            result = 0;
        }
        System.out.println(winner.getModels().getName() + " wins!");
        if(!loser.testLeadership(result)){
            System.out.println(loser.getModels().getName() + " fails their break test!");
            Dice d6 = Dice.getD6();
            int flee = d6.roll(2);
            System.out.println(loser.getModels().getName() + " flees " + flee + " inches");
            int pursue = d6.roll(2);
            System.out.println(winner.getModels().getName() + " pursues " + pursue + " inches");
            if(pursue >= flee){
                System.out.println(winner.getModels().getName() + " has caught " + loser.getModels().getName() + "!");
                System.out.println(loser.getModels().getName() + " has been annihilated!");
                loser.setNumModels(0);
                loser.setChampion(null);
                loser.setHero(null);
            }else{
                System.out.println(loser.getModels().getName() + " has escaped!");
            }
        }else{
            System.out.println(loser.getModels().getName() + " has passed their break test.");
        }

        return winner;
    }
}
