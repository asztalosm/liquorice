import java.io.IOException;
import java.util.List;

import GameClasses.GameClasses;

public class Weapons {
    public static GameClasses.Equipment universalActions = new GameClasses.Equipment(
        "",
        new GameClasses.Attack("Exit", (a, b)->{}, false, new int[]{0, 0}, 0, 0),
        new GameClasses.Attack("Pass", (a, b)->{}, false, new int[]{0, 0}, 0, 0)
    );

    public static GameClasses.Equipment rat = new GameClasses.Equipment(
        "Rat",
        new GameClasses.Attack(
            "Bite",
            (attacker, target) -> {},
            false,
            new int[]{4, 6},
            1,
            2),
        new GameClasses.Attack(
            "Claw",
            (attacker, target) -> {},
            false,
            new int[]{3, 4},
            2,
            2)
    );

    public static GameClasses.Equipment armCannon = new GameClasses.Equipment(
        "Armcannon",
        new GameClasses.Attack(
            "Shoot",
            (attacker, target) -> {
                try {
                    if (attacker.getEffect("Ammo").count>0) {
                        target.damageSelf(7);
                        attacker.getEffect("Ammo").count--;
                        Main.formatter.printSingle(Combat.getSubMenuRow(), "*BOOM*", TextFormatter.PaddingAlignment.CENTER);
                    } else {
                        Main.formatter.promptTextInput(Combat.getSubMenuRow()+1, TextFormatter.PaddingAlignment.CENTER, attacker.Name+" lacks ammunition."+" [ENTER]");
                    }
                } catch (IOException | InterruptedException e2) {}
            },
            false,
            new int[]{0, 0},
            1,
            2),
        new GameClasses.Attack(
            "Repurpose scrap",
            (attacker, target) -> {
                try {
                    Main.formatter.promptTextInput(Combat.getSubMenuRow(), TextFormatter.PaddingAlignment.CENTER, target.applyEffect(Effects.vulnerability, 1, 1, true)+" [ENTER]");
                    Main.formatter.promptTextInput(Combat.getSubMenuRow(), TextFormatter.PaddingAlignment.CENTER, attacker.applyEffect(Effects.ammo, 3, 1, true)+" [ENTER]");
                } catch (IOException | InterruptedException e2) {}
            },
            false,
            new int[]{4, 5},
            1,
            2)
    );

    public static GameClasses.Equipment geopolitanWeaver = new GameClasses.Equipment(
        "Geopolitan Weaver",
        new GameClasses.Attack(
            "Shoot",
            (attacker, target) -> {
                try {
                    if (attacker.getEffect("Ammo").count>0) {
                        target.consumeStamina(4);
                        attacker.getEffect("Ammo").count--;
                        Main.formatter.printSingle(Combat.getSubMenuRow(), "A string of light punctures your body and soul.", TextFormatter.PaddingAlignment.CENTER);
                        Main.formatter.promptTextInput(Combat.getSubMenuRow()+1, TextFormatter.PaddingAlignment.CENTER, target.Name+" lost 4 stamina."+" [ENTER]");
                    } else {
                        Main.formatter.promptTextInput(Combat.getSubMenuRow()+1, TextFormatter.PaddingAlignment.CENTER, attacker.Name+" lacks ammunition."+" [ENTER]");
                    }
                } catch (IOException | InterruptedException e2) {}
            },
            false,
            new int[]{1, 1},
            4,
            2),
        new GameClasses.Attack(
            "Reload",
            (attacker, target) -> {
                try {
                    Main.formatter.promptTextInput(Combat.getSubMenuRow(), TextFormatter.PaddingAlignment.CENTER, attacker.applyEffect(Effects.ammo, 3, 1, true)+" [ENTER]");
                } catch (IOException | InterruptedException e2) {}
            },
            false,
            new int[]{3, 4},
            1,
            2)
    );

    public static GameClasses.Equipment manhound = new GameClasses.Equipment(
        "Manhound",
        new GameClasses.Attack(
            "Taste",
            (attacker, target) -> {
                try {
                    Main.formatter.promptTextInput(Combat.getSubMenuRow(), TextFormatter.PaddingAlignment.CENTER, target.applyEffect(Effects.weakness, 2, 2, false)+" [ENTER]");
                } catch (IOException | InterruptedException e2) {}
            },
            false,
            new int[]{5, 7},
            1,
            4),
        new GameClasses.Attack(
            "Claw",
            (attacker, target) -> {},
            false,
            new int[]{3, 4},
            2,
            2),
        new GameClasses.Attack(
            "Stare",
            (attacker, target) -> {
                try {
                    Main.formatter.promptTextInput(Combat.getSubMenuRow(), TextFormatter.PaddingAlignment.CENTER, target.applyEffect(Effects.slowness, 2, 3, false)+" [ENTER]");
                } catch (IOException | InterruptedException e) {}
            },
            false,
            new int[]{0, 0},
            0,
            0)
    );

