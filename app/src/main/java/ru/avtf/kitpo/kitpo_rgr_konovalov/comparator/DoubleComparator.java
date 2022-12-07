package ru.avtf.kitpo.kitpo_rgr_konovalov.comparator;

import java.io.Serializable;

import ru.avtf.kitpo.kitpo_rgr_konovalov.types.DoubleType;

public class DoubleComparator implements Comparator, Serializable {
    @Override
    public double compare(Object o1, Object o2) {
        return ((DoubleType)o1).getDoubleTypeValue() - ((DoubleType)o2).getDoubleTypeValue();
    }
}
