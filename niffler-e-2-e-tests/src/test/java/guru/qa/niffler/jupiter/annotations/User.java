package guru.qa.niffler.jupiter.annotations;

import guru.qa.niffler.jupiter.extensions.UsersQueueExtension;
import org.hibernate.usertype.UserType;
import org.junit.jupiter.api.extension.ExtendWith;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
@ExtendWith(UsersQueueExtension.class)
public @interface User {
    UserType userType();

    enum UserType {
        WITH_FRIENDS, INVITATION_SENT, INVITATION_RECEIVED
    }
}
