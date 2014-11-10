package rational;

import java.util.Arrays;

public class Main {

    private static Unit unitA;
    private static Unit unitB;
    private static Equipment spear = new Equipment("Spear", Arrays.asList(SpecialRuleTypeEnum.FIGHT_IN_EXTRA_RANKS));
    private static Equipment lightArmor = new Equipment("Light Armor", 6, 0, true, false);
    private static Equipment shield = new Equipment("Shield", 1);
    private static UnitModel saurusWarriors = new UnitModel("Saurus Warriors", "Lizardmen", 4, 3, 0, 4, 4, 1, 1, 2, 8, 5, 8, Arrays.asList(spear, shield),
            Arrays.asList(SpecialRuleTypeEnum.COLD_BLOODED, SpecialRuleTypeEnum.PREDATORY_FIGHTER));
    private static UnitModel highElfSpearmen = new UnitModel("Spearmen", "High Elves", 5, 4, 4, 3, 3, 1, 5, 1, 8,  Arrays.asList(spear, lightArmor, shield),
            Arrays.asList(SpecialRuleTypeEnum.ALWAYS_STRIKE_FIRST, SpecialRuleTypeEnum.MARTIAL_PROWESS));
    private static UnitModel spawnLeader = new UnitModel("Spawn Leader", "Lizardmen", 4, 3, 0, 4, 4, 1, 1, 3, 8);
    private static UnitModel sentinel = new UnitModel("Sentinel", "High Elves", 5, 4, 4, 3, 3, 1, 5, 2, 8);

    public static void main(String[] args){
        CloseCombatService combatService = new DefaultCloseCombatService();
        unitA = new Unit();
        unitB = new Unit();
        UnitModel modelA = saurusWarriors;
        UnitModel modelB = highElfSpearmen;
        UnitModel championA = spawnLeader;
        UnitModel championB = sentinel;


        unitA.setModels(modelA);
        unitA.setName(modelA.getRace() + ": " + modelA.getName());
        unitA.setChampion(championA);
        unitA.setNumModels(30);
        unitA.setFiles(5);
        unitB.setLeadership(championA.getLeadership() > modelA.getLeadership() ? championA.getLeadership() : modelA.getLeadership());
        unitA.setHasBanner(true);

        unitB.setModels(modelB);
        unitB.setName(modelB.getRace() + ": " + modelB.getName());
        unitB.setChampion(championB);
        unitB.setNumModels(30);
        unitB.setFiles(5);
        unitB.setLeadership(championB.getLeadership() > modelB.getLeadership() ? championB.getLeadership() : modelB.getLeadership());
        unitB.setHasBanner(true);
        combatService.resolveCombat(unitA, unitB, AttackDirectionEnum.FRONT);
//        PrintStream originalStream = System.out;
//
//        PrintStream dummyStream    = new PrintStream(new OutputStream(){
//            public void write(int b) {
//                //NO-OP
//            }
//        });
//
//        System.setOut(dummyStream);
//        int saurusWins = 0;
//        int spearmenWins = 0;
//        int saurusCasualties = 0;
//        int spearmenCasualties = 0;
//        for(int i = 0; i<1000; i++){
//            Unit winner = combatService.resolveCombat(unitA, unitB, AttackDirectionEnum.FRONT);
//            if(null != winner) {
//                if (winner.equals(unitA)) {
//                    saurusWins++;
//                } else {
//                    spearmenWins++;
//                }
//                saurusCasualties += 30 - unitA.getNumModels();
//                spearmenCasualties += 30 - unitB.getNumModels();
//                unitA.setNumModels(30);
//                unitB.setNumModels(30);
//            }
//        }
//        System.setOut(originalStream);
//
//        System.out.print("Lizardmen wins: " + saurusWins + " \n " + "Average Lizardmen casualties: " + saurusCasualties/1000 +
//                "\nHigh Elf wins: " + spearmenWins + "\nAverage High Elf casualties: " + spearmenCasualties/1000);
    }
}
