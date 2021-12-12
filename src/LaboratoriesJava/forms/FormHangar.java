package LaboratoriesJava.forms;

import LaboratoriesJava.Hangar;
import LaboratoriesJava.HangarCollection;
import LaboratoriesJava.customExcep.HangarNotFoundException;
import LaboratoriesJava.customExcep.HangarOverflowException;
import LaboratoriesJava.enums.BombForms;
import LaboratoriesJava.transport.Bomber;
import LaboratoriesJava.transport.Plane;
import LaboratoriesJava.transport.Vehicle;
import org.apache.log4j.Logger;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
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

    private static final Logger logger = Logger.getLogger(FormHangar.class);

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
                    try {
                        hangar.addPlane(plane);
                        logger.info("Добавление в ангар " + listHangars.getSelectedValue() + " самолёт " + plane);
                        repaint();
                    }
                    catch (HangarOverflowException ex) {
                        logger.warn("Ошибка при добавлении в ангар " + listHangars.getSelectedValue() + ": " + ex.getMessage());
                        JOptionPane.showMessageDialog(null, "Ангар заполнен");
                    }
                    catch (Exception ex) {
                        logger.fatal("Неизвестная ошибка при добавлении в ангар: " + ex.getMessage());
                        JOptionPane.showMessageDialog(null, "Неизвестная ошибка при добавлении");
                    }
                }
            });
            formPlaneConfig.showDialog();
        });

        buttonAddHangar.addActionListener((e) -> {
            if (!inputHangar.getText().equals("")) {
                hangarCollection.addHangar(inputHangar.getText());
                logger.info("Добавили парковку "  + inputHangar.getText());
                ReloadListModel();
                listHangars.updateUI();
            }
        });
        buttonDelHangar.addActionListener((e) -> {
            hangarCollection.delHangar(listHangars.getSelectedValue());
            logger.info("Удалили ангар " + listHangars.getSelectedValue());
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

            try {
                Vehicle plane = hangar.removePlane(numberPlane);
                hangarCollection.listPlanes.add(plane);
                logger.info("Добавление самолёта в список: " + plane);
                repaint();
            }
            catch (HangarNotFoundException ex) {
                logger.warn("Ошибка при добавлении самолёта в список: " + ex.getMessage());
                JOptionPane.showMessageDialog(null,  "Отсутствует элемент под индексом " + numberPlane, "Ошибка", JOptionPane.ERROR_MESSAGE);
            }
            catch (Exception ex) {
                logger.fatal("Неизвестная Ошибка при добавлении самолёта в список: "  + ex.getMessage());
                JOptionPane.showMessageDialog(null,  "Неизвестная ошибка", "Ошибка", JOptionPane.ERROR_MESSAGE);
            }
        });

        buttonTakeList.addActionListener(e -> {
            if (hangarCollection.listPlanes.size() != 0){
                Vehicle transport = hangarCollection.listPlanes.removeLast();
                logger.info("Изъятие самолёта из списка: " + transport);

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
        JMenu fileMenu = new JMenu("Файл");
        JMenuItem save = new JMenuItem("Сохранить");
        JMenuItem load = new JMenuItem("Загрузить");
        JMenuItem saveHangar = new JMenuItem("Сохранить ангар");
        JMenuItem loadHangar = new JMenuItem("Загрузить ангар");
        fileMenu.add(save);
        fileMenu.addSeparator();
        fileMenu.add(load);
        fileMenu.addSeparator();
        fileMenu.add(saveHangar);
        fileMenu.addSeparator();
        fileMenu.add(loadHangar);

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
                    logger.info("Сохранён файл");
                }
                catch (IOException ex) {
                    logger.error("Ошибка при сохранении файла: " + ex.getMessage());
                    JOptionPane.showMessageDialog(null,  ex.getMessage(), "Информация", JOptionPane.ERROR_MESSAGE);
                }
                catch (Exception ex) {
                    logger.fatal("Неизвестная ошибка при сохранении файла: " + ex.getMessage());
                    JOptionPane.showMessageDialog(null,  "Неизвестная ошибка", "Информация", JOptionPane.ERROR_MESSAGE);
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
                        logger.info("Сохранён ангар");
                    }
                    catch (IOException ex) {
                        logger.error("Ошибка при сохранении ангара: " + ex.getMessage());
                        JOptionPane.showMessageDialog(null,  ex.getMessage(), "Информация", JOptionPane.INFORMATION_MESSAGE);
                    }
                    catch (Exception ex) {
                        logger.fatal("Неизвестная Ошибка при сохранении ангара: " + ex.getMessage());
                        JOptionPane.showMessageDialog(null,  "Неизвестная ошибка", "Информация", JOptionPane.ERROR_MESSAGE);
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
                    hangarCollection.loadData(file);
                    logger.info("Загрузка файла");
                    ReloadListModel();
                    repaint();
                }
                catch (HangarOverflowException ex) {
                    logger.warn("Ошибка при загрузке ангара: " + ex);
                    JOptionPane.showMessageDialog(null,  "При загрузке ангар был переполен", "Предупреждение", JOptionPane.WARNING_MESSAGE);
                }
                catch (IOException ex) {
                    logger.error("Ошибка при загрузке файла: " + ex);
                    JOptionPane.showMessageDialog(null,  ex.getMessage(), "Предупреждение", JOptionPane.WARNING_MESSAGE);
                }
                catch (Exception ex) {
                    logger.fatal("Неизвестная ошибка при загрузке файла: " + ex);
                    JOptionPane.showMessageDialog(null,  "Неизвестная ошибка", "Ошибка", JOptionPane.ERROR_MESSAGE);
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
                    hangarCollection.loadDataHangar(file);
                    logger.info("Загрузка ангара");
                    ReloadListModel();
                    repaint();
                }
                catch (HangarOverflowException ex) {
                    logger.warn("Ошибка при загрузке ангара: " + ex);
                    JOptionPane.showMessageDialog(null,  "При загрузке ангар был переполен", "Предупреждение", JOptionPane.WARNING_MESSAGE);
                }
                catch (IOException ex) {
                    logger.error("Ошибка при загрузке ангара: " + ex);
                    JOptionPane.showMessageDialog(null,  ex.getMessage(), "Предупреждение", JOptionPane.WARNING_MESSAGE);
                }
                catch (Exception ex) {
                    logger.fatal("Неизвестная ошибка при загрузке ангара: " + ex);
                    JOptionPane.showMessageDialog(null,  "Неизвестная ошибка", "Ошибка", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        return fileMenu;
    }

    private void createUIComponents() {
        panelMenu = new JPanel();
        JMenuBar menuBar = new JMenuBar();
        menuBar.add(createFileMenu());
        panelMenu.add(menuBar);
    }
}
