import java.util.List;

public class InventoryItems {

    //region weapons and attacks
    public static class Weapon {
        public String id;
        public String name;
        public List<Attacks.Attack> attacks;
    }
    //endregion
    public static class Helmet {}
    public static class Chestplate {}
    public static class Leggings {}
    public static class Consumable {

    }
}