package wjx.skill.exception;


import wjx.skill.enums.ResultEnum;

/**
 * 自定义异常 全局异常处理
 * Created by WangJX on 2018/10/29.
 */
public class SysException extends RuntimeException {

    private Integer code;

    public SysException(ResultEnum resultEnum) {
        super(resultEnum.getMsg());
        this.code = resultEnum.getCode();
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }
}
