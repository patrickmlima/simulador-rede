package simulador.model.node;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import simulador.model.link.Link;

public class Node implements Comparable<Node>{
	private String id;
	
	private String label;
	
	private List<Link> links;
	
	private Point point;
	
	private int radius;
	
	private boolean isSelected = false;
	
	private Rectangle b = new Rectangle();
	
	private Float tmpDistance;
	
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
	
	public Node(String id, String label, Point p, int r) {
		this(id, label);
		this.point = p;
		this.radius = r;
		this.setBoundary(b);
	}
	
	public Node(String label, Point p, int r) {
		this(label);
		this.point = p;
		this.radius = r;
		this.setBoundary(b);
	}
	
	/**
     * Calculate this node's rectangular boundary.
     */
    public void setBoundary(Rectangle b) {
        b.setBounds(point.x - radius, point.y - radius, 2 * radius, 2 * radius);
    }
    
    /**
     * Draw this node.
     */
    public void draw(Graphics g) {
        g.setColor(Color.BLACK);
        g.fillOval(b.x, b.y, b.width, b.height);
        g.drawString(this.getLabel(), b.x, b.y);
        if (isSelected) {
            g.setColor(Color.darkGray);
            g.drawRect(b.x, b.y, b.width, b.height);
        }
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

	public Point getPoint() {
		return point;
	}

	public void setPoint(Point p) {
		this.point = p;
	}

	public int getRadius() {
		return radius;
	}

	public void setRadius(int r) {
		this.radius = r;
	}

	public boolean isSelected() {
		return isSelected;
	}

	public void setSelected(boolean selected) {
		this.isSelected = selected;
	}

	public Rectangle getB() {
		return b;
	}

	public void setB(Rectangle b) {
		this.b = b;
	}

	public void setLinks(List<Link> links) {
		this.links = links;
	}
	public boolean contains(Point p) {
		return b.contains(p);
	}
	
	public Link getLinkToNode(Node destNode) {
		for(Link l : this.links) {
			if(l.getIsBidirectional() != null && l.getIsBidirectional()) {
				if(l.getFrom().equals(destNode) || l.getTo().equals(destNode)) {
					return l;
				}
			} else {
				if(l.getTo().equals(destNode)) {
					return l;
				}
			}
		}
		return null;
	}
	
	public void setTmpDistance(Float tmpDistance) {
		this.tmpDistance = tmpDistance;
	}

	@Override
	public int compareTo(Node o) {
		if(this.tmpDistance < o.tmpDistance) {
			return -1;
		} else if(this.tmpDistance > o.tmpDistance) {
			return 1;
		}
		return 0;
	}
	
	

}
