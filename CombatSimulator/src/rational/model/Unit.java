package rational.model;

import rational.enums.AttackDirectionEnum;
import rational.enums.SpecialRuleTypeEnum;

import java.util.*;

public class Unit implements Comparator<Unit>, Comparable<Unit> {
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
    private UnitModel[][] models;
    private Unit mounts;
    private UnitModel unitModel;
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
    private boolean mount;

    public void createUnit(UnitModel model, int numModels, int files, UnitModel hero){
        this.setUnitModel(model);
        this.setChampion(model.getChampion());
        this.setHero(hero);
        this.setLeadership(model.getLeadership());
        numModels = this.getChampion() != null ? numModels + 1 : numModels;
        numModels = hero != null ? numModels + 1 : numModels;
        int ranks = numModels / files;
        int remainder = numModels % files;
        if(remainder > 0){
            models = new UnitModel[ranks+1][files];
        }else{
            models = new UnitModel[ranks][files];
        }
        int startJ = 0;
        if(this.getChampion() != null){
            models[0][startJ] = model.getChampion();
            startJ++;
        }
        if(hero != null){
            models[0][startJ] = hero;
            startJ++;
        }
        for(int i=0; i<ranks; i++){
            for(int j= startJ; j < files; j++){
                UnitModel copy = new UnitModel(model);
                copy.setRank(i);
                copy.setFile(j);
                models[i][j] = copy;
            }
            startJ = 0;
        }
        if(remainder > 0){
            for (int k = 0; k < remainder; k++){
                UnitModel copy = new UnitModel(model);
                copy.setRank(ranks);
                copy.setFile(k);
                models[ranks][k] = copy;
            }
        }
        if(null != model.getMount()){
            UnitModel[][] mounts = new UnitModel[1][files];
            for(int i=0; i<files; i++){
                UnitModel mount = new UnitModel(model.getMount());
                mount.setFile(i);
                mount.setRank(0);
                mounts[0][i] = mount;
            }
            Unit mountUnit = new Unit();
            mountUnit.setMount(true);
            mountUnit.setUnitModel(new UnitModel(model.getMount()));
            mountUnit.setModels(mounts);
            mountUnit.setName(model.getMount().getName());
            mountUnit.setFlankAttack(this.flankAttack);
            mountUnit.setRearAttack(this.rearAttack);
            this.setMounts(mountUnit);
        }

        if(null != this.getChampion()){
            if(this.getChampion().getLeadership() > this.getLeadership() ){
                this.setLeadership(this.getChampion().getLeadership());
            }
        }
        if(null != this.getHero()){
            if(this.getHero().getLeadership() > this.getLeadership()){
                this.setLeadership(this.getHero().getLeadership());
            }
        }
    }

    public int getRanks(){
        int ranks = 0;
        for(int i = 0; i < models.length; i++){
            if(null !=models[i][0]){
                ranks++;
            }else{
                break;
            }
        }
        return ranks;
    }

    public int getFullRanks(){
        int ranks = 0;
        for(int i = 0; i < models.length; i++){
            int filesFilled = 0;
            for(int j = 0; j < models[i].length; j++){
                if(null !=models[i][j]){
                    filesFilled++;
                }else{
                    break;
                }
            }
            if(filesFilled != this.getFiles()){
                break;
            }else{
                ranks++;
            }
        }
        return ranks;
    }

