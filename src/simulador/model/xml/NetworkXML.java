package simulador.model.xml;

import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.TransformerFactoryConfigurationError;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import simulador.model.link.Link;
import simulador.model.network.SimpleNetwork;
import simulador.model.node.Node;

public class NetworkXML {
	private DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
	
	public boolean saveNetwork(SimpleNetwork network, String fileName) {
		File outputFile = new File(fileName);
		
		dbf.setNamespaceAware(true);
		dbf.setValidating(true);

		try {
			DocumentBuilder docBuilder = dbf.newDocumentBuilder();
			
			Document doc = docBuilder.newDocument();
			doc.setXmlStandalone(true);
			
			Element nodesEl = doc.createElement("nodes");
			
			for(Node node : network.getNodes()) {
//				Element nodeId = doc.createElement("id");
//				nodeId.appendChild(doc.createTextNode(node.getId()));
//				
//				Element nodeLabel = doc.createElement("label");
//				nodeLabel.appendChild(doc.createTextNode(node.getLabel()));
//				
//				Element nodeEl = doc.createElement("node");
//				nodeEl.appendChild(nodeId);
//				nodeEl.appendChild(nodeLabel);
//				
				
				Element nodeEl = doc.createElement("node");
				nodeEl.setAttribute("id", node.getId());
				nodeEl.setAttribute("label", node.getLabel());
				
				nodesEl.appendChild(nodeEl);
			}
			
			List<Link> links = network.getLinksList();
			
			Element linksEl = doc.createElement("links");
			for(Link l : links) {
//				Element linkId = doc.createElement("id");
//				linkId.appendChild(doc.createTextNode(l.getId()));
//				
//				Element linkLength = doc.createElement("length");
//				linkLength.appendChild(doc.createTextNode(l.getLength().toString()));
//				
//				Element linkIsBidirectional = doc.createElement("isBidirectional");
//				linkIsBidirectional.appendChild(doc.createTextNode(l.getIsBidirectional().toString()));
//				
//				Element linkFrom = doc.createElement("from");
//				linkFrom.appendChild(doc.createTextNode(l.getFrom().getId()));
//				
//				Element linkTo = doc.createElement("to");
//				linkTo.appendChild(doc.createTextNode(l.getTo().getId()));
//				
//				Element linkEl = doc.createElement("link");
//				linkEl.appendChild(linkId);
//				linkEl.appendChild(linkLength);
//				linkEl.appendChild(linkIsBidirectional);
//				linkEl.appendChild(linkFrom);
//				linkEl.appendChild(linkTo);
				
				Element linkEl = doc.createElement("link");
				linkEl.setAttribute("id", l.getId());
				linkEl.setAttribute("length", l.getLength().toString());
				linkEl.setAttribute("isBidirectional", l.getIsBidirectional().toString());
				linkEl.setAttribute("from", l.getFrom().getId());
				linkEl.setAttribute("to", l.getTo().getId());
				
				linksEl.appendChild(linkEl);
			}
			
			Element root = doc.createElement("network");
			root.setAttribute("name", network.getLabel());
			
			doc.appendChild(root);
			
			
			root.appendChild(nodesEl);
			root.appendChild(linksEl);
			
			
			Transformer transformer = TransformerFactory.newInstance().newTransformer();
			transformer.setOutputProperty(OutputKeys.INDENT, "yes");
			transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");
			
			transformer.transform(new DOMSource(doc), new StreamResult(outputFile));
			
			return true;
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (TransformerConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (TransformerFactoryConfigurationError e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (TransformerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}
	
	public SimpleNetwork loadNetwork(String filePath) {
		File file = new File(filePath);
		try {
			DocumentBuilder docBuilder = dbf.newDocumentBuilder();
			Document doc = docBuilder.parse(file);
			
			Element networkRoot = doc.getDocumentElement();
			String name = networkRoot.getAttribute("name");
			SimpleNetwork network = new SimpleNetwork(name);
			
			Element nodesRoot = (Element) networkRoot.getElementsByTagName("nodes").item(0);
			NodeList nodes = nodesRoot.getElementsByTagName("node");
			int nodesLen = nodes.getLength();
			for(int i = 0; i < nodesLen; ++i) {
				Element node = (Element) nodes.item(i);
				
				Node n = new Node(node.getAttribute("id"), node.getAttribute("label"));
				network.addNode(n);
			}
			
			Element linksRoot = (Element) networkRoot.getElementsByTagName("links").item(0);
			NodeList links = linksRoot.getElementsByTagName("link");
			int linksLen = links.getLength();
			for(int i = 0; i < linksLen; ++i) {
				Element link = (Element) links.item(i);
				Node nodeTo = network.getNodeFromId(link.getAttribute("to"));
				Node nodeFrom = network.getNodeFromId(link.getAttribute("from"));
				
				Float linkLength = Float.parseFloat(link.getAttribute("length"));
				Boolean isLinkBidirectional = Boolean.parseBoolean(link.getAttribute("isBidirectional"));
				
				Link l = new Link(link.getAttribute("id"), linkLength, 
						isLinkBidirectional);
				
				nodeFrom.addLink(l);
				nodeTo.addLink(l);
			}
			return network;
		} catch (SAXException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
}
