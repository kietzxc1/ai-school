package Task2;

public class NoOpAction extends Action {
	public static final NoOpAction NO_OP = new NoOpAction();
	public static int score = -10;

	public boolean isNoOp() {
		return true;
	}

	@Override
	public String toString() {
		return "NO ACTION";
	}

	@Override
	public int getScore() {
		return score;
	}
}