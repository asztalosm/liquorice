import java.io.IOException;
import java.util.List;

import GameClasses.GameClasses;

public class Effects {
    public static GameClasses.StatusEffect block = new GameClasses.StatusEffect("Block", (entity) -> {}); // buffer hp
    public static GameClasses.StatusEffect sturdyness = new GameClasses.StatusEffect("Sturdyness", (entity) -> {}); // damage reduction
    public static GameClasses.StatusEffect vulnerability = new GameClasses.StatusEffect("Vulnerability", (entity) -> {}); // increased damage intake
    public static GameClasses.StatusEffect strength = new GameClasses.StatusEffect("Strength", (entity) -> {}); // buffs damage rolls
    public static GameClasses.StatusEffect weakness = new GameClasses.StatusEffect("Weakness", (entity) -> {}); // debuffs damage rolls
    public static GameClasses.StatusEffect haste = new GameClasses.StatusEffect("Haste", (entity) -> {}); // buffs initiative order
    public static GameClasses.StatusEffect slowness = new GameClasses.StatusEffect("Slowness", (entity) -> {}); // debuffs initiative order
    public static GameClasses.StatusEffect relentlessness = new GameClasses.StatusEffect("Relentlessness", (entity) -> {}); // buffs stamina regen
    public static GameClasses.StatusEffect exhaustion = new GameClasses.StatusEffect("Exhaustion", (entity) -> {}); // debuffs stamina regen
    public static GameClasses.StatusEffect ammo = new GameClasses.StatusEffect("Ammo", (entity) -> {}); // charge mechanic
    public static GameClasses.StatusEffect badge = new GameClasses.StatusEffect("Taskmaster badge", (entity) -> {
        try {
            if (entity.Health<=0) {               
                Main.formatter.alert(Combat.getInitiativeRow()-10, List.of(
                    "The badge of "+entity.Name+" starts evaporating as you execute them.",
                    "From the thick cloud of ashes, a humanoid form emerges."
                ));
                entity.copyFrom(Entities.juggernaut);
            }
        } catch (IOException | InterruptedException e) {}
    }); // unique
    public static GameClasses.StatusEffect medkit = new GameClasses.StatusEffect("Medkit", (entity) -> {
        try {
            if (entity.Health<=(int)(entity.Health/2)) {               
                Main.formatter.alert(Combat.getInitiativeRow()-10, List.of(
                    entity.Name+"used medkit.",
                    "Full health restored"
                ));
                entity.Health += entity.MaxHealth;
            }
        } catch (IOException | InterruptedException e) {}
    }); // unique
    public static GameClasses.StatusEffect riotShield = new GameClasses.StatusEffect("Riot shield", (entity) -> {}); // block power boost
    public static GameClasses.StatusEffect overheat = new GameClasses.StatusEffect("Overheat", (entity) -> {}); // unique
}
