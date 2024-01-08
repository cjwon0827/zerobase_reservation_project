package zerobase.reservation.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import zerobase.reservation.auth.PrincipalDetails;
import zerobase.reservation.entity.Store;
import zerobase.reservation.service.ReviewService;
import zerobase.reservation.service.StoreService;

import java.util.List;

/**
 * 메인페이지(홈페이지)를 위한 Controller
 */
@Controller
@RequiredArgsConstructor
public class HomeController {
    private final StoreService storeService;
    private final ReviewService reviewService;

    /**
     * 사이트 메인페이지(홈페이지) 조회
     * @param model
     * @param principalDetails
     * @return
     */
    @GetMapping("/")
    public String home(Model model, @AuthenticationPrincipal PrincipalDetails principalDetails){
        List<Store> stores = storeService.findAllStore();
        model.addAttribute("stores", stores);

        if(principalDetails != null) {
            String uid = principalDetails.getUsername();
            model.addAttribute("uid", uid);
        }

        return "home";
    }


    /**
     * 로그인을 위한 페이지 이동(/loginForm.html)
     * @return
     */
    @GetMapping("/loginForm")
    public String loginForm(){
        return "loginForm";
    }


    /**
     * 일반 사용자들을 위한 회원가입 페이지 이동(/join/userJoin.html)
     * @return
     */
    @GetMapping("/userJoin")
    public String userJoin(){
        return "/join/userJoin";
    }


    /**
     * 관리자를 위한 회원가입 페이지 이동(/join/adminJoin.html)
     * @return
     */
    @GetMapping("/adminJoin")
    public String adminJoin(){
        return "/join/adminJoin";
    }

    /**
     * 매장 상세 정보를 보기 위한 페이지 이동(/store/storeDetail.html)
     * @param storeName
     * @param model
     * @return
     */
    @GetMapping("/store")
    public String storeDetail(@RequestParam("name") String storeName, Model model){
        Store store = storeService.findByName(storeName);
        model.addAttribute("store", store);
        return "/store/storeDetail";
    }


    /**
     * 매장 검색
     * @param storeName
     * @param model
     * @param principalDetails
     * @return
     */
    @GetMapping("/search")
    public String searchStore(@RequestParam("name") String storeName, Model model, @AuthenticationPrincipal PrincipalDetails principalDetails){
        List<Store> stores = null;

        if(principalDetails != null) {
            String uid = principalDetails.getUsername();
            model.addAttribute("uid", uid);
        }

        if(storeName == null){
            stores = storeService.findAllStore();
        } else {
            stores = storeService.storeSearch(storeName);
        }
        model.addAttribute("stores", stores);
        return "home";
    }
}
