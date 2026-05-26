package GameClasses;

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
}
