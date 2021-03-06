package wjx.skill.util;


import wjx.skill.enums.ResultEnum;
import wjx.skill.vo.ResultVo;

/**
 * Created by WangJX on 2018/10/29.
 */
public class ResultUtil {

    public static ResultVo success(Object o){
        ResultVo resultVo = new ResultVo();
        resultVo.setCode(ResultEnum.SUCCESS.getCode());
        resultVo.setMsg(ResultEnum.SUCCESS.getMsg());
        resultVo.setData(o);
        return resultVo;
    }


    public static ResultVo fail(){
        ResultVo resultVo = new ResultVo();
        resultVo.setCode(ResultEnum.UNKNOW_ERROR.getCode());
        resultVo.setMsg(ResultEnum.UNKNOW_ERROR.getMsg());
        return resultVo;
    }

    public static ResultVo fail(Integer code,String msg){
        ResultVo resultVo = new ResultVo();
        resultVo.setCode(code);
        resultVo.setMsg(msg);
        return resultVo;
    }

    public static ResultVo fail(ResultEnum resultEnum){
        ResultVo resultVo = new ResultVo();
        resultVo.setCode(resultEnum.getCode());
        resultVo.setMsg(resultEnum.getMsg());
        return  resultVo;
    }
}
