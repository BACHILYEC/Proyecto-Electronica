package Pantallas;

import javax.swing.*;
import Arduino.Arduino;
import java.awt.*;

public class Main {

    public static JPanel pantalla() {
        JPanel mainPanel = new JPanel();
        mainPanel.setOpaque(false);
        JPanel PanelMitad = PanelCentral();
        mainPanel.add(PanelMitad);
        return mainPanel;
    }

    public static JPanel PanelCentral() {
        JPanel PanelCentral = new JPanel();
        PanelCentral.setLayout(new BoxLayout(PanelCentral, BoxLayout.Y_AXIS));
        PanelCentral.setOpaque(false);

        JPanel infoPanel = new JPanel();
        infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.X_AXIS));
        infoPanel.setOpaque(false);

        JPanel temperaturaPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JLabel temperatura = new JLabel("Ambiente:");
        temperatura.setFont(new Font("COMICS SANS MS", Font.BOLD, 9));
        temperatura.setForeground(Color.white);
        temperaturaPanel.setOpaque(false);
        temperaturaPanel.add(temperatura);

        JPanel datoTemperaturaPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JLabel datoTemperatura = new JLabel();
        datoTemperatura.setForeground(Color.white);
        datoTemperaturaPanel.setOpaque(false);
        datoTemperaturaPanel.add(datoTemperatura);

        infoPanel.add(temperaturaPanel);
        infoPanel.add(datoTemperaturaPanel);
        PanelCentral.add(infoPanel);

        JLabel alerta = new JLabel();
        alerta.setFont(new Font("COMICS SANS MS", Font.BOLD, 9));
        alerta.setForeground(Color.white);
        alerta.setAlignmentX(Component.CENTER_ALIGNMENT);
        PanelCentral.add(alerta);

        Thread ard = new Thread(() -> {
            while (true) {
                String arduino = Arduino.leerSerial();
                if (arduino != null) {
                    try {
                        float x = Float.parseFloat(arduino.trim());
                        SwingUtilities.invokeLater(() -> {
                            datoTemperatura.setFont(new Font("COMICS SANS MS", Font.BOLD, 9));
                            datoTemperatura.setText(x + " °C");
                            alerta.setText(alerta((int) x, PanelCentral));
                        });
                        Thread.sleep(1000);
                    } catch (NumberFormatException e) {
                        System.err.println("Error: " + arduino);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                } else if (arduino == null) {
                    SwingUtilities.invokeLater(() -> {
                        datoTemperatura.setFont(new Font("COMICS SANS MS", Font.BOLD, 9));
                        datoTemperatura.setText("null");
                        alerta.setText(alerta(0, PanelCentral));
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

    public static String alerta(int value, JPanel panel) {
        String Alerta = "";
        if (value < 15 && value > 0) {
            Alerta = "Anda a ponerte Chompa! \n Hace frío";
        } else if (value > 20) {
            Alerta = "Tomarás agüita que hace calor";
        } else if (value >= 15 && value <= 20) {
            Alerta = "El clima está agradable";
        } else if (value == 0) {
            Alerta = "Conecte el sensor";
        }
        return Alerta;
    }

    public static void J(JPanel panel) {
        JWindow window = new JWindow();
        window.setLayout(new BorderLayout());
        window.setSize(250, 50);
        window.setBackground(new Color(0, 0, 0, 120));
        window.add(panel, BorderLayout.CENTER);
        window.setLocation(0, 0);
        window.setAlwaysOnTop(true);
        window.setFocusableWindowState(false);

        JButton cerrar = new JButton("Salir");
        cerrar.setFont(new Font("Arial", Font.BOLD, 8));
        cerrar.setForeground(new Color(Color.white.getRGB()));
        cerrar.setPreferredSize(new Dimension(50, 20));
        cerrar.setBackground(new Color(0, 0, 0, 0));
        cerrar.setFocusPainted(false);
        cerrar.addActionListener(e -> System.exit(0));
        panel.add(cerrar, BorderLayout.SOUTH);
        window.setVisible(true);
    }

}