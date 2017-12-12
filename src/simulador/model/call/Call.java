package simulador.model.call;

import java.util.List;

import simulador.model.link.Link;
import simulador.model.node.Node;

public class Call {
	private Node origin;
	
	private Node destination;
	
	private Integer lambda;
	
	private Boolean isEstablished;
	
	private List<Node> path;
	
	private List<Link> links;
	
	public Call(Node origin, Node destination, Integer lambda) {
		this.origin = origin;
		this.destination = destination;
		this.lambda = lambda; 
		this.isEstablished = (lambda != null);
	}

	public Node getOrigin() {
		return origin;
	}

	public void setOrigin(Node origin) {
		this.origin = origin;
	}

	public Node getDestination() {
		return destination;
	}

	public void setDestination(Node destination) {
		this.destination = destination;
	}

	public Integer getLambda() {
		return lambda;
	}

	public void setLambda(Integer lambda) {
		this.lambda = lambda;
	}

	public Boolean getIsEstablished() {
		return isEstablished;
	}

	public void setIsEstablished(Boolean isEstablished) {
		this.isEstablished = isEstablished;
	}

	public List<Node> getPath() {
		return path;
	}

	public void setPath(List<Node> path) {
		this.path = path;
	}

	public List<Link> getLinks() {
		return links;
	}

	public void setLinks(List<Link> links) {
		this.links = links;
	}
	
	
}
