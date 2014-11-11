package rational;

import java.io.OutputStream;
import java.io.PrintStream;
import java.math.BigDecimal;
import java.util.Arrays;

public class Main {
    private static Weapon spear = new Weapon("Spear", null, null, Arrays.asList(SpecialRuleTypeEnum.FIGHT_IN_EXTRA_RANKS));
    private static Weapon halberd = new Weapon("Halberd", null, 1, Arrays.asList(SpecialRuleTypeEnum.FIGHT_IN_EXTRA_RANKS));
    private static Weapon greatWeapon = new Weapon("Great Weapon", null, 2, Arrays.asList(SpecialRuleTypeEnum.ALWAYS_STRIKE_LAST));

    private static Armor lightArmor = new Armor("Light Armor", 6, null, true, false);
    private static Armor heavyArmor = new Armor("Heavy Armor", 5, null, true, false);
    private static Armor fullPlateArmor = new Armor("Full Plate Armor", 4, null, true, false);
    private static Armor shield = new Armor("Shield", 1);

    //High Elves
    private static UnitModel highElfSpearmen = new UnitModel("Spearmen", "High Elves", 5, 4, 4, 3, 3, 1, 5, 1, 8, false, null, null, spear, lightArmor, shield,
            Arrays.asList(SpecialRuleTypeEnum.ALWAYS_STRIKE_FIRST, SpecialRuleTypeEnum.MARTIAL_PROWESS));
    private static UnitModel sentinel = new UnitModel("Sentinel", "High Elves", 5, 4, 4, 3, 3, 1, 5, 2, 8, true, null, null, spear, lightArmor, shield,
            Arrays.asList(SpecialRuleTypeEnum.ALWAYS_STRIKE_FIRST, SpecialRuleTypeEnum.MARTIAL_PROWESS));

    private static UnitModel swordMastersOfHoeth = new UnitModel("Swordmasters of Hoeth", "High Elves", 5, 6, 4, 3, 3, 1, 5, 2, 8, false, null, null, greatWeapon,
            heavyArmor, null, Arrays.asList(SpecialRuleTypeEnum.ALWAYS_STRIKE_FIRST, SpecialRuleTypeEnum.MARTIAL_PROWESS));
    private static UnitModel bladeLord = new UnitModel("Bladelord", "High Elves", 5, 6, 4, 3, 3, 1, 5, 3, 8, true, null, null, greatWeapon,
            heavyArmor, null, Arrays.asList(SpecialRuleTypeEnum.ALWAYS_STRIKE_FIRST, SpecialRuleTypeEnum.MARTIAL_PROWESS));

    private static UnitModel phoenixGuard = new UnitModel("Phoenix Guard", "High Elves", 4, 5, 4, 3, 3, 1, 6, 1, 9, false, null, 4, halberd,
            heavyArmor, null, Arrays.asList(SpecialRuleTypeEnum.ALWAYS_STRIKE_FIRST, SpecialRuleTypeEnum.MARTIAL_PROWESS));
    private static UnitModel keeperOfTheFlame = new UnitModel("Keeper of the Flame", "High Elves", 4, 5, 4, 3, 3, 1, 6, 2, 9, true, null, 4, halberd,
            heavyArmor, null, Arrays.asList(SpecialRuleTypeEnum.ALWAYS_STRIKE_FIRST, SpecialRuleTypeEnum.MARTIAL_PROWESS));


    //Lizardmen
    private static UnitModel saurusWarriors = new UnitModel("Saurus Warriors", "Lizardmen", 4, 3, 0, 4, 4, 1, 1, 2, 8, false, 5, null, spear, null, shield,
            Arrays.asList(SpecialRuleTypeEnum.COLD_BLOODED, SpecialRuleTypeEnum.PREDATORY_FIGHTER));
    private static UnitModel spawnLeader = new UnitModel("Spawn Leader", "Lizardmen", 4, 3, 0, 4, 4, 1, 1, 3, 8, true, 5, null, spear, null, shield,
            Arrays.asList(SpecialRuleTypeEnum.COLD_BLOODED, SpecialRuleTypeEnum.PREDATORY_FIGHTER));

    private static UnitModel templeGuard = new UnitModel("Temple Guard", "Lizardmen", 4, 3, 0, 4, 4, 1, 1, 2, 8, true, 5, null, halberd, lightArmor, shield,
            Arrays.asList(SpecialRuleTypeEnum.COLD_BLOODED, SpecialRuleTypeEnum.PREDATORY_FIGHTER, SpecialRuleTypeEnum.STUBBORN));
    private static UnitModel reveredGuardian = new UnitModel("Revered Guardian", "Lizardmen", 4, 4, 0, 4, 4, 1, 2, 3, 8, true, 5, null, spear, null, shield,
            Arrays.asList(SpecialRuleTypeEnum.COLD_BLOODED, SpecialRuleTypeEnum.PREDATORY_FIGHTER, SpecialRuleTypeEnum.STUBBORN));

    private static UnitModel kroxigor = new UnitModel("Kroxigor", "Lizardmen", 6, 3, 0, 5, 4, 3, 1, 3, 7, false, 4, null, greatWeapon, null, null,
            Arrays.asList(SpecialRuleTypeEnum.COLD_BLOODED, SpecialRuleTypeEnum.PREDATORY_FIGHTER, SpecialRuleTypeEnum.MONSTEROUS_INFANTRY));
    private static UnitModel kroxigorAncient = new UnitModel("Kroxigor Ancient", "Lizardmen", 6, 3, 0, 5, 4, 3, 1, 4, 7, true, 4, null, greatWeapon, null, null,
            Arrays.asList(SpecialRuleTypeEnum.COLD_BLOODED, SpecialRuleTypeEnum.PREDATORY_FIGHTER, SpecialRuleTypeEnum.MONSTEROUS_INFANTRY));



