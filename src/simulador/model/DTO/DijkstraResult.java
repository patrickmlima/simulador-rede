package simulador.model.DTO;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import simulador.model.node.Node;

public class DijkstraResult {
	private Node origin;
	
	private Map<Node, Float> distances;
	
	private Map<Node, Node> previous;
	
	public DijkstraResult(Node origin, Map<Node, Float> distances, Map<Node, Node> previous) {
		this.origin = origin;
		this.distances = distances;
		this.previous = previous;
	}

	public Node getOrigin() {
		return origin;
	}

	public void setOrigin(Node origin) {
		this.origin = origin;
	}

	public Map<Node, Float> getDistances() {
		return distances;
	}

	public void setDistances(Map<Node, Float> distances) {
		this.distances = distances;
	}

	public Map<Node, Node> getPrevious() {
		return previous;
	}

	public void setPrevious(Map<Node, Node> previous) {
		this.previous = previous;
	}
	
	public List<Node> getShortestPath(Node destination) {
		if(this.origin == null) {
			return null;
		}
		ArrayList<Node> list = new ArrayList<Node>();
		Node dest = destination;
		while(this.previous.get(dest) != null) {
			list.add(dest);
			dest = previous.get(dest);
		}
		list.add(dest);
		Collections.reverse(list);
		return list;
	}
}
