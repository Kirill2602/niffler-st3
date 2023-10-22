package guru.qa.niffler.test.web;

import guru.qa.niffler.jupiter.annotations.ApiLogin;
import guru.qa.niffler.jupiter.annotations.GenerateUser;
import guru.qa.niffler.jupiter.annotations.GeneratedUser;
import guru.qa.niffler.model.UserJson;
import org.junit.jupiter.api.Test;

import static guru.qa.niffler.jupiter.annotations.GeneratedUser.Selector.NESTED;
import static guru.qa.niffler.jupiter.annotations.GeneratedUser.Selector.OUTER;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class GenerateUserExtensionTest extends BaseWebTest {
    @Test
    @ApiLogin(user = @GenerateUser)
    @GenerateUser
    void shouldResolveDifferentUsersFromAnnotation(@GeneratedUser(selector = NESTED) UserJson nestedCreateUser,
                                                   @GeneratedUser(selector = OUTER) UserJson outerCreateUser) {
        System.out.println(nestedCreateUser.getUsername());
        System.out.println(outerCreateUser.getUsername());
        assertNotEquals(nestedCreateUser.getUsername(), outerCreateUser.getUsername());
    }
}
