import java.io.IOException;
import java.util.ArrayList;
import java.util.Currency;
import java.util.List;
import java.util.PriorityQueue;

import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.TextColor;

import GameClasses.Effects;
import GameClasses.GameClasses;
import GameClasses.GameClasses.Entity;

public class Combat {

    public static int startRow = 6;
    public static int enemyRowMod = 18;
    // public static int (getInitiativeRow() = 24;
    // public static int (getMenuRow() = 28;
    // public static int (getSubMenuRow() = 36;

    public static int getInitiativeRow() {
        return startRow+enemyRowMod;
    }

    public static int getMenuRow() {
        return getInitiativeRow()+4;
    }

    public static int getSubMenuRow() {
        return getMenuRow()+8;
    }
    
    interface CombatHandler {
        void visualise(Entity currEntity) throws IOException, InterruptedException;
        GameClasses.Equipment chooseEquipment(Entity currEntity) throws IOException, InterruptedException;
        void clearEnemyPanel() throws IOException, InterruptedException;
        boolean clearCheck(List<Entity> li) throws IOException, InterruptedException;
        void printParticipants(Entity currEntity, boolean doTarget) throws IOException, InterruptedException;
        GameClasses.Attack chooseAttack(Entity currEntity) throws IOException, InterruptedException;
        String chooseAction(Entity currEntity) throws IOException, InterruptedException;
        void clearAlertPanel() throws IOException, InterruptedException;
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
            //region Combat handler
            @Override
            public void clearAlertPanel() throws IOException, InterruptedException {
                List<String> wiper = new ArrayList<>();
                for (int i = 0; i < 10; i++) {
                    wiper.add(" ".repeat(Main.formatter.getCols()));
                }
                Main.formatter.printMulti(getSubMenuRow(), wiper, TextFormatter.PaddingAlignment.LEFT);
            }

            @Override
            public void clearEnemyPanel() throws IOException, InterruptedException {
                List<String> wiper = new ArrayList<>();
                for (int i = 0; i < getInitiativeRow()-1; i++) {
                    wiper.add(" ".repeat(Main.formatter.getCols()));
                }
                Main.formatter.printMulti(0, wiper, TextFormatter.PaddingAlignment.LEFT);
            }

            @Override
            public void printParticipants(Entity currEntity, boolean doTarget) throws IOException, InterruptedException {
                clearEnemyPanel();
                List<List<String>> enemyArt = new ArrayList<>();
                List<List<String>> enemyArtBuffer = new ArrayList<>();
                List<String> enemyDisplay;
                for (Entity enemy : tempEnemies) {
                    enemyDisplay = new ArrayList<>();
                            
                    enemyDisplay.addAll(enemy.getSkin());
                    enemyDisplay.add("");
                    enemyDisplay.add("");
                    enemyDisplay.add(String.format("%s%s", enemy.Name, " ".repeat(Math.max(0, enemy.getSkin().get(0).length()-enemy.Name.length()))));
                    if (enemy == currEntity) {
                        if (doTarget) {
                            enemyDisplay.add("├─ Planning:");
                            enemyDisplay.add(String.format("│  %s", enemy.PlannedAttack.Name));
                        }
                        enemyDisplay.add(String.format("├─ Health:  %d | %d", enemy.MaxHealth, enemy.Health));
                        enemyDisplay.add(String.format("└─ Stamina: %d | %d", enemy.MaxStamina, enemy.Stamina));
                    }
                    enemyArt.add(Main.formatter.createBox(1, 1, enemyDisplay, TextFormatter.BorderStyle.NOTHING));
                    
                    if (doTarget && enemy == currEntity) {
                        enemyArtBuffer.add(Main.formatter.createBox(enemyDisplay.get(0).length(), enemyDisplay.size(), Main.formatter.loadArt("ascii-art/target.txt"), TextFormatter.BorderStyle.NOTHING));
                    } else {
                        enemyArtBuffer.add(Main.formatter.createBox(enemyDisplay.get(0).length(), enemyDisplay.size(), List.of(""), TextFormatter.BorderStyle.NOTHING));
                    }
                }

                Main.formatter.printMulti(startRow, Main.formatter.connectAsciiArt(enemyArt), TextFormatter.PaddingAlignment.CENTER);
                if (doTarget) {Main.formatter.printMultiIgnore(startRow, Main.formatter.connectAsciiArt(enemyArtBuffer), TextFormatter.PaddingAlignment.CENTER, TextColor.ANSI.RED_BRIGHT);}
            }

