package rational.controller;

import rational.enums.AttackDirectionEnum;
import rational.model.Army;
import rational.model.Unit;
import rational.model.UnitModel;
import rational.repository.Constants;
import rational.service.closecombat.CloseCombatService;
import rational.service.closecombat.impl.DefaultCloseCombatService;

import java.io.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;

public class Main {
    CloseCombatService combatService = new DefaultCloseCombatService();
    Army armyA;
    Army armyB;

    Unit unitA;
    Unit unitB;
    Unit unitC;
    int startingModelsA;
    int startingModelsB;
    int startingModelsC;


    public static void main(String[] args){
        Main main = new Main();
//        File unZippedFile = main.unZip(new File("C:\\Users\\pvarnerhowland\\BattleScribe\\data\\Warhammer Fantasy 8th ed\\High Elves - 8thBRB_8thAB.catz"));

        main.start();
    }

    private File unZip(File input) {
        File output = new File("unzippedFile");
        OutputStream out = null;
        try {
            ZipFile zipFile = new ZipFile(input);
            Enumeration<? extends ZipEntry> entries = zipFile.entries();
            while (entries.hasMoreElements()) {
                ZipEntry entry = entries.nextElement();
                File entryDestination = new File(output, entry.getName());
                entryDestination.getParentFile().mkdirs();
                InputStream in = zipFile.getInputStream(entry);
                ZipInputStream zis = new ZipInputStream(in);
                out = new FileOutputStream(entryDestination);
                byte[] buffer = new byte[8192];
                int bytes;
                while ((bytes = in.read(buffer)) > 0) {
                    out.write(buffer, 0, bytes);
                }
            }
        } catch (FileNotFoundException e) {
//            LOG.warn(e.getMessage());
        } catch (IOException e) {
//            LOG.warn(e.getMessage());
        } finally {
            try {
                out.close();
            } catch (IOException e) {
//                LOG.warn(e.getMessage());
            }
        }
        return output;
    }
    public void start(){
        createUnits();

        individualRounds(1);
//        printAverages();
    }

    public void createUnits(){
        armyA = new Army();
        armyB = new Army();

        unitA = new Unit();
        unitB = new Unit();
        unitC = new Unit();
        startingModelsA = 15;
        startingModelsB = 25;
        startingModelsC = 60;

        unitA.setHasBanner(true);
        unitA.setHasMusician(true);
        unitA.setHasChampion(true);
        unitA.setHasBattleStandard(true);
        unitA.setDefaultNumModels(startingModelsA);
        unitA.setCharging(true);
        unitA.initializeUnit(new UnitModel(Constants.Models.HighElves.dragonPrinces), 6, Arrays.asList(
                        new UnitModel(Constants.Models.HighElves.prince)), combatService);
        combatService.appendLog("Unit A: " + unitA.toString());

        unitB.setHasBanner(true);
        unitB.setHasMusician(true);
        unitB.setHasChampion(true);
        unitB.setHasBattleStandard(true);
        unitB.setDefaultNumModels(startingModelsB);
        unitB.initializeUnit(new UnitModel(Constants.Models.HighElves.highElfSpearmen), 5, new ArrayList<UnitModel>(), combatService);
        combatService.appendLog("Unit B: " + unitB.toString());

        unitC.setHasBanner(true);
        unitC.setHasMusician(true);
        unitC.setHasChampion(true);
        unitC.setDefaultNumModels(startingModelsB);
        unitC.initializeUnit(Constants.Models.Skaven.stormvermin, 12, new ArrayList<UnitModel>(), combatService);
        combatService.appendLog("Unit C: " + unitC.toString());

        unitA.getEngagedUnits().put(unitC, AttackDirectionEnum.FRONT);
        unitB.getEngagedUnits().put(unitC, AttackDirectionEnum.FLANK);
        unitC.getEngagedUnits().put(unitA, AttackDirectionEnum.FRONT);
        unitC.getEngagedUnits().put(unitB, null);
        armyA.getUnits().addAll(Arrays.asList(unitA, unitB));
        armyB.getUnits().add(unitC);
    }

