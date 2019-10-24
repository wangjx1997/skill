package wjx.skill.model;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.metadata.BaseRowModel;
import lombok.Data;

/**
 * @author WangJX
 * @date 2019/10/23 17:38
 */
@Data
public class ListedCompany extends BaseRowModel {
    @ExcelProperty(value = "company_name", index = 0)
    private String company_name;
    @ExcelProperty(value = "stockType", index = 1)
    private String stockType;
    @ExcelProperty(value = "province", index = 2)
    private String province;
    @ExcelProperty(value = "city", index = 3)
    private String city;
    @ExcelProperty(value = "es_number", index = 4)
    private String es_number;
    @ExcelProperty(value = "company_desc", index = 5)
    private String company_desc;
}
