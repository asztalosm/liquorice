import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import GameClasses.GameClasses;

public class Market {
    public static GameClasses.Product exit = new GameClasses.Product("Exit", "", 0, (entity) -> {});
    public static GameClasses.Product medkit = new GameClasses.Product("Medkit", "If your HP drops to 50% you get a full recovery.", 40, ((entity) -> {
        try {
            entity.applyEffect(Effects.medkit, 1, 1, true);
            Main.formatter.confirmInput(false, Campaign.getPerkRow(), TextFormatter.PaddingAlignment.CENTER, "Medkit applied [ENTER]");
        } catch (IOException | InterruptedException e) {}
    }));
    public static GameClasses.Product extraAmmo1 = new GameClasses.Product("+3 Ammo", "Grants 3 extra ammunition at combat start.", 30, ((entity) -> {
        try {
            entity.applyEffect(Effects.ammo, entity.getEffect("Ammo").count+3, 1, true);
            Main.formatter.confirmInput(false, Campaign.getPerkRow(), TextFormatter.PaddingAlignment.CENTER, "+3 ammo granted [ENTER]");
        } catch (IOException | InterruptedException e) {}
    }));
    public static GameClasses.Product extraAmmo2 = new GameClasses.Product("+6 Ammo", "Grants 6 extra ammunition at combat start.", 50, ((entity) -> {
        try {
            entity.applyEffect(Effects.ammo, entity.getEffect("Ammo").count+6, 1, true);
            Main.formatter.confirmInput(false, Campaign.getPerkRow(), TextFormatter.PaddingAlignment.CENTER, "+6 ammo granted [ENTER]");
        } catch (IOException | InterruptedException e) {}
    }));
    public static GameClasses.Product riotShield = new GameClasses.Product("Riot shield", "Boosts block power by 4", 60, ((entity) -> {
        try {
            entity.applyEffect(Effects.riotShield, 4, 1, true);
            Main.formatter.confirmInput(false, Campaign.getPerkRow(), TextFormatter.PaddingAlignment.CENTER, "Riot shield granted [ENTER]");
        } catch (IOException | InterruptedException e) {}
    }));
    public static GameClasses.Product shotgun = new GameClasses.Product("[RENTAL] Coolins&Coolinson Lever Action Shotgun", "A ranged weapon that consumes all ammo upon use but multiplies the damage dealt by the amount of ammo consumed.", 100, ((entity) -> {
        try {
            entity.Gear.add(Weapons.shotgun);
            Main.formatter.confirmInput(false, Campaign.getPerkRow(), TextFormatter.PaddingAlignment.CENTER, "Coolins&Coolinson Lever Action Shotgun granted [ENTER]");
        } catch (IOException | InterruptedException e) {}
    }));
    public static GameClasses.Product pipe = new GameClasses.Product("[RENTAL] Dr. Catsworth's Pipe of Madness", "A pipe that can emit large laced clouds of smoke, acting as a chemical weapon.", 80, ((entity) -> {
        try {
            entity.Gear.add(Weapons.pipe);
            Main.formatter.confirmInput(false, Campaign.getPerkRow(), TextFormatter.PaddingAlignment.CENTER, "Dr. Catsworth's Pipe of Madness granted [ENTER]");
        } catch (IOException | InterruptedException e) {}
    }));
    public static GameClasses.Product deathray = new GameClasses.Product("[RENTAL] Professor Carlstrumm's Deathray", "A single-use device which launches a bolt of lightning at the target, dealing high damage and reducing their Endurance by 5.", 200, ((entity) -> {
        try {
            entity.Gear.add(Weapons.deathray);
            Main.formatter.confirmInput(false, Campaign.getPerkRow(), TextFormatter.PaddingAlignment.CENTER, "Professor Carlstrumm's Deathray granted [ENTER]");
        } catch (IOException | InterruptedException e) {}
    }));
    public static GameClasses.Product claws = new GameClasses.Product("[RENTAL] McGurrigh's Fatal Exoclaws", "An external clawed right arm that can perform melee combat actions with much greater efficiency than any other melee weapon.", 110, ((entity) -> {
        try {
            entity.Gear.add(Weapons.exoclaws);
            Main.formatter.confirmInput(false, Campaign.getPerkRow(), TextFormatter.PaddingAlignment.CENTER, "McGurrigh's Fatal Exoclaws granted [ENTER]");
        } catch (IOException | InterruptedException e) {}
    }));

    public static List<GameClasses.Product> products = new ArrayList<>(List.of(
        medkit,
        extraAmmo1,
        extraAmmo2,
        riotShield,
        shotgun,
        pipe,
        deathray,
        claws
    ));
}