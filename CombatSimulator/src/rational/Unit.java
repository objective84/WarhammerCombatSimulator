package rational;

import java.util.Arrays;
import java.util.Random;

public class Unit {
    public static int[][] toHitChart = {
            {},
            {0,4,5,5,5,5,5,5,5,5,5},
            {0,3,4,5,5,5,5,5,5,5,5},
            {0,3,3,4,4,5,5,5,5,5,5},
            {0,3,3,3,4,4,4,5,5,5,5},
            {0,3,3,3,3,4,4,4,5,5,5},
            {0,3,3,3,3,3,4,4,4,4,4},
            {0,3,3,3,3,3,3,4,4,4,4},
            {0,3,3,3,3,3,3,3,4,4,4},
            {0,3,3,3,3,3,3,3,3,4,4},
            {0,3,3,3,3,3,3,3,3,3,4}
    };

    public static int[][] toWoundChart = {
            {},
            {0,4,5,6,6,6,6,6,6,6,6},
            {0,3,4,5,6,6,6,6,6,6,6},
            {0,2,3,4,5,6,6,6,6,6,6},
            {0,2,2,3,4,5,6,6,6,6,6},
            {0,2,2,2,3,4,5,6,6,6,6},
            {0,2,2,2,2,3,4,5,6,6,6},
            {0,2,2,2,2,2,3,4,5,6,6},
            {0,2,2,2,2,2,2,3,4,5,6},
            {0,2,2,2,2,2,2,2,3,4,5},
            {0,2,2,2,2,2,2,2,2,3,4},
    };

    private String name;
    private UnitModel models;
    private UnitModel hero;
    private UnitModel champion;
    private int numModels;
    private int files;
    private int leadership;
    private int woundsReceived;
    private boolean charging;
    private boolean hasBanner;
    private boolean hasBattleStandard;
    private boolean flankAttack;
    private boolean rearAttack;
    private boolean hasHighGround;
    private boolean hasMusician;
    private int overkill;

    public int getRanks(){
        return (numModels / files);
    }

    public boolean testLeadership(int modifier){
        System.out.println("\n" + this.getModels().getName() + " rolls for a break test with a modified leadership of " +
                (this.leadership - modifier) + "... ");
        Dice die = Dice.getD6();
        int test = 0;
        int[] rolls;
        if(this.getModels().getSpecialRules().contains(SpecialRuleTypeEnum.COLD_BLOODED)){
            rolls = die.rollSeparateDice(3);
        }else {
            rolls = die.rollSeparateDice(2);
            System.out.println("    " + Arrays.toString(rolls));
            int highest = 0;
            for (int i : rolls) {
                test += i;
                if (i > highest) {
                    highest = i;
                }
            }

            if (this.getModels().getSpecialRules().contains(SpecialRuleTypeEnum.COLD_BLOODED)) {
                test -= highest;
            }
            if(rolls[0] == 1 && rolls[1] == 1){
                return true;
            }
        }
        return test + modifier <= this.leadership;
    }

    public int calculateCombatScore(Unit other){
        int score = other.getWoundsReceived();
        score += this.charging ? 1 : 0;

        score += getRanks() > 3 ? (getRanks() - 3) : 0;
        score += this.hasBanner ? 1 : 0;
        score += this.flankAttack ? 1 : 0;
        score += this.rearAttack ? 2 : 0;
        score += overkill;
        other.setWoundsReceived(0);
        System.out.println("\n    " + this.getModels().getName() + " score: " + score);
        return score;
    }

    public int getUnitInitiative(){
        return models.getInitiative();
    }

    public UnitModel getModels() {
        return models;
    }

