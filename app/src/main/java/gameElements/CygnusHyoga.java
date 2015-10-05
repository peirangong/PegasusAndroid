package gameElements;

import gameElements.Weapon.SpecialWeapon;

public class CygnusHyoga extends BaseCharacter {

	private Weapon w = new Weapon(this, "Ice ring", 0, 10,
			SpecialWeapon.Ice_Ring);

	public CygnusHyoga() {
		super();
		totalArmor = 2;
		armorWorn = 0;
		level = NOVICE;
		name = "Cygnus Hyoga";

		skills.add(new Skill(this, "Diamond Microdust", 1, 1, 1));
		skills.add(new Skill(this, "Diamond Stardust", 2, 2, 2));
		skills.add(new Skill(this, "Absolute Zero", 4, 4, 4));
		skills.add(new Skill(this, "Cosmo Explosion", 10, 10, 10));
		skills.add(new Skill(this, "Aurora Sword", 12, 12, 12));
		skills.add(new Skill(this, "Aurora Thunder", 16, 16, 16));
		skills.add(new Skill(this, "Aurora Execution", 24, 24, 24));

		weapons.add(w);
		weapons.add(w);
	}
}