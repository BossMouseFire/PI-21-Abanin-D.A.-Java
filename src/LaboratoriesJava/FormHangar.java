package LaboratoriesJava;

import LaboratoriesJava.enums.BombForms;
import LaboratoriesJava.interfaces.IBombs;
import LaboratoriesJava.transport.Bomber;
import LaboratoriesJava.transport.Plane;

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

    private final Hangar<Plane, IBombs> parking;


    @Override
    public void paint(Graphics g) {
        super.paint(g);
        Graphics2D graphic2d = (Graphics2D) g;
        parking.Draw(graphic2d);
    }

    public FormHangar() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        getContentPane().add(panel);
        parking = new Hangar<>(700, 500);

        buttonSetPlane.addActionListener((e) -> {
            Color newColor = JColorChooser.showDialog(null, "Выберите цвет", Color.RED);
            if (newColor != null){
                Plane plane = new Plane(500, 100, newColor);
                if (parking.addPlane(plane) != -1){
                    repaint();
                }else{
                    JOptionPane.showMessageDialog(null, "Ангар заполнен");
                }
            }
        });
        buttonSetBomber.addActionListener((e) -> {
            Color mainColor = JColorChooser.showDialog(null, "Выберите основной цвет", Color.RED);
            if (mainColor != null){
                Color addColor = JColorChooser.showDialog(null, "Выберите дополнительный цвет", Color.RED);
                if (addColor != null){
                    Bomber bomber = new Bomber(500, true, true, 100, mainColor, addColor, BombForms.Rocket);
                    if (parking.addPlane(bomber) != -1){
                        repaint();
                    }else{
                        JOptionPane.showMessageDialog(null, "Ангар заполнен");
                    }
                }
            }
        });
        buttonTakenPlane.addActionListener((e) -> {
            if (!fieldNumberPlane.getText().equals("")){
                int numberPlane = Integer.parseInt(fieldNumberPlane.getText());
                Plane transport = parking.removePlane(numberPlane);
                if (transport != null){
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
            if (!fieldNumberEqualPlane.getText().equals("")){
                int numberPlane = Integer.parseInt(fieldNumberEqualPlane.getText());
                JOptionPane.showMessageDialog(null, "Количество равных переданному объекту самолётов: " + parking.equalsPlane(numberPlane));
            }
        });
        buttonNotEqualsPlane.addActionListener((e) -> {
            if (!fieldNumberEqualPlane.getText().equals("")){
                int numberPlane = Integer.parseInt(fieldNumberEqualPlane.getText());
                JOptionPane.showMessageDialog(null, "Количество не равных переданному объекту самолётов: " + parking.notEqualsPlane(numberPlane));
            }
        });
    }
}
