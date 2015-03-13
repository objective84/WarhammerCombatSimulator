package rational.model;

import rational.enums.SpecialRuleTypeEnum;
import rational.model.equipment.Armor;
import rational.model.equipment.Weapon;

import java.util.List;

public class CharacterModel extends UnitModel{


    public CharacterModel(UnitModel copy) {
        super(copy);
    }

    public CharacterModel(String name, String race, int movement, int weaponSkill, int ballisticSkill, int strength, int toughness,
                          int wounds, int initiative, int attacks, int leadership, Integer armorSave,
                          Integer wardSave, Weapon weapon, Armor armor, Armor shield, List<SpecialRuleTypeEnum> specialRules,
                          UnitModel mount, UnitModel champion, Integer basePointValue, Integer baseLength, Integer baseWidth) {
        super(name, race, movement, weaponSkill, ballisticSkill, strength, toughness, wounds, initiative, attacks, leadership,
                true, armorSave, wardSave, weapon, armor, shield, specialRules, mount, champion, basePointValue, baseLength, baseWidth);
    }
}
