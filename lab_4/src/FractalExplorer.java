import javax.swing.*;
import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.awt.event.*;
public class FractalExplorer {
    private int displaySize; // ширина и высота отображения в пикселях
    private JImageDisplay display; // для обновления отображения в разных методах в процессе вычисления фрактала
    private FractalGenerator fractal; // будет использоваться ссылка на базовый класс для отображения других видов фракталов в будущем
    private Rectangle2D.Double range; // указывает диапазон комплексной плоскости, которая выводится на экран

    public FractalExplorer(int displaySize){ // конструктор класса
        this.displaySize = displaySize;
        range = new Rectangle2D.Double(); // инициализация диапазона
        fractal = new Mandelbrot(); // инициализация фрактала
        fractal.getInitialRange(range); // вызывается метод для определённого фрактала и в объект range помещаются определённые координаты
        display = new JImageDisplay(displaySize, displaySize);
    }
    public void createAndShowGUI(){ //  графический интерфейс Swing
        JFrame frame = new JFrame("Фрактал Мандельброта"); // устанавливается рамка с названием
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // устанавливает закрытие фрейма по умолчанию на "exit"
        frame.add(display, BorderLayout.CENTER); // обьект изображения в центр
        JButton resetButton = new JButton("СБРОС"); // кнопка сброса

        //Экземпляр ResetButtonHandler на кнопке сброса
        ResetButtonHandler resetButtonHandler = new ResetButtonHandler();
        resetButton.addActionListener(resetButtonHandler); // получает уведомление, когда нажимают на кнопку

        //Экземпляр MouseHandler в компоненте фрактального отображения
        MouseHandler click = new MouseHandler();
        display.addMouseListener(click);

        frame.add(resetButton, BorderLayout.SOUTH);
        frame.pack(); // устанавливает такой минимальный размер контейнера,
        // который достаточен для отображения всех компонентов.
        frame.setVisible(true); // отрисовывает элементы интерфейса (делает видимым)
        frame.setResizable(false); // запрет на изменение размера экрана
        drawFractal();
    }

    private void drawFractal(){ // метод для отрисовки фрактала
        for(int i = 0; i < displaySize; i++){ // идем по всем пикселям
            for(int j = 0; j < displaySize; j++){
                double xCoord = fractal.getCoord(range.x, range.x + range.width, displaySize, i);
                double yCoord = fractal.getCoord(range.y, range.y + range.height, displaySize, j);
                // количество итераций для соответствующих координат в области отображения фрактала :
                int iteration = fractal.numIterations(xCoord, yCoord);
                if (iteration == -1) { // точка не выходит за границы
                    display.drawPixel(i, j, 0); // пиксель в черный цвет
                }
                else { // выбираем цвет на основе кол-ва итераций
                    float hue = 0.7f + (float) iteration / 200f;
                    int rgbColor = Color.HSBtoRGB(hue, 1f, 1f);
                    display.drawPixel(i, j, rgbColor); // устанавливаем пиксель в определенный цвет
                }
            }
            display.repaint(); // обновление изображения на экране
        }
    }
    //Имплементируем интерфейс ActionListener для кнопки сброса
    class ResetButtonHandler implements ActionListener { // используется нериализованный метод (класс интерфейс),
        // ActionListener для кнопки сброса; implements означает, что используются элементы интерфейса в классе,
        public void actionPerformed(ActionEvent e) { // обработчик сбрасывает диапазон до начального диапазона,
            // заданного генератором, а затем перерисовывает фрактал.
            fractal.getInitialRange(range);
            drawFractal();
        }
    }
    class MouseHandler extends MouseAdapter { // внутренний класс для обработки событий MouseListener с дисплея.
        @Override
        //При получении события о щелчке мышью, класс должен отобразить пиксельные кооринаты щелчка
        // в область фрактала, а затем вызвать метод генератора recenterAndZoomRange() с координатами,
        // по которым щелкнули, и масштабом 0.5. Таким образом, нажимая на какое-либо место
        // на фрактальном отображении, вы увеличиваете его
        public void mouseClicked(MouseEvent e) {
            display.clearImage(); // заполняем все черным цветом
            // получаем координаты области отображения щелчка мышью
            int x = e.getX();
            double xCoord = FractalGenerator.getCoord(range.x, range.x + range.width, displaySize, x);
            int y = e.getY();
            double yCoord = FractalGenerator.getCoord(range.y, range.y + range.height, displaySize, y);
            // Вызов метода генератора RecenterAndZoomRange () с
            // координатами, по которым щелкнули, и масштабом 0,5.
            fractal.recenterAndZoomRange(range, xCoord, yCoord, 0.5);
            drawFractal(); // рисует фрактал
        }
    }
    public static void main(String[] args){ // точка входа
        FractalExplorer fractalExplorer = new FractalExplorer(800);
        fractalExplorer.createAndShowGUI();
    }
}
