package GameClasses;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

public class GameClasses {

    public class UnitIdGenerator {
        private static int nextId = 0;

        public static int generateId() {
            return nextId++;
        }

        public static void resetId() {
            nextId = 0;
        }
    }

    public static int randInt(int min, int max) {
        return min + (int)(Math.random() * ((max - min) + 1));
    }

    public static class StatusEffect {
        public String name;
        public int count;
        public int duration;
        public boolean infinite;
        public Consumer<Entity> effectScript;
        public int callingOrder;
        public List<String> effectDesc;

        public StatusEffect(String name, Consumer<Entity> effectScript) {
            this(name, effectScript, 3, new ArrayList<>());
        }

        public StatusEffect(String name, Consumer<Entity> effectScript, int callingOrder, List<String> effectDesc) {
            this.name = name;
            this.count = 0;
            this.duration = 0;
            this.infinite = false;
            this.effectScript = effectScript;
            this.callingOrder = callingOrder;
            this.effectDesc = effectDesc;
        }

        @Override
        public String toString() {
            return name + " " + count + " " + duration + " " + infinite;
        }

        public void affect(Entity entity) {
            effectScript.accept(entity);
        }
    }

    public static class Attack {
        public String Name;
        public BiConsumer<Entity, Entity> AttackScript;
        public boolean IsMassAttack;
        public int[] DamageRange;
        public int Chances;
        public int Cost;
    
        public Attack(String name, BiConsumer<Entity, Entity> attackScript, boolean isMassAttack, int[] dmg, int chances, int cost) {
            this.Name = name;
            this.AttackScript = attackScript;
            this.IsMassAttack = isMassAttack;
            this.DamageRange = dmg;
            this.Chances = chances;
            this.Cost = cost;
        }

        public int roll(int modifier) {
            int min = DamageRange[0];
            int max = DamageRange[1];
            return randInt(min, max)+modifier;
        }

        public void affect(Entity attacker, Entity target) {
            AttackScript.accept(attacker, target);
        }
    }
    
    public static class Passive {
        public String Name;
        public List<String> Desc;
        public String EffectScript;
        public int CallingOrder;
        public int Clock;

        public Passive(String name, List<String> desc, String effectScript, int callingOrder) {
            this.Name = name;
            this.Desc = desc;
            this.EffectScript = effectScript;
            this.CallingOrder = callingOrder;
            this.Clock = 0;
        }

        public void trigger(Entity affectedEntity, Map<String, Object> locals) {
            throw new UnsupportedOperationException("Passive scripts not implemented in Java port.");
        }
    }
    
    public static class Equipment {
        public String Name;
        public Attack[] CombatActions;

        public Equipment(String name, Attack... atk) {
            this.Name = name;
            this.CombatActions = atk;
        }
    }
    
    // public static class Pattern {
    //     public List<Integer> cards;
    //     public String condition;

    //     public Pattern(List<Integer> cards, String condition) {
    //         this.cards = cards;
    //         this.condition = condition;
    //     }
    // }
    
    public static class Entity {
        private int id;
        public boolean Alive = true;
        public int MaxHealth;
        public int Health;
        public int MaxStamina; // Regenerating buffer HP, gets reduced upon performing an offensive action
        public int Stamina;
        public int Endurance; // Damage reduction
        public int Speed; // Innitiative order
        public String Name;
        public List<StatusEffect> Effects;
        public List<Passive> Passives;
        public Attack PlannedAttack;
        public String SkinPath;
        public Equipment EquippedWeapon;
        public List<Equipment> Gear;
        public int Clock; // turn count
        private double NextActionTime;
        public String[] Description;

        public Entity(String name, int maxHealth, int maxStamina, int endurance, int speed, String skinPath, List<Equipment> gear, String[] description) {
            this.MaxHealth = maxHealth;
            this.Health = MaxHealth;
            this.MaxStamina = maxStamina;
            this.Stamina = MaxStamina;
            this.Endurance = endurance;
            this.Speed = speed;
            this.Name = name;
            this.SkinPath = skinPath;
            this.Gear = gear;
            this.EquippedWeapon = gear.get(0);
            this.PlannedAttack = plan();
            this.Description = description;
            this.Effects = new ArrayList<>();
            this.Passives = new ArrayList<>();
            this.Clock = 0;
            this.Alive = true;
            this.NextActionTime = 0d;
            this.id = UnitIdGenerator.generateId();
        }
        
