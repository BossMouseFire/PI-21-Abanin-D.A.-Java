package Lab1;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class FormBomber extends JFrame {
    private JPanel panel;
    private JButton createButton;
    private JButton buttonUp;
    private JButton buttonDown;
    private JButton buttonLeft;
    private JButton buttonRight;
    private JButton buttonEightBombs;
    private JButton buttonTenBombs;
    private JButton buttonSixBombs;
    private Bomber bomber;
    private boolean isCreateBomber;

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        if (isCreateBomber) {
            Graphics2D graphic2d = (Graphics2D) g;
            bomber.DrawBomber(graphic2d);
        }
    }

    public FormBomber() {
        $$$setupUI$$$();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        getContentPane().add(panel);

        bomber = new Bomber(2000, true, true, 100, Color.decode("#000000"), Color.decode("#800000"));
        createButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!isCreateBomber) {
                    Graphics g = panel.getGraphics();
                    Graphics2D graphic2d = (Graphics2D) g;
                    bomber.setPosition(100, 100, 500, 500);
                    bomber.DrawBomber(graphic2d);
                    isCreateBomber = true;
                }
            }
        });
        buttonUp.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                bomber.Move(MovesBomber.Up);
                FormBomber.super.repaint();
            }
        });
        buttonDown.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                bomber.Move(MovesBomber.Down);
                FormBomber.super.repaint();
            }
        });
        buttonLeft.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                bomber.Move(MovesBomber.Left);
                FormBomber.super.repaint();
            }
        });
        buttonRight.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                bomber.Move(MovesBomber.Right);
                FormBomber.super.repaint();
            }
        });
        buttonSixBombs.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                bomber.amountBombs = 6;
                FormBomber.super.repaint();
            }
        });
        buttonEightBombs.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                bomber.amountBombs = 8;
                FormBomber.super.repaint();
            }
        });
        buttonTenBombs.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                bomber.amountBombs = 10;
                FormBomber.super.repaint();
            }
        });
    }

    /**
     * Method generated by IntelliJ IDEA GUI Designer
     * >>> IMPORTANT!! <<<
     * DO NOT edit this method OR call it in your code!
     *
     * @noinspection ALL
     */
    private void $$$setupUI$$$() {
        createUIComponents();
        panel = new JPanel();
        panel.setLayout(new GridBagLayout());
        buttonUp = new JButton();
        buttonUp.setText("Вперёд");
        GridBagConstraints gbc;
        gbc = new GridBagConstraints();
        gbc.gridx = 5;
        gbc.gridy = 4;
        gbc.weightx = 1.0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        panel.add(buttonUp, gbc);
        buttonDown = new JButton();
        buttonDown.setText("Назад");
        gbc = new GridBagConstraints();
        gbc.gridx = 5;
        gbc.gridy = 5;
        gbc.weightx = 1.0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        panel.add(buttonDown, gbc);
        createButton = new JButton();
        createButton.setEnabled(true);
        createButton.setText("Создать");
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1.0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        panel.add(createButton, gbc);
        buttonRight = new JButton();
        buttonRight.setText("Вправо");
        gbc = new GridBagConstraints();
        gbc.gridx = 6;
        gbc.gridy = 5;
        gbc.weightx = 1.0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        panel.add(buttonRight, gbc);
        final JPanel spacer1 = new JPanel();
        gbc = new GridBagConstraints();
        gbc.gridx = 5;
        gbc.gridy = 3;
        gbc.weighty = 20.0;
        gbc.fill = GridBagConstraints.VERTICAL;
        panel.add(spacer1, gbc);
        buttonEightBombs = new JButton();
        buttonEightBombs.setText("8 бомб");
        gbc = new GridBagConstraints();
        gbc.gridx = 3;
        gbc.gridy = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        panel.add(buttonEightBombs, gbc);
        buttonTenBombs = new JButton();
        buttonTenBombs.setText("10 бомб");
        gbc = new GridBagConstraints();
        gbc.gridx = 4;
        gbc.gridy = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        panel.add(buttonTenBombs, gbc);
        buttonSixBombs = new JButton();
        buttonSixBombs.setText("6 бомб");
        gbc = new GridBagConstraints();
        gbc.gridx = 2;
        gbc.gridy = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        panel.add(buttonSixBombs, gbc);
        buttonLeft = new JButton();
        buttonLeft.setText("Влево");
        gbc = new GridBagConstraints();
        gbc.gridx = 4;
        gbc.gridy = 5;
        gbc.weightx = 1.0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        panel.add(buttonLeft, gbc);
    }

    /**
     * @noinspection ALL
     */
    public JComponent $$$getRootComponent$$$() {
        return panel;
    }

    private void createUIComponents() {
        // TODO: place custom component creation code here
    }
}
