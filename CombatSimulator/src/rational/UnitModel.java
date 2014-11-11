package rational;

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

    public UnitModel() {
    }

    public UnitModel(String name, String race, int movement, int weaponSkill, int ballisticSkill, int strength, int toughness, int wounds,
                     int initiative, int attacks, int leadership) {
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
    }

    public UnitModel(String name, String race, int movement, int weaponSkill, int ballisticSkill, int strength, int toughness, int wounds,
                     int initiative, int attacks, int leadership, Weapon weapon, Armor armor, Armor shield, List<SpecialRuleTypeEnum> specialRules) {
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
        this.weapon = weapon;
        this.armor = armor;
        this.shield = shield;
        this.specialRules = specialRules;
    }

    public UnitModel(String name, String race, int movement, int weaponSkill, int ballisticSkill, int strength, int toughness, int wounds,
                     int initiative, int attacks, int leadership, Integer armorSave, Integer wardSave, Weapon weapon, Armor armor, Armor shield,
                     List<SpecialRuleTypeEnum> specialRules) {
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
        this.armor = armor;
        this.shield = shield;
        this.specialRules = specialRules;
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
        if(null != weapon.getStrength()){
            return weapon.getStrength();
        }else if(null != weapon.getStrengthBonus()){
            return this.strength + weapon.getStrengthBonus();
        }
        return strength;
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
        return initiative;
    }

    public void setInitiative(int initiative) {
        this.initiative = initiative;
    }

    public int getAttacks() {
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

    public Integer getArmorSave() {
        Integer armorSave = this.armorSave == null ? 7 : this.armorSave;
            if (null != armor && null != armor.getArmorSave()) {
                if (armor.getArmorSave() < armorSave) armorSave = armor.getArmorSave();
            }
            if(null != armor && armor.isArmorSaveModifiable() && null != this.shield){
                armorSave -= shield.getArmorSaveMod();
            }

        if(null != armorSave){
            return armorSave < 2 ? 2 : armorSave;
        }
        return null;    }


    public Integer getWardSave() {
        Integer wardSave = this.wardSave == null ? 7 : this.wardSave;
        if (null != armor && armor.getWardSave() != null) {
            if (armor.getWardSave() < wardSave) wardSave = armor.getWardSave();
        }
        if(null != armor && armor.isWardSaveModifiable() && null != this.shield){
            wardSave -= shield.getWardSave();
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
}
