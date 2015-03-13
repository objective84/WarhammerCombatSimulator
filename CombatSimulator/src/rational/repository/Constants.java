package rational.repository;

import rational.enums.SpecialRuleTypeEnum;
import rational.model.CharacterModel;
import rational.model.equipment.Armor;
import rational.model.UnitModel;
import rational.model.equipment.Weapon;

import java.util.ArrayList;
import java.util.Arrays;

public class Constants {


    public interface Equipment {
        public static final Weapon handWeapons = new Weapon("Hand Weapons", null, new ArrayList<SpecialRuleTypeEnum>(), null, null, null, null, null, false);
        public static final Weapon spear = new Weapon("Spear", null, Arrays.asList(SpecialRuleTypeEnum.FIGHT_IN_EXTRA_RANKS), null, null, null, null, null, false);
        public static final Weapon halberd = new Weapon("Halberd", null, new ArrayList<SpecialRuleTypeEnum>(), null, null, null, 1, null, true);
        public static final Weapon greatWeapon = new Weapon("Great Weapon", null, Arrays.asList(SpecialRuleTypeEnum.ALWAYS_STRIKE_LAST), null, null, null, 2, null, true);
        public static final Weapon lance = new Weapon("Lance", null, Arrays.asList(SpecialRuleTypeEnum.WHEN_CHARGING), null, null, null, 2, null, false);

        public static final Weapon swordOfBloodshed = new Weapon("Sword of Bloodshed", 60, new ArrayList<SpecialRuleTypeEnum>(), null, null, null, null, 3, false);
        public static final Weapon runefang = new Weapon("Runefang", 85, Arrays.asList(SpecialRuleTypeEnum.AUTO_WOUND, SpecialRuleTypeEnum.NO_ARMOR_SAVE),
                null, null, null, null, null, false);
        public static final Weapon sunfang = new Weapon("Sunfang", 0, Arrays.asList(SpecialRuleTypeEnum.FLAMING_ATTACKS), null, null, null, 3, null, false);
        public static final Weapon starLance = new Weapon("Star Lance", null, Arrays.asList(SpecialRuleTypeEnum.WHEN_CHARGING), null, null, null, 3, null, false);
        public static final Weapon bitingBlade = new Weapon("Biting Blade", null, Arrays.asList(SpecialRuleTypeEnum.ARMOR_PIERCING), null, null, null, null, null, false);
        public static final Weapon fencersBlades = new Weapon("FencersBlades", 10, Arrays.asList(SpecialRuleTypeEnum.EXTRA_ATTACKS), null, null, null, null, 1, false);

        public static final Armor lightArmor = new Armor("Light Armor", null, null, 6, null, null, true);
        public static final Armor heavyArmor = new Armor("Heavy Armor", null, null, 5, null, null, true);
        public static final Armor fullPlateArmor = new Armor("Full Plate Armor", null, null, 4, null, null, true);
        public static final Armor dragonArmourOfAvalorn = new Armor("Dragon Armour of Avalorn", 0, null, 1, null, 4, true);
        public static final Armor shield = new Armor("Shield", null, null, null, 1, 6, true);
    }

    public interface Models {

        public interface HighElves {
            public static final UnitModel sentinel = new CharacterModel("Sentinel", "High Elves", 5, 4, 4, 3, 3, 1, 5, 2, 8, null, null,
                    Constants.Equipment.spear, Constants.Equipment.lightArmor, Constants.Equipment.shield,
                    Arrays.asList(SpecialRuleTypeEnum.ALWAYS_STRIKE_FIRST, SpecialRuleTypeEnum.MARTIAL_PROWESS), null, null, 9, 20, 20);
            public static final UnitModel highElfSpearmen = new UnitModel("Spearmen", "High Elves", 5, 4, 4, 3, 3, 1, 5, 1, 8, false, null, null,
                    Constants.Equipment.spear, Constants.Equipment.lightArmor, Constants.Equipment.shield,
                    Arrays.asList(SpecialRuleTypeEnum.ALWAYS_STRIKE_FIRST, SpecialRuleTypeEnum.MARTIAL_PROWESS), null, sentinel, 9, 20, 20);

            public static final UnitModel bladeLord = new CharacterModel("Bladelord", "High Elves", 5, 6, 4, 3, 3, 1, 5, 3, 8, null, null,
                    Constants.Equipment.greatWeapon, Constants.Equipment.heavyArmor, null,
                    Arrays.asList(SpecialRuleTypeEnum.ALWAYS_STRIKE_FIRST, SpecialRuleTypeEnum.MARTIAL_PROWESS), null, null, 13, 20, 20);
            public static final UnitModel swordMastersOfHoeth = new UnitModel("Swordmasters of Hoeth", "High Elves", 5, 6, 4, 3, 3, 1, 5, 2, 8, false, null, null,
                    Constants.Equipment.greatWeapon, Constants.Equipment.heavyArmor, null,
                    Arrays.asList(SpecialRuleTypeEnum.ALWAYS_STRIKE_FIRST, SpecialRuleTypeEnum.MARTIAL_PROWESS), null, bladeLord, 13, 20, 20);

