/*
Original concept:

room example: (╔ ═ ╗ ║ ╚ ╝ ╣ ╦ ╩ ╬ ╠   ╍ ┋)

     ╔══════════════════════╗
     ║                      ╠══════════════════╗
     ║          3.          |                  ║
     ║                      ╠══════╗           ║
     ╚══════╗---════╦═══════║      ║    5.     ║
╔══════╦════╝       ║       ║      ║           ║
║      ║            |       ║      ║           ║
║  2.  |     1.     ║   4.  ║      ╚═══════════╝
║      ║            ║       ║
║      ║            ║       ║
╚══════╩---═════════╣       ║
                    ║       ║
                    ╚═══════╝
data about floor / encounter place:
room count: 4
1 opens 2,3,4
3 opens 5
*/

import java.io.IOException;
import java.util.List;
import java.util.ArrayList;

import GameClasses.GameClasses;

public class Campaign {
     public static void CampaignSelector() throws IOException, InterruptedException {
          Main.scene = "Campaign selector";
          Main.screen.clear();
          Main.screen.refresh();

          List<String> option1 = Main.formatter.loadArt("ascii-art/witch.txt");
          while (option1.size()<16) {option1.add(" ".repeat(option1.get(0).length()));}
          option1.add("┌"+"-".repeat(option1.get(0).length()-1));
          option1.add("└Northern farms"+" ".repeat(option1.get(0).length()-15));

          List<String> option2 = Main.formatter.loadArt("ascii-art/manhound.txt");
          while (option2.size()<16) {option2.add(" ".repeat(option2.get(0).length()));}
          option2.add("┌"+"-".repeat(option2.get(0).length()-1));
          option2.add("└Eastern farms"+" ".repeat(option2.get(0).length()-14));
          
          List<String> option3 = Main.formatter.loadArt("ascii-art/geopolitan.txt");
          while (option3.size()<16) {option3.add(" ".repeat(option3.get(0).length()));}
          option3.add("┌"+"-".repeat(option3.get(0).length()-1));
          option3.add("└Western farms"+" ".repeat(option3.get(0).length()-14));

          Main.formatter.printMulti(6, Main.formatter.connectAsciiArt(List.of(option1,option2,option3), 12), TextFormatter.PaddingAlignment.CENTER);

          Main.formatter.printMulti(30, Main.formatter.createBox(20, 3, List.of(), TextFormatter.BorderStyle.DOUBLE), TextFormatter.PaddingAlignment.CENTER);
          Main.formatter.printSelectionMultiLine(31, List.of("Northern farms", "Eastern farms", "Western farms", " [Exit]  "), List.of("Northern farms", "Eastern farms", "Western farms", "Main menu"), TextFormatter.PaddingAlignment.CENTER);
     }

     public static List<GameClasses.Entity> generateEncounter(List<GameClasses.Entity> enemyPool, int num) {
          List<GameClasses.Entity> product = new ArrayList<>();
          for (int i = 0; i < num; i++) {
               product.add(enemyPool.get(GameClasses.randInt(0, enemyPool.size())));
          }
          return product;
     }

     public static void playCampaign(List<GameClasses.Entity> enemyPool) throws IOException, InterruptedException {
          GameClasses.Entity User = new GameClasses.Entity("Marcukaa", 5, 10, 1, 2, "ascii-art/jane.txt", List.of(Weapons.TacticalAxe), null);
          while (User.Alive) {
               Combat.battle(User, generateEncounter(enemyPool, GameClasses.randInt(1, 3)));
               Main.formatter.alert(35, List.of("Good job, here comes the next bunch."));
          }
          Main.formatter.alert(35, List.of("Such a shame."));
     }
}