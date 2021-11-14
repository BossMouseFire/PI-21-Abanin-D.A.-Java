package LaboratoriesJava;

import LaboratoriesJava.enums.BombForms;
import LaboratoriesJava.enums.MovesBomber;
import LaboratoriesJava.interfaces.ITransport;
import LaboratoriesJava.transport.Bomber;
import LaboratoriesJava.transport.Plane;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class FormBomber extends JDialog {
    private JPanel panel;
    private JButton createPlaneButton;
    private JButton createBomberButton;
    private JButton buttonUp;
    private JButton buttonDown;
    private JButton buttonLeft;
    private JButton buttonRight;
    private JButton createBombsRect;
    private JButton createRocket;
    private ITransport plane;

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        if (plane != null) {
            Graphics2D graphic2d = (Graphics2D) g;
            plane.drawTransport(graphic2d);
        }
    }

    public FormBomber() {
        super();
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
                getContentPane().add(panel);
            }
        });

        createPlaneButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                plane = new Plane(2000, 100, Color.decode("#000000"));
                plane.setPosition(100, 100, 700, 500);
                repaint();
            }
        });
        createBomberButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                plane = new Bomber(2000,
                        true, true, 100,
                        Color.decode("#000000"), Color.decode("#d61e11"), BombForms.Oval);
                plane.setPosition(100, 100, 700, 500);
                repaint();
            }
        });
        buttonUp.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                plane.moveTransport(MovesBomber.Up);
                repaint();
            }
        });
        buttonDown.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                plane.moveTransport(MovesBomber.Down);
                repaint();
            }
        });
        buttonLeft.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                plane.moveTransport(MovesBomber.Left);
                repaint();
            }
        });
        buttonRight.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                plane.moveTransport(MovesBomber.Right);
                repaint();
            }
        });
        createRocket.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                plane = new Bomber(2000,
                        true, true, 100,
                        Color.decode("#000000"), Color.decode("#d61e11"), BombForms.Rocket);
                plane.setPosition(100, 100, 700, 500);
                repaint();
            }
        });
        createBombsRect.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                plane = new Bomber(2000,
                        true, true, 100,
                        Color.decode("#000000"), Color.decode("#d61e11"), BombForms.Rectangle);
                plane.setPosition(100, 100, 700, 500);
                repaint();
            }
        });
    }

    public void setPlane(ITransport plane) {
        this.plane = plane;
        plane.setPosition(50, 50, 700, 500);
        repaint();
    }

}