    public boolean testLeadership(int modifier){
        int modifiedLeadership = this.leadership - modifier;
        if(this.leadership - modifier < 0){
            modifiedLeadership = 0;
        }
        String modified = modifier == 0 ? "" : "modified ";
        System.out.println("\n" + this.getUnitModel().getName() + " rolls for a break test with a " + modified + "leadership of " +
                modifiedLeadership + "... ");
        Dice die = Dice.getD6();
        int test = 0;
        List<Integer> rolls;
        if(this.getUnitModel().getSpecialRules().contains(SpecialRuleTypeEnum.COLD_BLOODED)){
            rolls = die.rollSeparateDice(3);
        }else {
            rolls = die.rollSeparateDice(2);
        }
        System.out.println("    " + Arrays.toString(rolls.toArray()));
        int highest = 0;
        for (int i : rolls) {
            test += i;
            if (i > highest) {
                highest = i;
            }
        }
        if (this.getUnitModel().getSpecialRules().contains(SpecialRuleTypeEnum.COLD_BLOODED)) {
            test -= highest;
        }
        if(rolls.get(0) == 1 && rolls.get(1) == 1){
            return true;
        }
        return test + modifier <= this.leadership;
    }

    public int calculateCombatScore(Unit other){
        System.out.println("\n********************** " + this.getUnitModel().getName() + " Combat Score **********************");

        int score = other.getWoundsReceived();
        System.out.println("    Wounds dealt: " + other.getWoundsReceived());

        if(this.charging){
            score++;
            System.out.println("    Charge bonus: 1");
        }
        if(other.isFlankAttack() && other.getFullRanks() >= 2){
            System.out.println("    Unit is disrupted and receives no rank bonus");
        }else if(this.getRanks() > 3){
            score += getRanks() > 3 ? (getRanks() - 3) : 0;
            System.out.println("    Rank bonus: " + (getRanks() - 3));
        }
        if(this.hasBanner){
            score ++;
            System.out.println("    Standard bearer bonus: 1");
        }
        if(this.flankAttack){
            score ++;
            System.out.println("    Flank attack bonus: 1");
        }
        if(this.rearAttack){
            score += 2;
            System.out.println("    Rear attack bonus");
        }
        if(this.hasHighGround){
            score++;
            System.out.println("    High Ground bonus: 1");
        }
        if(this.overkill > 0){
            score += overkill;
            System.out.println("    Challenge overkill bonus: " + overkill);
        }
        other.setWoundsReceived(0);
        System.out.println("\nTotal Score: " + score);
        System.out.println("*******************************************************************\n");
        return score;
    }

    public int getUnitInitiative(){
        return unitModel.getInitiative();
    }

    public UnitModel getUnitModel() {
        return unitModel;
    }

    public void attack(Unit defender, AttackDirectionEnum direction){
        int difficulty = toHitChart[this.getUnitModel().getWeaponSkill()][defender.getUnitModel().getWeaponSkill()];
        System.out.println("    " + this.getUnitModel().getName() + " hits on " + difficulty + "+");
        int hits = rollCombatDice(defender, this.getNumAttacks(defender), difficulty, true);
        System.out.println("    " + hits + " attacks hit");
        int totalHits = hits;
        int wounds = 0;
        if(hits > 0){
            wounds += defender.getWoundsReceived() + this.getWounds(defender, hits, this.getUnitModel().getStrength());
        }

        if(this.getChampion() != null){
            System.out.println("\n    Rolling for " + this.getChampion().getName() + "....");
            System.out.println("    " + this.getChampion().getName() + " has " + this.getChampion().getAttacks() + " attacks");

            difficulty = toHitChart[this.getChampion().getWeaponSkill()][defender.getUnitModel().getWeaponSkill()];

            System.out.println("    " + this.getChampion().getName() + " hits on " + difficulty + "+");

            hits = rollCombatDice(defender, this.getChampion().getAttacks(), difficulty, true);

            System.out.println("    " + hits + " attacks hit");

            totalHits += hits;
            if(hits > 0){
                wounds += getWounds(defender, hits, this.champion.getStrength());
            }
            this.getChampion().setAttacks(0);
        }
        if(this.getHero() != null){
            System.out.println("\n    Rolling for " + this.getHero().getName() + "....");
            System.out.println("    " + this.getHero().getName() + " has " + this.getHero().getAttacks() + " attacks");
            difficulty = toHitChart[this.getHero().getWeaponSkill()][defender.getUnitModel().getWeaponSkill()];
            System.out.println("    " + this.getHero().getName() + " hits on " + difficulty + "+");
            hits = rollCombatDice(defender, this.getHero().getAttacks(), difficulty, true);
            System.out.println("    " + hits + " attacks hit");
            totalHits += hits;
            if(hits > 0){
                wounds += getWounds(defender, hits, this.hero.getStrength());
            }
            this.getHero().setAttacks(0);
        }
        System.out.println("    " + this.getUnitModel().getName() + " hits: " + totalHits);
        System.out.println("    " + this.getUnitModel().getName() + " wounds : " + wounds);
        defender.setWoundsReceived(wounds);
    }

