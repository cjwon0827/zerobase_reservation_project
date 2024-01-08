package zerobase.reservation.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import zerobase.reservation.domain.MemberRole;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@Entity(name = "member")
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; //member 테이블 고유 ID 값

    @Column(nullable = false, unique = true)
    private String uid; //사용자 id

    @Column(nullable = false)
    private String password; //사용자 비밀번호

    @Column(nullable = false)
    private String name; //사용자 이름

    @Column(nullable = false)
    private String tel; //사용자 전화번호

    @Enumerated(EnumType.STRING)
    private MemberRole role; //사용자 권한

    private LocalDateTime createDate; //회원가입 Date
}
