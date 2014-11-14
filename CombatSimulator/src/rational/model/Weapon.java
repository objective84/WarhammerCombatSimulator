package rational.model;

import rational.enums.SpecialRuleTypeEnum;

import java.util.List;

public class Weapon extends Equipment {

    private Integer strength;
    private Integer strengthBonus;

    public Weapon(String name, int str, int strBonus){
        this.name = name;
        this.strength = str;
        this.strengthBonus = strBonus;
    }

    public Weapon(String name, Integer str, Integer strBonus, List<SpecialRuleTypeEnum> specialRules){
        this.name = name;
        this.strength = str;
        this.strengthBonus = strBonus;
        this.specialRules = specialRules;
    }

    public Integer getStrength() {
        return strength;
    }

    public void setStrength(Integer strength) {
        this.strength = strength;
    }

    public Integer getStrengthBonus() {
        return strengthBonus;
    }

    public void setStrengthBonus(Integer strengthBonus) {
        this.strengthBonus = strengthBonus;
    }
}
