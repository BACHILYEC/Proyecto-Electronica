package Pantallas;

import javax.swing.*;
import Arduino.Arduino;
import java.awt.*;

public class Main {

<<<<<<< HEAD
    public static void pantalla() {
        JFrame frame = new JFrame("Temperatura Ambiental");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Fondo mainPanel = new Fondo(Fondo.getImageBackground("/Images/Fondo.png"));
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        JPanel PanelMitad = PanelCentral(frame);
        PanelMitad.setBorder(BorderFactory.createEmptyBorder(150, 50, 50, 50));
=======
    public static JPanel pantalla() {
        JPanel mainPanel = new JPanel();
        mainPanel.setOpaque(false);
        JPanel PanelMitad = PanelCentral();
>>>>>>> 80c1f4c45c1604537d352ddfce540624bfdccfb3
        mainPanel.add(PanelMitad);
        frame.setContentPane(mainPanel);
        frame.setSize(800, 600);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

    }

<<<<<<< HEAD
    public static JPanel PanelCentral(JFrame frame) {
        JPanel PanelCentral = new JPanel(new BorderLayout());
=======
    public static JPanel PanelCentral() {
        JPanel PanelCentral = new JPanel();
        PanelCentral.setLayout(new BoxLayout(PanelCentral, BoxLayout.Y_AXIS));
>>>>>>> 80c1f4c45c1604537d352ddfce540624bfdccfb3
        PanelCentral.setOpaque(false);

        JPanel infoPanel = new JPanel();
        infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.X_AXIS));
        infoPanel.setOpaque(false);

<<<<<<< HEAD
=======
        JPanel temperaturaPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JLabel temperatura = new JLabel("Ambiente:");
        temperatura.setFont(new Font("COMICS SANS MS", Font.BOLD, 9));
        temperatura.setForeground(Color.white);
        temperaturaPanel.setOpaque(false);
        temperaturaPanel.add(temperatura);

>>>>>>> 80c1f4c45c1604537d352ddfce540624bfdccfb3
        JPanel datoTemperaturaPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        datoTemperaturaPanel.setBorder(BorderFactory.createEmptyBorder(53, 275, 0, 0));
        JLabel datoTemperatura = new JLabel();
        datoTemperatura.setFont(new Font("COMICS SANS MS", Font.BOLD, 34));
        datoTemperatura.setForeground(Color.black);
        datoTemperaturaPanel.setOpaque(false);
        datoTemperaturaPanel.add(datoTemperatura);
        infoPanel.add(datoTemperaturaPanel);
<<<<<<< HEAD
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
=======
        PanelCentral.add(infoPanel);

        JLabel alerta = new JLabel();
        alerta.setFont(new Font("COMICS SANS MS", Font.BOLD, 9));
        alerta.setForeground(Color.white);
        alerta.setAlignmentX(Component.CENTER_ALIGNMENT);
        PanelCentral.add(alerta);
>>>>>>> 80c1f4c45c1604537d352ddfce540624bfdccfb3

        Thread ard = new Thread(() -> {
            while (true) {
                String arduino = Arduino.leerSerial();
                if (arduino != null) {
                    try {
                        float x = Float.parseFloat(arduino.trim());
<<<<<<< HEAD
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
=======
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
>>>>>>> 80c1f4c45c1604537d352ddfce540624bfdccfb3
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

<<<<<<< HEAD
    public static String alerta(int value) {
        String Alerta = "";
        if (value < 15 && value > 0) {
            Alerta = "Anda a ponerte Chompa! Hace frío";
=======
    public static String alerta(int value, JPanel panel) {
        String Alerta = "";
        if (value < 15 && value > 0) {
            Alerta = "Anda a ponerte Chompa! \n Hace frío";
>>>>>>> 80c1f4c45c1604537d352ddfce540624bfdccfb3
        } else if (value > 20) {
            Alerta = "Tomarás agüita que hace calor";
        } else if (value >= 15 && value <= 20) {
            Alerta = "El clima está agradable";
        } else if (value == 0) {
            Alerta = "Conecte el sensor";
        }
        return Alerta;
<<<<<<< HEAD
=======
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
>>>>>>> 80c1f4c45c1604537d352ddfce540624bfdccfb3
    }

}