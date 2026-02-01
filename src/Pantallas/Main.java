package Pantallas;

import javax.swing.*;
import javax.swing.plaf.basic.BasicProgressBarUI;

import Arduino.Arduino;

import java.awt.*;

public class Main {

    public static JPanel pantalla() {
        Fondo mainPanel = new Fondo(Fondo.getImageBackground("/Images/Fondo.jpg"));
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        JPanel Panelsuperior = Panelsuperior();
        JPanel PanelMitad = PanelCentral();
        PanelMitad.setBorder(BorderFactory.createEmptyBorder(150, 50, 50, 50));
        mainPanel.add(Panelsuperior);
        mainPanel.add(PanelMitad);
        return mainPanel;
    }

    public static JPanel Panelsuperior() {
        JPanel Panelsuperior = new JPanel();
        Panelsuperior.setOpaque(false);
        JPanel tituloJPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        tituloJPanel.setOpaque(false);
        tituloJPanel.setBorder(BorderFactory.createEmptyBorder(20, 0, 0, 0));
        JLabel titulo = new JLabel("Temperatura Ambiental");
        titulo.setFont(new Font("COMICS SANS MS", Font.BOLD, 24));
        titulo.setForeground(Color.white);
        tituloJPanel.add(titulo);
        JPanel imagen = new JPanel(new FlowLayout(FlowLayout.LEFT));
        imagen.setOpaque(false);
        ImageIcon icon = new ImageIcon(Fondo.getImage("/Images/iconTitulo.png"));
        Image img = icon.getImage().getScaledInstance(80, 80, Image.SCALE_SMOOTH);
        icon = new ImageIcon(img);
        JLabel labelImagen = new JLabel(icon);
        imagen.add(labelImagen);
        Panelsuperior.add(imagen);
        Panelsuperior.add(tituloJPanel);
        return Panelsuperior;
    }

    public static JPanel PanelCentral() {
        JPanel PanelCentral = new JPanel(new BorderLayout());
        PanelCentral.setOpaque(false);

        JProgressBar barraProgreso = new JProgressBar(0, 100);
        barraProgreso.setOpaque(false);
        barraProgreso.setOrientation(JProgressBar.VERTICAL);
        applyCleanProgressBarUI(barraProgreso);
        barraProgreso.setBorder(BorderFactory.createEmptyBorder(0, 100, 10, 0));
        PanelCentral.add(barraProgreso, BorderLayout.WEST);

        JPanel infoPanel = new JPanel();
        infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS));
        infoPanel.setOpaque(false);

        JPanel temperaturaPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JLabel temperatura = new JLabel("Temperatura Actual: ");
        temperatura.setFont(new Font("COMICS SANS MS", Font.BOLD, 20));
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
        PanelCentral.add(infoPanel, BorderLayout.CENTER);

        Thread ard = new Thread(() -> {
            while (true) {
                String arduino = Arduino.leerSerial();
                if (arduino != null) {
                    try {
                        float x = Float.parseFloat(arduino.trim());
                        int value = (int) x;

                        SwingUtilities.invokeLater(() -> {
                            colorBar(value, barraProgreso);
                            barraProgreso.setValue(value);
                            datoTemperatura.setFont(new Font("COMICS SANS MS", Font.BOLD, 48));
                            datoTemperatura.setText(x + " °C");
                        });
                        Thread.sleep(500);
                    } catch (NumberFormatException e) {
                        System.err.println("Error: " + arduino);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                } else if (arduino == null) {
                    SwingUtilities.invokeLater(() -> {
                        colorBar(0, barraProgreso);
                        barraProgreso.setValue(0);
                        datoTemperatura.setFont(new Font("COMICS SANS MS", Font.BOLD, 18));
                        datoTemperatura.setText("Conecte el sensor \n por favor");
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

    public static void colorBar(int valor, JProgressBar barra) {
        if (valor <= 25) {
            barra.setForeground(new Color(0, 200, 0));
        } else if (valor <= 50) {
            barra.setForeground(new Color(0, 153, 255));
        } else if (valor <= 75) {
            barra.setForeground(new Color(255, 165, 0));
        } else {
            barra.setForeground(Color.RED);
        }
    }

    public static void frame(JPanel panel) {
        JFrame frame = new JFrame("Medidor de Temperatura");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);
        frame.setContentPane(panel);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
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