        public Entity(Entity other) {
            this.MaxHealth = other.MaxHealth;
            this.Health = other.MaxHealth;
            this.MaxStamina = other.MaxStamina;
            this.Stamina = other.MaxStamina;
            this.Endurance = other.Endurance;
            this.Speed = other.Speed;
            this.Name = other.Name;
            this.SkinPath = other.SkinPath;
            this.EquippedWeapon = other.EquippedWeapon;
            this.Description = other.Description;
            this.Effects = new ArrayList<>();
            this.Passives = new ArrayList<>();
            this.Clock = other.Clock;
            this.Alive = other.Alive;
            this.NextActionTime = other.NextActionTime;
            this.id = other.id;
            this.PlannedAttack = other.PlannedAttack;
            // this.id = UnitIdGenerator.generateId();
        }
        
        public double getNextActionTime() {
            return this.NextActionTime;
        }

        public int getID() {
            return this.id;
        }

        public int rollBlockDie() {
            return randInt(2, 6);
        }

        public Attack plan() {
            return this.EquippedWeapon.CombatActions[randInt(0, this.EquippedWeapon.CombatActions.length-1)];
        }

        public double scheduleNextTurn() {
            return this.NextActionTime += 100.0/(this.Speed + this.getEffect("Haste").count);
        }

        public StatusEffect getEffect(String EffectName) {
            for (StatusEffect e : Effects) {
                if (EffectName.toLowerCase().equals(e.name.toLowerCase())) {return e;}
            }
            return new StatusEffect("-", (a)->{});
            // return new StatusEffect("-", (a) -> {});
        }

        public int getStrength() {
            return getEffect("Strength").count - getEffect("Weakness").count;
        }

        public List<String> getEffectDisplay() {
            List<String> product = new ArrayList<>();
            for (StatusEffect effect : this.Effects) {
                product.add(effect.name+" | "+effect.count+"/"+effect.duration);
            }
            return product;
        }

        public String damageSelf(int dmg) {
            int blockBuffer = this.getEffect("Block").count;
            int finalDamage = (dmg - this.Endurance - this.getEffect("Sturdyness").count + this.getEffect("Vulnerability").count);
            this.getEffect("Block").count = Math.max(0, this.getEffect("Block").count-finalDamage);
            finalDamage -= blockBuffer;
            if (finalDamage>0) {
                if (this.Stamina>0) {
                    int staminaBuffer = this.Stamina;
                    this.Stamina = Math.max(this.Stamina-finalDamage, 0);
                    if (this.Stamina>0) {return this.Name+" is getting tired. Stamina reduced by "+finalDamage;}
                    finalDamage -= staminaBuffer;
                } 
            }
            if (finalDamage>0) {
                this.Health = Math.max(this.Health-finalDamage, 0);
                return this.Name+" is vulnerable. Health reduced by "+finalDamage;
            }
            return "Try harder next time.";
        }

        public void consumeStamina(int amt) {
            int finalCost = amt;
            if (finalCost>0) {
                if (this.Stamina>0) {this.Stamina -= finalCost;}
                else {this.Stamina = 0;}
            }
        }

        public void regainStamina() {
            int staminaRegen = 1 + this.getEffect("Relentlessness").count - this.getEffect("Exhaustion").count;
            this.Stamina = Math.min(this.Stamina + staminaRegen, this.MaxStamina);
        }
        
        public String applyEffect(StatusEffect effect, int count, int duration, boolean infinite) {
            StatusEffect thisEffect = this.getEffect(effect.name);
            if (thisEffect.name.equals("-")) {
                this.Effects.add(effect);
                effect.count = count;
                effect.duration = duration;
                if (infinite) {
                    effect.infinite = true;
                }
            }
            else {
                thisEffect.count = Math.max(count, thisEffect.count);
                thisEffect.duration = Math.max(duration, thisEffect.duration);
                thisEffect.infinite = infinite;
            }
            System.out.println(thisEffect.name);
            return effect.count+" "+effect.name+" was applied on "+this.Name+"!";
        }

        public List<String> getSkin() {
            List<String> product = new ArrayList<>();
            try (BufferedReader br = new BufferedReader(new FileReader(this.SkinPath))) {
                String line;
                while ((line = br.readLine()) != null) {
                    product.add(line);
                }
            } catch (IOException e) {
                System.out.println("Error reading file: " + this.SkinPath);
            }
            return product;
        }
    }
}