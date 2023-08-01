package codesquad.kr.gyeonggidoidle.issuetracker.annotation;

import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.core.annotation.AliasFor;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@WebMvcTest()
public @interface ControllerTest {

    @AliasFor(annotation = WebMvcTest.class)
    Class<?>[] value() default {};
}