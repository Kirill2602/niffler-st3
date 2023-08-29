package guru.qa.niffler.jupiter.extensions;

import guru.qa.niffler.jupiter.annotations.User;
import guru.qa.niffler.model.UserJson;
import io.qameta.allure.AllureId;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.*;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

public class UsersQueueExtension implements BeforeEachCallback, AfterTestExecutionCallback, ParameterResolver {
    private static ExtensionContext.Namespace NAMESPACE = ExtensionContext.Namespace.create(UsersQueueExtension.class);
    private static Map<User.UserType, Queue<UserJson>> usersQueue = new ConcurrentHashMap<>();

    static {
        Queue<UserJson> usersWithFriends = new ConcurrentLinkedQueue<>();
        usersWithFriends.add(bindUser("Kirill", "12345"));
        usersWithFriends.add(bindUser("Vova", "12345"));
        usersQueue.put(User.UserType.WITH_FRIENDS, usersWithFriends);
        Queue<UserJson> usersInSent = new ConcurrentLinkedQueue<>();
        usersInSent.add(bindUser("Dima", "12345"));
        usersInSent.add(bindUser("Oleg", "12345"));
        usersQueue.put(User.UserType.INVITATION_SENT, usersInSent);
        Queue<UserJson> usersInRc = new ConcurrentLinkedQueue<>();
        usersInRc.add(bindUser("Roma", "12345"));
        usersInRc.add(bindUser("Anna", "12345"));
        usersQueue.put(User.UserType.INVITATION_RECEIVED, usersInRc);
    }

    @Override
    public void beforeEach(ExtensionContext context) throws Exception {
        Optional<Method> beforeEach = findBeforeEach(context);
        beforeEach.ifPresent(method -> fillStoreContextCreated(context, method));
        fillStoreContextCreated(context, context.getRequiredTestMethod());
    }

    @Override
    public void afterTestExecution(ExtensionContext context) throws Exception {
        Optional<Method> beforeEach = findBeforeEach(context);
        beforeEach.ifPresent(method -> fillStoreContextDeleted(context, method));
        fillStoreContextDeleted(context, context.getRequiredTestMethod());
    }

    @Override
    public boolean supportsParameter(ParameterContext parameterContext, ExtensionContext extensionContext) throws ParameterResolutionException {
        return parameterContext.getParameter()
                .getType()
                .isAssignableFrom(UserJson.class) && parameterContext.getParameter().isAnnotationPresent(User.class);
    }

    @Override
    public Object resolveParameter(ParameterContext parameterContext, ExtensionContext extensionContext) throws ParameterResolutionException {
        if (Objects.nonNull(parameterContext.getParameter().getDeclaringExecutable()
                .getAnnotation(BeforeEach.class))) {
            Optional<Method> beforeEach = findBeforeEach(extensionContext);
            if (beforeEach.isPresent()) {
                return extensionContext.getStore(NAMESPACE)
                        .get(getUniqueId(extensionContext, beforeEach.get(), parameterContext.getParameter()), UserJson.class);
            }
        }
        return extensionContext.getStore(NAMESPACE)
                .get(getUniqueId(extensionContext, extensionContext.getRequiredTestMethod(),
                        parameterContext.getParameter()), UserJson.class);
    }

    private static void fillStoreContextDeleted(ExtensionContext context, Method method){
        Arrays.stream(method.getParameters())
                .filter(parameter -> parameter.isAnnotationPresent(User.class)
                        && parameter.getType().isAssignableFrom(UserJson.class))
                .forEach(parameter -> {
                    UserJson userFromTest = context.getStore(NAMESPACE)
                            .get(getUniqueId(context, method, parameter), UserJson.class);
                    if (Objects.nonNull(userFromTest)) {
                        context.getStore(NAMESPACE).remove(getUniqueId(context, method, parameter));
                        Queue<UserJson> userQueue = usersQueue.get(userFromTest.getUserType());
                        userQueue.add(userFromTest);
                    }
                });
    }

    private static void fillStoreContextCreated(ExtensionContext context, Method method){
        Arrays.stream(method.getParameters())
                .filter(parameter -> parameter.isAnnotationPresent(User.class)
                        && parameter.getType().isAssignableFrom(UserJson.class))
                .forEach(parameter -> {
                    User parameterAnnotation = parameter.getAnnotation(User.class);
                    Queue<UserJson> usersQueueByType = usersQueue.get(parameterAnnotation.userType());
                    UserJson candidateForTest = null;
                    while (Objects.isNull(candidateForTest)) {
                        candidateForTest = usersQueueByType.poll();
                    }
                    candidateForTest.setUserType(parameterAnnotation.userType());
                    context.getStore(NAMESPACE).put(getUniqueId(context, method, parameter), candidateForTest);
                });
    }

    private static String getUniqueId(ExtensionContext context, Method method, Parameter parameter) {
        return new StringJoiner("_")
                .add(context.getUniqueId())
                .add(method.getName())
                .add(parameter.getName()).toString();
    }

    private static Optional<Method> findBeforeEach(ExtensionContext context) {
        return Arrays.stream(context.getRequiredTestClass().getDeclaredMethods())
                .filter(method -> method.isAnnotationPresent(BeforeEach.class))
                .findFirst();
    }

    private static UserJson bindUser(String username, String password) {
        UserJson user = new UserJson();
        user.setUsername(username);
        user.setPassword(password);
        return user;
    }
}
