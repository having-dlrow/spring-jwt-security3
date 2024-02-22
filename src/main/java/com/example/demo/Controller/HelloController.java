package com.example.demo.Controller;

import com.example.demo.Member.Member;
import com.example.demo.Member.MemberRepository;
import com.example.demo.config.auth.PrincipalDetails;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@Slf4j
public class HelloController {

    private final MemberRepository memberRepository;

    private final PasswordEncoder passwordEncoder;

    @GetMapping("/")
    public String greeting() {
        return "Hello, World";
    }

    @GetMapping("/user")
    public String user(Authentication authentication) {
        PrincipalDetails principal = (PrincipalDetails) authentication.getPrincipal();
        System.out.println("principal : " + principal.getUser().getId());
        System.out.println("principal : " + principal.getUser().getPassword());
        System.out.println("principal : " + principal.getUser().getUsername());

        return "<h1>user</h1>";
    }

    // 어드민이 접근 가능
    @GetMapping("admin/users")
    public List<Member> users() {
        return memberRepository.findAll();
    }

    @PostMapping("join")
    public String join(@RequestBody Member user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRoles("ROLE_MANAGER");
        memberRepository.save(user);
        return "회원가입완료";
    }
}
