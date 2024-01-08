package zerobase.reservation.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MemberDto {
    private String uid; //아이디
    private String password; //비밀번호
    private String name; //이름
    private String tel; //전화번호
}
