package LaboratoriesJava.forms;

import LaboratoriesJava.Hangar;
import LaboratoriesJava.HangarCollection;
import LaboratoriesJava.enums.BombForms;
import LaboratoriesJava.transport.Bomber;
import LaboratoriesJava.transport.Plane;
import LaboratoriesJava.transport.Vehicle;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

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
    private JPanel panelMenu;

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
            formPlaneConfig.addEvent(plane -> {
                if (plane != null) {
                    Hangar<Vehicle> hangar = hangarCollection.getHangar(listHangars.getSelectedValue());
                    if (hangar.addPlane(plane)) {
                        repaint();
                    } else {
                        JOptionPane.showMessageDialog(null, "Ангар заполнен");
                    }
                }
            });
            formPlaneConfig.showDialog();
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

    private JMenu createFileMenu()
    {
        JMenu file = new JMenu("Файл");
        JMenuItem save = new JMenuItem("Сохранить");
        JMenuItem load = new JMenuItem("Загрузить");
        JMenuItem saveHangar = new JMenuItem("Сохранить ангар");
        JMenuItem loadHangar = new JMenuItem("Загрузить ангар");
        file.add(save);
        file.addSeparator();
        file.add(load);
        file.addSeparator();
        file.add(saveHangar);
        file.addSeparator();
        file.add(loadHangar);

        save.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                JFileChooser fileChooser = new JFileChooser();
                File file = null;
                int ret = fileChooser.showDialog(null, "Открыть файл для записи");
                if(ret == JFileChooser.APPROVE_OPTION){
                    file = fileChooser.getSelectedFile();
                }
                try {
                    hangarCollection.saveData(file);
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(null,  "Файл не выбран", "Информация", JOptionPane.INFORMATION_MESSAGE);
                }
            }
        });

        saveHangar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                if (listHangars.getSelectedIndex() > -1) {
                    JFileChooser fileChooser = new JFileChooser();
                    File file = null;
                    int ret = fileChooser.showDialog(null, "Открыть файл для записи");
                    if(ret == JFileChooser.APPROVE_OPTION){
                        file = fileChooser.getSelectedFile();
                    }
                    try {
                        hangarCollection.saveDataHangar(file, listHangars.getSelectedValue());
                    }
                    catch (Exception e) {
                        JOptionPane.showMessageDialog(null,  "Файл не выбран", "Информация", JOptionPane.INFORMATION_MESSAGE);
                    }
                } else {
                    JOptionPane.showMessageDialog(null,  "Выберите ангар", "Ошибка", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        load.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                JFileChooser fileChooser = new JFileChooser();
                File file = null;
                int ret = fileChooser.showDialog(null, "Открыть файл для чтения");
                if(ret == JFileChooser.APPROVE_OPTION){
                    file = fileChooser.getSelectedFile();
                }
                try {
                    boolean result = hangarCollection.loadData(file);
                    if (result) {
                        ReloadListModel();
                        repaint();
                    }
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(null,  "Файл не выбран", "Информация", JOptionPane.INFORMATION_MESSAGE);
                }
            }
        });

        loadHangar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg) {
                JFileChooser fileChooser = new JFileChooser();
                File file = null;
                int ret = fileChooser.showDialog(null, "Открыть файл для чтения");
                if(ret == JFileChooser.APPROVE_OPTION){
                    file = fileChooser.getSelectedFile();
                }
                try {
                    boolean result = hangarCollection.loadDataHangar(file);
                    if (result) {
                        ReloadListModel();
                        repaint();
                    }
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(null,  "Файл не выбран", "Информация", JOptionPane.INFORMATION_MESSAGE);
                }
            }
        });

        return file;
    }

    private void createUIComponents() {
        panelMenu = new JPanel();
        JMenuBar menuBar = new JMenuBar();
        menuBar.add(createFileMenu());
        panelMenu.add(menuBar);
    }
}