    public void attack(Unit defender, AttackDirectionEnum direction){
        int difficulty = toHitChart[this.getModels().getWeaponSkill()][defender.getModels().getWeaponSkill()];
        System.out.println("    " + this.getModels().getName() + " hits on " + difficulty + "+");
        int hits = rollCombatDice(defender, this.getNumAttacks(defender, direction), difficulty, true);
        System.out.println("    " + hits + " attacks hit");
        int totalHits = hits;
        int wounds = 0;
        if(hits > 0){
            wounds += defender.getWoundsReceived() + this.getWounds(defender, hits, this.getModels().getStrength());
        }

        if(this.getChampion() != null){
            System.out.println("\n    Rolling for " + this.getChampion().getName() + "....");
            System.out.println("    " + this.getChampion().getName() + " has " + this.getChampion().getAttacks() + " attacks");
            difficulty = toHitChart[this.getChampion().getWeaponSkill()][defender.getModels().getWeaponSkill()];
            System.out.println("    " + this.getChampion().getName() + " hits on " + difficulty + "+");
            hits = rollCombatDice(defender, this.getChampion().getAttacks(), difficulty, true);
            System.out.println("    " + hits + " attacks hit");
            totalHits += hits;
            if(hits > 0){
                wounds += getWounds(defender, hits, this.champion.getStrength());
            }
        }
        if(this.getHero() != null){
            System.out.println("\n    Rolling for " + this.getHero().getName() + "....");
            System.out.println("    " + this.getHero().getName() + " has " + this.getHero().getAttacks() + " attacks");
            difficulty = toHitChart[this.getHero().getWeaponSkill()][defender.getModels().getWeaponSkill()];
            System.out.println("    " + this.getHero().getName() + " hits on " + difficulty + "+");
            hits = rollCombatDice(defender, this.getHero().getAttacks(), difficulty, true);
            System.out.println("    " + hits + " attacks hit");
            totalHits += hits;
            if(hits > 0){
                wounds += getWounds(defender, hits, this.hero.getStrength());
            }
        }
        System.out.println("    " + this.getModels().getName() + " hits: " + totalHits);
        System.out.println("    " + this.getModels().getName() + " wounds : " + wounds);
        defender.setWoundsReceived(wounds);
    }

    private int getWounds(Unit defender, int numHits, int attackStrength){
        System.out.println("    Rolling for wounds....");
        int difficulty = toWoundChart[attackStrength][defender.getModels().getToughness()];
        System.out.println("    " + this.getModels().getName() + " wounds on " + difficulty + "+");
        int wounds = rollCombatDice(defender, numHits, difficulty, false);
        if(null != defender.getModels().getArmorSave()){
            int save = defender.getModels().getArmorSave() + (3-attackStrength);
            if(save <= 6){
                if(wounds > 0){
                    System.out.println("    " + defender.getModels().getName() + " have a " + save + "+ armor save...");
                    wounds = rollSave(wounds, save);
                }
            }
        }
        if(null != defender.getModels().getWardSave()){
            int save = defender.getModels().getWardSave();
            if(save <= 6){
                if(wounds > 0){
                    System.out.println("    " + defender.getModels().getName() + " have a " + save + "+ ward save...");
                    wounds = rollSave(wounds, save);
                }
            }
        }
        return wounds;
    }

    public int rollCombatDice(Unit defender, Integer amt, int difficulty, boolean reRollAllowed) {
        Dice d6 = Dice.getD6();
        Random rand = new Random();
        int[] attacks = d6.rollSeparateDice(amt);
        System.out.println("    " + Arrays.toString(attacks));

        int hits = 0;
        for(int i=0; i<amt; i++) {
            if(attacks[i] >= difficulty){
                hits++;
            }
        }

        if(this.getModels().getSpecialRules().contains(SpecialRuleTypeEnum.PREDATORY_FIGHTER) && reRollAllowed){
            int additionalAttacks = 0;
            for(int i=0; i<amt; i++) {
                if(attacks[i] == 6){
                    additionalAttacks++;
                }
            }
            if(additionalAttacks > 0) {
                System.out.println("    " + this.getModels().getName() + " gets " + additionalAttacks + " additional attacks....");
                hits += rollCombatDice(defender, additionalAttacks, difficulty, false);
            }
        }

        if(this.getModels().getSpecialRules().contains(SpecialRuleTypeEnum.ALWAYS_STRIKE_FIRST) && reRollAllowed){
            if(amt - hits > 0) {
                System.out.println("    Re-rolling " + (amt - hits) + " misses....");

                if (this.getUnitInitiative() >= defender.getUnitInitiative() &&
                        !defender.getModels().getSpecialRules().contains(SpecialRuleTypeEnum.ALWAYS_STRIKE_FIRST)) {
                    hits += rollCombatDice(defender, amt - hits, difficulty, false);
                }
            }
        }

        return hits;
    }

    private int rollSave(int amt, int save){
        Dice d6 = Dice.getD6();
        int wounds = amt;
        int[] saves = d6.rollSeparateDice(amt);
        System.out.println("    " + Arrays.toString(saves));
        for(int i=0; i<amt; i++) {
            if(saves[i] >= save){
                wounds--;
            }
        }
        return wounds;
    }

