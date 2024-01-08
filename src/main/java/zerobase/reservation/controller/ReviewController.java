package zerobase.reservation.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import zerobase.reservation.auth.PrincipalDetails;
import zerobase.reservation.dto.ReviewDto;
import zerobase.reservation.entity.Reservation;
import zerobase.reservation.entity.Review;
import zerobase.reservation.service.ReservationService;
import zerobase.reservation.service.ReviewService;

import java.util.List;

/**
 * 리뷰를 위한 Controller
 */
@Controller
@RequiredArgsConstructor
public class ReviewController {
    private final ReservationService reservationService;
    private final ReviewService reviewService;


    /**
     * 사용자들이 매장 이용 내역을 볼 수 있는 페이지 이동
     * @param model
     * @param principalDetails
     * @return
     */
    @GetMapping("/usageHistory")
    public String usageHistory(Model model, @AuthenticationPrincipal PrincipalDetails principalDetails){
        String uid = principalDetails.getUsername();
        List<Reservation> usedHistory = reservationService.useHistory(uid);
        model.addAttribute("usedList", usedHistory);

        return "/review/usageHistory";
    }


    /**
     * 사용자가 작성 한 리뷰를 볼 수 있는 페이지 이동(/review/reviewList.html)
     * @param model
     * @param principalDetails
     * @return
     */
    @GetMapping("/review")
    public String reviewList(Model model, @AuthenticationPrincipal PrincipalDetails principalDetails){
        String uid = principalDetails.getUsername();
        List<Review> reviewList = reviewService.findReview(uid);
        model.addAttribute("reviewList", reviewList);

        return "/review/reviewList";
    }


    /**
     * 사용자가 작성 가능한 리뷰 리스트를 볼 수 있는 페이지 이동(/review/writableReview)
     * @param model
     * @param principalDetails
     * @return
     */
    @GetMapping("/writableReview")
    public String writableReview(Model model, @AuthenticationPrincipal PrincipalDetails principalDetails){
        String uid = principalDetails.getUsername();
        List<Reservation> writableList = reservationService.writableReview(uid);
        model.addAttribute("writableList", writableList);

        return "/review/writableReview";
    }


    /**
     * 리뷰 작성 페이지로 이동(/review/writeReview.html)
     * @param model
     * @param id
     * @param storeName
     * @param principalDetails
     * @return
     */
    @GetMapping("/writeReview")
    public String writeReview(Model model, @RequestParam("id") Long id, @RequestParam("name") String storeName, @AuthenticationPrincipal PrincipalDetails principalDetails){
        String uid = principalDetails.getUsername();
        String userName = principalDetails.getName();

        model.addAttribute("id", id);
        model.addAttribute("storeName", storeName);
        model.addAttribute("uid", uid);
        model.addAttribute("userName", userName);

        return "/review/writeReview";
    }


    /**
     * 리뷰 수정을 위한 페이지 이동(/review/modifyReview.html)
     * @param model
     * @param id
     * @return
     */
    @GetMapping("/updateReview")
    public String updateReview(Model model, @RequestParam("id") Long id){
        Review review = reviewService.getById(id);
        model.addAttribute("review", review);

        return "/review/modifyReview";
    }


    /**
     * 리뷰 작성을 위한 정보를 작성 후 POST 방식으로 전달
     * 리뷰 작성에 성공하면 리뷰 리스트 조회 페이지로 리다이렉트
     * @param reviewDto
     * @return
     */
    @PostMapping("/review/new")
    public String registReview(ReviewDto reviewDto){
        reviewService.saveReview(reviewDto);
        return "redirect:/review";
    }


    /**
     * 리뷰 수정을 위한 정보를 작성 후 POST 방식으로 전달
     * 리뷰 수정에 성공하면 리뷰 리스트 조회 페이지로 리다이렉트
     * @param reviewDto
     * @return
     */
    @PostMapping("/review/update")
    public String updateReview(ReviewDto reviewDto){
        reviewService.updateReview(reviewDto);
        return "redirect:/review";
    }


    /**
     * 리뷰 삭제를 위한 기능, 리뷰를 삭제할 경우 다시 리뷰 작성 가능
     * @param reviewId
     * @param reservationId
     * @return
     */
    @GetMapping("/deleteReview")
    public String deleteReview(@RequestParam("id") Long reviewId, @RequestParam("id2") Long reservationId){
        reviewService.userDeleteReview(reservationId, reviewId);
        return "redirect:/review";
    }
}
