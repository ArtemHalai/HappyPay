package factories;

import enums.LocaleEnum;

import static enums.LocaleEnum.*;

public class LocaleFactory {
    private static volatile LocaleFactory factory = null;

    private LocaleFactory() {

    }

    public static LocaleFactory getInstance() {
        if (factory == null) {
            synchronized (LocaleFactory.class) {
                if (factory == null) {
                    factory = new LocaleFactory();
                }
            }
        }
        return factory;
    }

    public LocaleEnum getLocale(LocaleEnum locale) {
        switch (locale) {
            case UA:
                return UA;
            case RU:
                return RU;
            default:
                return EN;
        }
    }
}
