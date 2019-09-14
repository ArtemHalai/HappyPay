package comparator;

import java.sql.Date;
import java.util.Comparator;

public class OperationDateComparator implements Comparator<Date> {

    @Override
    public int compare(Date o1, Date o2) {
        return o2.compareTo(o1);
    }
}
