package simulador.view.main;

import javax.swing.JPanel;

import simulador.model.algorithms.Dijkstra;
import simulador.model.link.Link;
import simulador.model.network.SimpleNetwork;
import simulador.model.node.Node;
import simulador.model.xml.NetworkXML;

import javax.swing.JDesktopPane;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.JOptionPane;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Graphics;
import java.awt.GraphicsEnvironment;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JScrollPane;
import javax.swing.JToolBar;
import javax.swing.filechooser.FileNameExtensionFilter;

import java.awt.event.ActionListener;

public class MainView extends JComponent {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private int HIGH;
	private int WIDE;
	private Point mousePt;
	
	
//    private static final int HIGH = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getDisplayMode().getHeight() - 60;
//    private static final int WIDE = (int) (HIGH * 1.33333);
    private static final int RADIUS = 30;
    
	 
    private Rectangle mouseRect = new Rectangle();
    private boolean selecting = false;
    
    private SimpleNetwork network = new SimpleNetwork();
    
    private NetworkXML netXml = new NetworkXML();
    
    private JButton btnAdd;
    private JButton btnDelete;
    private JButton btnLink;
    private JButton btnSave;
    private JButton btnImport;
    private JButton btnSimular;
    
	public MainView() {
//		this.HIGH = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getDisplayMode().getHeight() - 60;
//		this.WIDE = (int) (HIGH * 1.33333);
		
		this.HIGH = 360;
		this.WIDE = 660;
		
		this.mousePt = new Point(WIDE / 2, HIGH / 2);
		this.setBounds(0, 0, WIDE, HIGH);
		
		JToolBar toolBar = new JToolBar();
		toolBar.setFloatable(false);
		toolBar.setBounds(0, 0, 660, 34);
		add(toolBar);
		
		btnAdd = new JButton("Add");
		toolBar.add(btnAdd);
		
		btnDelete = new JButton("Delete");
		toolBar.add(btnDelete);
		
		btnLink = new JButton("Link");
		btnLink.setEnabled(false);
		toolBar.add(btnLink);
		
		btnSave = new JButton("Save");
		toolBar.add(btnSave);
		
		btnImport = new JButton("Import");
		toolBar.add(btnImport);
		
		btnSimular = new JButton("Simular");
		toolBar.add(btnSimular);
		
		// listener add node
		btnAdd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				network.selectNone();
				Point p = mousePt.getLocation();
				String name = "Link " + network.getLinks().size() + 1;
				Node node = new Node(name, p, RADIUS);
				node.setSelected(true);
				network.addNode(node);
				repaint();
			}
		});
		
		btnDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				network.removeSelectedNodes();
	            repaint();
	        }
		});
		
		btnLink.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				Float dist = null;
				do {
					String distStr = JOptionPane.showInputDialog("Qual a distância entre os nós?");
					if(distStr == null) {
						break;
					}
					dist = Float.parseFloat(distStr);
				} while(dist == null || dist.isNaN());
				if(dist == null) { return ; }
				List<Node> selected = network.getSelectedNodes();
	            if(selected.size() > 1) {
	            	for(int i = 0; i < selected.size() - 1; ++i) {
	            		Node n1 = selected.get(i);
	            		Node n2 = selected.get(i + 1);
	            		Link link = new Link(dist, n1, n2);
	            		network.addLink(link);
	                }
	            }
	            repaint();
				
			}
		});
		
		this.addMouseListener(new MouseHandler());
        this.addMouseMotionListener(new MouseMotionHandler());
	}
	
	@Override
    public Dimension getPreferredSize() {
        return new Dimension(WIDE, HIGH);
    }

    @Override
    public void paintComponent(Graphics g) {
        g.setColor(new Color(0x00f0f0f0));
        g.fillRect(0, 0, getWidth(), getHeight());
        for (Node n : this.network.getNodes()) {
        	n.draw(g);
        }
        for(Link l : this.network.getLinks()) {
            l.draw(g);
        }
        if(selecting) {
            g.setColor(Color.darkGray);
            g.drawRect(mouseRect.x, mouseRect.y,
                mouseRect.width, mouseRect.height);
        }
    }

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				MainView mView = new MainView();
				
				JFrame frame = new JFrame("Simulador");
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.setBounds(100, 100, mView.WIDE, mView.HIGH);
                frame.setResizable(false);
                frame.setLocationRelativeTo(null);
                
                mView.setBounds(frame.getBounds());
                frame.getContentPane().add(mView);
                frame.setLocationByPlatform(true);
                frame.setVisible(true);
                
                mView.btnImport.addActionListener(new ActionListener() {
        			@Override
        			public void actionPerformed(ActionEvent arg0) {
        				JFileChooser fc = new JFileChooser();
        				fc.setFileFilter(new FileNameExtensionFilter("Arquivo XML", "xml"));
        				//remove a extensão 'Todos os arquivos'
        				fc.setAcceptAllFileFilterUsed(false);
        				int r = fc.showOpenDialog(null);
        				
        				if(r == JFileChooser.APPROVE_OPTION) {
        					String path = fc.getSelectedFile().getAbsolutePath();
        					mView.network = mView.netXml.loadNetwork(path);
        					mView.repaint();
        				}
        			}
        		});
                
                
                
                mView.btnSave.addActionListener(new ActionListener() {
                	@Override
                	public void actionPerformed(ActionEvent arg0) {
						JFileChooser fc = new JFileChooser();
        				fc.setFileFilter(new FileNameExtensionFilter("Arquivo XML", "xml"));
        				//remove a extensão 'Todos os arquivos'
        				fc.setAcceptAllFileFilterUsed(false);
        				int r = fc.showOpenDialog(null);
        				
        				if(r == JFileChooser.APPROVE_OPTION) {
        					String path = fc.getSelectedFile().getAbsolutePath();
        					if(!path.endsWith(".xml")) {
        						path += ".xml";
        					}
        					mView.netXml.saveNetwork(mView.network, path);
        				}
					}
				});
                
                mView.btnSimular.addActionListener(new ActionListener() {
        			@Override
        			public void actionPerformed(ActionEvent e) {
        				Integer value = null;
        				do {
        					String valueStr = JOptionPane.showInputDialog("Digite a quantidade de simulações a serem realizadas (valor inteiro):");
        					if(valueStr == null) {
        						break;
        					}
        					value = Integer.parseInt(valueStr);
        				} while(value == null);
        				if(value == null || value < 0) { return ; }
        				
        				File file = new File("resultados_simulacao.txt");
        				try {
        					BufferedWriter writter = new BufferedWriter(new FileWriter(file));
        					for(int i = 0; i < value; ++i) {
        						int max = mView.network.getLinks().size();
        						int originIndex = new Random().nextInt(max);
        						int destIndex; 
        						do {
        							destIndex = new Random().nextInt(max);
        						} while(destIndex == originIndex);

        						Node origin = mView.network.getNodes().get(originIndex);
        						Node dest = mView.network.getNodes().get(destIndex);
        						Map<String, Float> result = (new Dijkstra().getMinDistance(mView.network.getNodes(), origin));
        						writter.write(result.get(dest.getId()) + "\n" );
        						writter.flush();
//        						System.out.println("min: " + result.get(dest.getId()));
        					}
        					writter.close();
        				} catch(Exception ex) {
        					ex.printStackTrace();
        					System.out.println("Exceção");
        				}
        				
        				System.out.println("Finalizada");
        			}
        		});
			}
		});
	}
	
	
	private class MouseHandler extends MouseAdapter {
        @Override
        public void mouseReleased(MouseEvent e) {
            selecting = false;
            mouseRect.setBounds(0, 0, 0, 0);
            if (e.isPopupTrigger()) {
//                showPopup(e);
            }
            e.getComponent().repaint();
        }

        @Override
        public void mousePressed(MouseEvent e) {
            mousePt = e.getPoint();
            if (e.isShiftDown()) {
                network.selectToggle(mousePt);
            } else if (e.isPopupTrigger()) {
                network.selectOne(mousePt);
//                showPopup(e);
            } else if (network.selectOne(mousePt)) {
                selecting = false;
            } else {
                network.selectNone();
                selecting = true;
            }
            
            List<?> selected = network.getSelectedNodes();
            btnLink.setEnabled(selected.size() == 2);
            btnDelete.setEnabled(selected.size() >= 1);
            e.getComponent().repaint();
        }

//        private void showPopup(MouseEvent e) {
//            control.popup.show(e.getComponent(), e.getX(), e.getY());
//        }
    }

    private class MouseMotionHandler extends MouseMotionAdapter {

        Point delta = new Point();

        @Override
        public void mouseDragged(MouseEvent e) {
            if (selecting) {
                mouseRect.setBounds(
                    Math.min(mousePt.x, e.getX()),
                    Math.min(mousePt.y, e.getY()),
                    Math.abs(mousePt.x - e.getX()),
                    Math.abs(mousePt.y - e.getY()));
                network.selectRect(mouseRect);
            } else {
                delta.setLocation(
                    e.getX() - mousePt.x,
                    e.getY() - mousePt.y);
                network.updatePosition(delta);
                mousePt = e.getPoint();
            }
            e.getComponent().repaint();
        }
    }
}
