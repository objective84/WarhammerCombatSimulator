package rational.model;

import rational.enums.SpecialRuleTypeEnum;

import java.util.List;

public class Armor extends Equipment {
    private Integer armorSave;
    private Integer armorSaveMod;
    private Integer wardSave;
    private boolean armorSaveModifiable;

    public Armor(){

    }

    public Armor(String name, int armorSaveMod, int wardSave) {
        this.name = name;
        this.armorSaveMod = armorSaveMod;
        this.wardSave = wardSave;
    }

    public Armor(String name, Integer armorSave, Integer wardSave, boolean armorSaveModifiable, boolean wardSaveModifiable) {
        this.name = name;
        this.armorSave = armorSave;
        this.wardSave = wardSave;
        this.armorSaveModifiable = armorSaveModifiable;
    }

    public Armor(String name, int armorSave, int wardSave, boolean armorSaveModifiable, boolean wardSaveModifiable, List<SpecialRuleTypeEnum> specialRules) {
        this.name = name;
        this.armorSave = armorSave;
        this.wardSave = wardSave;
        this.armorSaveModifiable = armorSaveModifiable;
        this.specialRules = specialRules;
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
