package gameElements;

import java.util.List;

public class MoveGenerator {
    private BaseCharacter baseChar;

    public MoveGenerator(BaseCharacter bc) {
        baseChar = bc;
    }

    public Move generateMove_1(double random) {
        Move move;
        if (random < 0.3) {
            move = baseChar.gather();
        } else if (random >= 0.3 && random < 0.4) {
            move = baseChar.defense();
        } else {
            List<Skill> skills = baseChar.getAvailableSkills();
            if (skills.size() > 0) {
                move = baseChar.attack(skills.get(skills.size() - 1));
            } else {
                move = baseChar.defense();
            }
        }
        return move;
    }

    public Move generateMove_2(double random) {
        Move move;
        if (random < 0.2) {
            move = baseChar.gather();
        } else if (random < 0.3) {
            move = baseChar.wearArmor();
        } else {
            List<Skill> skills = baseChar.getAvailableSkills();
            if (skills.size() > 0) {
                move = baseChar.attack(skills.get(skills.size() - 1));
            } else {
                move = baseChar.defense();
            }
        }
        return move;
    }
}
