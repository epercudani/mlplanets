package mlplanets.util;

import javax.transaction.NotSupportedException;
import java.awt.geom.Point2D;
import java.util.List;

public class Triangle {

    public static double EPSILON = 10E-8;

    Point2D[] points;

    public Triangle(Point2D[] points) throws NotSupportedException {
        if (points.length != 3) {
            throw new NotSupportedException("Se necesitan tres puntos para calcular el área de un triángulo.");
        }

        this.points = points;
    }

    public double getArea() {

        return (
                points[0].getX() * (points[1].getY() - points[2].getY()) +
                points[1].getX() * (points[2].getY() - points[0].getY()) +
                points[2].getX() * (points[0].getY() - points[1].getY())
        ) / 2;
    }
}
