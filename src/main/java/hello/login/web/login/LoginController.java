package hello.login.web.login;

import hello.login.domain.login.LoginService;
import hello.login.domain.member.Member;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;

@Slf4j
public class LoginController {

    private LoginService loginService;


    @GetMapping("/login")
    public String loginForm (@ModelAttribute("loginForm") LoginForm loginForm){
        return "login/loginForm";
    }

    @PostMapping("/login")
    public String login (@Valid @ModelAttribute LoginForm form , BindingResult result){
        if(result.hasErrors()){
            return "login/loginForm";
        }

        Member login = loginService.login(form.getLoginId(), form.getPassword());

        if(login == null){
        }

        return "redirect:/";
    }

}
