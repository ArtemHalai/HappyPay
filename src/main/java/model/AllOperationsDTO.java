package model;

import java.util.List;

import static enums.Attributes.PAGE_SIZE;

/**
 * Represents a AllOperationsDTO object.
 */
public class AllOperationsDTO {

    private int userId;
    private int pageSize;
    private List<OperationsData> list;

    /**
     * Creates a AllOperationsDTO object without params.
     */
    public AllOperationsDTO() {
        this.pageSize = Integer.parseInt(PAGE_SIZE.getName());
    }

    /**
     * Gets the value of {@link #pageSize}.
     *
     * @return the value of {@link #pageSize}.
     */
    public int getPageSize() {
        return pageSize;
    }

    /**
     * This is a setter which sets the page size.
     *
     * @param pageSize - the page size to be set
     */
    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    /**
     * Gets the value of {@link #userId}.
     *
     * @return the value of {@link #userId}.
     */
    public int getUserId() {
        return userId;
    }

    /**
     * This is a setter which sets the user id.
     *
     * @param userId - the user id to be set
     */
    public void setUserId(int userId) {
        this.userId = userId;
    }

    /**
     * Gets the list of OperationsData objects {@link #list}.
     *
     * @return the list of OperationsData objects {@link #list}.
     */
    public List<OperationsData> getList() {
        return list;
    }

    /**
     * This is a setter which sets the list.
     *
     * @param list - the list to be set
     */
    public void setList(List<OperationsData> list) {
        this.list = list;
    }
}