    public static GameClasses.Equipment geopolitanMace = new GameClasses.Equipment(
        "Geopolitan Mace",
        new GameClasses.Attack(
            "Bash",
            (attacker, target) -> {
                try {
                    Main.formatter.promptTextInput(Combat.getSubMenuRow(), TextFormatter.PaddingAlignment.CENTER, target.applyEffect(Effects.vulnerability, 1, 2, false)+" [ENTER]");
                } catch (IOException | InterruptedException e2) {}
            },
            false,
            new int[]{5, 7},
            1,
            4),
        new GameClasses.Attack(
            "Shield",
            (attacker, target) -> {
                try {
                    Main.formatter.promptTextInput(Combat.getSubMenuRow(), TextFormatter.PaddingAlignment.CENTER, attacker.applyEffect(Effects.slowness, 1, 3, false)+" [ENTER]");
                    Main.formatter.promptTextInput(Combat.getSubMenuRow(), TextFormatter.PaddingAlignment.CENTER, attacker.applyEffect(Effects.sturdyness, 2, 3, false)+" [ENTER]");
                } catch (IOException | InterruptedException e) {}
            },
            false,
            new int[]{1, 1},
            1,
            1),
        new GameClasses.Attack(
            "Synchronize",
            (attacker, target) -> {
                try {
                    Main.formatter.promptTextInput(Combat.getSubMenuRow(), TextFormatter.PaddingAlignment.CENTER, attacker.applyEffect(Effects.relentlessness, 2, 1, false)+" [ENTER]");
                } catch (IOException | InterruptedException e) {}
            },
            false,
            new int[]{2, 3},
            0,
            0)
    );

    public static GameClasses.Equipment geopolitanMace2 = new GameClasses.Equipment(
        "Geopolitan Mace",
        new GameClasses.Attack(
            "Rib breaker",
            (attacker, target) -> {
                try {
                    Main.formatter.promptTextInput(Combat.getSubMenuRow(), TextFormatter.PaddingAlignment.CENTER, target.applyEffect(Effects.vulnerability, 2, 2, false)+" [ENTER]");
                } catch (IOException | InterruptedException e2) {}
            },
            false,
            new int[]{6, 8},
            1,
            4),
        new GameClasses.Attack(
            "Multi Block",
            (attacker, target) -> {
                try {
                    Main.formatter.promptTextInput(Combat.getSubMenuRow(), TextFormatter.PaddingAlignment.CENTER, attacker.applyEffect(Effects.sturdyness, 3, 3, false)+" [ENTER]");
                } catch (IOException | InterruptedException e) {}
            },
            false,
            new int[]{1, 1},
            3,
            1),
        new GameClasses.Attack(
            "Synchronize",
            (attacker, target) -> {
                try {
                    Main.formatter.promptTextInput(Combat.getSubMenuRow(), TextFormatter.PaddingAlignment.CENTER, target.applyEffect(Effects.exhaustion, 2, 1, false)+" [ENTER]");
                } catch (IOException | InterruptedException e) {}
            },
            false,
            new int[]{2, 3},
            0,
            0)
    );

