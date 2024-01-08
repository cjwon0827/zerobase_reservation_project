package zerobase.reservation.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReservationDto {
    private String storeName;       //가게이름
    private String uid;             //예약자 아이디
    private String userName;        //예약자 이름
    private String tel;             //예약자 전화번호
    private String date;            //예약 날짜
}
