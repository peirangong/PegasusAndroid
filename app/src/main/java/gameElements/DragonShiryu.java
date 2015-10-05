package gameElements;

import gameElements.Weapon.SpecialWeapon;

public class DragonShiryu extends BaseCharacter {
	private Weapon w = new Weapon(this, "Excalibur", 9, 9,
			SpecialWeapon.Regular);

	public DragonShiryu() {
		super();
		totalArmor = 2;
		armorWorn = 0;
		level = NOVICE;
		name = "Dragon Shiryu";

		skills.add(new Skill(this, "Rozan Sho Ryu Ha", 2, 2, 2));
		skills.add(new Skill(this, "Rozan Ryu Hi Shou", 4, 12, 4));
		skills.add(new Skill(this, "Rozan Hundred Dragon Fist", 6, 6, 6));
		skills.add(new Skill(this, "Rozan Kou Ryu Ha", 8, 20, 6));
		skills.add(new Skill(this, "Cosmo Explosion", 10, 10, 10));

		weapons.add(w);
	}

}
