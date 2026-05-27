import java.io.IOException;
import java.util.List;

public class CampaignHandler {
    public static void north() throws IOException, InterruptedException {
        Main.scene = "northern farms";
        Main.screen.clear();
        Main.screen.refresh();
        int cProgress = Globals.progress;
        Campaign.playCampaign(
            List.of(
                Entities.brute,
                Entities.axeman,
                Entities.witch
            ),
            List.of(
                Entities.witch,
                Entities.masterWitch,
                Entities.witch
            ),
            0,
            1
        );
        if (Globals.progress>0 && cProgress==0) {
            Main.screen.clear();
            Main.screen.refresh();
            Main.formatter.alert(20, List.of("You earned yourself a promotion!", "+ Max Health up by 5", "+ Ritual dagger unlocked!"));
        }
    }

    public static void east() throws IOException, InterruptedException {
        Main.scene = "eastern farms";
        Main.screen.clear();
        Main.screen.refresh();
        int cProgress = Globals.progress;
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
        if (Globals.progress>1 && cProgress==1) {
            Main.screen.clear();
            Main.screen.refresh();
            Main.formatter.alert(20, List.of("You earned yourself a promotion!", "+ Speed and Endurance up by 2", "+ 4 blocking power!"));
        }
    }

    public static void west() throws IOException, InterruptedException {
        Main.scene = "western farms";
        Main.screen.clear();
        Main.screen.refresh();
        int cProgress = Globals.progress;
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
        if (Globals.progress>2 && cProgress==2) {
            Main.screen.clear();
            Main.screen.refresh();
            Main.formatter.alert(20, List.of("You earned yourself a promotion!", "+ Max Stamina up by 5", "+ Geopolitan Mace and Weaver unlocked!"));
        }
    }
}
