package hello.login.web.interceptor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

@Slf4j
public class LogInInterceptor implements HandlerInterceptor {

    public static final String LOG_ID = "LogID";

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String requestURI = request.getRequestURI();
        String uuid = UUID.randomUUID().toString();

         request.setAttribute(LOG_ID, uuid);


         // @ RequestMapping : HandlerMethos
        // wjdwjr flthtm : ㄲㄷ
        if(handler instanceof HandlerMethod){
            HandlerMethod handler1 = (HandlerMethod) handler;

        }

        log.info("REUQTS [{}] [{}] [{}] ",uuid, requestURI, handler);
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        log.info("postHandle [{}]", modelAndView);
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        String requestURI = request.getRequestURI();
        Object logId = request.getAttribute(LOG_ID);

        log.info("RESPONSE [{}] [{}] [{}] ",logId, requestURI, handler);

    }
}