            public static final UnitModel keeperOfTheFlame = new CharacterModel("Keeper of the Flame", "High Elves", 4, 5, 4, 3, 3, 1, 6, 2, 9, null, 4,
                    Constants.Equipment.halberd, Constants.Equipment.heavyArmor, null,
                    Arrays.asList(SpecialRuleTypeEnum.ALWAYS_STRIKE_FIRST, SpecialRuleTypeEnum.MARTIAL_PROWESS), null, null, 15, 20, 20);
            public static final UnitModel phoenixGuard = new UnitModel("Phoenix Guard", "High Elves", 4, 5, 4, 3, 3, 1, 6, 1, 9, false, null, 4,
                    Constants.Equipment.halberd, Constants.Equipment.heavyArmor, null,
                    Arrays.asList(SpecialRuleTypeEnum.ALWAYS_STRIKE_FIRST, SpecialRuleTypeEnum.MARTIAL_PROWESS), null, keeperOfTheFlame, 15, 20, 20);

            public static final UnitModel elvenSteed = new UnitModel("Elven Steeds", "High Elves", 9, 3, 0, 3, 3, 1, 4, 1, 5, false, null, null, null,
                    null, Constants.Equipment.shield, new ArrayList<SpecialRuleTypeEnum>(), null, null, 0, 25, 50);
            public static final UnitModel highHelm = new CharacterModel("High Helm", "High Elves", 5, 4, 4, 3, 3, 1, 5, 2, 8, null, null,
                    Constants.Equipment.handWeapons, Constants.Equipment.heavyArmor, null,
                    Arrays.asList(SpecialRuleTypeEnum.ALWAYS_STRIKE_FIRST, SpecialRuleTypeEnum.MARTIAL_PROWESS), elvenSteed, null, 21, 25, 50);
            public static final UnitModel silverHelm = new UnitModel("Silver Helms", "High Elves", 5, 4, 4, 3, 3, 1, 5, 1, 8, false, null, null,
                    Constants.Equipment.handWeapons, Constants.Equipment.heavyArmor, null,
                    Arrays.asList(SpecialRuleTypeEnum.ALWAYS_STRIKE_FIRST, SpecialRuleTypeEnum.MARTIAL_PROWESS), elvenSteed, highHelm, 21, 25, 50);

            public static final UnitModel drakemaster = new CharacterModel("Drakemaster", "High Elves", 5, 5, 4, 3, 3, 1, 6, 3, 9, null, null,
                    Constants.Equipment.handWeapons, Constants.Equipment.heavyArmor, null,
                    Arrays.asList(SpecialRuleTypeEnum.ALWAYS_STRIKE_FIRST, SpecialRuleTypeEnum.MARTIAL_PROWESS), elvenSteed, null, 29, 25, 50);
            public static final UnitModel dragonPrinces = new UnitModel("Dragon Princes of Calendor", "High Elves", 5, 5, 4, 3, 3, 1, 6, 2, 9, false, null, null,
                    Equipment.lance, Constants.Equipment.heavyArmor, Equipment.shield,
                    Arrays.asList(SpecialRuleTypeEnum.ALWAYS_STRIKE_FIRST, SpecialRuleTypeEnum.MARTIAL_PROWESS, SpecialRuleTypeEnum.SWIFTSTRIDE),
                    elvenSteed, drakemaster, 29, 25, 50);

            public static final UnitModel malhandir = new CharacterModel("Malhandir", "High Elves", 10, 4, 0, 4, 3, 1, 5, 2, 7, null, null, null,
                    null, Constants.Equipment.shield, new ArrayList<SpecialRuleTypeEnum>(), null, null, 0, 25, 50);
            public static final UnitModel tyrion = new CharacterModel("Tyrion", "High Elves", 5, 9, 7, 4, 3, 4, 10, 4, 10, null, null,
                    Equipment.sunfang, Constants.Equipment.dragonArmourOfAvalorn, null,
                    Arrays.asList(SpecialRuleTypeEnum.ALWAYS_STRIKE_FIRST, SpecialRuleTypeEnum.MARTIAL_PROWESS), malhandir, null, 410, 25, 50);

            public static final UnitModel prince = new CharacterModel("Prince", "High Elves", 5, 7, 7, 4, 3, 3, 8, 4, 10, null, null,
                    Equipment.greatWeapon, Equipment.heavyArmor, Equipment.shield,
                    Arrays.asList(SpecialRuleTypeEnum.ALWAYS_STRIKE_FIRST, SpecialRuleTypeEnum.MARTIAL_PROWESS), elvenSteed, null, 140, 20, 20);


        }

