package model;

import java.util.List;

import static enums.Attributes.PAGE_SIZE;

public class AllOperationsDTO {
    private int userId;
    private int pageSize;
    private List<OperationsData> list;

    public AllOperationsDTO() {
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

    public List<OperationsData> getList() {
        return list;
    }

    public void setList(List<OperationsData> list) {
        this.list = list;
    }
}
