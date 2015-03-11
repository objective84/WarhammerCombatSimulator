package rational.model;

import rational.enums.SpecialRuleTypeEnum;
import rational.service.CloseCombatService;
import rational.service.specialRules.tohit.ToHitService;
import rational.service.specialRules.tohit.impl.DefaultToHitService;
import rational.service.specialRules.tohit.impl.PredatoryStrikeService;
import rational.service.specialRules.tohit.impl.StrikeFirstToHitSpecialRuleService;
import rational.service.specialRules.towound.ToWoundService;
import rational.service.specialRules.towound.impl.DefaultToWoundService;

import java.util.ArrayList;
import java.util.List;

public class UnitModel {

    private String name;
    private String race;

    private int movement;
    private int weaponSkill;
    private int ballisticSkill;
    private int strength;
    private int toughness;
    private int wounds;
    private int initiative;
    private int attacks;
    private int leadership;
    private Integer armorSave = 7;
    private Integer wardSave = 7;
    private Weapon weapon;
    private Armor armor;
    private Armor shield;
    private List<SpecialRuleTypeEnum> specialRules = new ArrayList<>();
    private int rank;
    private int file;
    private boolean isCharacter;
    private UnitModel mount;
    private UnitModel champion;
    private boolean charging;

    private CloseCombatService combatService;
    private ToHitService toHitService;
    private ToWoundService toWoundService;

    public UnitModel() {
    }

    public UnitModel(UnitModel copy){
        this.name = copy.name;
        this.race = copy.race;
        this.movement = copy.movement;
        this.weaponSkill = copy.weaponSkill;
        this.ballisticSkill = copy.ballisticSkill;
        this.strength = copy.strength;
        this.toughness = copy.toughness;
        this.initiative = copy.initiative;
        this.attacks = copy.attacks;
        this.wounds = copy.wounds;
        this.leadership = copy.leadership;
        this.isCharacter = copy.isCharacter;
        this.armorSave = copy.armorSave;
        this.wardSave = copy.wardSave;
        this.weapon = copy.weapon;
        this.armor = copy.armor;
        this.shield = copy.shield;
        this.specialRules = copy.specialRules;
        this.mount = copy.mount;
        this.champion = copy.champion;
        this.charging = copy.charging;
        setCombatService(copy.combatService);
    }

    public UnitModel(String name, String race, int movement, int weaponSkill, int ballisticSkill, int strength, int toughness, int wounds,
                     int initiative, int attacks, int leadership, boolean isCharacter, Integer armorSave, Integer wardSave, Weapon weapon,
                     Armor armor, Armor shield, List<SpecialRuleTypeEnum> specialRules, UnitModel mount, UnitModel champion) {
        this.name = name;
        this.race = race;
        this.movement = movement;
        this.weaponSkill = weaponSkill;
        this.ballisticSkill = ballisticSkill;
        this.strength = strength;
        this.toughness = toughness;
        this.wounds = wounds;
        this.initiative = initiative;
        this.attacks = attacks;
        this.leadership = leadership;
        this.isCharacter = isCharacter;
        this.armorSave = armorSave;
        this.wardSave = wardSave;
        this.weapon = weapon;
        this.armor = armor;
        this.shield = shield;
        this.specialRules = specialRules;
        this.mount = mount;
        this.champion = champion;
    }

