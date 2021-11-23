package LaboratoriesJava.forms;

import LaboratoriesJava.Hangar;
import LaboratoriesJava.HangarCollection;
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
            FormPlaneConfig formPlaneConfig = new FormPlaneConfig();
            Vehicle plane = formPlaneConfig.showDialog();
            if (plane != null) {
                Hangar<Vehicle> hangar = hangarCollection.getHangar(listHangars.getSelectedValue());
                if (hangar.addPlane(plane)) {
                    repaint();
                } else {
                    JOptionPane.showMessageDialog(null, "Ангар заполнен");
                }
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
            int numberPlane = Integer.parseInt(inputPlaneToList.getText());
            Hangar<Vehicle> hangar = hangarCollection.getHangar(listHangars.getSelectedValue());
            Vehicle plane = hangar.removePlane(numberPlane);
            if (plane != null){
                hangarCollection.listPlanes.add(plane);
                repaint();
            } else{
                JOptionPane.showMessageDialog(null,  "Отсутствует выбранный элемент", "Ошибка", JOptionPane.ERROR_MESSAGE);
            }
        });

        buttonTakeList.addActionListener(e -> {
            if (hangarCollection.listPlanes.size() != 0){
                Vehicle transport = hangarCollection.listPlanes.removeLast();

                FormBomber formBomber = new FormBomber();
                formBomber.setPlane(transport);
                formBomber.setSize(700, 500);
                formBomber.setModal(true);
                formBomber.setVisible(true);

                repaint();
            } else{
                JOptionPane.showMessageDialog(null,  "Отсутствует выбранный элемент", "Ошибка", JOptionPane.ERROR_MESSAGE);
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
