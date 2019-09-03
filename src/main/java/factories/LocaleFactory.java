package factories;

public class LocaleFactory {
    private static volatile LocaleFactory factory = null;
    private static final String UA = "ua";
    private static final String RU = "ru";
    private static final String EN = "en";

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

    public String getLocale(String locale) {
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
