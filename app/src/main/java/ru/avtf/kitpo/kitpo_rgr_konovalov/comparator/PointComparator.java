package ru.avtf.kitpo.kitpo_rgr_konovalov.comparator;

import java.io.Serializable;

import ru.avtf.kitpo.kitpo_rgr_konovalov.types.PointType;

public class PointComparator implements Comparator, Serializable {
    /**
     * Разница межды объектами вычисляется
     * с помощью разности, между длиннами векторов
     * от заданной точки до цетра координатной оси.
     * Вычисляется по формуле: sqrt(x^2+y^2).
     * @param o1 первая точка
     * @param o2 вторая точка
     * @return разницу между точками
     */
    @Override
    public double compare(Object o1, Object o2) {
        double firstX = ((PointType) o1).getX();
        double secondX = ((PointType) o2).getX();
        double firstY = ((PointType) o1).getY();
        double secondY = ((PointType) o2).getY();
        return getVectorLength(firstX, firstY) - getVectorLength(secondX, secondY);
    }

    /**
     * @param x Точка Х
     * @param y Точка Y
     * @return Длина вектора от точки (x;y) до координатоной оси
     */
    public double getVectorLength(double x, double y){
        return Math.sqrt(Math.pow(x,2)+Math.pow(y,2));
    }
}
