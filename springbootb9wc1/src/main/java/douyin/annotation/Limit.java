package douyin.annotation;

import java.lang.annotation.*;
import java.util.concurrent.TimeUnit;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
@Documented
public @interface Limit {
    /**
     * 资源的key,唯一,默认使用方法名称
     */
    String key() default "";

    /**
     * 最多的访问限制次数
     */
    double permitsPerSecond() default 1;

    /**
     * 获取令牌的最大等待时间
     */
    long timeout() default 0;

    /**
     * 获取令牌最大等待时间单位
     */
    TimeUnit timeunit() default TimeUnit.MILLISECONDS;

    /**
     * 限流提示语
     */
    String msg() default "系统繁忙，请稍后再试";
}
