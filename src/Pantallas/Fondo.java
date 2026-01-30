package Pantallas;

import javax.swing.*;
import java.awt.*;

import java.net.URL;

public class Fondo extends JPanel {

    private Image backgroundImage;

    public Fondo(URL imageURL) {
        if (imageURL == null) {
            throw new RuntimeException("No se encontró la imagen: " + imageURL);
        }

        this.backgroundImage = new ImageIcon(imageURL).getImage();
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
    }

    public static URL getImageBackground(String ruta) {
        URL url = getImage(ruta);
        return url;
    }

    public static java.net.URL getImage(String relativePath) {
        java.net.URL url = Fondo.class.getResource(relativePath);
        if (url == null) {
            System.err.println("WARNING: Resource not found: " + relativePath);
        }
        return url;
    }
}
