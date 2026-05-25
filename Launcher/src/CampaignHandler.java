import java.io.IOException;
import java.util.List;

import GameClasses.GameClasses;

public class CampaignHandler {
    public static void east() throws IOException, InterruptedException {
        Main.scene = "eastern campaign";
        Main.screen.clear();
        Main.screen.refresh();
        Campaign.playCampaign(
            List.of(
                new GameClasses.Entity("Brute", 5, 5, -1, 1, "ascii-art/axeman.txt", List.of(Weapons.Fists), new String[] {""}),
                new GameClasses.Entity("Axeman", 3, 5, 0, 1, "ascii-art/axeman.txt", List.of(Weapons.TacticalAxe), new String[] {""}),
                new GameClasses.Entity("Witch", 3, 10, 0, 1, "ascii-art/witch.txt", List.of(Weapons.ritualDagger), new String[] {""})
            )
        );
    }    
}
