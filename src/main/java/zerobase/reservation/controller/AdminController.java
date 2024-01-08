package zerobase.reservation.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import zerobase.reservation.auth.PrincipalDetails;
import zerobase.reservation.dto.StoreDto;
import zerobase.reservation.entity.Reservation;
import zerobase.reservation.entity.Review;
import zerobase.reservation.entity.Store;
import zerobase.reservation.service.ReservationService;
import zerobase.reservation.service.ReviewService;
import zerobase.reservation.service.StoreService;

import java.util.List;

/**
 * 관리자 페이지를 위한 Controller
 */
@Controller
@RequiredArgsConstructor
@RequestMapping("/admin")
public class AdminController {
    private final StoreService storeService;
    private final ReservationService reservationService;
    private final ReviewService reviewService;


    /**
     * 매장 등록 사이트 이동(/admin/registShop.html)
     * @return
     */
    @GetMapping("/regist")
    public String registShop(){
        return "/admin/registShop";
    }

    /**
     * 매장 등록을 위해 필요한 정보들을 POST 방식으로 전달(매장 등록 후 루트 페이지로 리다이렉트)
     * @param storeDto
     * @return
     */
    @PostMapping("/registSuccess")
    public String registSuccess(StoreDto storeDto){
        storeService.registStore(storeDto);
        return "redirect:/";
    }

    /**
     * 매장 삭제(매장 삭제후 루트 페이지로 리다이렉트)
     * @param id
     * @return
     */
    @GetMapping("/delete")
    public String deleteStore(Long id){
        storeService.deleteStore(id);
        return "redirect:/";
    }

    /**
     * 매장 수정 사이트 이동(/admin/updateShop.html)
     * @param model
     * @param id
     * @return
     */
    @GetMapping("/update")
    public String updateStore(Model model, Long id){
        Store store = storeService.findById(id);
        model.addAttribute("store", store);

        return "/admin/updateShop";
    }

    /**
     * 매장 수정을 위해 필요한 정보들을 POST 방식으로 전달(매정 수정 후 루트 페이지로 리다이렉트)
     * @param storeDto
     * @return
     */
    @PostMapping("/updateSuccess")
    public String updateSuccess(StoreDto storeDto){
        storeService.updateStore(storeDto);
        return "redirect:/";
    }


    /**
     * 일반 사용자들이 예약한 정보들을 확인할 수 있는 페이지로 이동(/admin/reservationList.html)
     * @param model
     * @return
     */
    @GetMapping("/reservationList")
    public String reservationList(Model model){
        List<Reservation> reservations = reservationService.findAllReservation();
        model.addAttribute("reservations", reservations);

        return "/admin/reservationList";
    }

    /**
     * 일반 사용자들이 매장 이용 후 작성한 리뷰를 확인할 수 있는 페이지로 이동(/admin/reviewList.html)
     * @param model
     * @return
     */
    @GetMapping("/reviewList")
    public String reviewList(Model model){
        List<Review> reviews = reviewService.findAllReview();
        model.addAttribute("reviews", reviews);

        return "/admin/reviewList";
    }

    /**
     * 일반 사용자들이 작성 한 리뷰를 관리자가 삭제하는 기능(리뷰 삭제후 /admin/reviewList.html로 리다이렉트)
     * @param reviewId
     * @return
     */
    @GetMapping("/deleteReview")
    public String deleteReview(@RequestParam("id") Long reviewId){
        reviewService.adminDeleteReview(reviewId);
        return "redirect:/admin/reviewList";
    }
}
