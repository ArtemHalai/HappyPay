package comparator;

import java.sql.Date;
import java.util.Comparator;

/**
 * Define a comparator object which implements Comparator interface.
 *
 * @see Comparator
 */
public class OperationDateComparator implements Comparator<Date> {

    /**
     * Method used for comparing date in descending order.
     */
    @Override
    public int compare(Date o1, Date o2) {
        return o2.compareTo(o1);
    }
}
