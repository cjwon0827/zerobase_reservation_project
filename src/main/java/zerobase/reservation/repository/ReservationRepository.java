package zerobase.reservation.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import zerobase.reservation.entity.Reservation;

import java.util.List;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {
    @Query(value = "SELECT TIMESTAMPDIFF(MINUTE, r.reservation_date , now()) from reservation_info r where r.store_name = :name and r.reservation_check = false and r.uid = :uid",
            nativeQuery = true
            )
    int findMinute(@Param(value = "name") String storeName, @Param(value = "uid") String uid);

    @Query(value = "update reservation_info set reservation_check = true, entrance_date = now() where store_name = :name",
            nativeQuery = true)
    void reservationCheck(@Param(value = "name") String storeName);

    List<Reservation> findAllByUid(String uid);

    @Query(value = "delete from reservation_info where id = :id and reservation_check = false",
            nativeQuery = true)
    void deleteReservation(@Param(value = "id") Long id);

    @Query(value = "select * from reservation_info where uid = :uid and reservation_check = true",
        nativeQuery = true)
    List<Reservation> findAllByUidAndCheck(@Param(value = "uid") String uid);

    @Query(value = "select * from reservation_info where uid = :uid and reservation_check = true and review_check = false",
            nativeQuery = true)
    List<Reservation> writableReview(@Param(value = "uid") String uid);

    @Query(value = "update reservation_info set review_check = true where id = :id",
            nativeQuery = true)
    void updateReviewCheck(@Param(value = "id") Long id);

    @Query(value = "update reservation_info set review_check = false where id = :id",
        nativeQuery = true)
    void updateReviewCheckFalse(@Param(value = "id") Long id);
}
