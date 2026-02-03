package Pantallas;

import javax.swing.*;
import javax.swing.plaf.basic.BasicProgressBarUI;

import Arduino.Arduino;

import java.awt.*;

public class Main {

    public static void pantalla() {
        JFrame frame = new JFrame("Temperatura Ambiental");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Fondo mainPanel = new Fondo(Fondo.getImageBackground("/Images/Fondo.png"));
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        JPanel PanelMitad = PanelCentral(frame);
        PanelMitad.setBorder(BorderFactory.createEmptyBorder(150, 50, 50, 50));
        mainPanel.add(PanelMitad);
        frame.setContentPane(mainPanel);
        frame.setSize(800, 600);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

    }

    public static JPanel PanelCentral(JFrame frame) {
        JPanel PanelCentral = new JPanel(new BorderLayout());
        PanelCentral.setOpaque(false);

        JPanel infoPanel = new JPanel();
        infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS));
        infoPanel.setOpaque(false);

        JPanel datoTemperaturaPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        datoTemperaturaPanel.setBorder(BorderFactory.createEmptyBorder(53, 275, 0, 0));
        JLabel datoTemperatura = new JLabel();
        datoTemperatura.setFont(new Font("COMICS SANS MS", Font.BOLD, 34));
        datoTemperatura.setForeground(Color.black);
        datoTemperaturaPanel.setOpaque(false);
        datoTemperaturaPanel.add(datoTemperatura);
        infoPanel.add(datoTemperaturaPanel);
        PanelCentral.add(infoPanel, BorderLayout.NORTH);
        JPanel south = new JPanel();
        south.setLayout(new BoxLayout(south, BoxLayout.Y_AXIS));
        south.setOpaque(false);
        JPanel button = new JPanel(new FlowLayout(FlowLayout.CENTER));
        button.setOpaque(false);
        JButton cerrar = new JButton("Cerrar");
        button.setBorder(BorderFactory.createEmptyBorder(30, 0, 0, 0));
        cerrar.setFont(new Font("Arial", Font.BOLD, 12));
        cerrar.setForeground(new Color(Color.black.getRGB()));
        cerrar.setFocusPainted(false);
        cerrar.addActionListener(e -> {
            System.exit(0);
        });
        JPanel al = new JPanel(new FlowLayout(FlowLayout.CENTER));
        al.setOpaque(false);
        JLabel alerta = new JLabel();
        alerta.setBorder(BorderFactory.createEmptyBorder(130, 200, 60, 0));
        alerta.setFont(new Font("COMICS SANS MS", Font.BOLD, 20));
        alerta.setForeground(Color.black);
        al.add(alerta);
        PanelCentral.add(al, BorderLayout.CENTER);
        button.add(cerrar);
        south.add(button);
        PanelCentral.add(south, BorderLayout.SOUTH);

        Thread ard = new Thread(() -> {
            while (true) {
                String arduino = Arduino.leerSerial();
                if (arduino != null) {
                    try {
                        float x = Float.parseFloat(arduino.trim());
                        int value = (int) x;
                        SwingUtilities.invokeLater(() -> {
                            datoTemperatura.setText(x + " °C");
                            alerta.setText(alerta(value));
                        });
                        Thread.sleep(1000);
                    } catch (Exception e) {
                    }
                } else if (arduino == null) {
                    SwingUtilities.invokeLater(() -> {
                        datoTemperatura.setText("... °C");
                        alerta.setText(alerta(0));
                    });
                }

                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    break;
                }
            }
        });

        ard.setDaemon(true);
        ard.start();

        return PanelCentral;
    }

    public static String alerta(int value) {
        String Alerta = "";
        if (value < 15 && value > 0) {
            Alerta = "Anda a ponerte Chompa! Hace frío";
        } else if (value > 20) {
            Alerta = "Tomarás agüita que hace calor";
        } else if (value >= 15 && value <= 20) {
            Alerta = "El clima está agradable";
        } else if (value == 0) {
            Alerta = "Conecte el sensor";
        }
        return Alerta;
    }

    public static void applyCleanProgressBarUI(JProgressBar progressBar) {
        progressBar.setUI(new BasicProgressBarUI() {
            @Override
            protected void paintDeterminate(Graphics g, JComponent c) {
                Insets insets = progressBar.getInsets();
                int width = progressBar.getWidth() - insets.left - insets.right;
                int height = progressBar.getHeight() - insets.top - insets.bottom;

                int amountFull = getAmountFull(insets, width, height);

                Graphics2D g2 = (Graphics2D) g.create();
                g2.setColor(progressBar.getForeground());

                int y = insets.top + height - amountFull;

                g2.fillRect(
                        insets.left,
                        y,
                        width,
                        amountFull);

                g2.dispose();
            }
        });
    }

}
