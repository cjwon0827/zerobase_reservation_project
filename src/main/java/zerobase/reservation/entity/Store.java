package zerobase.reservation.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@Entity(name = "store")
public class Store {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; //store 테이블 고유 ID 값

    @Column(nullable = false, unique = true)
    private String name; //매장 이름

    @Column(nullable = false)
    private String tel; //매장 전화번호

    private double score; //평균 별점

    @Column(nullable = false)
    private String address; //매장 주소

    @Column(name = "shop_detail")
    private String shopDetail; //매장 상세사항
}
