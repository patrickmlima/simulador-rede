package simulador.view.panel;

import java.awt.Color;
import java.awt.FlowLayout;

import javax.swing.Action;
import javax.swing.JButton;
import javax.swing.JToolBar;

public class ControlPanel extends JToolBar {

//    private Action newNode = new NewNodeAction("New");
//    private Action clearAll = new ClearAction("Clear");
//    private Action kind = new KindComboAction("Kind");
//    private Action color = new ColorAction("Color");
//    private Action connect = new ConnectAction("Connect");
//    private Action delete = new DeleteAction("Delete");
//    private Action random = new RandomAction("Random");
//    private JComboBox kindCombo = new JComboBox();
//    private ColorIcon hueIcon = new ColorIcon(Color.blue);
//    private JPopupMenu popup = new JPopupMenu();

    ControlPanel() {
        this.setLayout(new FlowLayout(FlowLayout.LEFT));
        this.setBackground(Color.lightGray);

//        this.add(defaultButton);
//        this.add(new JButton(clearAll));
//        this.add(kindCombo);
//        this.add(new JButton(color));
//        this.add(new JLabel(hueIcon));
//        JSpinner js = new JSpinner();
//        js.setModel(new SpinnerNumberModel(RADIUS, 5, 100, 5));
//        js.addChangeListener(new ChangeListener() {
//
//            @Override
//            public void stateChanged(ChangeEvent e) {
//                JSpinner s = (JSpinner) e.getSource();
//                radius = (Integer) s.getValue();
//                Node.updateRadius(nodes, radius);
//                GraphPanel.this.repaint();
//            }
//        });
//        this.add(new JLabel("Size:"));
//        this.add(js);
//        this.add(new JButton(random));
//
//        popup.add(new JMenuItem(newNode));
//        popup.add(new JMenuItem(color));
//        popup.add(new JMenuItem(connect));
//        popup.add(new JMenuItem(delete));
//        JMenu subMenu = new JMenu("Kind");
//        for (Kind k : Kind.values()) {
//            kindCombo.addItem(k);
//            subMenu.add(new JMenuItem(new KindItemAction(k)));
//        }
//        popup.add(subMenu);
//        kindCombo.addActionListener(kind);
    }

//    class KindItemAction extends AbstractAction {
//
//        private Kind k;
//
//        public KindItemAction(Kind k) {
//            super(k.toString());
//            this.k = k;
//        }
//
//        @Override
//        public void actionPerformed(ActionEvent e) {
//            kindCombo.setSelectedItem(k);
//        }
//    }
}