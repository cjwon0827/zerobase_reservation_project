package zerobase.reservation.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import zerobase.reservation.domain.MemberRole;
import zerobase.reservation.dto.MemberDto;
import zerobase.reservation.entity.Member;
import zerobase.reservation.repository.MemberRepository;

import java.time.LocalDateTime;

/**
 * member 정보를 관리하기 위한 Service
 */
@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;
    private final BCryptPasswordEncoder passwordEncoder;


    /**
     * 일반 사용자들이 회원 가입을 위해 작성한 정보를 member 테이블에 저장하는 기능
     * @param memberDto
     */
    @Transactional
    public void createUser(MemberDto memberDto) {
        Member member = new Member();
        String encPassword = passwordEncoder.encode(memberDto.getPassword());
        member.setPassword(encPassword);
        member.setUid(memberDto.getUid());
        member.setName(memberDto.getName());
        member.setTel(memberDto.getTel());
        member.setCreateDate(LocalDateTime.now());
        member.setRole(MemberRole.ROLE_USER);
        memberRepository.save(member);
    }


    /**
     * 관리자가 회원 가입을 위해 작성한 정보를 member 테이블에 저장하는 기능
     * @param memberDto
     */
    @Transactional
    public void createAdmin(MemberDto memberDto) {
        Member member = new Member();
        String encPassword = passwordEncoder.encode(memberDto.getPassword());
        member.setPassword(encPassword);
        member.setUid(memberDto.getUid());
        member.setName(memberDto.getName());
        member.setTel(memberDto.getTel());
        member.setCreateDate(LocalDateTime.now());
        member.setRole(MemberRole.ROLE_ADMIN);
        memberRepository.save(member);
    }

}
