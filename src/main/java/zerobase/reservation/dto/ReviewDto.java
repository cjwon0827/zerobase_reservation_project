package zerobase.reservation.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReviewDto {
    private Long reservationId;     //예약 ID 값
    private Long reviewId;          //리뷰 ID 값
    private String uid;             //사용자 아이디
    private String userName;        //사용자 이름
    private String storeName;       //가게명
    private int score;              //별점
    private String content;         //리뷰 내용
}
