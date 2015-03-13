package rational.model.equipment;

import rational.enums.SpecialRuleTypeEnum;

import java.util.List;

public class Weapon extends Equipment {

    private Integer weaponSkill;
    private Integer weaponSkillBonus;
    private Integer strength;
    private Integer strengthBonus;
    private Integer attacksBonus;
    private boolean requiresTwoHands;

    public Weapon(String name, Integer pointValue, List<SpecialRuleTypeEnum> specialRules, Integer weaponSkill, Integer weaponSkillBonus,
                  Integer strength, Integer strengthBonus, Integer attacksBonus, boolean requiresTwoHands) {
        super(name, pointValue, specialRules);
        this.weaponSkill = weaponSkill;
        this.weaponSkillBonus = weaponSkillBonus;
        this.strength = strength;
        this.strengthBonus = strengthBonus;
        this.attacksBonus = attacksBonus;
        this.requiresTwoHands = requiresTwoHands;
    }

    public Integer getWeaponSkill() {
        return weaponSkill;
    }

    public void setWeaponSkill(Integer weaponSkill) {
        this.weaponSkill = weaponSkill;
    }

    public Integer getWeaponSkillBonus() {
        return weaponSkillBonus;
    }

    public void setWeaponSkillBonus(Integer weaponSkillBonus) {
        this.weaponSkillBonus = weaponSkillBonus;
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

    public Integer getAttacksBonus() {
        return attacksBonus;
    }

    public void setAttacksBonus(Integer attacksBonus) {
        this.attacksBonus = attacksBonus;
    }

    public boolean isRequiresTwoHands() {
        return requiresTwoHands;
    }

    public void setRequiresTwoHands(boolean requiresTwoHands) {
        this.requiresTwoHands = requiresTwoHands;
    }
}
