package factories;

public class LocaleFactory {
    private static volatile LocaleFactory factory = null;
    private static final String UK = "uk";
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
            case UK:
                return UK;
            case RU:
                return RU;
            default:
                return EN;
        }
    }
}