            public void printParticipants(Entity currEntity) throws IOException, InterruptedException {
                printParticipants(currEntity, false);
            }

            @Override
            public GameClasses.Attack chooseAttack(Entity currEntity) throws IOException, InterruptedException {
                List<String> options = new ArrayList<>();
                List<GameClasses.Attack> returnOptions = new ArrayList<>();
                GameClasses.Attack choice;
                
                options.add(Weapons.universalActions.CombatActions[0].Name);
                returnOptions.add(Weapons.universalActions.CombatActions[0]);
                options.add(Weapons.universalActions.CombatActions[1].Name);
                returnOptions.add(Weapons.universalActions.CombatActions[1]);
                for (GameClasses.Attack atk : currEntity.EquippedWeapon.CombatActions) {
                    options.add("["+atk.Cost+"] "+atk.Name+" ("+atk.DamageRange[0]+"~"+atk.DamageRange[1]+") x "+atk.Chances);
                    returnOptions.add(atk);
                }
                Main.formatter.printMulti(getMenuRow()-1, Main.formatter.createBox(TextFormatter.getLongestString(options).length()+2, 6, List.of(""), TextFormatter.BorderStyle.NOTHING), TextFormatter.PaddingAlignment.LC);
                do {
                    choice = Main.formatter.printAttackSelection(getMenuRow(), options, returnOptions, TextFormatter.PaddingAlignment.LC);
                    if (choice.Cost>currEntity.Stamina) {Main.formatter.alert(getSubMenuRow(), List.of("Not enough stamina."));}
                } while (choice.Cost>currEntity.Stamina);
                Main.formatter.printMulti(getMenuRow()-1, Main.formatter.createBox(TextFormatter.getLongestString(options).length()+2, 6, List.of(""), TextFormatter.BorderStyle.NOTHING), TextFormatter.PaddingAlignment.LC);
                currEntity.Stamina -= choice.Cost;
                return choice;
            }

            @Override
            public String chooseAction(Entity currEntity) throws IOException, InterruptedException {
                List<String> options = List.of("Exit", "Block [2~4]", "Respire [2]");
                List<String> returnOptions = List.of("exit", "block", "respire");
                String choice;

                Main.formatter.printMulti(getMenuRow()-1, Main.formatter.createBox(TextFormatter.getLongestString(options).length()+2, 6, List.of(""), TextFormatter.BorderStyle.NOTHING), TextFormatter.PaddingAlignment.LC);
                choice = Main.formatter.printSelectionInBox(getMenuRow(), options, returnOptions, TextFormatter.PaddingAlignment.LC, TextFormatter.BorderStyle.ROUNDED);
                return choice;
            }

            @Override
            public GameClasses.Equipment chooseEquipment(Entity currEntity) throws IOException, InterruptedException {
                List<String> options = new ArrayList<>();
                List<GameClasses.Equipment> returnOptions = new ArrayList<>();
                GameClasses.Equipment choice;

                for (GameClasses.Equipment weapon : currEntity.Gear) {
                    options.add(weapon.Name);
                    returnOptions.add(weapon);
                }

                Main.formatter.printMulti(getMenuRow()-1, Main.formatter.createBox(TextFormatter.getLongestString(options).length()+2, 6, List.of(""), TextFormatter.BorderStyle.NOTHING), TextFormatter.PaddingAlignment.LC);
                choice = Main.formatter.printEquipmentSelection(getMenuRow(), options, returnOptions, TextFormatter.PaddingAlignment.LC);
                return choice;
            }

