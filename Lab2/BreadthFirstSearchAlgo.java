package Lab2;

import java.util.*;

public class BreadthFirstSearchAlgo implements ISearchAlgo {

	@Override
	public Node execute(Node root, String goal) {
		Queue<Node> frontier = new LinkedList<Node>();
		List<Node> explored = new ArrayList<>();
		frontier.offer(root);
		while (!frontier.isEmpty()) {
			Node current = frontier.poll();
			if (current.getLabel().equals(goal))
				return current;
			for (Node child : current.getChildrenNodes()) {
				if (frontier.contains(child) || explored.contains(child))
					continue;
				frontier.offer(child);
				child.setParent(current);
			}
		}
		return null;
	}

	@Override
	public Node execute(Node root, String start, String goal) {
		boolean check = false;
		Queue<Node> frontier = new LinkedList<Node>();
		List<Node> explored = new ArrayList<>();
		frontier.offer(root);
		while (!frontier.isEmpty()) {
			Node current = frontier.poll();
			// meet the start point
			if (current.getLabel().equals(start) && !check) {
				check = true;
				frontier.clear();
				explored.clear();
				current.setParent(null);
				frontier.add(current);
				continue;
			}
			if (current.getLabel().equals(goal) && check)
				return current;
			for (Node child : current.getChildrenNodes()) {
				if (frontier.contains(child) || explored.contains(child))
					continue;
				frontier.offer(child);
				child.setParent(current);
			}
		}
		return null;
	}

}