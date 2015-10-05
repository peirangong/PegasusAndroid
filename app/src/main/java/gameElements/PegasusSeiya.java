package gameElements;

import gameElements.Weapon.SpecialWeapon;

public class PegasusSeiya extends BaseCharacter {

	private Weapon w = new Weapon(this, "Golden Arrow", 9, 9,
			SpecialWeapon.Regular);

	public PegasusSeiya() {
		super();
		totalArmor = 1;
		armorWorn = 0;
		level = NOVICE;
		name = "Pegasus Seiya";

		skills.add(new Skill(this, "Pegasus Meteor Fist", 2, 2, 2));
		skills.add(new Skill(this, "Pegasus Comet Fist", 4, 4, 4));
		skills.add(new Skill(this, "Pegasus Rolling Crush", 6, 6, 6));
		skills.add(new Skill(this, "Cosmo Explosion", 10, 10, 10));

		weapons.add(w);
		// weapons.add(w);
		// weapons.add(w);
	}

}