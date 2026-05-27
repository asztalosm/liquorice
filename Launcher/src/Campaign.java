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
          Saves.saveSave(Saves.currentFile, "eastern farms", Main.characterName, Globals.nemesisPercentage, Globals.money, Globals.progress);
          Main.scene = "Campaign selector";
          Main.screen.clear();
          Main.screen.refresh();

          List<String> option1 = Main.formatter.loadArt("ascii-art/witch.txt");
          while (option1.size()<16) {option1.add(0, " ".repeat(option1.get(0).length()));}
          option1.add(" ".repeat(option1.get(0).length()));
          option1.add("┌"+"-".repeat(option1.get(0).length()-1));
          option1.add("└Northern farms"+" ".repeat(option1.get(0).length()-"└Northern farms".length()));

          List<String> option2 = (Globals.progress > 0) ? Main.formatter.loadArt("ascii-art/manhound.txt") : Main.formatter.loadArt("ascii-art/lock.txt");
          while (option2.size()<16) {option2.add(0, " ".repeat(option2.get(0).length()));}
          option2.add(" ".repeat(option2.get(0).length()));
          option2.add("┌"+"-".repeat(option2.get(0).length()-1));
          option2.add("└Eastern farms"+" ".repeat(option2.get(0).length()-"└Eastern farms".length()));
          
          List<String> option3 = (Globals.progress > 1) ? Main.formatter.loadArt("ascii-art/geopolitan.txt") : Main.formatter.loadArt("ascii-art/lock.txt");
          while (option3.size()<16) {option3.add(0, " ".repeat(option3.get(0).length()));}
          option3.add(" ".repeat(option3.get(0).length()));
          option3.add("┌"+"-".repeat(option3.get(0).length()-1));
          option3.add("└Western farms"+" ".repeat(option3.get(0).length()-"└Western farms".length()));

          Main.formatter.printMulti(6, Main.formatter.connectAsciiArt(List.of(option1,option2,option3), 12), TextFormatter.PaddingAlignment.CENTER);

          Main.formatter.printMulti(30, Main.formatter.createBox(1, 1, List.of("Money: $"+Globals.money, "Chance of encountering Nemesis: "+Globals.nemesisPercentage), TextFormatter.BorderStyle.DOUBLE), TextFormatter.PaddingAlignment.CENTER);

          Main.formatter.printMulti(35, Main.formatter.createBox(20, 3, List.of(), TextFormatter.BorderStyle.DOUBLE), TextFormatter.PaddingAlignment.CENTER);
          Main.formatter.printSelectionMultiLine(36, List.of("Northern farms", "Eastern farms", "Western farms", " [Exit]  "), List.of("Northern farms", "Eastern farms", "Western farms", "Main menu"), TextFormatter.PaddingAlignment.CENTER);
     }

     public static List<GameClasses.Entity> generateEncounter(List<GameClasses.Entity> enemyPool, int num) {
          List<GameClasses.Entity> product = new ArrayList<>();
          for (int i = 0; i < num; i++) {
               product.add(new GameClasses.Entity(enemyPool.get(GameClasses.randInt(0, enemyPool.size()-1))));
          }
          return product;
     }

     public static void playCampaign(List<GameClasses.Entity> enemyPool, List<GameClasses.Entity> nemesisEntity, int requiredProgress, int progression) throws IOException, InterruptedException {
          if (Globals.progress<requiredProgress) {
               Main.screen.clear();
               Main.screen.refresh();
               Main.formatter.alert(20, List.of("You haven't unlocked this campaign yet."));
               SceneController.loadScene("Campaign selector");
          }
          
          GameClasses.Entity User = new GameClasses.Entity(Main.characterName, 5, 10, 1, 2, "ascii-art/jane.txt", List.of(
               Weapons.TacticalAxe,
               Weapons.Fists,
               Weapons.pistol
          ), List.of(
               new GameClasses.StatusEffect(Effects.ammo, 3, true)
          ), null);
          
          int money = 0;
          int localNemesisPercentage = 0;
          boolean hasDefeatedNemesis = false;
          boolean fled = false;
          while (User.Alive && !fled && !hasDefeatedNemesis) {
               int enemyNum = GameClasses.randInt(1, 2);
               if (GameClasses.randInt(0, 100)>localNemesisPercentage) {
                    Combat.battle(User, generateEncounter(enemyPool, enemyNum));
               } else {
                    Combat.battle(User, nemesisEntity);
                    if (User.Alive) {
                         hasDefeatedNemesis = true;
                    }
               }
               if (User.Alive) {
                    Main.screen.clear();
                    Main.screen.refresh();
                    money += enemyNum*35;
                    localNemesisPercentage += enemyNum*2;
                    Globals.nemesisPercentage += GameClasses.randInt(0, 1);
                    Main.formatter.printMulti(20, Main.formatter.createBox(1, 1, List.of(
                         enemyNum+" x "+"35 = $"+enemyNum*35+" earned.",
                         "Chance of encountering the leader: "+localNemesisPercentage,
                         "Chance of encountering Nemesis: "+Globals.nemesisPercentage,
                         "",
                         "Do you wish to continue?"
                    ), TextFormatter.BorderStyle.SINGLE), TextFormatter.PaddingAlignment.CENTER);
                    switch (Main.formatter.printSelectionInBox(30, List.of("Yes", "No"), List.of("y", "n"), TextFormatter.PaddingAlignment.CENTER, TextFormatter.BorderStyle.ROUNDED)) {
                         case "y" -> {
                              Main.formatter.printMulti(30, Main.formatter.createBox(30, 50, List.of(""), TextFormatter.BorderStyle.NOTHING), TextFormatter.PaddingAlignment.CENTER);
                              Main.formatter.alert(30, List.of("Good job, here comes the next bunch."));
                         }
                         case "n" -> {
                              Main.formatter.alert(30, List.of("You decide not to risk it any further."));
                              fled = true;
                         }
                    }
               }
          }
          Main.screen.clear();
          Main.screen.refresh();
          if (!User.Alive) {
               Main.formatter.alert(20, List.of("Such a shame."));
               SceneController.loadScene("Campaign selector");
          }
          else {
               if (hasDefeatedNemesis) {
                    Main.formatter.alert(20, List.of(
                         "Mission complete.",
                         "",
                         "Money earned: "+money
                    ));
                    Globals.money = money;
                    Globals.progress = Math.max(progression, Globals.progress);
                    SceneController.loadScene("Campaign selector");
               } else {
                    Main.formatter.alert(20, List.of(
                         "Good job, but that's not all you've got "+User.Name+".",
                         "",
                         "Money earned: "+money
                    ));
                    Globals.money = money;
                    SceneController.loadScene("Campaign selector");
               }
          }
     }
}