package rational;

import java.util.ArrayList;
import java.util.List;

public class Equipment {

    private String name;
    private int armorSave;
    private int armorSaveMod;
    private int wardSave;
    private boolean armorSaveModifiable;
    private boolean wardSaveModifiable;
    private List<SpecialRuleTypeEnum> specialRules = new ArrayList<>();

    public Equipment() {
    }

    public Equipment(String name, int armorSaveMod) {
        this.name = name;
        this.armorSaveMod = armorSaveMod;
    }

    public Equipment(String name, List<SpecialRuleTypeEnum> specialRules) {
        this.name = name;
        this.specialRules = specialRules;
    }

    public Equipment(String name, int armorSave, int wardSave, boolean armorSaveModifiable, boolean wardSaveModifiable) {
        this.name = name;
        this.armorSave = armorSave;
        this.wardSave = wardSave;
        this.armorSaveModifiable = armorSaveModifiable;
        this.wardSaveModifiable = wardSaveModifiable;
    }

    public Equipment(String name, int armorSave, int wardSave, boolean armorSaveModifiable, boolean wardSaveModifiable, List<SpecialRuleTypeEnum> specialRules) {
        this.name = name;
        this.armorSave = armorSave;
        this.wardSave = wardSave;
        this.armorSaveModifiable = armorSaveModifiable;
        this.wardSaveModifiable = wardSaveModifiable;
        this.specialRules = specialRules;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getArmorSave() {
        return armorSave;
    }

    public void setArmorSave(int armorSave) {
        this.armorSave = armorSave;
    }

    public int getWardSave() {
        return wardSave;
    }

    public void setWardSave(int wardSave) {
        this.wardSave = wardSave;
    }

    public boolean isArmorSaveModifiable() {
        return armorSaveModifiable;
    }

    public void setArmorSaveModifiable(boolean armorSaveModifiable) {
        this.armorSaveModifiable = armorSaveModifiable;
    }

    public boolean isWardSaveModifiable() {
        return wardSaveModifiable;
    }

    public void setWardSaveModifiable(boolean wardSaveModifiable) {
        this.wardSaveModifiable = wardSaveModifiable;
    }

    public List<SpecialRuleTypeEnum> getSpecialRules() {
        return specialRules;
    }

    public void setSpecialRules(List<SpecialRuleTypeEnum> specialRules) {
        this.specialRules = specialRules;
    }

    public int getArmorSaveMod() {
        return armorSaveMod;
    }

    public void setArmorSaveMod(int armorSaveMod) {
        this.armorSaveMod = armorSaveMod;
    }
}
