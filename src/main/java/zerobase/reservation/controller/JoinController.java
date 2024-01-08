package zerobase.reservation.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import zerobase.reservation.dto.MemberDto;
import zerobase.reservation.service.MemberService;

/**
 * 회원가입을 위한 Controller
 */
@Controller
@RequiredArgsConstructor
public class JoinController {
    private final MemberService memberService;

    /**
     * 관리자 회원가입을 하기 위한 정보를 작성 후 POST 방식으로 전달
     * (회원가입 성공 후 loginForm.html로 리다이렉트)
     * @param memberDto
     * @return
     */
    @PostMapping("/adminJoin")
    public String adminJoin(MemberDto memberDto){
        memberService.createAdmin(memberDto);
        return "redirect:/loginForm";
    }


    /**
     * 일반 사용자들 회원가입을 하기 위한 정보를 작성 후 POST 방식으로 전달
     * (회원가입 성공 후 loginForm.html로 리다이렉트)
     * @param memberDto
     * @return
     */
    @PostMapping("/userJoin")
    public String userJoin(MemberDto memberDto){
        memberService.createUser(memberDto);
        return "redirect:/loginForm";
    }
}
