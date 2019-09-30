package model;

import java.util.List;

import static enums.Attributes.PAGE_SIZE;

/**
 * Represents a RefillPaginationDTO object.
 */
public class RefillPaginationDTO {

    private int userId;
    private int page;
    private int count;
    private int pageSize;
    private List<RefillOperation> list;

    /**
     * Creates a RefillPaginationDTO object without params.
     */
    public RefillPaginationDTO() {
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
     * Gets the value of {@link #page}.
     *
     * @return the value of {@link #page}.
     */
    public int getPage() {
        return page;
    }

    /**
     * This is a setter which sets the page.
     *
     * @param page - the page to be set
     */
    public void setPage(int page) {
        this.page = page;
    }

    /**
     * Gets the value of {@link #count}.
     *
     * @return the value of {@link #count}.
     */
    public int getCount() {
        return count;
    }

    /**
     * This is a setter which sets the count.
     *
     * @param count - the count to be set
     */
    public void setCount(int count) {
        this.count = count;
    }

    /**
     * Gets the list of RefillOperation objects {@link #list}.
     *
     * @return the list of RefillOperation objects {@link #list}.
     */
    public List<RefillOperation> getList() {
        return list;
    }

    /**
     * This is a setter which sets the list.
     *
     * @param list - the list to be set
     */
    public void setList(List<RefillOperation> list) {
        this.list = list;
    }
}
