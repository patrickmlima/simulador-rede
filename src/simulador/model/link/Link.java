package simulador.model.link;

import java.util.UUID;

import simulador.model.node.Node;

public class Link {
	private String id;
	
	private Float length;
	
	private Node from;
	
	private Node to;
	
	private Boolean isBidirectional;
	
	public Link() {
		this(null, null, true, null, null);
	}
	
	public Link(Boolean isBidirectional) {
		this(null, null, isBidirectional, null, null);
	}
	
	public Link(String id, Float length, Boolean isBidirectional) {
		this(id, length, isBidirectional, null, null);
	}
	
	public Link(String id, Float length, Boolean isBidirectional, Node from, Node to) {
		if(id == null) {
			this.id = UUID.randomUUID().toString();
		} else {
			this.id = id;
		}
		if(length == null) {
			this.length = 0.0f;
		} else {
			this.length = length;
		}
		this.isBidirectional = isBidirectional;
		
		if(from != null) {
			from.addLink(this);
		}
		this.from = from;
		
		if(to != null) {
			to.addLink(this);
		}
		this.to = to;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Float getLength() {
		return length;
	}

	public void setLength(Float length) {
		this.length = length;
	}

	public Node getFrom() {
		return from;
	}

	public void setFrom(Node from) {
		this.from = from;
	}

	public Node getTo() {
		return to;
	}

	public void setTo(Node to) {
		this.to = to;
	}

	public Boolean getIsBidirectional() {
		return isBidirectional;
	}

	public void setIsBidirectional(Boolean isBidirectional) {
		this.isBidirectional = isBidirectional;
	}
	
	public void clear() {
		if(this.getTo() != null) {
			this.getTo().removeLink(this);
		}
		if(this.getFrom() != null) {
			this.getFrom().removeLink(this);
		}
	}
}