            @Override
            public void visualise(Entity currEntity) throws IOException, InterruptedException {
                Main.screen.clear();
                Main.screen.refresh();

                for (Entity entity : tempEnemies) {
                    if (entity.Health<=0) {
                        entity.Alive = false;
                        entity.SkinPath = "ascii-art/death.txt";
                        order.remove(entity);
                    }
                }

                List<List<String>> enemySkins = new ArrayList<>();
                for (Entity entity : tempEnemies) {
                    enemySkins.add(entity.getSkin());
                }
                int enemySpace = TextFormatter.getGreaterList(enemySkins).size()+10;
                System.out.println(enemySpace);
                System.out.println(startRow);
                if (enemySpace+startRow>24) {
                    enemyRowMod = enemySpace;
                } else {
                    enemyRowMod = 18;
                }
                
                printParticipants(currEntity);
                int simulatedTurns = 6;
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
                Main.formatter.printMulti(getInitiativeRow(), List.of(initiativeString.toString()), TextFormatter.PaddingAlignment.CENTER, TextFormatter.BorderStyle.ROUNDED, 0);
                // System.out.println(initiativeString.toString());
                
                // TextFormatter.PaddingAlignment playerGuiAlignment = (User != currEntity) ? TextFormatter.PaddingAlignment.CENTER : TextFormatter.PaddingAlignment.RC;
                TextFormatter.PaddingAlignment playerGuiAlignment = TextFormatter.PaddingAlignment.CENTER;
                List<List<String>> playerGui = new ArrayList<>();
                List<String> effectString = new ArrayList<>(List.of("Effects:"));
                effectString.addAll(User.getEffectDisplay());
                playerGui.add(Main.formatter.createBox(1, 4, List.of(
                    "HP:        "+User.Health+" | "+User.MaxHealth,
                    "Stamina:   "+User.Stamina+" | "+User.MaxStamina,
                    "Endurance: "+User.Endurance,
                    "Speed:     "+User.Speed
                ), TextFormatter.BorderStyle.ROUNDED));
                playerGui.add(Main.formatter.createBox(1, 4, effectString, TextFormatter.BorderStyle.ROUNDED));
                
                Main.formatter.printMulti(getMenuRow()-1, Main.formatter.connectAsciiArt(playerGui), playerGuiAlignment);
            }

            @Override
            public boolean clearCheck(List<Entity> li) throws IOException {
                for (Entity enemy : li) {
                    if (enemy.Alive) {
                        return true;
                    }
                }
                return false;
            }
        };

