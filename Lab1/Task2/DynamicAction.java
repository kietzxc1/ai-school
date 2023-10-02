package Task2;

public class DynamicAction extends Action {
	private String name;
	private int score;

	public DynamicAction(String name, int score) {
		this.name = name;
		this.score = score;
	}

	@Override
	public boolean isNoOp() {
		return false;
	}

	@Override
	public int getScore() {
		return this.score;
	}

	@Override
	public String toString() {
		return this.name;
	}

	public String getName() {
		return name;
	}

}