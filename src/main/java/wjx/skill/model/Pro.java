package wjx.skill.model;

import lombok.Data;

/**
 * @author WangJX
 * @date 2019/8/19 16:40
 */
@Data
public class Pro {
    private Integer id;
    private Integer sort;
    private Integer parent_id;
    private String province_number;
    private String province_name;
    private String parent_number;

}
