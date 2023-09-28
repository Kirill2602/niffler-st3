package guru.qa.niffler.api.interceptor;

import com.github.jknack.handlebars.internal.lang3.StringUtils;
import guru.qa.niffler.api.context.SessionStorageContext;
import okhttp3.Interceptor;
import okhttp3.Response;

import java.io.IOException;

public class RecievedCookieInterceptor implements Interceptor {
    @Override
    public Response intercept(Chain chain) throws IOException {
        Response response = chain.proceed(chain.request());
        String locationHeader = response.header("Location");
        if (response.code() == 302 && locationHeader != null && locationHeader.contains("authorized?code=")) {
            SessionStorageContext.getInstance().setCode(StringUtils.substringAfter(locationHeader, "code="));
        }
        return response;
    }
}
