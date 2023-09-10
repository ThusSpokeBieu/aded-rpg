package github.gmess.aded.domain.system.dices;

public enum DiceType {
    D4(4),
    D6(6),
    D8(8),
    D10(10),
    D12(12),
    D20(20),
    D100(100);

    private final int sides;

    DiceType(int sides) {
        this.sides = sides;
    }

    public int getSides() {
        return sides;
    }
}

