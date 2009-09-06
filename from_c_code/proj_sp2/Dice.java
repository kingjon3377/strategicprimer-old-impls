package proj_sp2;
import java.util.Random;
final class Dice {
	private Dice() { }
	private static final Random r = new Random();
	static int rollDice(final int diceSize) {
		return r.nextInt(diceSize) + 1;
	}
}
