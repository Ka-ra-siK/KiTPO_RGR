package ru.avtf.kitpo.kitpo_rgr_konovalov.factory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

import ru.avtf.kitpo.kitpo_rgr_konovalov.types.users.DoubleUserType;
import ru.avtf.kitpo.kitpo_rgr_konovalov.types.users.PointUserType;
import ru.avtf.kitpo.kitpo_rgr_konovalov.types.users.UserType;

/**
 * Класс - фабрика.
 * Возвращает класс по названию типа данных.
 */
public class UserFactory {
    private final static ArrayList<UserType> typeList = new ArrayList<>();

    static {
        ArrayList<UserType> buildersClasses = new ArrayList<>(Arrays.asList(new DoubleUserType(), new PointUserType()));
        buildersClasses.forEach(userType -> typeList.add(userType));
    }
    public static Set<String> getTypeNameList() {
        return typeList.stream().map(UserType::typeName).collect(Collectors.toSet());
    }
    public static UserType getBuilderByName(String name){
        if (name == null){
            throw new RuntimeException("Error! Name of type is empty!");
        }
        for (UserType userType : typeList) {
            if (name.equals(userType.typeName()))
                return userType;
        }
        return null;
    }
}
