package LaboratoriesJava.forms;

import LaboratoriesJava.enums.BombForms;
import LaboratoriesJava.interfaces.Eventual;
import LaboratoriesJava.transport.Bomber;
import LaboratoriesJava.transport.Plane;
import LaboratoriesJava.transport.Vehicle;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class FormPlaneConfig extends JDialog {
    private JLabel labelPlane;
    private JLabel labelBomber;
    private JPanel panel;
    private JPanel panelShow;
    private JLabel labelPanelShow;
    private JPanel panelChooseColor;
    private JLabel labelMainColor;
    private JLabel labelAddColor;
    private JCheckBox checkBoxRadar;
    private JCheckBox checkBoxBombs;
    private JSpinner spinnerSpeed;
    private JSpinner spinnerWeight;
    private JLabel labelOvalBombs;
    private JLabel labelRectangleBombs;
    private JLabel labelRocketBombs;
    private JButton buttonAddPlane;
    private JButton buttonClose;

    BombForms bombForms = null;
    private Vehicle plane = null;

    private Eventual eventParkPlane;

    public void paint(Graphics g) {
        super.paint(g);
        if (plane != null) {
            Graphics2D graphic2d = (Graphics2D) g;
            plane.setPosition(400, 35, 100, 100);
            plane.drawTransport(graphic2d);
        }
    }

    public FormPlaneConfig(){
        initUI();
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
                getContentPane().add(panel);
            }
        });
        buttonAddPlane.addActionListener(e -> {
            if (eventParkPlane != null && plane != null) {
                eventParkPlane.parkPlane(plane);
                setVisible(false);
                dispose();
            }
        });
        buttonClose.addActionListener(e -> {
            plane = null;
            setVisible(false);
            dispose();
        });
    }

    public void showDialog(){
        setSize(700, 500);
        setModal(true);
        setVisible(true);
    }

    private void initUI(){
        labelPlane.setTransferHandler(new TransferHandler("text"));
        labelPlane.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                var c = (JComponent) e.getSource();
                var handler = c.getTransferHandler();
                handler.exportAsDrag(c, e, TransferHandler.COPY);
            }
        });

        labelBomber.setTransferHandler(new TransferHandler("text"));
        labelBomber.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                var c = (JComponent) e.getSource();
                var handler = c.getTransferHandler();
                handler.exportAsDrag(c, e, TransferHandler.COPY);
            }
        });

        labelPanelShow.setMinimumSize(new Dimension(250, 125));
        labelPanelShow.setMaximumSize(new Dimension(250, 125));

        labelPanelShow.setTransferHandler(new TransferHandler("text"));
        labelPanelShow.addPropertyChangeListener("text", propertyChangeEvent -> {
            if (labelPanelShow.getText().equals("Обычный самолет")) {
                plane = new Plane(100, 100, Color.BLACK);
            }
            if (labelPanelShow.getText().equals("Бомбардировщик")) {
                plane = new Bomber(100, true, true, 100, Color.BLACK, Color.RED, BombForms.Rectangle);
            }
            if (plane instanceof Bomber && bombForms != null) {
                ((Bomber) plane).changeBombsType(bombForms);
            }
            repaint();
            labelPanelShow.setText("");
        });

        for(Component component: panelChooseColor.getComponents()) {
            if (component instanceof JPanel) {
                ((JPanel) component).setTransferHandler(new TransferHandler("background"));
                component.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mousePressed(MouseEvent e) {
                        var c = (JComponent) e.getSource();
                        var handler = c.getTransferHandler();
                        handler.exportAsDrag(c, e, TransferHandler.COPY);
                    }
                });
            }
        }

        labelMainColor.setTransferHandler(new TransferHandler("background"));
        labelMainColor.addPropertyChangeListener("background", propertyChangeEvent -> {
            Color color = (Color) propertyChangeEvent.getNewValue();
            if (plane != null) {
                plane.setMainColor(color);
                repaint();
            }
        });

        labelAddColor.setTransferHandler(new TransferHandler("background"));
        labelAddColor.addPropertyChangeListener("background", propertyChangeEvent -> {
            Color color = (Color) propertyChangeEvent.getNewValue();
            if (plane != null && plane instanceof Bomber) {
                Bomber bomber = (Bomber) plane;
                bomber.setAddColor(color);
                repaint();
            }
        });

        checkBoxRadar.addActionListener(e -> {

            if (plane != null && plane instanceof Bomber) {
                Bomber bomber = (Bomber) plane;
                bomber.isStateGun = checkBoxRadar.isSelected();
                repaint();
            }
        });

        checkBoxBombs.addActionListener(e -> {
            if (plane != null && plane instanceof Bomber) {
                Bomber bomber = (Bomber) plane;
                bomber.isStateBombs = checkBoxBombs.isSelected();
                repaint();
            }
        });

        spinnerSpeed.addChangeListener(e -> {
            if (plane != null) {
                plane.maxSpeed = Integer.parseInt(spinnerSpeed.getValue().toString());
            }
        });

        spinnerWeight.addChangeListener(e -> {
            if (plane != null) {
                plane.weight = Integer.parseInt(spinnerWeight.getValue().toString());
            }
        });

        labelOvalBombs.setTransferHandler(new TransferHandler("text"));
        labelOvalBombs.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                var c = (JComponent) e.getSource();
                var handler = c.getTransferHandler();
                handler.exportAsDrag(c, e, TransferHandler.COPY);
                if (plane != null && plane instanceof Bomber) {
                    bombForms = BombForms.Oval;
                }
            }
        });

        labelRectangleBombs.setTransferHandler(new TransferHandler("text"));
        labelRectangleBombs.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                var c = (JComponent) e.getSource();
                var handler = c.getTransferHandler();
                handler.exportAsDrag(c, e, TransferHandler.COPY);
                if (plane != null && plane instanceof Bomber) {
                    bombForms = BombForms.Rectangle;
                }
            }
        });

        labelRocketBombs.setTransferHandler(new TransferHandler("text"));
        labelRocketBombs.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                var c = (JComponent) e.getSource();
                var handler = c.getTransferHandler();
                handler.exportAsDrag(c, e, TransferHandler.COPY);
                if (plane != null && plane instanceof Bomber) {
                    bombForms = BombForms.Rocket;
                }
            }
        });
    }

    public void addEvent(Eventual event) {
        eventParkPlane = event;
    }
}