    public static GameClasses.Equipment juggernaut = new GameClasses.Equipment(
        "Juggernaut",
        new GameClasses.Attack(
            "Charge",
            (attacker, target) -> {
                try {
                    Main.formatter.promptTextInput(Combat.getSubMenuRow(), TextFormatter.PaddingAlignment.CENTER, attacker.applyEffect(Effects.haste, 2, 2, false)+" [ENTER]");
                    Main.formatter.promptTextInput(Combat.getSubMenuRow()+1, TextFormatter.PaddingAlignment.CENTER, target.applyEffect(Effects.weakness, 2, 2, false)+" [ENTER]");
                } catch (IOException | InterruptedException e2) {}
            },
            false,
            new int[]{1, 10},
            1,
            5),
        new GameClasses.Attack(
            "Reinforce",
            (attacker, target) -> {
                try {
                    Main.formatter.promptTextInput(Combat.getSubMenuRow(), TextFormatter.PaddingAlignment.CENTER, attacker.applyEffect(Effects.strength, 3, 1, false)+" [ENTER]");
                    Main.formatter.promptTextInput(Combat.getSubMenuRow(), TextFormatter.PaddingAlignment.CENTER, attacker.applyEffect(Effects.sturdyness, 3, 3, false)+" [ENTER]");
                } catch (IOException | InterruptedException e) {}
            },
            false,
            new int[]{1, 1},
            3,
            3),
        new GameClasses.Attack(
            "Realign",
            (attacker, target) -> {
                try {
                    Main.formatter.alert(Combat.getSubMenuRow(), List.of("The particles of "+attacker.Name+" started realigning!", attacker.Name+" became vulnerable!"));
                    Main.formatter.promptTextInput(Combat.getSubMenuRow(), TextFormatter.PaddingAlignment.CENTER, attacker.applyEffect(Effects.vulnerability, 5, 1, false)+" [ENTER]");
                    Main.formatter.promptTextInput(Combat.getSubMenuRow(), TextFormatter.PaddingAlignment.CENTER, attacker.applyEffect(Effects.relentlessness, 2, 3, false)+" [ENTER]");
                } catch (IOException | InterruptedException e) {}
            },
            false,
            new int[]{2, 3},
            0,
            0)
    );

    public static GameClasses.Equipment masterWitch = new GameClasses.Equipment(
        "Amplified Ritual Dagger",
        new GameClasses.Attack(
            "Stab",
            (attacker, target) -> {},
            false,
            new int[]{3, 4},
            1,
            1),
        new GameClasses.Attack(
            "Chant and slash",
            (attacker, target) -> {
                try {
                    Main.formatter.promptTextInput(Combat.getSubMenuRow(), TextFormatter.PaddingAlignment.CENTER, attacker.applyEffect(Effects.strength, 1, 4, false)+" [ENTER]");
                } catch (IOException | InterruptedException e2) {}
            },
            false,
            new int[]{1, 3},
            1,
            1),
        new GameClasses.Attack(
            "Oether's carnage",
            (attacker, target) -> {
                try {
                    Main.formatter.alert(Combat.getSubMenuRow(), List.of("Blue flames burst from "+attacker.Name+"'s wounds.", "You lose 2 hp and "+attacker.Name+" loses 1."));
                    attacker.Health -= 1;
                    target.Health -= 2;
                } catch (IOException | InterruptedException e3) {}
            },
            false,
            new int[]{0, 0},
            0,
            1)
    );

    public static GameClasses.Equipment nemesis = new GameClasses.Equipment(
        "Nemesis frame",
        new GameClasses.Attack(
            "Multi-cleave",
            (attacker, target) -> {},
            false,
            new int[]{2, 3},
            3,
            5),
        new GameClasses.Attack(
            "Concentrated fire",
            (attacker, target) -> {
                try {
                    if (attacker.getEffect("Ammo").count>1) {
                        target.damageSelf(8);
                        attacker.getEffect("Ammo").count -= 2;
                        Main.formatter.printSingle(Combat.getSubMenuRow(), "*BOOM*", TextFormatter.PaddingAlignment.CENTER);
                    } else {
                        Main.formatter.promptTextInput(Combat.getSubMenuRow()+1, TextFormatter.PaddingAlignment.CENTER, attacker.Name+" lacks ammunition."+" [ENTER]");
                    }
                } catch (IOException | InterruptedException e2) {}
            },
            false,
            new int[]{0, 0},
            1,
            3),
        new GameClasses.Attack(
            "Repair",
            (attacker, target) -> {
                try {
                    attacker.Stamina = attacker.MaxStamina;
                    Main.formatter.promptTextInput(Combat.getSubMenuRow()+1, TextFormatter.PaddingAlignment.CENTER, attacker.Name+" replenished all stamina."+" [ENTER]");
                    Main.formatter.promptTextInput(Combat.getSubMenuRow(), TextFormatter.PaddingAlignment.CENTER, attacker.applyEffect(Effects.ammo, 5, 1, true)+" [ENTER]");
                } catch (IOException | InterruptedException e3) {}
            },
            false,
            new int[]{0, 0},
            0,
            10),
        new GameClasses.Attack(
            "Accellerate",
            (attacker, target) -> {
                try {
                    Main.formatter.promptTextInput(Combat.getSubMenuRow(), TextFormatter.PaddingAlignment.CENTER, attacker.applyEffect(Effects.haste, attacker.getEffect("Hatse").count+2, 1, true)+" [ENTER]");
                } catch (IOException | InterruptedException e3) {}
            },
            false,
            new int[]{6, 8},
            0,
            5)
    );
    
