package rational.repository;

import rational.enums.SpecialRuleTypeEnum;
import rational.model.Armor;
import rational.model.UnitModel;
import rational.model.Weapon;

import java.util.ArrayList;
import java.util.Arrays;

public class Constants {


    public interface Equipment {
        public static final Weapon handWeapons = new Weapon("Hand Weapons", null, null, new ArrayList<SpecialRuleTypeEnum>());
        public static final Weapon spear = new Weapon("Spear", null, null, Arrays.asList(SpecialRuleTypeEnum.FIGHT_IN_EXTRA_RANKS));
        public static final Weapon halberd = new Weapon("Halberd", null, 1, Arrays.asList(SpecialRuleTypeEnum.FIGHT_IN_EXTRA_RANKS));
        public static final Weapon greatWeapon = new Weapon("Great Weapon", null, 2, Arrays.asList(SpecialRuleTypeEnum.ALWAYS_STRIKE_LAST));

        public static final Armor lightArmor = new Armor("Light Armor", 6, null, true, false);
        public static final Armor heavyArmor = new Armor("Heavy Armor", 5, null, true, false);
        public static final Armor fullPlateArmor = new Armor("Full Plate Armor", 4, null, true, false);
        public static final Armor shield = new Armor("Shield", 1);
    }

    public interface Models {

        public interface HighElves {
            public static final UnitModel sentinel = new UnitModel("Sentinel", "High Elves", 5, 4, 4, 3, 3, 1, 5, 2, 8, true, null, null,
                    Constants.Equipment.spear, Constants.Equipment.lightArmor, Constants.Equipment.shield,
                    Arrays.asList(SpecialRuleTypeEnum.ALWAYS_STRIKE_FIRST, SpecialRuleTypeEnum.MARTIAL_PROWESS), null, null);
            public static final UnitModel highElfSpearmen = new UnitModel("Spearmen", "High Elves", 5, 4, 4, 3, 3, 1, 5, 1, 8, false, null, null,
                    Constants.Equipment.spear, Constants.Equipment.lightArmor, Constants.Equipment.shield,
                    Arrays.asList(SpecialRuleTypeEnum.ALWAYS_STRIKE_FIRST, SpecialRuleTypeEnum.MARTIAL_PROWESS), null, sentinel);

            public static final UnitModel bladeLord = new UnitModel("Bladelord", "High Elves", 5, 6, 4, 3, 3, 1, 5, 3, 8, true, null, null,
                    Constants.Equipment.greatWeapon, Constants.Equipment.heavyArmor, null,
                    Arrays.asList(SpecialRuleTypeEnum.ALWAYS_STRIKE_FIRST, SpecialRuleTypeEnum.MARTIAL_PROWESS), null, null);
            public static final UnitModel swordMastersOfHoeth = new UnitModel("Swordmasters of Hoeth", "High Elves", 5, 6, 4, 3, 3, 1, 5, 2, 8, false, null, null,
                    Constants.Equipment.greatWeapon, Constants.Equipment.heavyArmor, null,
                    Arrays.asList(SpecialRuleTypeEnum.ALWAYS_STRIKE_FIRST, SpecialRuleTypeEnum.MARTIAL_PROWESS), null, bladeLord);

            public static final UnitModel keeperOfTheFlame = new UnitModel("Keeper of the Flame", "High Elves", 4, 5, 4, 3, 3, 1, 6, 2, 9, true, null, 4,
                    Constants.Equipment.halberd, Constants.Equipment.heavyArmor, null,
                    Arrays.asList(SpecialRuleTypeEnum.ALWAYS_STRIKE_FIRST, SpecialRuleTypeEnum.MARTIAL_PROWESS), null, null);
            public static final UnitModel phoenixGuard = new UnitModel("Phoenix Guard", "High Elves", 4, 5, 4, 3, 3, 1, 6, 1, 9, false, null, 4,
                    Constants.Equipment.halberd, Constants.Equipment.heavyArmor, null,
                    Arrays.asList(SpecialRuleTypeEnum.ALWAYS_STRIKE_FIRST, SpecialRuleTypeEnum.MARTIAL_PROWESS), null, keeperOfTheFlame);

