package zerobase.reservation.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@Entity(name = "review")
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; //review 테이블 고유 ID 값

    @Column(name = "reservation_id")
    private Long reservationId; //reservation_info 테이블 고유 ID 값

    private String uid; //사용자 아이디

    @Column(name = "user_name")
    private String userName; //사용자 이름

    @Column(name = "store_name")
    private String storeName; //가게 이름

    @Column(name = "review_content", length = 2500)
    private String content; //리뷰 내용

    private int score; //별점

    @Column(name = "regist_date")
    private LocalDateTime registDate; //리뷰 작성 Date

    @Column(name = "modify_date")
    private LocalDateTime modifyDate; //리뷰 수정 Date
}
