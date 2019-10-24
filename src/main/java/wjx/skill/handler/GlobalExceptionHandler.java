/**
 * 
 */
package wjx.skill.handler;

import org.apache.shiro.authz.UnauthenticatedException;
import org.apache.shiro.authz.UnauthorizedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;
import wjx.skill.enums.ResultEnum;
import wjx.skill.exception.SysException;
import wjx.skill.vo.Response;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;

/**
 * @author Administrator
 *
 */
@ControllerAdvice
public class GlobalExceptionHandler {
	private final static Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

	@ExceptionHandler(value = UnauthorizedException.class)
	public static ModelAndView UnauthorizedException(Exception e, Model model) {
		logger.info("无权限");
		model.addAttribute("error", "对不起你无权访问,请联系管理员！");
		return new ModelAndView("/unauthorized","errorModel", model);
	}
	@ExceptionHandler(value = UnauthenticatedException.class)
	public static ModelAndView UnauthenticatedException(Exception e,Model model) {
		logger.info("未登录");
		model.addAttribute("error", ResultEnum.USER_SESSION_TIMEOUT.getMsg());
		return new ModelAndView("/unauthorized","errorModel", model);
	}

	@ExceptionHandler(value = Exception.class)
	public ResponseEntity<Response> handler(Exception e){
		if(e instanceof SysException){
			logger.info("自定义异常");
			SysException sysException = (SysException) e;
			ResponseEntity.ok().body(new Response(false, sysException.getMessage()));
		}else if(e instanceof HttpRequestMethodNotSupportedException){
			logger.info("接口请求方式错误");
			return ResponseEntity.ok().body(new Response(false, e.getMessage()));
		}else if(e instanceof ConstraintViolationException){
			logger.info("传参不对");
			StringBuilder str = new StringBuilder();
			for(ConstraintViolation violation: ((ConstraintViolationException) e).getConstraintViolations()){
				str.append(violation.getMessage()+",");
			}
			return ResponseEntity.ok().body(new Response(false,str.toString()));
		}else {
			logger.error("【系统异常】={}",e);
			return ResponseEntity.ok().body(new Response(false,e.getMessage()));
		}
		logger.error("【系统异常】={}",e);
		return ResponseEntity.ok().body(new Response(false,e.getMessage()));
	}
}
