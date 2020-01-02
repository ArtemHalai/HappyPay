package model;

import lombok.Data;

import java.util.List;

import static enums.Attributes.PAGE_SIZE;

@Data
public class RefillPaginationDTO {
    private int userId;
    private int page;
    private int count;
    private int pageSize;
    private List<RefillOperation> list;

    public RefillPaginationDTO() {
        this.pageSize = Integer.parseInt(PAGE_SIZE.getName());
    }
}