    private int getWounds(Unit defender, int numHits, int attackStrength){
        System.out.println("    Rolling for wounds....");
        int difficulty = toWoundChart[attackStrength][defender.getUnitModel().getToughness()];
        System.out.println("    " + this.getUnitModel().getName() + " wounds on " + difficulty + "+");
        int wounds = rollCombatDice(defender, numHits, difficulty, false);
        if(null != defender.getUnitModel().getModifiedArmorSave()){
            int save = defender.getUnitModel().getModifiedArmorSave() + Math.abs(3 - attackStrength);
            if(save <= 6){
                if(wounds > 0){
                    System.out.println("    " + defender.getUnitModel().getName() + " have a " + save + "+ armor save...");
                    wounds = rollSave(wounds, save);
                }
            }
        }
        if(null != defender.getUnitModel().getModifiedWardSave()){
            int save = defender.getUnitModel().getModifiedWardSave();
            if(save <= 6){
                if(wounds > 0){
                    System.out.println("    " + defender.getUnitModel().getName() + " have a " + save + "+ ward save...");
                    wounds = rollSave(wounds, save);
                }
            }
        }
        return wounds;
    }

    public int rollCombatDice(Unit defender, Integer amt, int difficulty, boolean reRollAllowed) {
        Dice d6 = Dice.getD6();
        Random rand = new Random();
        List<Integer> attacks = d6.rollSeparateDice(amt);
        System.out.println("    " + Arrays.toString(attacks.toArray()));

        int hits = 0;
        for(int i=0; i<amt; i++) {
            if(attacks.get(i) >= difficulty){
                hits++;
            }
        }

        if(this.getUnitModel().getSpecialRules().contains(SpecialRuleTypeEnum.PREDATORY_FIGHTER) && reRollAllowed){
            int additionalAttacks = 0;
            for(int i=0; i<amt; i++) {
                if(attacks.get(i) == 6){
                    additionalAttacks++;
                }
            }
            if(additionalAttacks > 0) {
                System.out.println("    " + this.getUnitModel().getName() + " gets " + additionalAttacks + " additional attacks....");
                hits += rollCombatDice(defender, additionalAttacks, difficulty, false);
            }
        }

        if(this.getUnitModel().getSpecialRules().contains(SpecialRuleTypeEnum.ALWAYS_STRIKE_FIRST) && reRollAllowed){
            if(amt - hits > 0) {
                System.out.println("    Re-rolling " + (amt - hits) + " misses....");

                if (this.getUnitInitiative() >= defender.getUnitInitiative() &&
                        !defender.getUnitModel().getSpecialRules().contains(SpecialRuleTypeEnum.ALWAYS_STRIKE_FIRST)) {
                    hits += rollCombatDice(defender, amt - hits, difficulty, false);
                }
            }
        }

        return hits;
    }

    private int rollSave(int amt, int save){
        Dice d6 = Dice.getD6();
        int wounds = amt;
        List<Integer> saves = d6.rollSeparateDice(amt);
        System.out.println("    " + Arrays.toString(saves.toArray()));
        for(int i=0; i<amt; i++) {
            if(saves.get(i) >= save){
                wounds--;
            }
        }
        return wounds;
    }

