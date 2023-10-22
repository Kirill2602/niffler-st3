package guru.qa.niffler.jupiter.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.PARAMETER, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface GenerateUser {
    String username() default "";

    String password() default "";

    boolean handleAnnotation() default true;

    Friend friends() default @Friend(handleAnnotation = false);

    IncomeInvitation incomeInvitations() default @IncomeInvitation(handleAnnotation = false);

    OutcomeInvitation outcomeInvitations() default @OutcomeInvitation(handleAnnotation = false);
}
