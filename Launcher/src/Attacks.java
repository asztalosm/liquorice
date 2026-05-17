public class Attacks {
    public record Attack(String name, int minRoll, int maxRoll, int rollToHit, int rollToCrit, double critMultiplier) {}
}
