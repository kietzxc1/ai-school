package Task2;

public class AgentProgram {
	public Action execute(Percept p) {// location, status
		String pos = p.getAgentLocation();
		if (p.getLocationState() == Environment.LocationState.DIRTY) {
			return Environment.SUCK_DIRT;
		}
		else if (p.getLocationState() == Environment.LocationState.CLEAN) {
			DynamicAction act = chooseRandom();
			if (Environment.possibleLocation(act, pos) == null)  {
				System.out.printf("Agent cannot move %s because currently at %s\n", act.getName(), pos);
				return NoOpAction.NO_OP;
			}
			return act;
		}
		return NoOpAction.NO_OP;
	}



	private DynamicAction chooseRandom() {
		Action[] rs = {Environment.MOVE_UP, Environment.MOVE_DOWN, Environment.MOVE_LEFT, Environment.MOVE_RIGHT};
		int random = (int) ((Math.random() * (4)));
		return (DynamicAction) rs[random];
	}
}