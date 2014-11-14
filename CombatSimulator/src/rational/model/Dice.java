package rational.model;

import java.util.Random;

public class Dice {

    private Long id;
    private String name;

    private int maxRoll;

    private int minRoll = 1;

    private Integer averageRoll;

    public Dice(){}

    public Dice(Dice dice){
        this.name = dice.getName();
        this.minRoll = dice.getMinRoll();
        this.maxRoll = dice.getMaxRoll();
        this.averageRoll = dice.getAverageRoll();
    }

    public Dice(String name, int min, int max, int average){
        this.name = name;
        this.minRoll = min;
        this.maxRoll = max;
        this.averageRoll = average;
    }

    public int roll(Integer amt) {
        Random rand = new Random();
        Integer totalRoll = 0;
        for(int i=0; i<amt; i++) {
            totalRoll += rand.nextInt(this.getMaxRoll()) + this.getMinRoll();
        }
        return totalRoll;
    }

    public int[] rollSeparateDice(Integer amt) {
        Random rand = new Random();
        int[] rolls = new int[amt];
        for(int i=0; i<amt; i++) {
            rolls[i] = rand.nextInt(this.getMaxRoll()) + this.getMinRoll();
        }
        return rolls;
    }

    public static Dice getD6(){
        return new Dice("d6", 1, 6, 4);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getMaxRoll() {
        return maxRoll;
    }

    public void setMaxRoll(int minimumValue) {
        this.maxRoll = minimumValue;
    }

    public Integer getMinRoll() {
        return minRoll;
    }

    public void setMinRoll(Integer minRoll) {
        this.minRoll = minRoll;
    }

    public Integer getAverageRoll() {
        return averageRoll;
    }

    public void setAverageRoll(Integer averageRoll) {
        this.averageRoll = averageRoll;
    }
}
