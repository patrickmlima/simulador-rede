package simulador.model.algorithms;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import simulador.model.link.Link;
import simulador.model.node.Node;

public class BFS {

	public List<List<Node>> getAllPaths(List<Node> nodes, Node origin, Node dest) {
		ArrayList<Node> visitados = new ArrayList<Node>();
		ArrayList<String> listVisitados = new ArrayList<String>();
		visitados.add(origin);
		listVisitados.add(origin.getLabel());

		List<List<Node>> allPaths = new ArrayList<List<Node>>();

		this.getMinDistance(nodes, origin, dest, visitados, allPaths);

//		for(List<Node> p : allPaths) {
//			this.printPath(p);
//		}

		return allPaths;
	}

	//private static boolean flag;
	public List<Node> getMinDistance(List<Node> nodes, Node origin, Node dest, ArrayList<Node> visitados, List<List<Node>> paths) {

		HashMap<Node, Float> dist = new HashMap<Node, Float>(); //acumula a distancia da origin ate dest
		HashMap<Node, Node> previous = new HashMap<Node, Node>(); //armazena o v�rtice anterior

		// 0- Branco; 1-CINZA; 2 PRETo

		for(Node n : nodes) {
			if(n.equals(origin)) {
				dist.put(n, 0.0f);
				n.setTmpDistance(0.0f);
			} else {
				dist.put(n, Float.POSITIVE_INFINITY);
				n.setTmpDistance(Float.POSITIVE_INFINITY);
			}
			previous.put(n, null);
		}

		Node u = visitados.get(visitados.size()-1);
		for(Link link : u.getLinks()) {
			if(!link.getIsActive()) {
				continue;
			}
			Node v = link.getFrom().equals(u) ? link.getTo() : link.getFrom();
			/*Float tmp = dist.get(u) + link.getLength();
						if(tmp < dist.get(v) )  {
							v.setTmpDistance(tmp);
							listNo.add(u.getLabel());
							dist.put(v, tmp);
							previous.put(v, u);
						}*/
			if(visitados.contains(v)) {
				continue;
			}
			if( v.equals(dest)) {
				visitados.add(v);
				List<Node> p = new ArrayList<Node>(visitados);
				paths.add(p);
				//printPath(visitados);
				//flag = true;
				visitados.remove(visitados.get(visitados.size()-1));
				break;
			}
		}

		for (Link link : u.getLinks()) {
			if(!link.getIsActive()) {
				continue;
			}
			
			Node v = link.getFrom().equals(u) ? link.getTo() : link.getFrom();
			if(visitados.contains(v) || v.equals(dest)) {
				continue;
			}
			visitados.add(v);

			getMinDistance(nodes, origin, dest, visitados, paths);

			visitados.remove(visitados.get(visitados.size()-1));
		}

		/*if (flag == false)

		        { //pode apagar se quiser, mas coloquei pra sinalizar que nao existe caminho ente os nos

		            System.out.println("Nao existe caminho entre " + origin.getLabel() + " e "

		                    + dest.getLabel());

		            flag = true;

		        }*/


		return null; //nao coloquei uma lista de n�, porque no dijkstra tu fez diferente.
	}

	private void printPath(List<Node> visitados) {
		for (Node node : visitados) {
			System.out.print(node.getLabel() +",");
		}
		System.out.println(" ");
	}
}