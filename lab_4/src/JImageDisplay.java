import java.awt.*;
import java.awt.image.BufferedImage;

public class JImageDisplay extends javax.swing.JComponent{
    private BufferedImage bufferImage;

    public JImageDisplay(int height, int width){ // конструктор принимает на вход высоту и ширину
        bufferImage = new BufferedImage(height, width, BufferedImage.TYPE_INT_RGB); // инициализируем объект BufferedImage новым изображением с этой шириной и высотой,
        // и типом изображения TYPE_INT_RGB
        Dimension dimension = new Dimension(height, width);
        super.setPreferredSize(dimension);
    }

    @Override
    public void paintComponent(Graphics g) { // переопределяем, чтобы предыдщуий рисунок был постоянным в компоненте
        super.paintComponent(g); // чтобы объекты отображались правильно
        g.drawImage (bufferImage, 0, 0, bufferImage.getWidth(), bufferImage.getHeight(), null); // нарисовать изображение в компоненте
        // null для ImageObserver, тк данная функциональность не требуется
    }
    public void clearImage() { // устанавливает все пиксели изображения в черный цвет
        int[] rgbArray = new int[bufferImage.getWidth() * bufferImage.getHeight()];
        bufferImage.setRGB(0, 0, bufferImage.getWidth(), bufferImage.getHeight(), rgbArray, 0, 1);
    }
    public void drawPixel(int x, int y, int color){ // устанавливает пиксель в определенный цвет
        bufferImage.setRGB(x, y, color);
    }
}
