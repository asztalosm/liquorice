package GameClasses;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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

    public static class StatusEffect {
        public String name;
        public int count;
        public boolean infinite;
        public String effectScript;
        public int callingOrder;
        public List<String> effectDesc;

        public StatusEffect(String name, int count, boolean infinite, String effectScript) {
            this(name, count, infinite, effectScript, 3, new ArrayList<>());
        }

        public StatusEffect(String name, int count, boolean infinite, String effectScript, int callingOrder, List<String> effectDesc) {
            this.name = name;
            this.count = count;
            this.infinite = infinite;
            this.effectScript = effectScript;
            this.callingOrder = callingOrder;
            this.effectDesc = effectDesc;
        }

        @Override
        public String toString() {
            return name + " " + count + " " + infinite;
        }

        public void trigger(Entity affectedEntity, Map<String, Object> locals) {
            throw new UnsupportedOperationException("Még nincsen cucmó");
        }
    }

    public static class Attack {
        public String Name;
        public String AttackScript;
        public boolean IsMassAttack;
        public int[] DamageRange;
        public int Cost;
    
        public Attack(String name, String attackScript, boolean isMassAttack, int[] dmg, int cost) {
            this.Name = name;
            this.AttackScript = attackScript;
            this.IsMassAttack = isMassAttack;
            this.DamageRange = dmg;
            this.Cost = cost;
        }

        public Attack() {
            this("-", "", false, new int[]{0, 1}, 1);
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

        public Equipment(String name) {
            this.Name = name;
        }
    }
    
    public static class Pattern {
        public List<Integer> cards;
        public String condition;

        public Pattern(List<Integer> cards, String condition) {
            this.cards = cards;
            this.condition = condition;
        }
    }
    
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
        public int Clock; // turn count
        private double NextActionTime;
        public String[] Description;

        public Entity(String name, int maxHealth, int maxStamina, int endurance, int speed, String skinPath, Equipment equippedWeapon, String[] description) {
            this.MaxHealth = maxHealth;
            this.Health = MaxHealth;
            this.MaxStamina = maxStamina;
            this.Stamina = MaxStamina;
            this.Endurance = endurance;
            this.Speed = speed;
            this.Name = name;
            this.SkinPath = skinPath;
            this.EquippedWeapon = equippedWeapon;
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
            // this.id = UnitIdGenerator.generateId();
        }
        
        public double getNextActionTime() {
            return this.NextActionTime;
        }

        public int getID() {
            return this.id;
        }

        public double scheduleNextTurn() {
            // return this.NextActionTime += 100.0/this.Speed + (Math.random()*0.1)+0.01;
            return this.NextActionTime += 100.0/this.Speed;
        }

        public StatusEffect getEffect(String EffectName) {
            for (StatusEffect e : Effects) {
                if (EffectName.toLowerCase().equals(e.name)) {return e;}
            }
            return new StatusEffect("-", 0, false, "");
        }

        public int damageSelf(int dmg) {
            int finalDamage = (dmg - this.Endurance);
            if (finalDamage>0) {
                this.Health -= finalDamage;
                return finalDamage;
            }
            return 0;
        }
        
        public void applyEffect(StatusEffect effect, int count, boolean infinite) {
            if (infinite) {
                effect.infinite = true;
            }
            this.Effects.add(effect);
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
