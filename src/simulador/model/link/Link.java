package simulador.model.link;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import simulador.model.node.Node;

public class Link {
	private String id;
	
	private Float length;
	
	private Node from;
	
	private Node to;
	
	private Boolean isBidirectional;
	
	private Integer numLambdas;
	
	private HashMap<Integer, Boolean> availableLambdas = new HashMap<Integer, Boolean>();
	
	public Link() {
		this(null, null, true, null, null, null);
	}
	
	public Link(Boolean isBidirectional) {
		this(null, null, isBidirectional, null, null, null);
	}
	
	public Link(String id, Float length, Boolean isBidirectional) {
		this(id, length, isBidirectional, null, null, null);
	}
	
	public Link(Float length, Node from, Node to) {
		this(null, length, true, from, to, null);
	}
	
	public Link(Float length, Node from, Node to, Integer numLambdas) {
		this(null, length, true, from, to, numLambdas);
	}
	
	public Link(String id, Float length, Boolean isBidirectional, Node from, Node to, Integer numLambdas) {
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
		if(numLambdas == null || numLambdas < 0) {
			numLambdas = 0;
		}
		this.numLambdas = numLambdas;
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
	
	public Integer getNumLambdas() {
		return numLambdas;
	}

	public void setNumLambdas(Integer numLambdas) {
		this.numLambdas = numLambdas;
	}

	public void clear() {
		if(this.getTo() != null) {
			this.getTo().removeLink(this);
		}
		if(this.getFrom() != null) {
			this.getFrom().removeLink(this);
		}
	}
	
	 public void draw(Graphics g) {
         Point p1 = to.getPoint();
         Point p2 = from.getPoint();
         g.setColor(Color.darkGray);
         g.drawLine(p1.x, p1.y, p2.x, p2.y);
         g.drawString(this.getLength().toString(), (p1.x + p2.x)/2, (p1.y +p2.y)/2);
     }
	 
	 public void resetAvailableLambdas() {
		 this.availableLambdas = new HashMap<Integer, Boolean>();
	 }
	 
	 public void setLambdaStatus(int lambda, boolean isAvailable) {
		 if(lambda >= 0 && lambda < this.numLambdas) {
			 this.availableLambdas.put(lambda, isAvailable);
		 }
	 }
	 
	 public Boolean getLambdaStatus(int lambda) {
		 if(this.availableLambdas.containsKey(lambda)) {
			 return this.availableLambdas.get(lambda);
		 }
		 return null;
	 }
	 
	 public Integer getFirstAvailableLambda() {
		 return this.getNextAvailableLambda(0);
	 }
	 
	 public Integer getNextAvailableLambda(Integer lastLambda) {
		 if(lastLambda == null || lastLambda < 0) {
			 lastLambda = 0;
		 }
		 for(int i = lastLambda; i < this.numLambdas; ++i) {
			 Boolean isAvailable = this.availableLambdas.get(i);
			 if(isAvailable == null || isAvailable.booleanValue()) {
				 return i;
			 }
		 }
		 return null;
	 }
	 
	 public List<Integer> getAllAvailableLambdas() {
		 ArrayList<Integer> availableList = new ArrayList<Integer>();
		 for(int i = 0; i < this.numLambdas; ++i) {
			 Boolean isAvailable = this.availableLambdas.get(i);
			 if(isAvailable == null || isAvailable.booleanValue()) {
				 availableList.add(i);
			 }
		 }
		 return availableList;
	 }
	 
	 public boolean isLambdaAvailable(int lambda) {
		 if(lambda < 0 || lambda >= this.numLambdas) {
			 return false;
		 }
		 Boolean b = this.availableLambdas.get(lambda);
		 if(b != null) {
			 return b.booleanValue();
		 }
		 return true;
	 }
}
