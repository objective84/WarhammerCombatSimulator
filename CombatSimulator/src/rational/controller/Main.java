package rational.controller;

import rational.model.Unit;
import rational.model.UnitModel;
import rational.repository.Constants;
import rational.service.CloseCombatService;
import rational.service.DefaultCloseCombatService;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.util.Arrays;

public class Main {
    CloseCombatService combatService = new DefaultCloseCombatService();

    Unit unitA;
    Unit unitB;
    int startingModelsA;
    int startingModelsB;


    public static void main(String[] args){
        Main main = new Main();
        main.start();
    }
    public void start(){
        createUnits();

        individualRounds(1);
//        printAverages();
    }

    public void createUnits(){
        unitA = new Unit();
        unitB = new Unit();
        startingModelsA = 15;
        startingModelsB = 30;

        unitA.setHasBanner(true);
        unitA.setHasMusician(true);
        unitA.setHasChampion(true);
        unitA.setFlankAttack(false);
        unitA.setHasBattleStandard(true);
        unitA.setDefaultNumModels(startingModelsA);
        unitA.setCharging(true);
        unitA.initializeUnit(new UnitModel(Constants.Models.HighElves.dragonPrinces), 5, Arrays.asList(
                        new UnitModel(Constants.Models.HighElves.prince)), combatService);
        combatService.appendLog("Unit A: " + unitA.toString());

        unitB.setHasBanner(true);
        unitB.setHasMusician(true);
        unitB.setHasChampion(true);
        unitB.setFlankAttack(false);
        unitB.setDefaultNumModels(startingModelsB);
        unitB.initializeUnit(Constants.Models.Skaven.stormvermin, 5, Arrays.asList(Constants.Models.Skaven.warlord), combatService);
        combatService.appendLog("Unit B: " + unitB.toString());
    }

    public void individualRounds(int numRounds){
        for(int i = 1; i<= numRounds; i++) {
            combatService.appendLog("Round " + i + "\n");
            combatService.resolveCombat(unitA, unitB);
            if(unitA.getNumModels() == 0 || unitB.getNumModels() == 0){
                break;
            }
        }

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
            Unit winner = combatService.resolveCombat(unitA, unitB);
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
