package rational.model;

import rational.enums.UpgradeTypeEnum;

public class ModelUpgrade {

    private UnitModel model;
    private Integer cost;
    private UpgradeTypeEnum upgradeType;
    private Object upgrade;

    public UnitModel getModel() {
        return model;
    }

    public void setModel(UnitModel model) {
        this.model = model;
    }

    public Integer getCost() {
        return cost;
    }

    public void setCost(Integer cost) {
        this.cost = cost;
    }

    public UpgradeTypeEnum getUpgradeType() {
        return upgradeType;
    }

    public void setUpgradeType(UpgradeTypeEnum upgradeType) {
        this.upgradeType = upgradeType;
    }

    public Object getUpgrade() {
        return upgrade;
    }

    public void setUpgrade(Object upgrade) {
        this.upgrade = upgrade;
    }
}
