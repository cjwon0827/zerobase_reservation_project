package zerobase.reservation.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@Entity(name = "reservation_info")
public class Reservation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; //reservation_info 테이블 고유 ID 값

    private String uid; //사용자 아이디
    private String tel; //사용자 전화번호
    private String storeName; //가게 이름
    private String userName; //사용자 이름

    @Column(name = "reservation_date")
    private LocalDateTime reservationDate; //예약 Date

    @Column(name = "entrance_date")
    private LocalDateTime entranceDate; //입장 Date

    @Column(name = "reservation_check")
    private boolean reservationCheck; //입장 여부

    @Column(name = "review_check")
    private boolean reviewCheck; //리뷰 작성 여부

}
