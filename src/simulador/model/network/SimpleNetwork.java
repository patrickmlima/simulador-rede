package simulador.model.network;

import java.awt.Point;
import java.awt.Rectangle;
import java.io.BufferedWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import simulador.model.DTO.DijkstraResult;
import simulador.model.algorithms.BFS;
import simulador.model.algorithms.Dijkstra;
import simulador.model.call.Call;
import simulador.model.link.Link;
import simulador.model.node.Node;

public class SimpleNetwork {
	private String label;
	
	private List<Node> nodes;
	
	private List<Link> links;
	
	private List<Call> calls;
	
	public SimpleNetwork() {
		this.nodes = new ArrayList<Node>();
		this.links = new ArrayList<Link>();
		this.calls = new ArrayList<Call>();
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
	
	public List<Call> getCalls() {
		return calls;
	}

	public void setCalls(List<Call> calls) {
		this.calls = calls;
	}
	
	public void addCall(Call call) {
		if(!this.calls.contains(call)) {
			this.calls.add(call);
		}
	}
	
	public void removeCall(Call call) {
		if(this.calls.contains(call)) {
			this.calls.remove(call);
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
	
	public Call createCallFirstFit(List<Node> path) {
		return this.createCallFirstFit(path, null);
	}
	
	public Call createCallFirstFit(List<Node> path, Call call) {
		int pathSize = path.size();
		ArrayList<Link> pathLinks = new ArrayList<Link>();
		
		Node origin = path.get(0);
		Node nextNode = path.get(1);
		Link firstLink = origin.getLinkToNode(nextNode);
		
		// libera lambda
		if(call != null) {
			for(Link l : call.getLinks()) {
				l.setLambdaStatus(call.getLambda(), true);
			}
		}
		
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
			if(call == null) {
				call = new Call(origin, path.get(path.size() - 1));
			}
			call.setLambda(selectedLambda);
			call.setPath(path);
			call.setLinks(pathLinks);
			
			for(Link l : pathLinks) {
				l.setLambdaStatus(selectedLambda, false);
				l.addCall(call);
			}
			return call;
		}
		return null;
	}
	
	
	public void testSurvival(BufferedWriter writer) throws IOException {
		BFS bfs = new BFS();
		Dijkstra dikjstra = new Dijkstra();
		
		System.out.println("\nIniciando sobrevivência...\n");
		writer.write("\nIniciando sobrevivência...\n\n");
		
		float rateTotal = 0.0f;
		for(Link link : this.getLinks()) {
			System.out.println("Desativando Link dos nós: " + link.getFrom().getLabel() + " - " + link.getTo().getLabel());
			writer.write("Desativando Link dos nós: " + link.getFrom().getLabel() + " - " + link.getTo().getLabel() + "\n");
			
			link.setIsActive(false);
			List<Call> calls = link.getCalls();
			// para restaurar chamadas (caso necessario)
//			List<Call> tmpCalls = new ArrayList<Call>(calls);
			
			Integer totalCalls = calls.size();
			
			System.out.println("Chamadas a serem reestabelecidas: " + totalCalls);
			writer.write("Chamadas a serem reestabelecidas: " + totalCalls + "\n");
			
			Integer totalEstablished = 0;
			for(int i = 0; i < calls.size(); ++i) {
				Call call = calls.get(i);
				
				DijkstraResult result = dikjstra.getMinDistance(this.getNodes(), call.getOrigin());
				List<Node> sPath = result.getShortestPath(call.getDestination());
				
				List<List<Node>> resultBFS = bfs.getAllPaths(this.getNodes(), call.getOrigin(), call.getDestination());
				List<List<Node>> allPaths = this.insertShortestPath(sPath, resultBFS);
//				Float distanceValue = result.getDistances().get(call.getDestination());
				
				System.out.println("Analizando caminhos...");
				writer.write("Analizando caminhos...\n");
				
				Call c = null;
				for(List<Node> path : allPaths) {
					this.printPath(path);
					c = this.createCallFirstFit(path, call);
					if(c != null) {
						System.out.println("Chamada reestabelecida\n");
						writer.write("Chamada reestabelecida\n\n");
						this.addCall(c);
						++totalEstablished;
						break;
					}
				}
				if(c == null) {
					System.out.println("Chamada perdida\n");
					writer.write("Chamada perdida\n\n");
					this.addCall(new Call(call.getOrigin(), call.getDestination(), null));
				}
			}
			
			Float successRate = Float.parseFloat(totalEstablished.toString()) / totalCalls;
			
			rateTotal = rateTotal + successRate.floatValue();
			link.setIsActive(true);
		}
		rateTotal = rateTotal / Float.parseFloat(new Integer(this.getLinks().size()).toString());
		System.out.println("Taxa de sucesso Total: " + String.format("%.4f", rateTotal));
		writer.write("Taxa de sucesso Total: " + String.format("%.4f", rateTotal) + "\n");
	}
	
	private void printPath(List<Node> visitados) {
		for (Node node : visitados) {
			System.out.print(node.getLabel() +" | ");
		}
		System.out.println(" ");
	}
	
	private List<List<Node>> insertShortestPath(List<Node> sPath, List<List<Node>> paths) {
		if(paths == null) {
			paths = new ArrayList<List<Node>>();
		}
		if(paths.size() > 0) {
			int index = 0;
			for(List<Node> l : paths) {
				if(l.size() == sPath.size() && sPath.containsAll(l)) {
					break;
				}
				++index;
			}
			if(index < paths.size()) {
				paths.remove(index);
			}
		}
		paths.add(0, sPath);
		return paths;
	}
}
