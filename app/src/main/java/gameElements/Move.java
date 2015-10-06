package gameElements;

import android.util.Log;

public class Move {

	public static final String GATHER = "Gather";
	public static final String DEFENSE = "Defense";
	public static final String ARMOR = "Wear Armor";
	public static final String ATTACK_SKILL = "Use Skill";
	public static final String ATTACK_WEAPON = "Use Weapon";
	public static final String SPECIAL = "Special";

	public String moveLog = "";

	protected String name = "";

	private BaseCharacter character = null;

	protected int att;

	protected int def;

	public Move(BaseCharacter ch, String n, int a, int d) {
		character = ch;
		name = n;
		att = a;
		def = d;
	}

	public BaseCharacter getCharacter() {
		return character;
	}

	public String getName() {
		return name;
	}

	public int getAtt() {
		return att;
	}

	public int getDef() {
		return def;
	}

	public static boolean Analyze(Move A, Move B) {
		if (A.getName() == "Ice ring") {
			if (B.getName() == Move.GATHER) {
				A.getCharacter().increaseEnergy();
			} else if (B.getName() == Move.ARMOR) {
				A.getCharacter().increaseArmor();
				B.getCharacter().lossArmor(1);
			} else if (B.getCharacter().getAttackPower() < A.getCharacter()
					.getDefendPower()) {
				Weapon w = new Weapon(B.getCharacter(), B.getName(), B
						.getCharacter().getAttackPower(), B.getCharacter()
						.getDefendPower(), Weapon.SpecialWeapon.Regular);
				A.getCharacter().increaseWeapon(w);
			}
		} else if (B.getName() == "Ice ring") {
			if (A.getName() == Move.GATHER) {
				B.getCharacter().increaseEnergy();
			} else if (A.getName() == Move.ARMOR) {
				B.getCharacter().increaseArmor();
				A.getCharacter().lossArmor(1);
			} else if (A.getCharacter().getAttackPower() < B.getCharacter()
					.getDefendPower()) {
				Weapon w = new Weapon(A.getCharacter(), A.getName(), A
						.getCharacter().getAttackPower(), A.getCharacter()
						.getDefendPower(), Weapon.SpecialWeapon.Regular);
				B.getCharacter().increaseWeapon(w);
			}
		}

		int AtoB = A.getCharacter().getAttackPower()
				- B.getCharacter().getDefendPower();
		int BtoA = B.getCharacter().getAttackPower()
				- A.getCharacter().getDefendPower();
		if (BtoA > 0) {
			// B attacks A
			int lossArmor = (int) (BtoA / 10) + 1;
			A.getCharacter().lossArmor(lossArmor);
		} else if (AtoB > 0) {
			// A attacks B
			int lossArmor = (int) (AtoB / 10) + 1;
			B.getCharacter().lossArmor(lossArmor);
		}

		if (!A.getCharacter().isAlive() || !B.getCharacter().isAlive()) {
			return true;
		} else {
			return false;
		}
	}
}