    public void individualRounds(int numRounds){
//        for(int i = 1; i<= numRounds; i++) {
//            combatService.appendLog("Round " + i + "\n");
            combatService.resolveCombat(armyA, armyB);
//            if(unitA.getNumModels() == 0 || unitB.getNumModels() == 0){
//                break;
//            }
//        }

        System.out.println(combatService.getLog());
//        System.out.println(unitA.getCombatScore());
        try (PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("warhammer_combat_log.txt", true)))) {
            out.println(combatService.getLog());
//            out.println(unitA.getCombatScore());
        } catch (IOException e) {
            //exception handling left as an exercise for the reader
        }
    }

    public void printAverages(){
        combatService.setAverage(true);

        int modelAWins = 0;
        int modelBWins = 0;
        int modelACasualties = 0;
        int modelBCasualties = 0;
        int numBattles = 50000;
        int numUnitAAnnihilated = 0;
        int numUnitBAnnihilated = 0;
        BigDecimal unitAAverageScore = BigDecimal.ZERO;
        BigDecimal unitBAverageScore = BigDecimal.ZERO;
        int modelsA = unitA.getNumModels();
        int modelsB = unitB.getNumModels();
        UnitModel modelA = unitA.getUnitModel();
        UnitModel modelB = unitB.getUnitModel();
        String report = unitA.toString() + unitB.toString() + "\n";
        BigDecimal brokenA = BigDecimal.ZERO;
        BigDecimal brokenB = BigDecimal.ZERO;

        for(int i = 0; i<numBattles; i++){
            Unit winner = combatService.resolveCombat(armyA, armyB);
            if(null != winner) {
                if (winner.equals(unitA)) {
                    modelAWins++;
                } else {
                    modelBWins++;
                }
                if(null != unitA.getModels()){
                    modelACasualties += modelsA - unitA.getNumModels();
                }
                if(null != unitB.getModels()){
                    modelBCasualties += modelsB - unitB.getNumModels();
                }
                if(null == unitA.getModels()){
                    numUnitAAnnihilated++;
                }else if(null == unitB.getModels()){
                    numUnitBAnnihilated++;
                }
                if(unitA.isBroken()){
                    brokenA = brokenA.add(BigDecimal.ONE);
                }
                else if(unitB.isBroken()){
                    brokenB = brokenB.add(BigDecimal.ONE);
                }
                unitAAverageScore = unitAAverageScore.add(BigDecimal.valueOf(unitA.getCombatScore()));
                unitBAverageScore = unitBAverageScore.add(BigDecimal.valueOf(unitB.getCombatScore()));
            }
            createUnits();
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

        unitAAverageScore = unitAAverageScore.divide(BigDecimal.valueOf(numBattles)).setScale(0, BigDecimal.ROUND_HALF_UP);
        unitBAverageScore = unitBAverageScore.divide(BigDecimal.valueOf(numBattles)).setScale(0, BigDecimal.ROUND_HALF_UP);

        modelAAverage = modelAAverage.setScale(0, BigDecimal.ROUND_HALF_UP);
        modelBAverage = modelBAverage.setScale(0, BigDecimal.ROUND_HALF_UP);
        modelAAverage = modelAAverage.setScale(0, BigDecimal.ROUND_HALF_UP);
        modelAAnnihilatedAverage = modelAAnnihilatedAverage.setScale(0, BigDecimal.ROUND_HALF_UP);
        modelBAnnihilatedAverage = modelBAnnihilatedAverage.setScale(0, BigDecimal.ROUND_HALF_UP);
        drawAverage = drawAverage.setScale(0, BigDecimal.ROUND_HALF_UP);
        BigDecimal modelAAverageCasualties = BigDecimal.valueOf(modelACasualties).divide(BigDecimal.valueOf(numBattles)).setScale(0, BigDecimal.ROUND_HALF_UP);
        BigDecimal modelBAverageCasualties = BigDecimal.valueOf(modelBCasualties).divide(BigDecimal.valueOf(numBattles)).setScale(0, BigDecimal.ROUND_HALF_UP);

        brokenA = brokenA.divide(BigDecimal.valueOf(numBattles)).multiply(new BigDecimal(100)).setScale(0, BigDecimal.ROUND_HALF_UP);
        brokenB = brokenB.divide(BigDecimal.valueOf(numBattles)).multiply(new BigDecimal(100)).setScale(0, BigDecimal.ROUND_HALF_UP);

        report = report.concat(modelA.getName() + ":\nWin rate: " + modelAAverage + "%\n" + "Average wounds dealt: " +
                modelBAverageCasualties + "\n" + "Flee rate: " + brokenA + "%\nAnnihilation rate: " + modelAAnnihilatedAverage + "%\n" +
                "Average combat score: " + unitAAverageScore + "\n\n" +
                modelB.getName() + ":\n" + "Win rate: " + modelBAverage + "%\nAverage wounds dealt: " +
                modelAAverageCasualties + "\n"  + "Flee rate: " + brokenB +  "%\nAnnihilation rate: " + modelBAnnihilatedAverage + "%\n" +
                "Average combat score: " + unitBAverageScore + "\n\nDraw rate: " + drawAverage + "%" + "" +
                "\n*****************************************************************************************************************************" +
                "***************************\n");

        System.out.println(report);
        try(PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("warhammer_combat_averages_log.txt", true)))) {
            out.println(report);
        }catch (IOException e) {
            //exception handling left as an exercise for the reader
        }
    }
}
