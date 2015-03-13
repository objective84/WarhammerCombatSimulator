package rational.model.equipment;

import rational.enums.SpecialRuleTypeEnum;

import java.util.ArrayList;
import java.util.List;

public class Equipment {

    protected String name;
    protected Integer pointValue;
    protected List<SpecialRuleTypeEnum> specialRules = new ArrayList<>();

    public Equipment(String name, Integer pointValue, List<SpecialRuleTypeEnum> specialRules) {
        this.name = name;
        this.specialRules = specialRules;
        this.pointValue = pointValue;
    }



    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<SpecialRuleTypeEnum> getSpecialRules() {
        return specialRules;
    }

    public void setSpecialRules(List<SpecialRuleTypeEnum> specialRules) {
        this.specialRules = specialRules;
    }

}
