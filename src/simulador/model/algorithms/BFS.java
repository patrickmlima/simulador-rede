package simulador.model.algorithms;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import simulador.model.link.Link;
import simulador.model.node.Node;

	public class BFS {

		//private static boolean flag;
		public List<Node> getMinDistance(List<Node> nodes, Node origin, Node dest, ArrayList<Node> visitados, ArrayList<String> listVisitados) {
						
			HashMap<Node, Float> dist = new HashMap<Node, Float>(); //acumula a distancia da origin ate dest
			HashMap<Node, Node> previous = new HashMap<Node, Node>(); //armazena o vértice anterior
			
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
				String nodeU = listVisitados.get(listVisitados.size()-1); //essa lista, tu pode apagar, se quiser. Coloquei ela pra facilitar meu trabalho na leitura dos nós
				for(Link link : u.getLinks()) {
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
						listVisitados.add(v.getLabel());
						printPath(visitados);
						printPathL(listVisitados, origin.getLabel(), dest.getLabel());
						//flag = true;
						visitados.remove(visitados.get(visitados.size()-1));
						listVisitados.remove(listVisitados.get(listVisitados.size()-1));
						break;
					}
				}
				
				for (Link link : u.getLinks()) {
					Node v = link.getFrom().equals(u) ? link.getTo() : link.getFrom();
					if(visitados.contains(v) || v.equals(dest)) {
						continue;
					}
					visitados.add(v);
					listVisitados.add(v.getLabel());
					
					getMinDistance(nodes, origin, dest, visitados, listVisitados);
					
					visitados.remove(visitados.get(visitados.size()-1));
					listVisitados.remove(listVisitados.get(listVisitados.size()-1));
				}
				
				/*if (flag == false)

		        { //pode apagar se quiser, mas coloquei pra sinalizar que nao existe caminho ente os nos

		            System.out.println("Nao existe caminho entre " + origin.getLabel() + " e "

		                    + dest.getLabel());

		            flag = true;

		        }*/
			
			
			return null; //nao coloquei uma lista de nó, porque no dijkstra tu fez diferente.
		}

		private void printPathL(ArrayList<String> listVisitados, String origin, String dest) {
			for (String string : listVisitados) {
				System.out.print(string + ",");
				//System.out.println(",");
			}
			System.out.println(" ");
			
		}

		private void printPath(ArrayList<Node> visitados) {
			for (Node node : visitados) {
				System.out.println(node +",");
			}
			System.out.println(" ");
		}
		
		
}

	

	