    public void setRuleServices(){
        List<SpecialRuleTypeEnum> specialRules = this.getSpecialRules();
        if(specialRules.contains(SpecialRuleTypeEnum.ALWAYS_STRIKE_FIRST)){
            toHitService = new StrikeFirstToHitSpecialRuleService(this);
        }else if(specialRules.contains(SpecialRuleTypeEnum.PREDATORY_FIGHTER)){
            toHitService = new PredatoryStrikeService(this);
        }else{
            toHitService = new DefaultToHitService(this);
        }
        if(specialRules.contains(SpecialRuleTypeEnum.POISONED_ATTACKS)){

        }else if(specialRules.contains(SpecialRuleTypeEnum.AUTO_WOUND)){

        }else{
            toWoundService = new DefaultToWoundService(this);
        }
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRace() {
        return race;
    }

    public void setRace(String race) {
        this.race = race;
    }

    public int getMovement() {
        return movement;
    }

    public void setMovement(int movement) {
        this.movement = movement;
    }

    public int getWeaponSkill() {
        int weaponSkill = this.weaponSkill;
        if(null != this.weapon && null != this.weapon.getWeaponSkill()){
            weaponSkill = this.weapon.getWeaponSkill();
        }
        return weaponSkill;
    }

    public void setWeaponSkill(int weaponSkill) {
        this.weaponSkill = weaponSkill;
    }

    public int getBallisticSkill() {
        return ballisticSkill;
    }

    public void setBallisticSkill(int ballisticSkill) {
        this.ballisticSkill = ballisticSkill;
    }

    public int getStrength() {
        if(null != weapon) {
            if(weapon.getSpecialRules().contains(SpecialRuleTypeEnum.WHEN_CHARGING) && !this.charging){
                return this.strength;
            }else {
                if (null != weapon.getStrength()) {
                    return weapon.getStrength();
                } else if (null != weapon.getStrengthBonus()) {
                    return this.strength + weapon.getStrengthBonus();
                }
            }
        }
        return this.strength;
    }

    public void setStrength(int strength) {
        this.strength = strength;
    }

    public int getToughness() {
        return toughness;
    }

    public void setToughness(int toughness) {
        this.toughness = toughness;
    }

    public int getInitiative() {
        if(this.getSpecialRules().contains(SpecialRuleTypeEnum.ALWAYS_STRIKE_FIRST) &&
                !this.getSpecialRules().contains(SpecialRuleTypeEnum.ALWAYS_STRIKE_LAST)){
            return 11;
        }else if(this.getSpecialRules().contains(SpecialRuleTypeEnum.ALWAYS_STRIKE_LAST)){
            return 0;
        }else{
            return this.initiative;
        }
    }

    public void setInitiative(int initiative) {
        this.initiative = initiative;
    }


    public int getAttacks() {
        int attacks = this.attacks;
        if(attacks > 0 & null != this.getWeapon() && null != this.getWeapon().getAttacksBonus()){
            attacks += this.getWeapon().getAttacksBonus();
        }
        return attacks;
    }

    public void setAttacks(int attacks) {
        this.attacks = attacks;
    }

    public int getLeadership() {
        return leadership;
    }

    public void setLeadership(int leadership) {
        this.leadership = leadership;
    }

    public int getWounds() {
        return wounds;
    }

    public void setWounds(int wounds) {
        this.wounds = wounds;
    }

    public List<SpecialRuleTypeEnum> getSpecialRules() {
        List<SpecialRuleTypeEnum> modifiedList = new ArrayList<>(specialRules);
        if(null != this.armor) modifiedList.addAll(armor.getSpecialRules());
        if(null != this.shield) modifiedList.addAll(shield.getSpecialRules());
        if(null != this.weapon) modifiedList.addAll(this.weapon.getSpecialRules());
        return modifiedList;
    }

    public void setSpecialRules(List<SpecialRuleTypeEnum> specialRules) {
        this.specialRules = specialRules;
    }

    public Integer getModifiedArmorSave() {
        Integer armorSave = this.armorSave == null ? 7 : this.armorSave;
        if (null != armor && null != armor.getArmorSave()) {
            if (armor.getArmorSave() < armorSave) armorSave = armor.getArmorSave();
        }
        if(null != armor && armor.isArmorSaveModifiable() && null != this.shield && !this.weapon.isRequiresTwoHands()){
            armorSave -= shield.getArmorSaveMod();
        }
        if(null != this.getMount()){
            armorSave--;
            if(null != this.getMount().getShield()){
                armorSave--;
            }
        }

        if(null != armorSave){
            return armorSave < 2 ? 2 : armorSave;
        }
        return null;
    }


    public Integer getModifiedWardSave() {
        Integer wardSave = this.wardSave == null ? 7 : this.wardSave;
        if (null != armor && armor.getWardSave() != null) {
            if (armor.getWardSave() < wardSave) wardSave = armor.getWardSave();
        }
        if(null != this.shield && wardSave < shield.getWardSave()){
            wardSave = shield.getWardSave();
        }
        if(null != wardSave){
            return wardSave < 2 ? 2 : wardSave;
        }
        return null;
    }

    public Weapon getWeapon() {
        return weapon;
    }

    public void setWeapon(Weapon weapon) {
        this.weapon = weapon;
    }

    public Armor getArmor() {
        return armor;
    }

    public void setArmor(Armor armor) {
        this.armor = armor;
    }

    public Armor getShield() {
        return shield;
    }

    public void setShield(Armor shield) {
        this.shield = shield;
    }

    public int getRank() {
        return rank;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }

    public int getFile() {
        return file;
    }

    public void setFile(int file) {
        this.file = file;
    }

    public boolean isCharacter() {
        return isCharacter;
    }

    public void setCharacter(boolean character) {
        this.isCharacter = character;
    }

    public Integer getArmorSave() {
        return armorSave;
    }

    public void setArmorSave(Integer armorSave) {
        this.armorSave = armorSave;
    }

    public Integer getWardSave() {
        return wardSave;
    }

    public void setWardSave(Integer wardSave) {
        this.wardSave = wardSave;
    }

    public UnitModel getMount() {
        return mount;
    }

    public void setMount(UnitModel mount) {
        this.mount = mount;
    }

    public UnitModel getChampion() {
        return champion;
    }

    public void setChampion(UnitModel champion) {
        this.champion = champion;
    }

    public ToHitService getToHitService() {
        return toHitService;
    }

    public void setToHitService(ToHitService toHitService) {
        this.toHitService = toHitService;
    }

    //    @Override
//    public String toString(){
//        return this.name + "\nM " + this.movement + " | WS " + this.weaponSkill + " | S " + this.strength + " | T " + this.toughness + " | A " + this.attacks + " | L " + this.leadership +
//                "\nEquipment: " + this.weapon.getName() + (null != this.armor ? ", " + this.armor.getName(): "") + "" + (null != this.shield ? ", Shield" : "");
//    }
    @Override
    public String toString(){
        return this.name;
    }

    public boolean isCharging() {
        return charging;
    }

    public void setCharging(boolean charging) {
        this.charging = charging;
    }

    public CloseCombatService getCombatService() {
        return combatService;
    }

    public void setCombatService(CloseCombatService combatService) {
        this.combatService = combatService;
        setRuleServices();
    }

    public ToWoundService getToWoundService() {
        return toWoundService;
    }

    public void setToWoundService(ToWoundService toWoundService) {
        this.toWoundService = toWoundService;
    }
}
