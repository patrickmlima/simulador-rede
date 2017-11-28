package simulador.model.algorithms;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import simulador.model.DTO.DijkstraResult;
import simulador.model.link.Link;
import simulador.model.node.Node;

public class Dijkstra {
	
	public DijkstraResult getMinDistance(List<Node> nodes, Node origin) {
		HashMap<Node, Float> dist = new HashMap<Node, Float>();
		HashMap<Node, Node> pervious = new HashMap<Node, Node>();
		ArrayList<Node> list = new ArrayList<Node>();
		ArrayList<String> listNo = new ArrayList<String>();
		
		list.add(origin);
		
		for(Node n : nodes) {
			if(n.equals(origin)) {
				dist.put(n, 0.0f);
				n.setTmpDistance(0.0f);
			} else {
				dist.put(n, Float.POSITIVE_INFINITY);
				list.add(n);
				n.setTmpDistance(Float.POSITIVE_INFINITY);
			}
			pervious.put(n, null);
		}
		
		while(!list.isEmpty()) {
			Collections.sort(list);
			
			Node u = list.get(0);
			list.remove(0);
			
			for(Link link : u.getLinks()) {
				Node v = link.getFrom().equals(u) ? link.getTo() : link.getFrom();
				Float tmp = dist.get(u) + link.getLength();
//				if(tmp != null) {
//					tmp = tmp + link.getLength();
//				}
//				if(dist.get(v.getId()) != null) {
					if(tmp < dist.get(v) )  {
						v.setTmpDistance(tmp);
						listNo.add(u.getLabel());
						dist.put(v, tmp);
						pervious.put(v, u);
					}
//				}
			}
		}
		return new DijkstraResult(origin, dist, pervious);
	}
}
