package Pantallas;

import javax.swing.*;

import Arduino.Arduino;

import java.awt.*;

public class Main {

    public static JPanel pantalla() {
        Fondo mainPanel = new Fondo(Fondo.getImageBackground("/Images/Fondo.jpg"));
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        JPanel Panelsuperior = Panelsuperior();
        mainPanel.add(Panelsuperior);
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
        JPanel barra = new JPanel();
        barra.setOpaque(false);
        JProgressBar barraProgreso = new JProgressBar();
        return PanelCentral;
    }

    public static void frame(JPanel panel) {
        JFrame frame = new JFrame("Medidor de Temperatura");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);
        frame.setContentPane(panel);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