    private int getNumAttacks(Unit defender){
        int numAttacks = 0;
        int ranks = this.getSupportingRanks() + 1 > this.getRanks() ? this.getRanks() : this.getSupportingRanks() + 1;
        if(defender.isFlankAttack()){
            int flankAttacks = this.getRanks() > defender.getFiles() ? this.getRanks() : defender.getFiles();
            for(int f = 0; f < flankAttacks; f++){
                if(!models[f][0].isChampionHero()){
                    numAttacks += models[f][0].getAttacks();
                    models[f][0].setAttacks(0);
                }
            }
        }else if(defender.isRearAttack()){
            int rearAttacks = this.getFiles() > defender.getFiles() ? defender.getFiles() : this.getFiles();
            for(int r = this.getFiles(); r < rearAttacks; r++){
                UnitModel model = models[this.getRanks()-1][r];
                if(null == model){
                    if(r > 0){
                        model = models[this.getRanks() - 2][r];
                    }else{
                        break;
                    }
                }else{
                    numAttacks += model.getAttacks();
                    model.setAttacks(0);
                }
            }
        }else {
            for(int i = 0; i < ranks; i++){
                for(int j = 0; j < this.getFiles(); j++){
                    if(null == models[i][j]){
                        break;
                    }
                    if(!models[i][j].isChampionHero()){
                        numAttacks += models[i][j].getAttacks();
                        models[i][j].setAttacks(0);
                    }
                }
            }
        }

        System.out.println("    " + this.getUnitModel().getName() + " gets " + numAttacks + " attacks....");
        return numAttacks;
    }

    public Unit strikeFirst(Unit other){
        if(this.getUnitModel().getSpecialRules().contains(SpecialRuleTypeEnum.ALWAYS_STRIKE_FIRST) &&
                !this.getUnitModel().getSpecialRules().contains(SpecialRuleTypeEnum.ALWAYS_STRIKE_LAST)){
            if(!other.getUnitModel().getSpecialRules().contains(SpecialRuleTypeEnum.ALWAYS_STRIKE_FIRST)){
                return this;
            }
        }else if(other.getUnitModel().getSpecialRules().contains(SpecialRuleTypeEnum.ALWAYS_STRIKE_FIRST)){
            return other;
        }
        if(this.getUnitModel().getSpecialRules().contains(SpecialRuleTypeEnum.ALWAYS_STRIKE_LAST)){
            if(!other.getUnitModel().getSpecialRules().contains(SpecialRuleTypeEnum.ALWAYS_STRIKE_LAST)){
                return other;
            }
        }else if(other.getUnitModel().getSpecialRules().contains(SpecialRuleTypeEnum.ALWAYS_STRIKE_LAST)){
            return this;
        }

        return this.getUnitInitiative() < other.getUnitInitiative() ? this :
                (this.getUnitInitiative() == other.getUnitInitiative() ? null : other);
    }

    public void removeCasualties(int amt){
        int casualtiesToRemove = amt;
        for(int i = this.models.length-1; i >= 0; i--){
            for(int j = this.models[i].length-1; j >= 0; j--){
                UnitModel model = models[i][j];
                if(casualtiesToRemove == 0){
                    return;
                }
                if(model != null){
                    if(model.getWounds() <= casualtiesToRemove){
                        casualtiesToRemove -= model.getWounds();
                        if(null != models[i][j].getMount()){
                            this.mounts.getModels()[i][j] = null;
                        }
                        models[i][j] = null;
                    }else{
                        model.setWounds(model.getWounds() - casualtiesToRemove);
                        casualtiesToRemove = 0;
                    }
                }
            }
        }
    }

    public boolean isSteadfast(Unit other){
        return countRanksForSteadfast(this) > countRanksForSteadfast(other);
    }