    private int getNumAttacks(Unit defender, AttackDirectionEnum direction){
        int numAttacks = this.getModels().getAttacks();
        if(this.getRanks() < this.getSupportingRanks()){
            numAttacks *= this.getNumModels();
        }else {
            int modelsInBaseContact;
            if(direction.equals(AttackDirectionEnum.FLANK)){
                modelsInBaseContact = this.getRanks() <= defender.getFiles() ? this.getRanks() : defender.getFiles();
                numAttacks *= modelsInBaseContact;
            }else {
                modelsInBaseContact = this.getFiles() <= defender.getFiles() ? this.getFiles() : defender.getFiles();
                numAttacks = numAttacks * modelsInBaseContact * (1 + this.getSupportingRanks());
                if (this.getChampion() != null) {
                    numAttacks--;
                }
                if (this.getHero() != null) {
                    numAttacks --;
                }
            }
        }
        System.out.println("    " + this.getModels().getName() + " gets " + numAttacks + " attacks....");
        return numAttacks;
    }

    public Unit strikeFirst(Unit other){
        if(this.getModels().getSpecialRules().contains(SpecialRuleTypeEnum.ALWAYS_STRIKE_FIRST)){
            if(!other.getModels().getSpecialRules().contains(SpecialRuleTypeEnum.ALWAYS_STRIKE_FIRST)){
                return this;
            }
        }else if(other.getModels().getSpecialRules().contains(SpecialRuleTypeEnum.ALWAYS_STRIKE_FIRST)){
                return other;
        }
        if(this.getModels().getSpecialRules().contains(SpecialRuleTypeEnum.ALWAYS_STRIKE_LAST)){
            if(!other.getModels().getSpecialRules().contains(SpecialRuleTypeEnum.ALWAYS_STRIKE_LAST)){
                return other;
            }
        }else if(other.getModels().getSpecialRules().contains(SpecialRuleTypeEnum.ALWAYS_STRIKE_LAST)){
                return this;
        }

        return this.getUnitInitiative() < other.getUnitInitiative() ? this :
                (this.getUnitInitiative() == other.getUnitInitiative() ? null : other);
    }

    public void setModels(UnitModel models) {
        this.models = models;
    }

    public UnitModel getChampion() {
        return champion;
    }

    public void setChampion(UnitModel champion) {
        this.champion = champion;
    }

    public UnitModel getHero() {
        return hero;
    }

    public void setHero(UnitModel hero) {
        this.hero = hero;
    }

    public int getNumModels() {
        return numModels;
    }

    public void setNumModels(int numModels) {
        this.numModels = numModels;
    }

    public int getFiles() {
        return files;
    }

    public void setFiles(int files) {
        this.files = files;
    }

    public int getSupportingRanks() {
        int supportingRanks = 1;
        for(SpecialRuleTypeEnum specialRule : this.models.getSpecialRules()){
            if(specialRule.equals(SpecialRuleTypeEnum.MARTIAL_PROWESS)) supportingRanks++;
            if(specialRule.equals(SpecialRuleTypeEnum.FIGHT_IN_EXTRA_RANKS) && !charging) supportingRanks++;
        }
        return supportingRanks;
    }

    public int getLeadership() {
        return leadership;
    }

    public void setLeadership(int leadership) {
        this.leadership = leadership;
    }

    public boolean isCharging() {
        return charging;
    }

    public void setCharging(boolean charging) {
        this.charging = charging;
    }

    public int getWoundsReceived() {
        return woundsReceived;
    }

    public void setWoundsReceived(int woundsReceived) {
        this.woundsReceived = woundsReceived;
    }

    public boolean isHasBanner() {
        return hasBanner;
    }

    public void setHasBanner(boolean hasBanner) {
        this.hasBanner = hasBanner;
    }

    public boolean isHasBattleStandard() {
        return hasBattleStandard;
    }

    public void setHasBattleStandard(boolean hasBattleStandard) {
        this.hasBattleStandard = hasBattleStandard;
    }

    public boolean isFlankAttack() {
        return flankAttack;
    }

    public void setFlankAttack(boolean flankAttack) {
        this.flankAttack = flankAttack;
    }

    public boolean isRearAttack() {
        return rearAttack;
    }

    public void setRearAttack(boolean rearAttack) {
        this.rearAttack = rearAttack;
    }

    public boolean isHasHighGround() {
        return hasHighGround;
    }

    public void setHasHighGround(boolean hasHighGround) {
        this.hasHighGround = hasHighGround;
    }

    public int getOverkill() {
        return overkill;
    }

    public void setOverkill(int overkill) {
        this.overkill = overkill;
    }

    public boolean isHasMusician() {
        return hasMusician;
    }

    public void setHasMusician(boolean hasMusician) {
        this.hasMusician = hasMusician;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
