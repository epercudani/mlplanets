package com.mlinc.mlplanets.domain.util;

import javax.transaction.NotSupportedException;
import java.awt.geom.Path2D;
import java.awt.geom.Point2D;

public class Triangle2D {

    public static double EPSILON = 10E-8;

    Point2D[] points;

    public Triangle2D(Point2D[] points) throws NotSupportedException {
        if (points.length != 3) {
            throw new NotSupportedException("Se necesitan tres puntos para calcular el área de un triángulo.");
        }

        this.points = points;
    }

    public Triangle2D(Triangle2D otherTriangle) {
        this.points = otherTriangle.points.clone();
    }

    public Point2D[] getPoints() {
        return points;
    }

    public void setPoints(Point2D[] points) {

        for (int i = 0; i < 3; ++i) {
            this.points[i].setLocation(points[i]);
        }
    }

    public double getArea() {

        return (
                points[0].getX() * (points[1].getY() - points[2].getY()) +
                points[1].getX() * (points[2].getY() - points[0].getY()) +
                points[2].getX() * (points[0].getY() - points[1].getY())
        ) / 2;
    }

    public double getPerimeter() {
        return
                Math.sqrt(Math.pow(points[1].getX() - points[0].getX(), 2) + Math.pow(points[1].getY() - points[0].getY(), 2)) +
                Math.sqrt(Math.pow(points[2].getX() - points[1].getX(), 2) + Math.pow(points[2].getY() - points[1].getY(), 2)) +
                Math.sqrt(Math.pow(points[0].getX() - points[2].getX(), 2) + Math.pow(points[0].getY() - points[2].getY(), 2));
    }

    public boolean contains(Point2D point) {

        Path2D path = new Path2D.Double();
        path.moveTo(points[0].getX(), points[0].getY());
        path.lineTo(points[1].getX(), points[1].getY());
        path.lineTo(points[2].getX(), points[2].getY());

        return path.contains(point);
    }
}
