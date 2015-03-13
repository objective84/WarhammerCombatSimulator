package rational.model;

import rational.enums.SpecialRuleTypeEnum;
import rational.enums.TroopTypeEnum;
import rational.enums.UnitTypeEnum;
import rational.model.equipment.Armor;
import rational.model.equipment.Equipment;
import rational.model.equipment.Weapon;
import rational.service.closecombat.CloseCombatService;
import rational.service.specialRules.tohit.ToHitBehavior;
import rational.service.specialRules.tohit.impl.DefaultToHitBehavior;
import rational.service.specialRules.tohit.impl.PredatoryStrikeBehavior;
import rational.service.specialRules.tohit.impl.StrikeFirstToHitSpecialRuleBehavior;
import rational.service.specialRules.towound.ToWoundBehavior;
import rational.service.specialRules.towound.impl.DefaultToWoundBehavior;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    private Integer basePointValue;
    private TroopTypeEnum troopType;
    private UnitTypeEnum unitType;
    private Integer armorSave = 7;
    private Integer wardSave = 7;
    private boolean isCharacter;
    private UnitModel champion;
    private UnitModel mount;
    private List<SpecialRuleTypeEnum> specialRules = new ArrayList<>();
    private Map<Equipment, Integer> upgrades = new HashMap<>();
    private Integer baseWidth;
    private Integer baseLength;

    private Weapon weapon;
    private Armor armor;
    private Armor shield;
    private int rank;
    private int file;
    private boolean charging;

    private CloseCombatService combatService;
    private ToHitBehavior toHitBehavior;
    private ToWoundBehavior toWoundBehavior;

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
        this.baseLength = copy.baseLength;
        this.baseWidth = copy.baseWidth;
        setCombatService(copy.combatService);
    }

    public UnitModel(String name, String race, int movement, int weaponSkill, int ballisticSkill, int strength, int toughness, int wounds,
                     int initiative, int attacks, int leadership, boolean isCharacter, Integer armorSave, Integer wardSave, Weapon weapon,
                     Armor armor, Armor shield, List<SpecialRuleTypeEnum> specialRules, UnitModel mount, UnitModel champion, Integer basePointValue,
                     Integer baseWidth, Integer baseLength) {
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
        this.armorSave = armorSave;
        this.wardSave = wardSave;
        this.weapon = weapon;
        this.isCharacter = isCharacter;
        this.armor = armor;
        this.shield = shield;
        this.specialRules = specialRules;
        this.mount = mount;
        this.champion = champion;
        this.basePointValue = basePointValue;
        this.baseLength = baseLength;
        this.baseWidth = baseWidth;
    }

    public void setRuleServices(){
        List<SpecialRuleTypeEnum> specialRules = this.getSpecialRules();
        if(specialRules.contains(SpecialRuleTypeEnum.ALWAYS_STRIKE_FIRST)){
            toHitBehavior = new StrikeFirstToHitSpecialRuleBehavior(this);
        }else if(specialRules.contains(SpecialRuleTypeEnum.PREDATORY_FIGHTER)){
            toHitBehavior = new PredatoryStrikeBehavior(this);
        }else{
            toHitBehavior = new DefaultToHitBehavior(this);
        }
        if(specialRules.contains(SpecialRuleTypeEnum.POISONED_ATTACKS)){

        }else if(specialRules.contains(SpecialRuleTypeEnum.AUTO_WOUND)){

        }else{
            toWoundBehavior = new DefaultToWoundBehavior(this);
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
        if(null != this.armor && null != this.armor.getSpecialRules()) modifiedList.addAll(armor.getSpecialRules());
        if(null != this.shield && null != this.shield.getSpecialRules()) modifiedList.addAll(shield.getSpecialRules());
        if(null != this.weapon && null != this.weapon.getSpecialRules()) modifiedList.addAll(this.weapon.getSpecialRules());
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

    public ToHitBehavior getToHitBehavior() {
        return toHitBehavior;
    }

    public void setToHitBehavior(ToHitBehavior toHitBehavior) {
        this.toHitBehavior = toHitBehavior;
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

    public ToWoundBehavior getToWoundBehavior() {
        return toWoundBehavior;
    }

    public void setToWoundBehavior(ToWoundBehavior toWoundBehavior) {
        this.toWoundBehavior = toWoundBehavior;
    }

    public Integer getBasePointValue() {
        return basePointValue;
    }

    public void setBasePointValue(Integer basePointValue) {
        this.basePointValue = basePointValue;
    }

    public TroopTypeEnum getTroopType() {
        return troopType;
    }

    public void setTroopType(TroopTypeEnum troopType) {
        this.troopType = troopType;
    }

    public UnitTypeEnum getUnitType() {
        return unitType;
    }

    public void setUnitType(UnitTypeEnum unitType) {
        this.unitType = unitType;
    }

    public Map<Equipment, Integer> getUpgrades() {
        return upgrades;
    }

    public void setUpgrades(Map<Equipment, Integer> upgrades) {
        this.upgrades = upgrades;
    }

    public Integer getBaseWidth() {
        return baseWidth;
    }

    public void setBaseWidth(Integer baseWidth) {
        this.baseWidth = baseWidth;
    }

    public Integer getBaseLength() {
        return baseLength;
    }

    public void setBaseLength(Integer baseLength) {
        this.baseLength = baseLength;
    }

    public boolean isCharacter() {
        return isCharacter;
    }

    public void setCharacter(boolean isCharacter) {
        this.isCharacter = isCharacter;
    }
}
