package proj_sp2;
final class Dice {
	private Dice() { }
	static int rollDice(final int diceSize) {
		return (Integer.random() % diceSize) + 1;
	}
}
