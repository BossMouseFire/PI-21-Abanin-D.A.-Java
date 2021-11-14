package LaboratoriesJava;

import LaboratoriesJava.enums.BombForms;
import LaboratoriesJava.transport.Bomber;
import LaboratoriesJava.transport.Plane;
import LaboratoriesJava.transport.Vehicle;

import javax.swing.*;
import java.awt.*;

public class FormHangar extends JFrame {
    private JPanel panel;
    private JPanel panelHangar;
    private JPanel panelInstruments;
    private JButton buttonSetPlane;
    private JButton buttonSetBomber;
    private JTextField fieldNumberPlane;
    private JButton buttonTakenPlane;
    private JTextField fieldNumberEqualPlane;
    private JButton buttonNotEqualsPlane;
    private JButton buttonEqualsPlane;
    private JTextField inputHangar;
    private JButton buttonAddHangar;
    DefaultListModel<String> listModel = new DefaultListModel<>();
    private JList<String> listHangars;
    private JButton buttonDelHangar;
    private JButton buttonTakeList;
    private JTextField inputPlaneToList;
    private JButton buttonAddList;

    private final HangarCollection hangarCollection;


    @Override
    public void paint(Graphics g) {
        super.paint(g);
        assert listHangars != null;
        if (listHangars.getSelectedIndex() > -1) {
            Graphics2D graphic2d = (Graphics2D) g;
            hangarCollection.getHangar(listHangars.getSelectedValue()).Draw(graphic2d);
        }
    }

    public FormHangar() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        getContentPane().add(panel);
        hangarCollection = new HangarCollection(700, 500);
        listHangars.setModel(listModel);
        buttonSetPlane.addActionListener((e) -> {
            Color newColor = JColorChooser.showDialog(null, "Выберите цвет", Color.RED);
            if (newColor != null) {
                Plane plane = new Plane(500, 100, newColor);
                Hangar<Vehicle> hangar = hangarCollection.getHangar(listHangars.getSelectedValue());
                if (hangar.addPlane(plane)) {
                    repaint();
                } else {
                    JOptionPane.showMessageDialog(null, "Ангар заполнен");
                }
            }
        });
        buttonSetBomber.addActionListener((e) -> {
            Color mainColor = JColorChooser.showDialog(null, "Выберите основной цвет", Color.RED);
            if (mainColor != null) {
                Color addColor = JColorChooser.showDialog(null, "Выберите дополнительный цвет", Color.RED);
                if (addColor != null) {
                    Bomber bomber = new Bomber(500, true, true, 100, mainColor, addColor, BombForms.Rocket);
                    Hangar<Vehicle> hangar = hangarCollection.getHangar(listHangars.getSelectedValue());
                    if (hangar.addPlane(bomber)) {
                        repaint();
                    } else {
                        JOptionPane.showMessageDialog(null, "Ангар заполнен");
                    }
                }
            }
        });
        buttonTakenPlane.addActionListener((e) -> {
            if (!fieldNumberPlane.getText().equals("")) {
                int numberPlane = Integer.parseInt(fieldNumberPlane.getText());
                Hangar<Vehicle> hangar = hangarCollection.getHangar(listHangars.getSelectedValue());
                Vehicle transport = hangar.removePlane(numberPlane);
                if (transport != null) {
                    FormBomber formBomber = new FormBomber();
                    formBomber.setPlane(transport);
                    formBomber.setSize(700, 500);
                    formBomber.setModal(true);
                    formBomber.setVisible(true);

                    repaint();
                }
            }
        });
        buttonEqualsPlane.addActionListener((e) -> {
            if (!fieldNumberEqualPlane.getText().equals("")) {
                int numberPlane = Integer.parseInt(fieldNumberEqualPlane.getText());
                Hangar<Vehicle> hangar = hangarCollection.getHangar(listHangars.getSelectedValue());
                JOptionPane.showMessageDialog(null, "Количество равных переданному объекту самолётов: " + hangar.equalsPlane(numberPlane));
            }
        });
        buttonNotEqualsPlane.addActionListener((e) -> {
            if (!fieldNumberEqualPlane.getText().equals("")) {
                int numberPlane = Integer.parseInt(fieldNumberEqualPlane.getText());
                Hangar<Vehicle> hangar = hangarCollection.getHangar(listHangars.getSelectedValue());
                JOptionPane.showMessageDialog(null, "Количество не равных переданному объекту самолётов: " + hangar.notEqualsPlane(numberPlane));
            }
        });
        buttonAddHangar.addActionListener((e) -> {
            if (!inputHangar.getText().equals("")) {
                hangarCollection.addHangar(inputHangar.getText());
                ReloadListModel();
                listHangars.updateUI();
            }
        });
        buttonDelHangar.addActionListener((e) -> {
            hangarCollection.delHangar(listHangars.getSelectedValue());
            ReloadListModel();
            listHangars.updateUI();
            repaint();
        });

        listHangars.addListSelectionListener((e) -> {
            repaint();
        });

        buttonAddList.addActionListener(e -> {
            Hangar<Vehicle> hangar = hangarCollection.getHangar(listHangars.getSelectedValue());
            Vehicle plane = hangar.getPlane(Integer.parseInt(inputPlaneToList.getText()));
            hangarCollection.listPlanes.add(plane);
        });

        buttonTakeList.addActionListener(e -> {
            Vehicle transport = hangarCollection.listPlanes.removeLast();
            if (transport != null) {
                FormBomber formBomber = new FormBomber();
                formBomber.setPlane(transport);
                formBomber.setSize(700, 500);
                formBomber.setModal(true);
                formBomber.setVisible(true);

                repaint();
            }
        });
    }

    public void ReloadListModel(){
        listModel.removeAllElements();
        for(String hangarName: hangarCollection.getKeys()){
            listModel.addElement(hangarName);
        }
    }

}