            public static final UnitModel elvenSteed = new UnitModel("Elven Steeds", "High Elves", 9, 3, 0, 3, 3, 1, 4, 1, 5, false, null, null, null,
                    null, Constants.Equipment.shield, new ArrayList<SpecialRuleTypeEnum>(), null, null);
            public static final UnitModel highHelm = new UnitModel("High Helm", "High Elves", 5, 4, 4, 3, 3, 1, 5, 2, 8, true, null, null,
                    Constants.Equipment.handWeapons, Constants.Equipment.heavyArmor, null,
                    Arrays.asList(SpecialRuleTypeEnum.ALWAYS_STRIKE_FIRST, SpecialRuleTypeEnum.MARTIAL_PROWESS), elvenSteed, null);
            public static final UnitModel silverHelm = new UnitModel("Silver Helms", "High Elves", 5, 4, 4, 3, 3, 1, 5, 1, 8, false, null, null,
                    Constants.Equipment.handWeapons, Constants.Equipment.heavyArmor, null,
                    Arrays.asList(SpecialRuleTypeEnum.ALWAYS_STRIKE_FIRST, SpecialRuleTypeEnum.MARTIAL_PROWESS), elvenSteed, highHelm);

        }

        public interface Empire {
            public static final UnitModel empireWarhorse = new UnitModel("Warhorse", "Empire", 8, 3, 0, 3, 3, 1, 3, 1, 5, false, null, null, null,
                    null, Constants.Equipment.shield, new ArrayList<SpecialRuleTypeEnum>(), null, null);
            public static final UnitModel preceptor = new UnitModel("Preceptor", "Empire", 4, 4, 3, 3, 3, 1, 3, 2, 8, true, null, null,
                    Constants.Equipment.handWeapons, Constants.Equipment.fullPlateArmor, Constants.Equipment.shield,
                    new ArrayList<SpecialRuleTypeEnum>(), empireWarhorse, null);
            public static final UnitModel empireKnight = new UnitModel("Empire Knight", "Empire", 4, 4, 3, 3, 3, 1, 3, 1, 8, false, null, null,
                    Constants.Equipment.handWeapons, Constants.Equipment.fullPlateArmor, Constants.Equipment.shield,
                    new ArrayList<SpecialRuleTypeEnum>(), empireWarhorse, preceptor);

        }

        public interface Lizardmen {
            public static final UnitModel spawnLeader = new UnitModel("Spawn Leader", "Lizardmen", 4, 3, 0, 4, 4, 1, 1, 3, 8, true, 5, null,
                    Constants.Equipment.spear, null, Constants.Equipment.shield,
                    Arrays.asList(SpecialRuleTypeEnum.COLD_BLOODED, SpecialRuleTypeEnum.PREDATORY_FIGHTER), null, null);
            public static final UnitModel saurusWarriors = new UnitModel("Saurus Warriors", "Lizardmen", 4, 3, 0, 4, 4, 1, 1, 2, 8, false, 5, null,
                    Constants.Equipment.spear, null, Constants.Equipment.shield,
                    Arrays.asList(SpecialRuleTypeEnum.COLD_BLOODED, SpecialRuleTypeEnum.PREDATORY_FIGHTER), null, spawnLeader);

            public static final UnitModel reveredGuardian = new UnitModel("Revered Guardian", "Lizardmen", 4, 4, 0, 4, 4, 1, 2, 3, 8, true, 5, null,
                    Constants.Equipment.spear, null, Constants.Equipment.shield,
                    Arrays.asList(SpecialRuleTypeEnum.COLD_BLOODED, SpecialRuleTypeEnum.PREDATORY_FIGHTER, SpecialRuleTypeEnum.STUBBORN), null, null);
            public static final UnitModel templeGuard = new UnitModel("Temple Guard", "Lizardmen", 4, 3, 0, 4, 4, 1, 1, 2, 8, true, 5, null,
                    Constants.Equipment.halberd, Constants.Equipment.lightArmor, Constants.Equipment.shield,
                    Arrays.asList(SpecialRuleTypeEnum.COLD_BLOODED, SpecialRuleTypeEnum.PREDATORY_FIGHTER, SpecialRuleTypeEnum.STUBBORN), null, reveredGuardian);

            public static final UnitModel kroxigorAncient = new UnitModel("Kroxigor Ancient", "Lizardmen", 6, 3, 0, 5, 4, 3, 1, 4, 7, true, 4, null,
                    Constants.Equipment.greatWeapon, null, null,
                    Arrays.asList(SpecialRuleTypeEnum.COLD_BLOODED, SpecialRuleTypeEnum.PREDATORY_FIGHTER, SpecialRuleTypeEnum.MONSTEROUS_INFANTRY), null, null);
            public static final UnitModel kroxigor = new UnitModel("Kroxigor", "Lizardmen", 6, 3, 0, 5, 4, 3, 1, 3, 7, false, 4, null,
                    Constants.Equipment.greatWeapon, null, null,
                    Arrays.asList(SpecialRuleTypeEnum.COLD_BLOODED, SpecialRuleTypeEnum.PREDATORY_FIGHTER, SpecialRuleTypeEnum.MONSTEROUS_INFANTRY),
                    null, kroxigorAncient);
        }
    }

}
