package nicolis_A_RFC.bin;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.List;

public class Grafica extends JPanel implements WindowPanel {

        private Image image;
        private final int panelWidth = 400;
        private final int panelHeight = 450;

        public Grafica() {

            setLayout(new GridLayout(4, 0, 10, 0));

            JLabel title = new JLabel("Marconi time system");
            title.setHorizontalAlignment( SwingConstants.CENTER );
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
}
