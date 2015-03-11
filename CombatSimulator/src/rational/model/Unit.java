package rational.model;

import rational.enums.SpecialRuleTypeEnum;
import rational.enums.UnitTypeEnum;
import rational.service.CloseCombatService;
import rational.service.specialRules.leadership.LeadershipTestService;
import rational.service.specialRules.leadership.breaktest.BreakTestService;
import rational.service.specialRules.leadership.breaktest.impl.DefaultBreakTestService;
import rational.service.specialRules.leadership.breaktest.impl.StrengthInNumbersBreakTestService;
import rational.service.specialRules.leadership.impl.ColdBloodedLeadershipTestService;
import rational.service.specialRules.leadership.impl.DefaultLeadershipTestService;
import rational.service.specialRules.leadership.impl.StrengthInNumbersLeadershipTestService;
import rational.service.specialRules.movement.SpecialMovementService;
import rational.service.specialRules.movement.impl.DefaultSpecialMovementService;
import rational.service.specialRules.movement.impl.ScurryAwaySpecialMovementService;
import rational.service.specialRules.movement.impl.SwiftstrideMovementSpecialRuleService;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

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

    private CloseCombatService combatService;
    private String name;
    private UnitModel[][] models;
    private Unit mounts;
    private UnitModel unitModel;
    private List<UnitModel> characters = new ArrayList<>();
    private int defaultNumModels;
    private int files;
    private int leadership;
    private int woundsReceived;
    private boolean charging;
    private boolean hasBanner;
    private boolean hasBattleStandard;
    private boolean hasGriffinBattleStandard;
    private boolean flankAttack;
    private boolean rearAttack;
    private boolean hasHighGround;
    private boolean hasMusician;
    private boolean hasChampion;
    private int overkill;
    private boolean mount;
    private int combatScore;
    private boolean isFlanked;
    private boolean isBroken;
    private UnitTypeEnum unitType;

    private SpecialMovementService specialMovementService;
    private LeadershipTestService leadershipTestService;
    private BreakTestService breakTestService;

    public void initializeUnit(UnitModel model, int files, List<UnitModel> characters, CloseCombatService combatService){
        this.combatService = combatService;
        model.setCombatService(combatService);
        model.setCharging(this.charging);
        if(null != model.getMount()){
            this.unitType = UnitTypeEnum.CAVALRY;
        }else{
            this.unitType = UnitTypeEnum.INFANTRY;
        }
        this.setUnitModel(model);
        this.getCharacters().addAll(characters);
        int numModels = this.defaultNumModels + this.characters.size();
        if(this.hasChampion){
            this.characters.add(model.getChampion());
            this.defaultNumModels--;
        }
        this.setLeadership(model.getLeadership());
        int ranks = numModels / files;
        int remainder = numModels % files;

        if(remainder > 0){
            models = new UnitModel[ranks+1][files];
        }else{
            models = new UnitModel[ranks][files];
        }
        int startJ = 0;

        if(this.unitType.equals(UnitTypeEnum.CAVALRY)){
            UnitModel[][] mounts;
            if(remainder > 0){
                mounts = new UnitModel[ranks+1][files];
            }else{
                mounts = new UnitModel[ranks][files];
            }
            Unit mountUnit = new Unit();
            mountUnit.setMount(true);
            UnitModel mount = new UnitModel(model.getMount());
            mount.setCombatService(combatService);
            mountUnit.setUnitModel(mount);
            mountUnit.setModels(mounts);
            mountUnit.setName(model.getMount().getName());
            mountUnit.setFlankAttack(this.flankAttack);
            mountUnit.setRearAttack(this.rearAttack);
            mountUnit.setCombatService(combatService);
            this.setMounts(mountUnit);
        }

        List<UnitModel> temp = new ArrayList<>(this.characters);
        if(!this.characters.isEmpty()){
            for(int i=0; i < ranks; i++) {
                for (int j = 0; j < files; j++) {
                    UnitModel character = temp.get(0);
                    character.setCombatService(combatService);
                    character.setCharging(this.isCharging());
                    character.setRank(ranks);
                    character.setFile(j);
                    models[i][j] = character;
                    temp.remove(0);
                    startJ++;
                    if(this.unitType.equals(UnitTypeEnum.CAVALRY)){
                        if(character.getMount().isCharacter()){
                            character.getMount().setCombatService(combatService);
                            this.mounts.getCharacters().add(character.getMount());
                        }
                        this.mounts.models[i][j] = character.getMount();
                    }
                    if(temp.isEmpty()) break;
                }
                if(temp.isEmpty()) break;
            }
        }
        for(int i=0; i<ranks; i++){
            for(int j= startJ; j < files; j++){
                UnitModel copy = new UnitModel(model);
                copy.setCombatService(combatService);
                copy.setRank(i);
                copy.setFile(j);
                models[i][j] = copy;
                if(this.unitType.equals(UnitTypeEnum.CAVALRY)){
                    this.mounts.models[i][j] = copy.getMount();
                }
            }
            startJ = 0;
        }
        if(remainder > 0){
            for (int k = 0; k < remainder; k++){
                UnitModel copy = new UnitModel(model);
                copy.setCombatService(combatService);
                copy.setRank(ranks);
                copy.setFile(k);
                models[ranks][k] = copy;
                if(this.unitType.equals(UnitTypeEnum.CAVALRY)){
                    this.mounts.models[ranks][k] = copy.getMount();
                }
            }
        }

        if(null != this.getCharacters()){
            for(UnitModel heroModel : this.characters) {
                if (heroModel.getLeadership() > this.getLeadership()) {
                    this.setLeadership(heroModel.getLeadership());
                }
            }
        }
        setRuleServices();
    }

    public void setRuleServices(){
        List<SpecialRuleTypeEnum> specialRules = this.getUnitModel().getSpecialRules();
        if(specialRules.contains(SpecialRuleTypeEnum.SWIFTSTRIDE)){
            specialMovementService = new SwiftstrideMovementSpecialRuleService(this);
        }else if(specialRules.contains(SpecialRuleTypeEnum.SCURRY_AWAY)){
            specialMovementService = new ScurryAwaySpecialMovementService(this);
        }else{
            specialMovementService = new DefaultSpecialMovementService(this);
        }
        if(specialRules.contains(SpecialRuleTypeEnum.STRENGTH_IN_NUMBERS)){
            breakTestService = new StrengthInNumbersBreakTestService(this);
            leadershipTestService = new StrengthInNumbersLeadershipTestService(this);
        }else if(specialRules.contains(SpecialRuleTypeEnum.COLD_BLOODED)){
            leadershipTestService = new ColdBloodedLeadershipTestService(this);
            breakTestService = new DefaultBreakTestService(this);
        }else{
            leadershipTestService = new DefaultLeadershipTestService(this);
            breakTestService = new DefaultBreakTestService(this);
        }
    }

    public int getRanks(){
        int ranks = 0;
        if(null != models){
            for (int i = 0; i < models.length; i++) {
                if (null != models[i][0]) {
                    ranks++;
                } else {
                    break;
                }
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

    private String getStartLogDivider(){
        int numDividers = (61 - this.getUnitModel().getName().length()) / 2;
        String divider = "";
        for(int i=0; i< numDividers; i++){
            divider += "*";
        }
        return divider;
    }

    private String getEndLogDivider(String startDivider){
        String divider = "";
        for(int i=0; i< 76; i++){
            divider += "*";
        }
        return divider;
    }

    public int calculateCombatScore(Unit other){
        String divider = getStartLogDivider();
        combatService.appendLog( "\n" + divider + " " + this.getUnitModel().getName() + " Combat Score " + divider);

        int score = (other.getWoundsReceived());
        combatService.appendLog( "    Wounds dealt: " + (other.getWoundsReceived()));

        if(this.charging){
            score++;
            combatService.appendLog( "    Charge bonus: 1");
        }
        if((other.isFlankAttack() || this.isFlanked) && other.getFullRanks() >= 2){
            combatService.appendLog( "    Unit is disrupted and receives no rank bonus");
        }else if(this.getRanks() > 1){
            int rankBonus = getFullRanks() > 1 ? (getFullRanks() - 1) : 0;
            rankBonus = rankBonus > 3 ? 3 : rankBonus;
            score += rankBonus;
            combatService.appendLog( "    Rank bonus: " + rankBonus);
            if(this.hasGriffinBattleStandard){
                score+= rankBonus;
                combatService.appendLog( "    Griffin Battle Standard bonus: " + rankBonus);
            }
        }
        if(this.hasBanner){
            score ++;
            combatService.appendLog( "    Standard bearer bonus: 1");
        }
        if(this.flankAttack){
            score ++;
            combatService.appendLog( "    Flank attack bonus: 1");
        }
        if(this.rearAttack){
            score += 2;
            combatService.appendLog( "    Rear attack bonus");
        }
        if(this.hasHighGround){
            score++;
            combatService.appendLog( "    High Ground bonus: 1");
        }
        if(this.hasBattleStandard){
            score++;
            combatService.appendLog( "    Battle Standard bonus: 1");
        }
        if(this.overkill > 0){
            score += overkill;
            combatService.appendLog( "    Challenge overkill bonus: " + overkill);
        }
        other.setWoundsReceived(0);
        combatService.appendLog( "\nTotal Score: " + score);
        combatService.appendLog( getEndLogDivider(divider) + "\n");
        this.combatScore = score;
        return score;
    }

    public int getRankBonus(){
        int rankBonus = getFullRanks() > 1 ? (getFullRanks() - 1) : 0;
        rankBonus = rankBonus > 3 ? 3 : rankBonus;
        return rankBonus;
    }

    public int getUnitInitiative(){
        return unitModel.getInitiative();
    }

    public UnitModel getUnitModel() {
        return unitModel;
    }

    public int rollCombatDice(Unit defender, Integer amt, int difficulty, boolean reRollAllowed) {
        Dice d6 = Dice.getD6();
        List<Integer> attacks = d6.rollSeparateDice(amt);
        combatService.appendLog( "    " + Arrays.toString(attacks.toArray()));

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
                combatService.appendLog( "    " + this.getUnitModel().getName() + " gets " + additionalAttacks + " additional attacks....");
                hits += rollCombatDice(defender, additionalAttacks, difficulty, false);
            }
        }

        if(this.getUnitModel().getSpecialRules().contains(SpecialRuleTypeEnum.ALWAYS_STRIKE_FIRST) && reRollAllowed){
            if(amt - hits > 0) {
                combatService.appendLog( "    Re-rolling " + (amt - hits) + " misses....");

                if (this.getUnitInitiative() >= defender.getUnitInitiative() &&
                        !defender.getUnitModel().getSpecialRules().contains(SpecialRuleTypeEnum.ALWAYS_STRIKE_FIRST)) {
                    hits += rollCombatDice(defender, amt - hits, difficulty, false);
                }
            }
        }

        return hits;
    }



    public int getNumAttacks(Unit defender){
        int numAttacks = 0;
        int ranks = this.getSupportingRanks() + 1 > this.getRanks() ? this.getRanks() : this.getSupportingRanks() + 1;
        if(defender.isFlankAttack()){
            int flankAttacks = this.getRanks() > defender.getFiles() ? this.getRanks() : defender.getFiles();
            for(int f = 0; f < flankAttacks; f++){
                if(!models[f][0].isCharacter()){
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
                    if(!models[i][j].isCharacter()){
                        if(i > 0){
                            //Supporting ranks do not get more than 1 attack.
                            numAttacks++;
                        }else {
                            numAttacks += models[i][j].getAttacks();
                        }
                        models[i][j].setAttacks(0);
                    }
                }
            }
        }

        combatService.appendLog( "    " + this.getUnitModel().getName() + " gets " + numAttacks + " attacks....");
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
        return (countRanksForSteadfast(this) > countRanksForSteadfast(other) && !isFlanked) ||
                this.getUnitModel().getSpecialRules().contains(SpecialRuleTypeEnum.STUBBORN);
    }

    private int countRanksForSteadfast(Unit unit){
        int ranks = 0;
        int requiredNumberOfFiles = unit.getUnitModel().getSpecialRules().contains(SpecialRuleTypeEnum.MONSTROUS_INFANTRY) ? 3 : 5;
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

    public List<UnitModel> getCharacters() {
        return characters;
    }

    public void setCharacters(List<UnitModel> characters) {
        this.characters = characters;
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

    public int getDefaultNumModels() {
        return defaultNumModels;
    }

    public void setDefaultNumModels(int numModels) {
        this.defaultNumModels = numModels;
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
        if(this.unitModel.getSpecialRules().contains(SpecialRuleTypeEnum.MONSTROUS_INFANTRY)) return 3;
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
        if (defaultNumModels != unit.defaultNumModels) return false;
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
        result = 31 * result + defaultNumModels;
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

    public boolean isHasGriffinBattleStandard() {
        return hasGriffinBattleStandard;
    }

    public void setHasGriffinBattleStandard(boolean hasGriffinBattleStandard) {
        this.hasGriffinBattleStandard = hasGriffinBattleStandard;
    }

    public int getCombatScore() {
        return combatScore;
    }

    public void setCombatScore(int combatScore) {
        this.combatScore = combatScore;
    }

    public boolean isFlanked() {
        return isFlanked;
    }

    public void setFlanked(boolean isFlanked) {
        this.isFlanked = isFlanked;
    }

    @Override
    public String toString(){
        String heroes = "";
        if(null != this.characters) {
            for (UnitModel hero : this.characters) {
                heroes = heroes.concat(", " + hero.toString());
            }
        }
        if(!heroes.isEmpty()) heroes = heroes.substring(1, heroes.length());
        String unit =
                this.unitModel.getRace() + " " + this.unitModel.getName() + "\n" +
                "    Model count: " + this.getNumModels() + "\n" +
                "    Ranks: " + this.getFullRanks() + "\n" +
                "    Files: " + this.getFiles() + "\n" +
                "    Model: " + this.unitModel.toString() + "\n" +
                (null != this.characters && !this.characters.isEmpty() ? "    Characters: " + heroes : "") +"\n";

        return unit;
    }

    public boolean isBroken() {
        return isBroken;
    }

    public void setBroken(boolean isBroken) {
        this.isBroken = isBroken;
    }

    public boolean isHasChampion() {
        return hasChampion;
    }

    public void setHasChampion(boolean hasChampion) {
        this.hasChampion = hasChampion;
    }

    public CloseCombatService getCombatService() {
        return combatService;
    }

    public void setCombatService(CloseCombatService combatService) {
        this.combatService = combatService;
    }

    public UnitTypeEnum getUnitType() {
        return unitType;
    }

    public void setUnitType(UnitTypeEnum unitType) {
        this.unitType = unitType;
    }

    public SpecialMovementService getSpecialMovementService() {
        return specialMovementService;
    }

    public void setSpecialMovementService(SpecialMovementService specialMovementService) {
        this.specialMovementService = specialMovementService;
    }

    public LeadershipTestService getLeadershipTestService() {
        return leadershipTestService;
    }

    public void setLeadershipTestService(LeadershipTestService leadershipTestService) {
        this.leadershipTestService = leadershipTestService;
    }

    public BreakTestService getBreakTestService() {
        return breakTestService;
    }

    public void setBreakTestService(BreakTestService breakTestService) {
        this.breakTestService = breakTestService;
    }
}
