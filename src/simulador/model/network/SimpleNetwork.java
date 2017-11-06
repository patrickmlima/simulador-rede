package simulador.model.network;

import java.util.ArrayList;
import java.util.List;

import simulador.model.link.Link;
import simulador.model.node.Node;

public class SimpleNetwork {
	private String label;
	
	private List<Node> nodes;
	
	public SimpleNetwork() {
		this.nodes = new ArrayList<Node>();
	}
	
	public SimpleNetwork(String label) {
		this();
		this.label = label;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public List<Node> getNodes() {
		return nodes;
	}

	public void setNodes(List<Node> nodes) {
		this.nodes = nodes;
	}
	
	public void addNode(Node node) {
		if(!this.nodes.contains(node)) {
			this.nodes.add(node);
		}
	}
	
	public void removeNode(Node node) {
		if(this.nodes.contains(node)) {
			node.clear();
			this.nodes.remove(node);
		}
	}
	
	public Node createNode(String label) {
		Node node = new Node(label);
		this.addNode(node);
		return node;
	}
	
	public Node getNodeFromId(String id) {
		for(Node n : this.nodes) {
			if(n.getId().equals(id)) {
				return n;
			}
		}
		return null;
	}
	
	public List<Link> getLinksList() {
		ArrayList<Link> list = new ArrayList<Link>();
		for(Node n : this.nodes) {
			for(Link l : n.getLinks()) {
				if(!list.contains(l)) {
					list.add(l);
				}
			}
		}
		return list;
	}
}
