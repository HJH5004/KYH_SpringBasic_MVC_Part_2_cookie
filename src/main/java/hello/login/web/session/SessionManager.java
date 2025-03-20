package hello.login.web.session;


import org.springframework.stereotype.Component;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class SessionManager {
    public static final String SESSION_COOKIE_NAME = "mySessionId";
    private Map<String, Object> sessionStrore = new ConcurrentHashMap<>();



    /**
     * 세션 생서
     *
     *  - sessionId 생성
     *  - 새션 저장소에 sessionId랑 보관할 값 저장
     *  sessionId 응답 쿠키를 생성해서 클라이언트에 전달
     *
     * */
    public void createSession (Object value, HttpServletResponse response) {


        String sessionId = UUID.randomUUID().toString();
        sessionStrore.put(sessionId, value);

        Cookie mySessionCookie = new Cookie(SESSION_COOKIE_NAME, sessionId);

        response.addCookie(mySessionCookie);

    }

    /**
     * 세션 조회
     * */
    public Object getSession (HttpServletRequest request) {
        Cookie cookies = findCookie(request, SESSION_COOKIE_NAME);

        //sessionId를 가지고 있는지 조회
        if(cookies == null) {
            return null;
        }


        return sessionStrore.get(cookies.getValue());
    }

    /**
     * 세션 삭제
     * */

    public void expire(HttpServletRequest request) {
        Cookie cookies = findCookie(request, SESSION_COOKIE_NAME);

        //sessionId를 가지고 있는지 조회
        if(cookies != null) {
            sessionStrore.remove(cookies.getValue());
        }
    }

    public Cookie findCookie(HttpServletRequest request, String name) {
        Cookie[] cookies = request.getCookies();

        if(cookies == null) {
            return null;
        }

        return Arrays.stream(cookies)
                .filter(cookie -> cookie.getName().equals(name))
                .findAny()
                .orElse(null);
    }
}
