package simulador.model.node;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import simulador.model.link.Link;

public class Node {
	private String id;
	
	private String label;
	
	private List<Link> links;
	
	public Node(String id, String label) {
		this.id = id;
		this.label = label;
		this.links = new ArrayList<Link>();
	}
	
	public Node(String label) {
		this.id = UUID.randomUUID().toString();
		this.label = label;
		this.links = new ArrayList<Link>();
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public List<Link> getLinks() {
		return links;
	}

	public void setLink(List<Link> links) {
		this.links = links;
	}
	
	public void addLink(Link link) {
		if(!this.links.contains(link)) {
			if(link.getFrom() == null) {
				link.setFrom(this);
			} else {
				link.setTo(this);
			}
			this.links.add(link);
		}
	}
	
	public void removeLink(Link link) {
		if(this.links.contains(link)) {
			if(link.getTo().equals(this)) {
				link.getFrom().getLinks().remove(link);
			} else {
				link.getTo().getLinks().remove(link);
			}
			this.links.remove(link);
		}
	}
	
	public void clear() {
		for(Link link : this.links) {
			if(link.getTo().equals(this)) {
				link.getFrom().getLinks().remove(link);
			} else {
				link.getTo().getLinks().remove(link);
			}
		}
		this.links.clear();
	}
}
