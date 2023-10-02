package Task2;

public class Environment {
	public static final Action MOVE_LEFT = new DynamicAction("LEFT", 10);
	public static final Action MOVE_RIGHT = new DynamicAction("RIGHT", 10);
	public static final Action MOVE_UP = new DynamicAction("UP", 10);
	public static final Action MOVE_DOWN = new DynamicAction("DOWN", 10);
	public static final Action SUCK_DIRT = new DynamicAction("SUCK", 500);
	public static final String LOCATION_A = "A";
	public static final String LOCATION_B = "B";
	public static final String LOCATION_C = "C";
	public static final String LOCATION_D = "D";
	public int score = 0;
	public static final String[][] LOCATION_MAP = {
			{LOCATION_A, LOCATION_B},
			{LOCATION_C, LOCATION_D}
	};

	public enum LocationState {
		CLEAN, DIRTY
	}

	private EnvironmentState envState;
	private boolean isDone = false;// all squares are CLEAN
	private Agent agent = null;

	public Environment(LocationState locAState, LocationState locBState) {
		envState = new EnvironmentState(locAState, locBState);
	}

	public Environment(LocationState locAState, LocationState locBState,
					   LocationState locCState, LocationState locDState) {
		envState = new EnvironmentState(locAState, locBState, locCState, locDState);
	}

	// add an agent into the enviroment
	public void addAgent(Agent agent, String location) {
		this.agent = agent;
		this.envState.setAgentLocation(location);
	}

	public EnvironmentState getCurrentState() {
		return this.envState;
	}

	// Update enviroment state when agent do an action
	public EnvironmentState executeAction(Action action) {
		String agentLocation = envState.getAgentLocation();
		if (action == Environment.SUCK_DIRT) {
			envState.setLocationState(agentLocation, LocationState.CLEAN);
		}
		else if (action instanceof DynamicAction) {
			var location = possibleLocation((DynamicAction) action, agentLocation);
			envState.setAgentLocation(location);
		}
		score += action.getScore();
		return envState;
	}

	// get percept<AgentLocation, LocationState> at the current location where agent
	// is in.
	public Percept getPerceptSeenBy() {
		String agentLocation = envState.getAgentLocation();
		return new Percept(agentLocation, envState.getLocationState(agentLocation));
	}

	public void step() {
		envState.display();
		String agentLocation = this.envState.getAgentLocation();
		Action anAction = agent.execute(getPerceptSeenBy());
		EnvironmentState es = executeAction(anAction);

//		System.out.println("Agent Loc.: " + agentLocation + "\tAction: " + anAction);
		System.out.printf("Agent Loc.: %s\tAction: %s(%d score)\tTotal Score: %d\n", agentLocation, anAction, anAction.getScore(), score);

		if ((es.getLocationState(LOCATION_A) == LocationState.CLEAN)
				&& (es.getLocationState(LOCATION_B) == LocationState.CLEAN))
			isDone = true;// if both squares are clean, then agent do not need to do any action
		es.display();
	}

	public void step(int n) {
		for (int i = 0; i < n; i++) {
			step();
			System.out.println("-------------------------");
		}
	}

	public void stepUntilDone() {
		int i = 0;

		while (!isDone) {
			System.out.println("step: " + i++);
			step();
		}
	}

	public static int[] getIndexLocation(String location) {
		int[] rs = null;
		switch (location) {
			case Environment.LOCATION_A -> rs = new int[]{0, 0};
			case Environment.LOCATION_B -> rs = new int[]{0, 1};
			case Environment.LOCATION_C -> rs = new int[]{1, 0};
			case Environment.LOCATION_D -> rs = new int[]{1, 1};
		}
		return rs;
	}

	public static String possibleLocation(DynamicAction move, String location) {
		var pos = getIndexLocation(location);
		switch (move.getName()) {
			case "UP" -> pos[0]--;
			case "DOWN" -> pos[0]++;
			case "RIGHT" -> pos[1]++;
			case "LEFT" -> pos[1]--;
		}
		if ((pos[0] < 0 || pos[1] < 0 || pos[0] >= 2 || pos[1] >= 2)) return null;
//		System.out.printf("LOCATION_MAP[%d][%d]\n", pos[0], pos[1]);
		return LOCATION_MAP[pos[0]][pos[1]];
	}
}