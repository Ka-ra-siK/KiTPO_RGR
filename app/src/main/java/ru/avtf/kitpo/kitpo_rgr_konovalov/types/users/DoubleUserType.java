package ru.avtf.kitpo.kitpo_rgr_konovalov.types.users;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Random;

import ru.avtf.kitpo.kitpo_rgr_konovalov.comparator.Comparator;
import ru.avtf.kitpo.kitpo_rgr_konovalov.comparator.DoubleComparator;
import ru.avtf.kitpo.kitpo_rgr_konovalov.types.DoubleType;

/**
 * Интерфейс для создания объектов,
 * вещественное число.
 * @see UserType#typeName() Получение имя типа
 * @see UserType#create() Создание объекта
 * @see UserType#clone(Object) Клонирование текущего объекта
 * @see UserType#readValue(InputStreamReader) Чтение объекта
 * @see UserType#parseValue(String) Парсинг содержимого из стоки, с помощью регулярных выражений
 * @see UserType#getTypeComparator() Получение экземпляра компаратора
 */
public class DoubleUserType implements UserType {

    private final double MAX = 1000.0;
    private final double MIN = -1000.0;

    @Override
    public String typeName() {
        return "Double";
    }

    @Override
    public Object create() {
        Random random = new Random();
        DoubleType doubleTypeValue = new DoubleType(random.nextDouble() * (MAX - MIN) + MIN);
        return doubleTypeValue;
    }

    @Override
    public Object clone(Object object) {
        DoubleType copyDoubleTypeValue = new DoubleType(((DoubleType)object).getDoubleTypeValue());
        return copyDoubleTypeValue;
    }

    @Override
    public Object readValue(InputStreamReader in) throws IOException {
        try {
            return in.read();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public DoubleType parseValue(String doubleString) {
        return new DoubleType(Double.parseDouble(doubleString));
    }

    @Override
    public Comparator getTypeComparator() {
        Comparator comparator = new DoubleComparator();
        return comparator;
    }

    @Override
    public String toString(Object object) {
        return object.toString();
    }
}
