package model;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

import static enums.Attributes.PAGE_SIZE;

@Data
public class AllOperationsDTO {
    private int userId;
    private int pageSize;
    private List<OperationsData> list = new ArrayList<>();

    public AllOperationsDTO() {
        this.pageSize = Integer.parseInt(PAGE_SIZE.getName());
    }
}
