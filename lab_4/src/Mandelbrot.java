import java.awt.geom.Rectangle2D;

public class Mandelbrot extends FractalGenerator{

    public static final int MAX_ITERATIONS = 2000; // константа с максимальным количеством итераций
    public void getInitialRange(Rectangle2D.Double range) { // метод позволяет генератору фракталов определить наиболее
        // «интересную» область комплексной плоскости для конкретного фрактала.
        // Rectangle2D.Double range - прямоугольный обьект
        range.x = -2;
        range.y = -1.5;
        range.width = 3;
        range.height = 3;
    }
// (-2 - 1.5i) - (1 + 1.5i)
    public int numIterations(double x, double y) { // реализует итеративную функцию для фрактала Мандельброта.
        int iteration = 0; //
        double zRe = 0; // действительная часть
        double zIm = 0; // мнимая часть
        double zRe2 = 0; // квадрат действительной части
        double zIm2 = 0; // квадрат мнимой часть
        while(iteration < MAX_ITERATIONS && (zRe2 + zIm2) < 4) // для быстродействия
        {
            zIm = (2 * zRe * zIm) + y; //считается мнимая часть числа
            zRe = (zRe2 - zIm2) + x; //считается действ. часть числа

            zRe2 = zRe*zRe;
            zIm2 = zIm*zIm;
            iteration++;
        }
        if (iteration == MAX_ITERATIONS) { // точка не выходит за границы
            return -1;
        }
        return iteration;
    }

}
