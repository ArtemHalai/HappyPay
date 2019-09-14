package model;

import java.util.List;

import static enums.Attributes.PAGE_SIZE;

public class RefillPaginationDTO {

    private int userId;
    private int page;
    private int count;
    private int pageSize;
    private List<RefillOperation> list;

    public RefillPaginationDTO() {
        this.pageSize = Integer.parseInt(PAGE_SIZE.getName());
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public List<RefillOperation> getList() {
        return list;
    }

    public void setList(List<RefillOperation> list) {
        this.list = list;
    }
}
