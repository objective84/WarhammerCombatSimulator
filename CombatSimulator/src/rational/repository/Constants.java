package rational.repository;

import rational.enums.SpecialRuleTypeEnum;
import rational.model.Armor;
import rational.model.UnitModel;
import rational.model.Weapon;

import java.util.ArrayList;
import java.util.Arrays;

public class Constants {


    public interface Equipment {
        public static final Weapon handWeapons = new Weapon("Hand Weapons", null, null, null, null, null, new ArrayList<SpecialRuleTypeEnum>(), false);
        public static final Weapon spear = new Weapon("Spear", null, null, null, null, null, Arrays.asList(SpecialRuleTypeEnum.FIGHT_IN_EXTRA_RANKS), false);
        public static final Weapon halberd = new Weapon("Halberd", null, null, null, 1, null, new ArrayList<SpecialRuleTypeEnum>(), true);
        public static final Weapon greatWeapon = new Weapon("Great Weapon", null, null, null, 2, null, Arrays.asList(SpecialRuleTypeEnum.ALWAYS_STRIKE_LAST), true);
        public static final Weapon lance = new Weapon("Lance", null, null, null, 2, null, Arrays.asList(SpecialRuleTypeEnum.WHEN_CHARGING), false);

        public static final Weapon swordOfBloodshed = new Weapon("Sword of Bloodshed", null, null, null, null, 3, new ArrayList<SpecialRuleTypeEnum>(), false);
        public static final Weapon runefang = new Weapon("Runefang", null, null, null, 2, null, Arrays.asList(SpecialRuleTypeEnum.AUTO_WOUND, SpecialRuleTypeEnum.NO_ARMOR_SAVE), false);
        public static final Weapon sunfang = new Weapon("Sunfang", null, null, null, 3, null, Arrays.asList(SpecialRuleTypeEnum.FLAMING_ATTACKS), false);
        public static final Weapon starLance = new Weapon("Star Lance", null, null, null, 3, null, Arrays.asList(SpecialRuleTypeEnum.WHEN_CHARGING), false);
        public static final Weapon bitingBlade = new Weapon("Biting Blade", null, null, null, null, null, Arrays.asList(SpecialRuleTypeEnum.ARMOR_PIERCING), false);
        public static final Weapon fencersBlades = new Weapon("FencersBlades", 10, null, null, null, 1, Arrays.asList(SpecialRuleTypeEnum.EXTRA_ATTACKS), false);

        public static final Armor lightArmor = new Armor("Light Armor", 6, null, true, false);
        public static final Armor heavyArmor = new Armor("Heavy Armor", 5, null, true, false);
        public static final Armor fullPlateArmor = new Armor("Full Plate Armor", 4, null, true, false);
        public static final Armor dragonArmourOfAvalorn = new Armor("Dragon Armour of Avalorn", 1, 4, false, true);
        public static final Armor shield = new Armor("Shield", 1, 6);
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

            public static final UnitModel drakemaster = new UnitModel("Drakemaster", "High Elves", 5, 5, 4, 3, 3, 1, 6, 3, 9, true, null, null,
                    Constants.Equipment.handWeapons, Constants.Equipment.heavyArmor, null,
                    Arrays.asList(SpecialRuleTypeEnum.ALWAYS_STRIKE_FIRST, SpecialRuleTypeEnum.MARTIAL_PROWESS), elvenSteed, null);
            public static final UnitModel dragonPrinces = new UnitModel("Dragon Princes of Calendor", "High Elves", 5, 5, 4, 3, 3, 1, 6, 2, 9, false, null, null,
                    Equipment.lance, Constants.Equipment.heavyArmor, Equipment.shield,
                    Arrays.asList(SpecialRuleTypeEnum.ALWAYS_STRIKE_FIRST, SpecialRuleTypeEnum.MARTIAL_PROWESS, SpecialRuleTypeEnum.SWIFTSTRIDE),
                    elvenSteed, drakemaster);

