package wjx.skill.handler;


import org.apache.shiro.authz.UnauthenticatedException;
import org.apache.shiro.authz.UnauthorizedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import wjx.skill.enums.ResultEnum;
import wjx.skill.exception.SysException;
import wjx.skill.util.ResultUtil;
import wjx.skill.vo.ResultVo;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;

/**
 * Created by WangJX on 2018/10/29.
 */
//@ControllerAdvice
public class ExceptionHandelr {

    private final static Logger logger = LoggerFactory.getLogger(ExceptionHandelr.class);

    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public ResultVo handler(Exception e){
        if(e instanceof SysException){
            SysException sysException = (SysException) e;
            return ResultUtil.fail(sysException.getCode(),sysException.getMessage());
        }else if(e instanceof UnauthenticatedException){
            return ResultUtil.fail(ResultEnum.NO_PERMISSIONS);
        }else if(e instanceof UnauthorizedException){
            return ResultUtil.fail(ResultEnum.NO_PERMISSIONS);
        }else if(e instanceof ConstraintViolationException){
            StringBuilder str = new StringBuilder();
            for(ConstraintViolation violation: ((ConstraintViolationException) e).getConstraintViolations()){
                str.append(violation.getMessage());
           }
           return ResultUtil.fail(420,str.toString());
        }else {
            logger.error("【系统异常】={}",e);
            return ResultUtil.fail();
        }
    }
}
