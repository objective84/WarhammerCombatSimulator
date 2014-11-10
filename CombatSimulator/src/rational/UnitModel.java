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
    private List<Equipment> equipment = new ArrayList<>();
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
                     int initiative, int attacks, int leadership, List<Equipment> equipment, List<SpecialRuleTypeEnum> specialRules) {
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
        this.equipment = equipment;
        this.specialRules = specialRules;
    }

    public UnitModel(String name, String race, int movement, int weaponSkill, int ballisticSkill, int strength, int toughness, int wounds,
                     int initiative, int attacks, int leadership, Integer armorSave, Integer wardSave, List<Equipment> equipment,
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
        this.equipment = equipment;
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
        for(Equipment equipment : this.equipment){
            modifiedList.addAll(equipment.getSpecialRules());
        }
        return modifiedList;
    }

    public void setSpecialRules(List<SpecialRuleTypeEnum> specialRules) {
        this.specialRules = specialRules;
    }

    public Integer getArmorSave() {
        Integer armorSave = this.armorSave;
        for(Equipment equipment : this.equipment){
            if(equipment.isArmorSaveModifiable()){
                return equipment.getArmorSave();
            }else if(equipment.getArmorSave() != 0){
                if(equipment.getArmorSave() < armorSave) armorSave = equipment.getArmorSave();
            }else if(equipment.getArmorSaveMod() != 0){
                armorSave -= equipment.getArmorSaveMod();
            }
        }
        return armorSave < 2 ? 2 : armorSave;
    }


    public Integer getWardSave() {
        Integer wardSave = this.wardSave;
        for(Equipment equipment : this.equipment){
            if(equipment.isWardSaveModifiable()){
                return equipment.getWardSave();
            }else if(equipment.getWardSave() != 0){
                if(equipment.getWardSave() < wardSave) wardSave = equipment.getWardSave();
            }
        }
        return wardSave < 2 ? 2 : wardSave;
    }
}
