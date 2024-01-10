package cc.mikaka.ddd.service.event;

import java.lang.annotation.*;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface EventHandleables {
    EventHandleable[] value() default {};
}
