package simulador.model.network;

import java.awt.Point;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import simulador.model.link.Link;
import simulador.model.node.Node;

public class SimpleNetwork {
	private String label;
	
	private List<Node> nodes;
	
	private List<Link> links;
	
	public SimpleNetwork() {
		this.nodes = new ArrayList<Node>();
		this.links = new ArrayList<Link>();
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
	
	public List<Link> getLinks() {
		return links;
	}

	public void setLinks(List<Link> links) {
		this.links = links;
	}
	
	public void addLink(Link link) {
		if(!this.links.contains(link)) {
			this.links.add(link);
		}
	}
	
	public void removeLink(Link link) {
		if(!this.links.contains(link)) {
			this.links.remove(link);
		}
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
	
	public void removeSelectedNodes() {
		ArrayList<Node> selected = new ArrayList<Node>();
		for(Node n : this.nodes) {
			if(n.isSelected()) {
				selected.add(n);
				n.clear();
			}
		}
		this.nodes.removeAll(selected);
		this.links = this.getLinksList();
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
	
	public void selectNone() {
		for(Node n : this.nodes) {
			n.setSelected(false);
		}
	}
	
	public boolean selectOne(Point p) {
		for(Node n : this.nodes) {
			if(n.contains(p)) {
				if(!n.isSelected()) {
					this.selectNone();
					n.setSelected(true);
				}
				return true;
			}
		}
		return false;
	}
	
	public void selectToggle(Point p) {
		for(Node n : this.nodes) {
			if(n.contains(p)) {
                n.setSelected(!n.isSelected());
            }
		}
	}
	
	public void updatePosition(Point p) {
		 for(Node n : this.nodes) {
			 if (n.isSelected()) {
				 n.getPoint().x += p.x;
				 n.getPoint().y += p.y;
                 n.setBoundary(n.getB());
             }
         }
	}
	
	public void selectRect(Rectangle r) {
		for(Node n : this.nodes) {
			n.setSelected(r.contains(n.getPoint()));
        }
	}
	
	public List<Node> getSelectedNodes() {
		ArrayList<Node> list = new ArrayList<Node>();
		for(Node n : this.nodes) {
			if(n.isSelected()) {
				list.add(n);
			}
		}
		return list;
	}
	
	public Integer selectLambdaFirstFit(List<Node> path) {
		int pathSize = path.size();
		ArrayList<Link> pathLinks = new ArrayList<Link>();
		
		Node origin = path.get(0);
		Node nextNode = path.get(1);
		Link firstLink = origin.getLinkToNode(nextNode);
		
		boolean lambdaChosen = false;
		Integer selectedLambda = -1;
		do {
			Node n1 = nextNode;
			pathLinks.add(firstLink);
			selectedLambda = firstLink.getNextAvailableLambda(selectedLambda);
			if(selectedLambda != null) {
				for(int i = 2; i < pathSize; ++i) {
					Node n2 = path.get(i);
					Link l = n1.getLinkToNode(n2);
					if(l.isLambdaAvailable(selectedLambda)) {
						pathLinks.add(l);
						n1 = n2;
					} else {
						selectedLambda += 1;
						pathLinks.clear();
						break;
					}
				}
				if(!pathLinks.isEmpty()) {
					lambdaChosen = true;
					break;
				}
			} else {
				break;
			}
		} while(true);
		if(lambdaChosen) {
			for(Link l : pathLinks) {
				l.setLambdaStatus(selectedLambda, false);
			}
			return selectedLambda;
		}
		return null;
	}
}