    CloseCombatService combatService;
    private Unit unitA;
    private Unit unitB;
    private UnitModel modelA;
    private UnitModel modelB;
    private UnitModel championA;
    private UnitModel championB;

    int startingModelsA;
    int startingModelsB;


    public static void main(String[] args){
        Main main = new Main();
        main.start();
    }

    public void start(){
        startingModelsA = 8;
        startingModelsB = 30;

        combatService = new DefaultCloseCombatService();
        unitA = new Unit();
        unitB = new Unit();
        modelA = kroxigor;
        modelB = highElfSpearmen;
        championA = kroxigorAncient;
        championB = sentinel;


        unitA.createUnit(modelA, startingModelsA, 3, championA, null);
        unitA.setName(modelA.getRace() + ": " + modelA.getName());
        unitA.setChampion(championA);
        unitA.setLeadership(championA.getLeadership() > modelA.getLeadership() ? championA.getLeadership() : modelA.getLeadership());
        unitA.setHasBanner(true);
        unitA.setFlankAttack(false);

        unitB.createUnit(modelB, startingModelsB, 5, championB, null);
        unitB.setName(modelB.getRace() + ": " + modelB.getName());
        unitB.setChampion(championB);
        unitB.setLeadership(championB.getLeadership() > modelB.getLeadership() ? championB.getLeadership() : modelB.getLeadership());
        unitB.setHasBanner(true);
        unitB.setFlankAttack(false);

        combatService.resolveCombat(unitA, unitB);
//        printAverages();
    }

    public void printAverages(){
        PrintStream originalStream = System.out;

        PrintStream dummyStream    = new PrintStream(new OutputStream(){
            public void write(int b) {
                //NO-OP
            }
        });

        System.setOut(dummyStream);
        int modelAWins = 0;
        int modelBWins = 0;
        int modelACasualties = 0;
        int modelBCasualties = 0;
        int numBattles = 100000;
        int numUnitAAnnihilated = 0;
        int numUnitBAnnihilated = 0;
        int modelsA = unitA.getNumModels();
        int modelsB = unitB.getNumModels();

        for(int i = 0; i<numBattles; i++){
            Unit winner = combatService.resolveCombat(unitA, unitB);
            if(null != winner) {
                if (winner.equals(unitA)) {
                    modelAWins++;
                } else {
                    modelBWins++;
                }
                modelACasualties += modelsA - unitA.getNumModels();
                modelBCasualties += modelsB - unitB.getNumModels();
                if(unitA.getNumModels() <= 0){
                    numUnitAAnnihilated++;
                }else if(unitB.getNumModels() <= 0){
                    numUnitBAnnihilated++;
                }
            }
            unitA.createUnit(modelA, startingModelsA, 3, championA, null);
            unitB.createUnit(modelB, startingModelsB, 5, championB, null);
        }
        BigDecimal modelAAverage = new BigDecimal(modelAWins);
        BigDecimal modelBAverage = new BigDecimal(modelBWins);
        BigDecimal modelAAnnihilatedAverage = new BigDecimal(numUnitAAnnihilated);
        BigDecimal modelBAnnihilatedAverage = new BigDecimal(numUnitBAnnihilated);
        BigDecimal drawAverage = new BigDecimal(numBattles - modelAWins - modelBWins);
        drawAverage = drawAverage.divide(new BigDecimal(numBattles));
        modelAAverage = modelAAverage.divide(new BigDecimal(numBattles));
        modelBAverage = modelBAverage.divide(new BigDecimal(numBattles));
        modelAAnnihilatedAverage = modelAAnnihilatedAverage.divide(new BigDecimal(numBattles));
        modelBAnnihilatedAverage = modelBAnnihilatedAverage.divide(new BigDecimal(numBattles));
        modelAAverage = modelAAverage.multiply(new BigDecimal(100));
        modelBAverage = modelBAverage.multiply(new BigDecimal(100));
        modelAAnnihilatedAverage = modelAAnnihilatedAverage.multiply(new BigDecimal(100));
        modelBAnnihilatedAverage = modelBAnnihilatedAverage.multiply(new BigDecimal(100));
        drawAverage = drawAverage.multiply(new BigDecimal(100));

        modelAAverage = modelAAverage.setScale(1, BigDecimal.ROUND_UP);
        modelBAverage = modelBAverage.setScale(1, BigDecimal.ROUND_UP);
        modelAAverage = modelAAverage.setScale(1, BigDecimal.ROUND_UP);
        modelAAnnihilatedAverage = modelAAnnihilatedAverage.setScale(1, BigDecimal.ROUND_UP);
        modelBAnnihilatedAverage = modelBAnnihilatedAverage.setScale(1, BigDecimal.ROUND_UP);
        drawAverage = drawAverage.setScale(1, BigDecimal.ROUND_UP);
        int modelAAverageCasualties = modelACasualties/numBattles;
        int modelBAverageCasualties = modelBCasualties/numBattles;
        System.setOut(originalStream);
        System.out.print(modelA.getName() + " win rate: " + modelAAverage + "%\n" + "Average " + modelA.getName() + " casualties: " +
                modelAAverageCasualties + "\n" + modelA.getName() + " annihilation average: " + modelAAnnihilatedAverage + "%\n" + modelB.getName() + " win rate: " + modelBAverage + "%\nAverage " + modelB.getName() +
                " casualties: " + modelBAverageCasualties + "\n" + modelB.getName() + " annihilation average: " + modelBAnnihilatedAverage + "%\n" + "\nDraw rate: " + drawAverage + "%");
    }
}
