package hello.login.domain.login;


import com.fasterxml.classmate.MemberResolver;
import hello.login.domain.member.Member;
import hello.login.domain.member.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class LoginService {

    private final MemberRepository memberRepository;



    public Member login(String longId, String password){

        return memberRepository.findByLoginId(longId)
                .filter(m -> m.getPassword().equals(password))
                .orElse(null);
    }
}