            public static final UnitModel malhandir = new UnitModel("Malhandir", "High Elves", 10, 4, 0, 4, 3, 1, 5, 2, 7, true, null, null, null,
                    null, Constants.Equipment.shield, new ArrayList<SpecialRuleTypeEnum>(), null, null);
            public static final UnitModel tyrion = new UnitModel("Tyrion", "High Elves", 5, 9, 7, 4, 3, 4, 10, 4, 10, true, null, null,
                    Equipment.sunfang, Constants.Equipment.dragonArmourOfAvalorn, null,
                    Arrays.asList(SpecialRuleTypeEnum.ALWAYS_STRIKE_FIRST, SpecialRuleTypeEnum.MARTIAL_PROWESS), malhandir, null);

            public static final UnitModel prince = new UnitModel("Prince", "High Elves", 5, 7, 7, 4, 3, 3, 8, 4, 10, true, null, null,
                    Equipment.greatWeapon, Equipment.heavyArmor, Equipment.shield,
                    Arrays.asList(SpecialRuleTypeEnum.ALWAYS_STRIKE_FIRST, SpecialRuleTypeEnum.MARTIAL_PROWESS), elvenSteed, null);


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

            public static final UnitModel sergeant2 = new UnitModel("Sergeant", "Empire", 4, 3, 3, 3, 3, 1, 3, 2, 9, true, null, null,
                    Equipment.halberd, Equipment.lightArmor, null, new ArrayList<>(Arrays.asList(SpecialRuleTypeEnum.COLD_BLOODED)),
                    null, null);
            public static final UnitModel spearmen = new UnitModel("Spearmen", "Empire", 4, 3, 3, 3, 3, 1, 3, 1, 9, false, null, 6,
                    Equipment.spear, Equipment.lightArmor, Equipment.shield, new ArrayList<>(Arrays.asList(SpecialRuleTypeEnum.COLD_BLOODED)),
                    null, sergeant2);

            public static final UnitModel sergeant = new UnitModel("Sergeant", "Empire", 4, 3, 3, 3, 3, 1, 3, 2, 9, true, null, null,
                    Equipment.halberd, Equipment.lightArmor, null, new ArrayList<>(Arrays.asList(SpecialRuleTypeEnum.COLD_BLOODED)),
                    null, null);
            public static final UnitModel halberdier = new UnitModel("Halberdier", "Empire", 4, 3, 3, 3, 3, 1, 3, 1, 9, false, null, 6,
                    Equipment.halberd, Equipment.lightArmor, null, new ArrayList<>(Arrays.asList(SpecialRuleTypeEnum.COLD_BLOODED)),
                    null, sergeant);

            public static final UnitModel duelist = new UnitModel("Duelist", "Empire", 4, 4, 3, 3, 3, 1, 3, 2, 9, true, null, null,
                    Equipment.handWeapons, Equipment.lightArmor, Equipment.shield, new ArrayList<>(Arrays.asList(SpecialRuleTypeEnum.COLD_BLOODED)),
                    null, null);
            public static final UnitModel swordsmen = new UnitModel("Swordsmen", "Empire", 4, 4, 3, 3, 3, 1, 3, 1, 9, false, null, 6,
                    Equipment.handWeapons, Equipment.lightArmor, Equipment.shield, new ArrayList<>(Arrays.asList(SpecialRuleTypeEnum.COLD_BLOODED)),
                    null, duelist);

            public static final UnitModel countsChampion = new UnitModel("Count's Champion", "Empire", 4, 3, 3, 3, 3, 1, 3, 2, 9, true, null, null,
                    Equipment.greatWeapon, Equipment.fullPlateArmor, null, new ArrayList<>(Arrays.asList(SpecialRuleTypeEnum.COLD_BLOODED)),
                    null, null);
            public static final UnitModel greatswords = new UnitModel("Greatswords", "Empire", 4, 3, 3, 3, 3, 1, 3, 1, 9, false, null, 6,
                    Equipment.greatWeapon, Equipment.fullPlateArmor, null, new ArrayList<>(Arrays.asList(SpecialRuleTypeEnum.COLD_BLOODED)),
                    null, sergeant);

