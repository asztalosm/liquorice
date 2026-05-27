import java.io.IOException;
import java.util.List;

public class CampaignHandler {
    public static void north() throws IOException, InterruptedException {
        Main.scene = "northern farms";
        Main.screen.clear();
        Main.screen.refresh();
        Campaign.playCampaign(
            List.of(
                // Entities.witch,
                // Entities.axeman,
                // Entities.witch
                Entities.geopolitanTaskmaster
            ),
            List.of(
                Entities.witch,
                Entities.masterWitch,
                Entities.witch
            ),
            0,
            1
        );
    }

    public static void east() throws IOException, InterruptedException {
        Main.scene = "eastern farms";
        Main.screen.clear();
        Main.screen.refresh();
        Campaign.playCampaign(
            List.of(
                Entities.spawn,
                Entities.manhound
            ),
            List.of(
                Entities.spawn,
                Entities.gunner,
                Entities.spawn
            ),
            1,
            2
        );
    }

    public static void west() throws IOException, InterruptedException {
        Main.scene = "western farms";
        Main.screen.clear();
        Main.screen.refresh();
        Campaign.playCampaign(
            List.of(
                Entities.geopolitanTrooper,
                Entities.geopolitanGunner
            ),
            List.of(
                Entities.geopolitanTaskmaster
            ),
            2,
            2
        );
    }
}