        public interface Empire {
            public static final UnitModel empireWarhorse = new UnitModel("Warhorse", "Empire", 8, 3, 0, 3, 3, 1, 3, 1, 5, false, null, null, null,
                    null, Constants.Equipment.shield, new ArrayList<SpecialRuleTypeEnum>(), null, null, 0, 25, 50);
            public static final UnitModel preceptor = new CharacterModel("Preceptor", "Empire", 4, 4, 3, 3, 3, 1, 3, 2, 8, null, null,
                    Constants.Equipment.handWeapons, Constants.Equipment.fullPlateArmor, Constants.Equipment.shield,
                    new ArrayList<SpecialRuleTypeEnum>(), empireWarhorse, null, 22, 25, 50);
            public static final UnitModel empireKnight = new UnitModel("Empire Knight", "Empire", 4, 4, 3, 3, 3, 1, 3, 1, 8, false, null, null,
                    Constants.Equipment.handWeapons, Constants.Equipment.fullPlateArmor, Constants.Equipment.shield,
                    new ArrayList<SpecialRuleTypeEnum>(), empireWarhorse, preceptor, 22, 25, 50);

            public static final UnitModel spearSergeant = new CharacterModel("Sergeant", "Empire", 4, 3, 3, 3, 3, 1, 3, 2, 9, null, null,
                    Equipment.halberd, Equipment.lightArmor, null, new ArrayList<>(Arrays.asList(SpecialRuleTypeEnum.COLD_BLOODED)),
                    null, null, 5, 20, 20);
            public static final UnitModel spearmen = new UnitModel("Spearmen", "Empire", 4, 3, 3, 3, 3, 1, 3, 1, 9, false, null, 6,
                    Equipment.spear, Equipment.lightArmor, Equipment.shield, new ArrayList<>(Arrays.asList(SpecialRuleTypeEnum.COLD_BLOODED)),
                    null, spearSergeant, 5, 20, 20);

            public static final UnitModel halberdSergeant = new CharacterModel("Sergeant", "Empire", 4, 3, 3, 3, 3, 1, 3, 2, 9, null, null,
                    Equipment.halberd, Equipment.lightArmor, null, new ArrayList<>(Arrays.asList(SpecialRuleTypeEnum.COLD_BLOODED)),
                    null, null, 6, 20, 20);
            public static final UnitModel halberdier = new UnitModel("Halberdier", "Empire", 4, 3, 3, 3, 3, 1, 3, 1, 9, false, null, 6,
                    Equipment.halberd, Equipment.lightArmor, null, new ArrayList<>(Arrays.asList(SpecialRuleTypeEnum.COLD_BLOODED)),
                    null, halberdSergeant, 6, 20, 20);

            public static final UnitModel duelist = new CharacterModel("Duelist", "Empire", 4, 4, 3, 3, 3, 1, 3, 2, 9, null, null,
                    Equipment.handWeapons, Equipment.lightArmor, Equipment.shield, new ArrayList<>(Arrays.asList(SpecialRuleTypeEnum.COLD_BLOODED)),
                    null, null, 7, 20, 20);
            public static final UnitModel swordsmen = new UnitModel("Swordsmen", "Empire", 4, 4, 3, 3, 3, 1, 3, 1, 9, false, null, 6,
                    Equipment.handWeapons, Equipment.lightArmor, Equipment.shield, new ArrayList<>(Arrays.asList(SpecialRuleTypeEnum.COLD_BLOODED)),
                    null, duelist, 7, 20, 20);

            public static final UnitModel countsChampion = new CharacterModel("Count's Champion", "Empire", 4, 3, 3, 3, 3, 1, 3, 2, 9, null, null,
                    Equipment.greatWeapon, Equipment.fullPlateArmor, null, new ArrayList<>(Arrays.asList(SpecialRuleTypeEnum.COLD_BLOODED)),
                    null, null, 11, 20, 20);
            public static final UnitModel greatswords = new UnitModel("Greatswords", "Empire", 4, 3, 3, 3, 3, 1, 3, 1, 9, false, null, 6,
                    Equipment.greatWeapon, Equipment.fullPlateArmor, null, new ArrayList<>(Arrays.asList(SpecialRuleTypeEnum.COLD_BLOODED)),
                    null, halberdSergeant, 11, 20, 20);

            public static final UnitModel captainOfTheEmpire = new CharacterModel("Captain Of The Empire", "Empire", 4, 5, 5, 4, 4, 1, 3, 3, 8, null, null,
                    Equipment.greatWeapon, Equipment.fullPlateArmor, null, new ArrayList<>(Arrays.asList(SpecialRuleTypeEnum.COLD_BLOODED)),
                    null, null, 60, 20, 20);

