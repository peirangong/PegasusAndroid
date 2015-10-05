package gameElements;

/** The weapon(s) that came from the armor. */
public class Weapon extends Move {

	public enum SpecialWeapon {
		Regular, Ice_Ring;
	}

	private SpecialWeapon special;

	public Weapon(BaseCharacter ch, String n, int a, int d, SpecialWeapon s) {
		super(ch, n, a, d);
		special = s;
	}

	public SpecialWeapon getSpecial() {
		return special;
	}
}
