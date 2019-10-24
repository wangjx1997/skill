package wjx.skill.config.datesource;

import java.lang.annotation.*;

/**
 * @Author wjx
 * @Description 作用于类、接口或者方法上
 */
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface TargetDataSource {

    String name();
}
