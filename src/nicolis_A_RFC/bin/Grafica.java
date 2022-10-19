package nicolis_A_RFC.bin;

import javax.swing.*;
import java.awt.*;

public class Grafica extends JPanel {

    private final int panelWidth = 400;
    private final int panelHeight = 450;
    private Image image;

    public Grafica() {

        setLayout(new GridLayout(4, 0, 10, 0));

        JLabel title = new JLabel("Marconi time system");
        title.setHorizontalAlignment(SwingConstants.CENTER);
        title.setForeground(Color.WHITE);
        title.setFont(new Font("Impact", Font.PLAIN, 30));

        add(title, 0, 0);

    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(panelWidth, panelHeight);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(image, 0, 0, this); // see javadoc for more info on the parameters
    }
}