        //region Combat logic
        while (User.Alive && combatHandler.clearCheck(tempEnemies)) { 

            Entity current = order.poll();
            
            current.scheduleNextTurn();
            order.add(current);
            
            combatHandler.visualise(current);
            
            boolean acting = current.Name.equals(User.Name);
            
            if (!acting) {
                Entity target = User;
                current.consumeStamina(current.PlannedAttack.Cost);
                Main.formatter.printSingle(getSubMenuRow(), current.Name+" intends to use "+current.PlannedAttack.Name+" ("+current.PlannedAttack.Chances+" rolls)", TextFormatter.PaddingAlignment.CENTER);
                Main.formatter.promptTextInput(getSubMenuRow()+1, TextFormatter.PaddingAlignment.CENTER, "Proceed [ENTER]");
                combatHandler.clearAlertPanel();
                int roll = current.PlannedAttack.roll(current.getStrength());
                for (int i = 0; i < current.PlannedAttack.Chances; i++) {
                    Main.formatter.printSingle(getSubMenuRow()+i, target.damageSelf(roll), TextFormatter.PaddingAlignment.CENTER);
                    Main.formatter.promptTextInput(getSubMenuRow()+1+i, TextFormatter.PaddingAlignment.CENTER, "Understood [ENTER]");                    
                }
                combatHandler.clearAlertPanel();
                current.PlannedAttack.affect(current, target);
            }
            
            while (acting) {
                String action = Main.formatter.printSelectionInBox(getMenuRow(), List.of("Attack", "Action", "Equipment", "Inspect"), List.of("attack", "action", "equipment", "inspect"), TextFormatter.PaddingAlignment.LC, TextFormatter.BorderStyle.ROUNDED);
                switch (action) {
                    case "attack" -> {
                        combatHandler.visualise(current);
                        GameClasses.Attack cAttack = combatHandler.chooseAttack(current);
                        boolean targetting = !cAttack.Name.equals("Exit");
                        Entity target = tempEnemies.get(0);
                        while (targetting) {
                            Main.formatter.printSingle(getSubMenuRow(), "Press backspace to cancel", TextFormatter.PaddingAlignment.CENTER);
                            combatHandler.printParticipants(target, true);
                            KeyStroke key = Main.screen.readInput();
                            if (key == null) continue;
                            switch (key.getKeyType()) {
                                case ArrowRight -> {
                                    if (tempEnemies.indexOf(target)<tempEnemies.size()-1) {target = tempEnemies.get(tempEnemies.indexOf(target)+1);}
                                }
                                case ArrowLeft -> {
                                    if (tempEnemies.indexOf(target)>0) {target = tempEnemies.get(tempEnemies.indexOf(target)-1);}
                                }
                                case Enter -> {
                                    combatHandler.clearAlertPanel();
                                    User.consumeStamina(cAttack.Cost);
                                    Main.formatter.promptTextInput(getSubMenuRow(), TextFormatter.PaddingAlignment.CENTER, "Roll for damage [ENTER]");
                                    int roll = cAttack.roll(current.getStrength());
                                    Main.formatter.printSingle(getSubMenuRow(), "Rolled: "+roll, TextFormatter.PaddingAlignment.CENTER);
                                    Main.formatter.printSingle(getSubMenuRow()+1, target.damageSelf(roll), TextFormatter.PaddingAlignment.CENTER);
                                    Main.formatter.promptTextInput(getSubMenuRow()+2, TextFormatter.PaddingAlignment.CENTER, "Understood [ENTER]");
                                    combatHandler.clearAlertPanel();
                                    cAttack.affect(User, target);
                                    acting = false;
                                    targetting = false;
                                }
                                case Backspace -> {
                                    combatHandler.clearAlertPanel();
                                    targetting = false;
                                }
                            }
                            Main.screen.refresh();
                        }
                    }
                    case "action" -> {
                        String cAction = combatHandler.chooseAction(current);
                        switch (cAction) {
                            case "block" -> {
                                int roll = User.rollBlockDie();
                                User.applyEffect(Effects.block, roll, 2, false);
                                Main.formatter.printSingle(getSubMenuRow(), User.Name+" gained "+roll+" block.", TextFormatter.PaddingAlignment.CENTER);
                                Main.formatter.promptTextInput(getSubMenuRow()+1, TextFormatter.PaddingAlignment.CENTER, "Neat. [ENTER]");
                            }
                            case "respire" -> {
                                User.Stamina = Math.min(User.MaxStamina, User.Stamina += 2);
                                Main.formatter.printSingle(getSubMenuRow(), User.Name+" replenished 2 stamina.", TextFormatter.PaddingAlignment.CENTER);
                                Main.formatter.promptTextInput(getSubMenuRow()+1, TextFormatter.PaddingAlignment.CENTER, "Neat. [ENTER]");
                            }
                            case "exit" -> {}
                        }
                        acting = false;
                    }
                    case "equipment" -> {
                        User.EquippedWeapon = combatHandler.chooseEquipment(User);
                        acting = false;
                    }
                    case "inspect" -> {
                        Main.formatter.printSingle(getSubMenuRow(), "Kukucs", TextFormatter.PaddingAlignment.CENTER);
                        acting = false;
                    }
                }
                combatHandler.printParticipants(current, false);
                Main.screen.refresh();
            }

            current.regainStamina();
            
            combatHandler.visualise(current);
            Main.formatter.promptTextInput(getSubMenuRow(), TextFormatter.PaddingAlignment.CENTER, "Next [ENTER]");

            for (int i = 0; i < current.Effects.size(); i++) {
                current.Effects.get(i).affect(current);
                // System.out.println(e.Effects.get(i));
                if (!current.Effects.get(i).infinite) {current.Effects.get(i).duration -= 1;}
                // System.out.println(e.Effects.get(i));
                if (current.Effects.get(i).duration<=0 || current.Effects.get(i).count<=0) {current.Effects.remove(i); i++;}
            }
            // System.out.println("---");

            if (current != User) {current.PlannedAttack = current.plan();}
        }
    }
}
