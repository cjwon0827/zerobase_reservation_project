package zerobase.reservation.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import zerobase.reservation.entity.Review;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Long> {
    Review getById(Long id);
    List<Review> findAllByUid(String uid);
}
