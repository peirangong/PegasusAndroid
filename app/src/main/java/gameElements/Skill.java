package gameElements;

public class Skill extends Move {

	private int cost;

	public Skill(BaseCharacter ch, String n, int a, int d, int c) {
		super(ch, n, a, d);
		cost = c;
	}

	public void useSkill() {
		// TODO: finish
	}

	public int getCost() {
		return cost;
	}

}
