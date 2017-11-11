package simulador.view.main;

import java.util.Map;

import simulador.model.algorithms.Dijkstra;
import simulador.model.link.Link;
import simulador.model.network.SimpleNetwork;
import simulador.model.node.Node;
import simulador.model.xml.NetworkXML;

public class Main {
//	public static void main(String[] args) {
//		// TODO Auto-generated method stub
////		SimpleNetwork network = new SimpleNetwork();
////		
////		Node node1 = new Node("Node 1");
////		Node node2 = new Node("Node 2");
////		Node node3 = new Node("Node 3");
////		Node node4 = new Node("Node 4");
////		Node node5 = new Node("Node 5");
////		Node node6 = new Node("Node 6");
////		
////		Link link12 = new Link();
////		link12.setLength(1.2f);
////		node1.addLink(link12);
////		node2.addLink(link12);
////		
////		Link link23 = new Link();
////		link23.setLength(2.5f);
////		node2.addLink(link23);
////		node3.addLink(link23);
////		
////		network.addNode(node1);
////		network.addNode(node2);
////		network.addNode(node3);
////		
////		printNetwork(network);
//		
//		NetworkXML w = new NetworkXML();
////		w.saveNetwork(network, "/home/patrick/workspace/simulacao-sistemas/test.xml");
//		SimpleNetwork n = w.loadNetwork("/home/patrick/workspace/simulacao-sistemas/test.xml");
//		printNetwork(n);
//		
//		Dijkstra d = new Dijkstra();
//		Map<String, Float> res = d.getMinDistance(n.getNodes(), n.getNodes().get(0));
//		
//		for(Node nd : n.getNodes()) {
//			System.out.println(nd.getId() + ": " + res.get(nd.getId()));
//		}
//		System.out.println();
//	}
	
	public static void printNetwork(SimpleNetwork network) {
		for(Node n : network.getNodes()) {
			System.out.println(n.getLabel() + ": ");
			for(Link l : n.getLinks()) {
				System.out.println("From: " + l.getFrom().getLabel() + " to: " + l.getTo().getLabel());
			}
			System.out.println("---------------------");
		}
		System.out.println();
	}

}
