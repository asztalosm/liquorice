import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.PriorityQueue;

import GameClasses.GameClasses.Entity;

public class Combat {

    interface CombatHandler {
        void visualise(Entity currEntity) throws IOException;
        boolean isCleared(List<Entity> li) throws IOException;
    }

    public static void battle(Entity User, List<Entity> Enemies) throws IOException, InterruptedException {
        Main.scene = "Combat";
        List<Entity> tempEnemies = new ArrayList<>(Enemies);
        PriorityQueue<Entity> order = new PriorityQueue<>(java.util.Comparator
            .comparingDouble((Entity e) -> e.getNextActionTime())
            .thenComparingInt(e -> e.getID())
        );
        for (Entity entity : tempEnemies) {
            order.add(entity);
        }
        order.add(User);

        CombatHandler combatHandler = new CombatHandler() {
            @Override
            public void visualise(Entity currEntity) throws IOException {
                Main.screen.clear();
                Main.screen.refresh();

                List<List<String>> enemyArt = new ArrayList<>();
                List<String> enemyDisplay = Main.formatter.loadArt("ascii-art/jane.txt");
                for (Entity enemy : tempEnemies) {
                    enemyDisplay = new ArrayList<>();

                    enemyDisplay.addAll(enemy.getSkin());
                    enemyDisplay.add("");
                    enemyDisplay.add(String.format("%s%s", enemy.Name, " ".repeat(enemy.getSkin().get(0).length()-enemy.Name.length())));
                    if (enemy == currEntity) {
                        enemyDisplay.add(String.format("Health:  %d | %d", enemy.MaxHealth, enemy.Health));
                        enemyDisplay.add(String.format("Stamina: %d | %d", enemy.MaxStamina, enemy.Stamina));
                    }
                    
                    enemyArt.add(Main.formatter.createBox(1, 1, enemyDisplay, TextFormatter.BorderStyle.DOUBLE));
                }
                Main.formatter.printMulti(6, Main.formatter.connectAsciiArt(enemyArt), TextFormatter.PaddingAlignment.CENTER);
                
                //region Forecast thingamajig
                int simulatedTurns = 10;
                StringBuilder initiativeString = new StringBuilder("["+currEntity.Name+"] | "+"NEXT UP: ");
                PriorityQueue<Entity> tempQueue = new PriorityQueue<>(java.util.Comparator
                    .comparingDouble((Entity e) -> e.getNextActionTime())
                    .thenComparingInt(e -> e.getID())
                );
                
                for (Entity e : order) {tempQueue.add(new Entity(e));}

                for (int i = 0; i < simulatedTurns; i++) {
                    Entity ce = tempQueue.poll();
                    if (ce == null) {
                        break;
                    }
                    ce.scheduleNextTurn();
                    tempQueue.add(ce);
                    initiativeString.append(ce.Name).append(" -> ");
                }
                Main.formatter.printSingle(24, initiativeString.toString(), TextFormatter.PaddingAlignment.CENTER);
                System.out.println(initiativeString.toString());
                //endregion
            }

            public boolean isCleared(List<Entity> li) throws IOException {
                for (Entity enemy : li) {
                    if (enemy.Alive == true) {
                        return true;
                    }
                }
                return false;
            }
        };

        while (User.Alive && combatHandler.isCleared(tempEnemies)) { 

            // tempOrder.clear();
            // for (Entity entity : order) {
            //     tempOrder.add(entity);
            // }

            Entity current = order.poll();
            
            current.scheduleNextTurn();
            order.add(current);
            
            combatHandler.visualise(current);
            Main.formatter.promptTextInput(26, TextFormatter.PaddingAlignment.CENTER, "Next [ENTER]");
        }

    }
}
