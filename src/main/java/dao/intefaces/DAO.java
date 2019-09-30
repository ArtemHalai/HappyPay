package dao.intefaces;

/**
 * Define a typed data access object used for executing requests to database.
 */
public interface DAO<T>{

    /**
     * Method to get typed object by id.
     *
     * @param id The id.
     * @return The typed object by given id.
     */
    T getById(int id);
}
