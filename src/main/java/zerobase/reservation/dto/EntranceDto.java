package zerobase.reservation.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EntranceDto {
    private Long id;                //가게 id 값
    private String storeName;       //입장 가게이름
    private String name;            //사용자 이름
    private String tel;             //사용자 전화번호
}
