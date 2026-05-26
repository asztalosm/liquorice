import java.io.IOException;
import java.util.List;

import GameClasses.Effects;
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
            new int[]{3, 4},
            1,
            2),
        new GameClasses.Attack(
            "Claw",
            (attacker, target) -> {},
            false,
            new int[]{2, 3},
            2,
            2)
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
                    Main.formatter.promptTextInput(Combat.getSubMenuRow(), TextFormatter.PaddingAlignment.CENTER, attacker.applyEffect(Effects.sturdyness, 1, 2, false)+" [ENTER]");
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
            new int[]{2, 6},
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
                    Main.formatter.promptTextInput(Combat.getSubMenuRow(), TextFormatter.PaddingAlignment.CENTER, attacker.applyEffect(Effects.strength, 1, 4, false)+" [ENTER]");
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
                    Main.formatter.promptTextInput(Combat.getSubMenuRow(), TextFormatter.PaddingAlignment.CENTER, attacker.applyEffect(Effects.strength, 3, 4, false)+" [ENTER]");
                } catch (IOException | InterruptedException e3) {}
            },
            false,
            new int[]{0, 0},
            0,
            1)
    );
}
