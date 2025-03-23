package hello.login.web.login;

import hello.login.domain.login.LoginService;
import hello.login.domain.member.Member;
import hello.login.web.SessionConst;
import hello.login.web.session.SessionManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.*;
import javax.validation.Valid;

@Slf4j
@Controller
@RequiredArgsConstructor
public class LoginController {

    private final LoginService loginService;
    private final SessionManager sessionManager;


    @GetMapping("/login")
    public String loginForm (@ModelAttribute("loginForm") LoginForm loginForm) {
        return "login/loginForm";
    }

//    @PostMapping("/login")
    public String loginV1 (@Valid @ModelAttribute LoginForm form , BindingResult result, HttpServletResponse response){
        if(result.hasErrors()){
            return "login/loginForm";
        }

        Member login = loginService.login(form.getLoginId(), form.getPassword());


        //로그인 실패 처리
        if(login == null){
            result.reject("loginFail", "아이디 또는 비밃번호가 맞지 않습니다.");
            return "login/loginForm";
        }

        //로그인 성공 처리 todo
        Cookie cookieId = new Cookie("memberId", String.valueOf(login.getId()));
        response.addCookie(cookieId);


        return "redirect:/";
    }

//    @PostMapping("/login")
    public String loginV2 (@Valid @ModelAttribute LoginForm form , BindingResult result, HttpServletResponse response){
        if(result.hasErrors()){
            return "login/loginForm";
        }

        Member login = loginService.login(form.getLoginId(), form.getPassword());


        //로그인 실패 처리
        if(login == null){
            result.reject("loginFail", "아이디 또는 비밃번호가 맞지 않습니다.");
            return "login/loginForm";
        }

        sessionManager.createSession(login, response);


        return "redirect:/";
    }

    @PostMapping("/login")
    public String loginV3 (@Valid @ModelAttribute LoginForm form , BindingResult result, HttpServletRequest request){
        if(result.hasErrors()){
            return "login/loginForm";
        }

        Member login = loginService.login(form.getLoginId(), form.getPassword());


        //로그인 실패 처리
        if(login == null) {
            result.reject("loginFail", "아이디 또는 비밃번호가 맞지 않습니다.");
            return "login/loginForm";
        }

        //세션이 이씅면 세션을 반환하고 없으면 신규 세션을 생성 한다.
        HttpSession session = request.getSession();
        //세션에 로그인 회원 정보 보관.
        session.setAttribute(SessionConst.LOGIN_MEMBER, login);

        return "redirect:/";
    }
//    @PostMapping("/logout")
    public String logoutV1 (HttpServletResponse response){
        expireCookie(response, "memberId");
        return "redirect:/";
    }

//    @PostMapping("/logout")
    public String logoutV2 (HttpServletRequest request, HttpServletResponse response){
        sessionManager.expire(request);
        return "redirect:/";
    }

    @PostMapping("/logout")
    public String logoutV3 (HttpServletRequest request){
        HttpSession session = request.getSession(false);
        if(session != null){
            session.invalidate();
        }

        return "redirect:/";
    }

    private void expireCookie(HttpServletResponse response, String cookieName) {
        Cookie cookie = new Cookie(cookieName, null);
        cookie.setMaxAge(0);
        response.addCookie(cookie);
    }

}
