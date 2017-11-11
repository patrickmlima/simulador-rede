package simulador.model.algorithms;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import simulador.model.link.Link;
import simulador.model.node.Node;

public class Dijkstra {
	
	public Map<String, Float> getMinDistance(List<Node> nodes, Node origin) {
		HashMap<String, Float> dist = new HashMap<String, Float>();
		ArrayList<Node> list = new ArrayList<Node>();
		
		list.add(origin);
		
		for(Node n : nodes) {
			if(n.equals(origin)) {
				dist.put(n.getId(), 0.0f);
			} else {
				dist.put(n.getId(), null);
				list.add(n);
			}
		}
		
		while(!list.isEmpty()) {
			Node v = list.get(0);
			list.remove(0);
			
			for(Link link : v.getLinks()) {
				Node n = link.getFrom().equals(v) ? link.getTo() : link.getFrom();
				if(dist.get(n.getId()) == null || (dist.get(v.getId()) != null && 
						(dist.get(v.getId()) + link.getLength() < dist.get(n.getId())) ) ) {
					System.out.println(n.getLabel());
					dist.put(n.getId(), dist.get(v.getId()) + link.getLength());
				}
			}
		}
		
		return dist;
	}
}