    //region PLAYER WEAPONS

    public static GameClasses.Equipment Fists = new GameClasses.Equipment(
        "Fists",
        new GameClasses.Attack(
            "Punch",
            (attacker, target) -> {},
            false,
            new int[]{1, 4},
            2,
            1),
        new GameClasses.Attack(
            "Block",
            (attacker, target) -> {
                try {
                    Main.formatter.promptTextInput(Combat.getSubMenuRow(), TextFormatter.PaddingAlignment.CENTER, attacker.applyEffect(Effects.sturdyness, 2, 2, false)+" [ENTER]");
                } catch (IOException | InterruptedException e1) {}
            },
            false,
            new int[]{1, 2},
            1,
            0)
    );
    
    public static GameClasses.Equipment TacticalAxe = new GameClasses.Equipment(
        "Tactical Axe",
        new GameClasses.Attack(
            "Slash",
            (attacker, target) -> {},
            false,
            new int[]{1, 4},
            1,
            1),
        new GameClasses.Attack(
            "Cleave",
            (attacker, target) -> {},
            false,
            new int[]{3, 6},
            1,
            3),
        new GameClasses.Attack(
            "Bait",
            (attacker, target) -> {
                try {
                    Main.formatter.promptTextInput(Combat.getSubMenuRow(), TextFormatter.PaddingAlignment.CENTER, target.applyEffect(Effects.slowness, 2, 3, false)+" [ENTER]");
                } catch (IOException | InterruptedException e) {}
            },
            false,
            new int[]{1, 4},
            1,
            0)
    );

    public static GameClasses.Equipment pistol = new GameClasses.Equipment(
        "Revolver",
        new GameClasses.Attack(
            "Shoot",
            (attacker, target) -> {
                try {
                    if (attacker.getEffect("Ammo").count>0) {
                        target.damageSelf(7);
                        attacker.getEffect("Ammo").count--;
                        Main.formatter.printSingle(Combat.getSubMenuRow(), "*BOOM*", TextFormatter.PaddingAlignment.CENTER);
                    } else {
                        Main.formatter.promptTextInput(Combat.getSubMenuRow()+1, TextFormatter.PaddingAlignment.CENTER, attacker.Name+" lacks ammunition."+" [ENTER]");
                    }
                } catch (IOException | InterruptedException e2) {}
            },
            false,
            new int[]{0, 0},
            1,
            2),
        new GameClasses.Attack(
            "Reload",
            (attacker, target) -> {
                try {
                    Main.formatter.promptTextInput(Combat.getSubMenuRow(), TextFormatter.PaddingAlignment.CENTER, attacker.applyEffect(Effects.ammo, 3, 1, true)+" [ENTER]");
                } catch (IOException | InterruptedException e2) {}
            },
            false,
            new int[]{3, 4},
            1,
            2)
    );

    public static GameClasses.Equipment ritualDagger = new GameClasses.Equipment(
        "Ritual Dagger",
        new GameClasses.Attack(
            "Stab",
            (attacker, target) -> {},
            false,
            new int[]{3, 4},
            1,
            1),
        new GameClasses.Attack(
            "Chant and slash",
            (attacker, target) -> {
                try {
                    Main.formatter.promptTextInput(Combat.getSubMenuRow(), TextFormatter.PaddingAlignment.CENTER, attacker.applyEffect(Effects.strength, 2, 1, false)+" [ENTER]");
                } catch (IOException | InterruptedException e2) {}
            },
            false,
            new int[]{1, 3},
            1,
            1),
        new GameClasses.Attack(
            "Sacrifice",
            (attacker, target) -> {
                try {
                    Main.formatter.alert(Combat.getSubMenuRow(), List.of("The knife sinks into "+attacker.Name+"'s left thigh.", attacker.Name+" has suffered 1 damage due to blood loss."));
                    attacker.Health -= 1;
                    Main.formatter.promptTextInput(Combat.getSubMenuRow(), TextFormatter.PaddingAlignment.CENTER, attacker.applyEffect(Effects.strength, 3, 2, false)+" [ENTER]");
                } catch (IOException | InterruptedException e3) {}
            },
            false,
            new int[]{0, 0},
            0,
            1)
    );
}