            public static final UnitModel generalOfTheEmpire = new CharacterModel("General Of The Empire", "Empire", 4, 5, 5, 4, 4, 3, 5, 3, 9, null, null,
                    Equipment.greatWeapon, Equipment.fullPlateArmor, null, new ArrayList<>(Arrays.asList(SpecialRuleTypeEnum.COLD_BLOODED)),
                    null, null, 95, 20, 20);
        }

        public interface Lizardmen {
            public static final UnitModel spawnLeader = new CharacterModel("Spawn Leader", "Lizardmen", 4, 3, 0, 4, 4, 1, 1, 3, 8, 5, null,
                    Constants.Equipment.spear, null, Constants.Equipment.shield,
                    Arrays.asList(SpecialRuleTypeEnum.COLD_BLOODED, SpecialRuleTypeEnum.PREDATORY_FIGHTER), null, null, 11, 25, 25);
            public static final UnitModel saurusWarriors = new UnitModel("Saurus Warriors", "Lizardmen", 4, 3, 0, 4, 4, 1, 1, 2, 8, false, 5, null,
                    Constants.Equipment.spear, null, Constants.Equipment.shield,
                    Arrays.asList(SpecialRuleTypeEnum.COLD_BLOODED, SpecialRuleTypeEnum.PREDATORY_FIGHTER), null, spawnLeader, 11, 25, 25);

            public static final UnitModel reveredGuardian = new CharacterModel("Revered Guardian", "Lizardmen", 4, 4, 0, 4, 4, 1, 2, 3, 8, 5, null,
                    Constants.Equipment.spear, null, Constants.Equipment.shield,
                    Arrays.asList(SpecialRuleTypeEnum.COLD_BLOODED, SpecialRuleTypeEnum.PREDATORY_FIGHTER, SpecialRuleTypeEnum.STUBBORN), null, null,
                    14, 25, 25);
            public static final UnitModel templeGuard = new UnitModel("Temple Guard", "Lizardmen", 4, 3, 0, 4, 4, 1, 1, 2, 8, false, 5, null,
                    Constants.Equipment.halberd, Constants.Equipment.lightArmor, Constants.Equipment.shield,
                    Arrays.asList(SpecialRuleTypeEnum.COLD_BLOODED, SpecialRuleTypeEnum.PREDATORY_FIGHTER, SpecialRuleTypeEnum.STUBBORN), null,
                    reveredGuardian, 14, 25, 25);

            public static final UnitModel kroxigorAncient = new CharacterModel("Kroxigor Ancient", "Lizardmen", 6, 3, 0, 5, 4, 3, 1, 4, 7, 4, null,
                    Constants.Equipment.greatWeapon, null, null,
                    Arrays.asList(SpecialRuleTypeEnum.COLD_BLOODED, SpecialRuleTypeEnum.PREDATORY_FIGHTER, SpecialRuleTypeEnum.MONSTROUS_INFANTRY),
                    null, null, 50, 40, 40);
            public static final UnitModel kroxigor = new UnitModel("Kroxigor", "Lizardmen", 6, 3, 0, 5, 4, 3, 1, 3, 7, false, 4, null,
                    Constants.Equipment.greatWeapon, null, null,
                    Arrays.asList(SpecialRuleTypeEnum.COLD_BLOODED, SpecialRuleTypeEnum.PREDATORY_FIGHTER, SpecialRuleTypeEnum.MONSTROUS_INFANTRY),
                    null, kroxigorAncient, 50, 40, 40);
        }

        public interface Skaven {
            public static final UnitModel fangleader = new CharacterModel("Fangleader", "Skaven", 5, 4, 3, 3, 3, 1, 5, 2, 5, null, null,
                    Equipment.halberd, Equipment.heavyArmor, Equipment.shield,
                    Arrays.asList(SpecialRuleTypeEnum.SCURRY_AWAY, SpecialRuleTypeEnum.STRENGTH_IN_NUMBERS), null, null, 7, 20, 20);
            public static final UnitModel stormvermin = new UnitModel("Stormvermin", "Skaven", 5, 4, 3, 3, 3, 1, 5, 1, 5, false, null, null,
                    Equipment.halberd, Equipment.heavyArmor, Equipment.shield,
                    Arrays.asList(SpecialRuleTypeEnum.SCURRY_AWAY, SpecialRuleTypeEnum.STRENGTH_IN_NUMBERS), null, fangleader, 7, 20, 20);

            public static final UnitModel warlord = new CharacterModel("Warlord", "Skaven", 5, 6, 4, 4, 4, 3, 7, 4, 7, null, null,
                    Equipment.greatWeapon, Equipment.heavyArmor, null,
                    Arrays.asList(SpecialRuleTypeEnum.SCURRY_AWAY, SpecialRuleTypeEnum.STRENGTH_IN_NUMBERS), null, null, 90, 20, 20);

        }
    }

}
