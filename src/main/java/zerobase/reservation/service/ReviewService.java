package zerobase.reservation.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import zerobase.reservation.dto.ReviewDto;
import zerobase.reservation.entity.Review;
import zerobase.reservation.repository.ReservationRepository;
import zerobase.reservation.repository.ReviewRepository;

import java.time.LocalDateTime;
import java.util.List;

/**
 * review 정보를 관리하기 위한 Service
 */
@Service
@RequiredArgsConstructor
public class ReviewService {
    private final ReviewRepository reviewRepository;
    private final ReservationRepository reservationRepository;

    /**
     * 사용자가 작성한 리뷰를 review 테이블에 저장하는 기능(예약정보 테이블에 리뷰 작성 완료 값 Update)
     * @param reviewDto
     */
    @Transactional
    public void saveReview(ReviewDto reviewDto) {
        Review review = new Review();
        review.setUid(reviewDto.getUid());
        review.setReservationId(reviewDto.getReservationId());
        review.setUserName(reviewDto.getUserName());
        review.setStoreName(reviewDto.getStoreName());
        review.setScore(reviewDto.getScore());
        review.setContent(reviewDto.getContent());
        review.setRegistDate(LocalDateTime.now());

        reviewRepository.save(review);
        reservationRepository.updateReviewCheck(reviewDto.getReservationId());
    }

    /**
     * 사용자에게 작성한 리뷰를 보여주는 기능
     * @param uid
     * @return
     */
    public List<Review> findReview(String uid) {
        return reviewRepository.findAllByUid(uid);
    }

    /**
     * 리뷰 ID 값을 비교하여 일치하는 Review 객체 전달
     * @param id
     * @return
     */
    public Review getById(Long id) {
        return reviewRepository.getById(id);
    }

    /**
     * 사용자가 수정한 리뷰를 review 테이블에 저장하는 기능
     * @param reviewDto
     */
    @Transactional
    public void updateReview(ReviewDto reviewDto) {
        Review nowReview = reviewRepository.getById(reviewDto.getReviewId());
        nowReview.setReservationId(reviewDto.getReservationId());
        nowReview.setUid(reviewDto.getUid());
        nowReview.setUserName(reviewDto.getUserName());
        nowReview.setStoreName(reviewDto.getStoreName());
        nowReview.setContent(reviewDto.getContent());
        nowReview.setScore(reviewDto.getScore());
        nowReview.setModifyDate(LocalDateTime.now());

        reviewRepository.save(nowReview);
    }

    /**
     * 사용자가 리뷰 삭제를 위한 기능 + 삭제한 해당 매장 리뷰 재작성 가능(실수로 삭제 했을 수도 있기 떄문)
     * @param reservationId
     * @param reviewId
     */
    @Transactional
    public void userDeleteReview(Long reservationId, Long reviewId) {
        reviewRepository.deleteById(reviewId);
        reservationRepository.updateReviewCheckFalse(reservationId);
    }

    /**
     * 관리자에게 모든 사용자의 리뷰를 보여주는 기능
     * @return
     */
    public List<Review> findAllReview() {
        return reviewRepository.findAll();
    }

    /**
     * 관리자가 리뷰 삭제를 위한 기능, 관리자가 삭제한 리뷰는 사용자가 재작성 할 수 없음
     * @param reviewId
     */
    @Transactional
    public void adminDeleteReview(Long reviewId) {
        reviewRepository.deleteById(reviewId);
    }
}
