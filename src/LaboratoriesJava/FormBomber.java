package LaboratoriesJava;

import LaboratoriesJava.enums.MovesBomber;
import LaboratoriesJava.interfaces.ITransport;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class FormBomber extends JDialog {
    private JPanel panel;
    private JButton buttonUp;
    private JButton buttonDown;
    private JButton buttonLeft;
    private JButton buttonRight;
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
    }

    public void setPlane(ITransport plane) {
        this.plane = plane;
        plane.setPosition(50, 50, 700, 500);
        repaint();
    }

}
