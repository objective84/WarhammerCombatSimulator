package rational.model.equipment;

import rational.enums.SpecialRuleTypeEnum;

import java.util.List;

public class Armor extends Equipment {
    private Integer armorSave;
    private Integer armorSaveMod;
    private Integer wardSave;
    private boolean armorSaveModifiable;

    public Armor(String name, Integer pointValue, List<SpecialRuleTypeEnum> specialRules, Integer armorSave, Integer armorSaveMod,
                 Integer wardSave, boolean armorSaveModifiable) {
        super(name, pointValue, specialRules);
        this.armorSave = armorSave;
        this.armorSaveMod = armorSaveMod;
        this.wardSave = wardSave;
        this.armorSaveModifiable = armorSaveModifiable;
    }

    public Integer getArmorSave() {
        return armorSave;
    }

    public void setArmorSave(Integer armorSave) {
        this.armorSave = armorSave;
    }

    public Integer getArmorSaveMod() {
        return armorSaveMod;
    }

    public void setArmorSaveMod(Integer armorSaveMod) {
        this.armorSaveMod = armorSaveMod;
    }

    public Integer getWardSave() {
        return wardSave;
    }

    public void setWardSave(Integer wardSave) {
        this.wardSave = wardSave;
    }

    public boolean isArmorSaveModifiable() {
        return armorSaveModifiable;
    }

    public void setArmorSaveModifiable(boolean armorSaveModifiable) {
        this.armorSaveModifiable = armorSaveModifiable;
    }
}