    private int countRanksForSteadfast(Unit unit){
        int ranks = 0;
        int requiredNumberOfFiles = unit.getUnitModel().getSpecialRules().contains(SpecialRuleTypeEnum.MONSTEROUS_INFANTRY) ? 3 : 5;
        for(int i = 0; i < unit.getModels().length; i++){
            int filesFilled = 0;
            for(int j = 0; j < unit.getModels()[i].length; j++){
                if(null !=unit.getModels()[i][j]){
                    filesFilled++;
                }else{
                    break;
                }
            }
            if(filesFilled != requiredNumberOfFiles){
                break;
            }else{
                ranks++;
            }
        }
        return ranks;
    }

    public void setUnitModel(UnitModel unitModel) {
        this.unitModel = unitModel;
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
        int numModels = 0;
        for(int i = 0; i < this.getRanks(); i++){
            for(int j = 0; j < this.getFiles(); j++){
                if(null != models[i][j]){
                    numModels++;
                }
            }
        }
        return numModels;
    }

    public void setNumModels(int numModels) {
        this.numModels = numModels;
    }

    public int getFiles() {
        int files = 0;
        for(int i = 0; i < models[0].length; i++){
            if(null !=models[0][i]){
                files++;
            }else{
                break;
            }
        }
        return files;
    }

    public int getSupportingRanks() {
        if(this.unitModel.getSpecialRules().contains(SpecialRuleTypeEnum.MONSTEROUS_INFANTRY)) return 3;
        int supportingRanks = 1;
        for(SpecialRuleTypeEnum specialRule : this.unitModel.getSpecialRules()){
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

    public UnitModel[][] getModels() {
        return models;
    }

    public void setModels(UnitModel[][] models) {
        this.models = models;
    }

    public Unit getMounts() {
        return mounts;
    }

    public void setMounts(Unit mounts) {
        this.mounts = mounts;
    }

    public boolean isMount() {
        return mount;
    }

    public void setMount(boolean mount) {
        this.mount = mount;
    }

    @Override
    public int compare(Unit o1, Unit o2) {
        return o1.getUnitInitiative() > o2.getUnitInitiative() ? 1 : (o1.getUnitInitiative() == o2.getUnitInitiative() ? 0 :-1);
    }

    @Override
    public int compareTo(Unit other) {
        return this.getUnitInitiative() > other.getUnitInitiative() ? 1 : (this.getUnitInitiative() == other.getUnitInitiative() ? 0 :-1);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Unit unit = (Unit) o;

        if (charging != unit.charging) return false;
        if (files != unit.files) return false;
        if (flankAttack != unit.flankAttack) return false;
        if (hasBanner != unit.hasBanner) return false;
        if (hasBattleStandard != unit.hasBattleStandard) return false;
        if (hasHighGround != unit.hasHighGround) return false;
        if (hasMusician != unit.hasMusician) return false;
        if (leadership != unit.leadership) return false;
        if (numModels != unit.numModels) return false;
        if (overkill != unit.overkill) return false;
        if (rearAttack != unit.rearAttack) return false;
        if (woundsReceived != unit.woundsReceived) return false;
        if (mounts != null ? !mounts.equals(unit.mounts) : unit.mounts != null) return false;
        if (name != null ? !name.equals(unit.name) : unit.name != null) return false;
        if (unitModel != null ? !unitModel.equals(unit.unitModel) : unit.unitModel != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = name != null ? name.hashCode() : 0;
        result = 31 * result + (mounts != null ? mounts.hashCode() : 0);
        result = 31 * result + (unitModel != null ? unitModel.hashCode() : 0);
        result = 31 * result + numModels;
        result = 31 * result + files;
        result = 31 * result + leadership;
        result = 31 * result + woundsReceived;
        result = 31 * result + (charging ? 1 : 0);
        result = 31 * result + (hasBanner ? 1 : 0);
        result = 31 * result + (hasBattleStandard ? 1 : 0);
        result = 31 * result + (flankAttack ? 1 : 0);
        result = 31 * result + (rearAttack ? 1 : 0);
        result = 31 * result + (hasHighGround ? 1 : 0);
        result = 31 * result + (hasMusician ? 1 : 0);
        result = 31 * result + overkill;
        return result;
    }
}
