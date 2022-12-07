package ru.avtf.kitpo.kitpo_rgr_konovalov.types.users;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import ru.avtf.kitpo.kitpo_rgr_konovalov.comparator.Comparator;
import ru.avtf.kitpo.kitpo_rgr_konovalov.comparator.PointComparator;
import ru.avtf.kitpo.kitpo_rgr_konovalov.types.PointType;

public class PointUserType implements UserType{
    private static final float MAX = 10.0F;
    private static final float MIN = -10.0F;
    private static final String REGULAR_EXPRESSION = "\\(([-]?[0-9]+(?:[.,][0-9]+){0,1});([-]?[0-9]+(?:[.,][0-9]+){0,1})\\)";

    @Override
    public String typeName() {
        return "Point";
    }

    @Override
    public Object create() {
        Random random = new Random();
        double x = random.nextDouble() * (MAX - MIN) + MIN;
        double y = random.nextDouble() * (MAX - MIN) + MIN;
        PointType pointTypeValue = new PointType(x, y);
        return pointTypeValue;
    }

    @Override
    public Object clone(Object object) {
        PointType copyPointTypeValue = new PointType(((PointType) object).getX(), ((PointType) object).getY());
        return copyPointTypeValue;
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
    public PointType parseValue(String pointString) {
        Pattern patternString = Pattern.compile(REGULAR_EXPRESSION);
        Matcher matcher = patternString.matcher(pointString);
        if (matcher.find()) {
            PointType pointType = new PointType(Double.valueOf(matcher.group(1)), Double.valueOf(matcher.group(2)));
            return pointType;
        }
        return null;
    }

    @Override
    public Comparator getTypeComparator() {
        Comparator comparator = new PointComparator();
        return comparator;
    }

    @Override
    public String toString(Object object) {
        return object.toString();
    }
}
