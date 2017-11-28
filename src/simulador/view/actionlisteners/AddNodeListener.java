package simulador.view.actionlisteners;

import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JComponent;

import simulador.model.network.SimpleNetwork;
import simulador.model.node.Node;

public class AddNodeListener implements ActionListener {
	private JComponent view;
	private SimpleNetwork network;
	private Point mousePt;
	private int NODE_RADIUS;
	
	public AddNodeListener(JComponent view, SimpleNetwork network, Point mousePt, int NODE_RADIUS) {
		this.network = network;
		this.mousePt = mousePt;
		this.NODE_RADIUS = NODE_RADIUS;
	}
	
	public void actionPerformed(ActionEvent arg0) {
		network.selectNone();
		Point p = mousePt.getLocation();
		String name = "Link " + (network.getLinks().size() + 1);
		Node node = new Node(name, p, NODE_RADIUS);
		node.setSelected(true);
		network.addNode(node);
		this.view.repaint();
	}
}