            public static final UnitModel captainOfTheEmpire = new UnitModel("Captain Of The Empire", "Empire", 4, 5, 5, 4, 4, 1, 3, 3, 8, true, null, null,
                    Equipment.greatWeapon, Equipment.fullPlateArmor, null, new ArrayList<>(Arrays.asList(SpecialRuleTypeEnum.COLD_BLOODED)),
                    null, null);

            public static final UnitModel generalOfTheEmpire = new UnitModel("General Of The Empire", "Empire", 4, 5, 5, 4, 4, 3, 5, 3, 9, true, null, null,
                    Equipment.greatWeapon, Equipment.fullPlateArmor, null, new ArrayList<>(Arrays.asList(SpecialRuleTypeEnum.COLD_BLOODED)),
                    null, null);
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
            public static final UnitModel templeGuard = new UnitModel("Temple Guard", "Lizardmen", 4, 3, 0, 4, 4, 1, 1, 2, 8, false, 5, null,
                    Constants.Equipment.halberd, Constants.Equipment.lightArmor, Constants.Equipment.shield,
                    Arrays.asList(SpecialRuleTypeEnum.COLD_BLOODED, SpecialRuleTypeEnum.PREDATORY_FIGHTER, SpecialRuleTypeEnum.STUBBORN), null, reveredGuardian);

            public static final UnitModel kroxigorAncient = new UnitModel("Kroxigor Ancient", "Lizardmen", 6, 3, 0, 5, 4, 3, 1, 4, 7, true, 4, null,
                    Constants.Equipment.greatWeapon, null, null,
                    Arrays.asList(SpecialRuleTypeEnum.COLD_BLOODED, SpecialRuleTypeEnum.PREDATORY_FIGHTER, SpecialRuleTypeEnum.MONSTROUS_INFANTRY), null, null);
            public static final UnitModel kroxigor = new UnitModel("Kroxigor", "Lizardmen", 6, 3, 0, 5, 4, 3, 1, 3, 7, false, 4, null,
                    Constants.Equipment.greatWeapon, null, null,
                    Arrays.asList(SpecialRuleTypeEnum.COLD_BLOODED, SpecialRuleTypeEnum.PREDATORY_FIGHTER, SpecialRuleTypeEnum.MONSTROUS_INFANTRY),
                    null, kroxigorAncient);
        }

        public interface Skaven {
            public static final UnitModel fangleader = new UnitModel("Fangleader", "Skaven", 5, 4, 3, 3, 3, 1, 5, 2, 5, true, null, null,
                    Equipment.halberd, Equipment.heavyArmor, Equipment.shield,
                    Arrays.asList(SpecialRuleTypeEnum.SCURRY_AWAY, SpecialRuleTypeEnum.STRENGTH_IN_NUMBERS), null, null);
            public static final UnitModel stormvermin = new UnitModel("Stormvermin", "Skaven", 5, 4, 3, 3, 3, 1, 5, 1, 5, false, null, null,
                    Equipment.halberd, Equipment.heavyArmor, Equipment.shield,
                    Arrays.asList(SpecialRuleTypeEnum.SCURRY_AWAY, SpecialRuleTypeEnum.STRENGTH_IN_NUMBERS), null, fangleader);

            public static final UnitModel warlord = new UnitModel("Warlord", "Skaven", 5, 6, 4, 4, 4, 3, 7, 4, 7, true, null, null,
                    Equipment.greatWeapon, Equipment.heavyArmor, null,
                    Arrays.asList(SpecialRuleTypeEnum.SCURRY_AWAY, SpecialRuleTypeEnum.STRENGTH_IN_NUMBERS), null, null);

        }
    }

}
