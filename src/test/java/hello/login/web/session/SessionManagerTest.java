package hello.login.web.session;


import hello.login.domain.member.Member;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

import javax.servlet.http.HttpServletResponse;

public class SessionManagerTest {

    SessionManager sessionManager = new SessionManager();


    @Test
    void testSession(){

        MockHttpServletResponse response = new MockHttpServletResponse();

        //세션 생성
        Member member = new Member();
        sessionManager.createSession(member, response);


        //요청 응답 쿠키 저장
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.setCookies(response.getCookies());

        Object result = sessionManager.getSession(request);
        Assertions.assertThat(result).isEqualTo(member);

    }

}
