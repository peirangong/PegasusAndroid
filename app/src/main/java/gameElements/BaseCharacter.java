package gameElements;

import android.util.Log;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import gameElements.Weapon.SpecialWeapon;

public class BaseCharacter {

	// TODO: documentation of variables
	private static final String TAG = "PegasusGame";
	private static final boolean DEBUG = true;

	protected int totalArmor;
	protected int armorWorn;
	protected int level;
	protected String name;
	protected int energy;
	// protected boolean defend;
	protected boolean weaponReady;
	protected int attackPwr;
	protected int defendPwr;

	protected List<Skill> skills;
	protected LinkedList<Weapon> weapons;

	public String fightLog;

	public static final int NOVICE = 1;
	public static final int MEDIUM = 2;
	public static final int EXPERT = 3;

	public BaseCharacter() {
		totalArmor = 0;
		armorWorn = 0;
		level = NOVICE;
		name = "Default Character";
		energy = 0;
		weaponReady = false;
		attackPwr = 0;
		defendPwr = 0;

		skills = new ArrayList<Skill>();
		weapons = new LinkedList<Weapon>();
		fightLog = "";
	}

	public String getStatus() {
		String s = name + " - Armors Worn: " + armorWorn + ", Armors Left: "
				+ totalArmor + ", Energy: " + energy;
		return s;
	}

	public boolean isAlive() {
		return (armorWorn >= 0);
	}

	public String toString() {
		return name + " Armor: " + totalArmor + " Level: " + level;
	}

	public Move gather() {

		attackPwr = 0;
		defendPwr = 0;
		energy = energy + 2;
		Move move = new Move(this, Move.GATHER, attackPwr, defendPwr);
		if (DEBUG) {
			Log.d(TAG, name + " gathers!");
			fightLog += name + " gathers!\n";
			move.moveLog = name + " gathers!\n";
		}

		return move;
	}

	public Move defense() {
		attackPwr = 0;
		defendPwr = 10;
		Move move = new Move(this, Move.DEFENSE, attackPwr, defendPwr);
		if (DEBUG) {
			Log.d(TAG, name + " defends!");
			fightLog += name + " defends!\n";
			move.moveLog = name + " defends!\n";
		}

		return move;
	}

	public Move wearArmor() {
		Move move;
		attackPwr = 0;
		defendPwr = 10;

		if (totalArmor > 0) {
			if (!weaponReady) {
				weaponReady = true;
			}
			armorWorn++;
			totalArmor--;
			move = new Move(this, Move.ARMOR, attackPwr, defendPwr);

			if (DEBUG) {
				Log.d(TAG, name + "  wears one piece of armor!");
				fightLog += name + "  wears one piece of armor!\n";
				move.moveLog = name + "  wears one piece of armor!\n";
			}

		} else {
			move = new Move(this, Move.ARMOR, attackPwr, defendPwr);

			if (DEBUG) {
				Log.d(TAG, "No more armor! " + name + " defends!");
				fightLog += "No more armor! " + name + " defends!\n";
				move.moveLog = "No more armor! " + name + " defends!\n";
			}
		}

		return move;
	}

	public Skill attack(Skill s) {
		Skill move;

		if (energy < s.getCost()) {
			attackPwr = 0;
			defendPwr = 10;
			move = new Skill(this, Move.DEFENSE, attackPwr, defendPwr, 0);
			Log.d(TAG, "Not enough energy!");
			fightLog += "Not enough energy!\n";
			move.moveLog = "Not enough energy!\n";
		} else {
			energy = energy - s.getCost();
			attackPwr = s.getAtt();
			defendPwr = s.getDef();
			move = new Skill(this, s.getName(), attackPwr, defendPwr,
					s.getCost());
			if (DEBUG) {
				Log.d(TAG, "Attack! " + s.getName());
				fightLog += "Attack! " + s.getName() + "\n";
				move.moveLog = "Attack! " + s.getName() + "\n";
			}
		}

		return move;

	}

	public Weapon attack(LinkedList<Weapon> weaponList, int loc) {
		Weapon w = weaponList.get(loc);
		Weapon move;
		if (!weaponReady) {
			attackPwr = 0;
			defendPwr = 10;
			move = new Weapon(this, Move.DEFENSE, attackPwr, defendPwr,
					SpecialWeapon.Regular);
			Log.d(TAG, "Weapon is not ready!");
			fightLog += "Weapon is not ready!\n";
			move.moveLog = "Weapon is not ready!\n";
		} else {
			attackPwr = w.getAtt();
			defendPwr = w.getDef();
			weaponList.remove(loc);
			move = new Weapon(this, w.getName(), attackPwr, defendPwr,
					w.getSpecial());
			if (DEBUG) {
				Log.d(TAG, "Attack! " + w.getName());
				fightLog += "Attack! " + w.getName() + "\n";
				move.moveLog = "Attack! " + w.getName() + "\n";
			}
		}

		return move;
	}

	public List<Skill> getAvailableSkills() {
		List<Skill> s = new ArrayList<Skill>();
		for (int i = 0; i < skills.size(); i++) {
			if (skills.get(i).getCost() <= energy) {
				s.add(skills.get(i));
			}
		}

		if (DEBUG) {
			String availableSkills = "Available skills: ";
			if (s.size() > 0) {
				for (int i = 0; i < s.size() - 1; i++) {
					availableSkills += s.get(i).getName() + ", ";
				}
				availableSkills += s.get(s.size() - 1).getName();
			} else {
				availableSkills = "No available skills";
			}
			Log.d(TAG, availableSkills);
		}

		return s;
	}

	public int getAttackPower() {
		return attackPwr;
	}

	public int getDefendPower() {
		return defendPwr;
	}

	public void lossArmor(int loss) {
		armorWorn = armorWorn - loss;
		if (DEBUG) {
			Log.d(TAG, this.name + " loses " + loss + " armors!");
			fightLog += this.name + " loses " + loss + " armors!\n";
		}
	}

	public String getName() {
		return name;
	}

	public int getEnergy() {
		return energy;
	}

	public LinkedList<Weapon> getWeapons() {
		if (!weaponReady) {
			return new LinkedList<Weapon>();
		}
		return weapons;
	}

	public void increaseWeapon(Weapon w) {
		weapons.add(w);
		if (DEBUG) {
			Log.d(TAG, this.name + " captures " + w.getName());
			fightLog += this.name + " captures " + w.getName() + "\n";
		}
	}

	public void increaseEnergy() {
		energy = energy + 2;
		if (DEBUG) {
			Log.d(TAG, this.name + " captures energy!");
			fightLog += this.name + " captures energy!\n";
		}
	}

	public void increaseArmor() {
		totalArmor++;
		if (DEBUG) {
			Log.d(TAG, this.name + " captures armor!");
			fightLog += this.name + " captures armor!\n";
		}
	}

	public void clearLog() {
		fightLog = "";
	}
}
