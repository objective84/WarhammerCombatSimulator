package rational.controller;

import rational.model.Unit;
import rational.model.UnitModel;
import rational.repository.Constants;
import rational.service.CloseCombatService;
import rational.service.DefaultCloseCombatService;

import java.io.OutputStream;
import java.io.PrintStream;
import java.math.BigDecimal;

public class Main {
    CloseCombatService combatService = new DefaultCloseCombatService();
    private Unit unitA = new Unit();
    private Unit unitB = new Unit();

    int startingModelsA;
    int startingModelsB;


    public static void main(String[] args){
        Main main = new Main();
        main.start();
    }

    public void start(){
        startingModelsA = 5;
        startingModelsB = 5;

        unitA.createUnit(Constants.Models.HighElves.silverHelm, startingModelsA, 5, null);
        unitA.setHasBanner(true);
        unitA.setHasMusician(true);
        unitA.setFlankAttack(false);

        unitB.createUnit(Constants.Models.Empire.empireKnight, startingModelsB, 5, null);
        unitB.setHasBanner(true);
        unitB.setHasMusician(true);
        unitB.setFlankAttack(false);

//        combatService.resolveCombat(unitA, unitB);
        printAverages();
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
        UnitModel modelA = unitA.getUnitModel();
        UnitModel modelB = unitB.getUnitModel();

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
            unitA.createUnit(modelA, startingModelsA, 3, null);
            unitB.createUnit(modelB, startingModelsB, 5, null);
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
                modelAAverageCasualties + "\n" + modelA.getName() + " annihilation average: " + modelAAnnihilatedAverage + "%\n" + modelB.getName()
                + " win rate: " + modelBAverage + "%\nAverage " + modelB.getName() + " casualties: " + modelBAverageCasualties + "\n" +
                modelB.getName() + " annihilation average: " + modelBAnnihilatedAverage + "%\n" + "\nDraw rate: " + drawAverage + "%");
    }
}
