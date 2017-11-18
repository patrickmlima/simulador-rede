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
		ArrayList<String> listNo = new ArrayList<String>();
		
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
				if(dist.get(v.getId()) != null) {
					System.out.println("vID" + (dist.get(v.getId()) + link.getLength()));
					System.out.println("tamLink " + link.getLength());
					if(dist.get(n.getId()) == null || (dist.get(v.getId()) + link.getLength() < dist.get(n.getId())) )  {
						System.out.println("noLabel " + v.getLabel());
						listNo.add(v.getLabel());
						Float f = dist.get(v.getId()) + link.getLength();
						dist.put(n.getId(), f);
					}
				}
			}
		}
		System.out.println("listaNo " + listNo);
		return dist;
	}
}
