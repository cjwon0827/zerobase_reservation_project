package zerobase.reservation.controller;

import lombok.RequiredArgsConstructor;
import org.dom4j.rule.Mode;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import zerobase.reservation.auth.PrincipalDetails;
import zerobase.reservation.dto.EntranceDto;
import zerobase.reservation.dto.ReservationDto;
import zerobase.reservation.entity.Reservation;
import zerobase.reservation.entity.Store;
import zerobase.reservation.service.ReservationService;
import zerobase.reservation.service.StoreService;

import java.util.List;
import java.util.Objects;

/**
 * 에약을 위한 Controller
 */
@Controller
@RequiredArgsConstructor
public class ReservationController {
    private final StoreService storeService;
    private final ReservationService reservationService;


    /**
     * 매장 예약을 하기 위한 페이지 이동(/reservation/reservationForm.html)
     * @param model
     * @param name
     * @param principalDetails
     * @return
     */
    @GetMapping("/reservation")
    public String reservation(Model model, @RequestParam("name") String name, @AuthenticationPrincipal PrincipalDetails principalDetails){
        Store store = storeService.findByName(name);
        model.addAttribute("store", store);

        String uid = principalDetails.getUsername();
        String userName = principalDetails.getName();
        String tel = principalDetails.getTel();

        model.addAttribute("uid", uid);
        model.addAttribute("userName", userName);
        model.addAttribute("tel", tel);

        return "/reservation/reservationForm";
    }

    /**
     * 예약 정보 확인을 위한 페이지 이동(/reservation/reservationCheck.html)
     * @param model
     * @param principalDetails
     * @return
     */
    @GetMapping("/reservationCheck")
    public String reservationCheck(Model model, @AuthenticationPrincipal PrincipalDetails principalDetails){
        String uid = principalDetails.getUsername();
        List<Reservation> reservations = reservationService.findReservation(uid);
        model.addAttribute("reservations", reservations);

        return "/reservation/reservationCheck";
    }


    /**
     * 예약에 필요한 정보 작성 후 POST 방식으로 전송, 단 같은 매장 예약은 해당 매장 이용 후 가능
     * 예약에 성공하면 루트 페이지로 리다이렉트
     * @param model
     * @param reservationDto
     * @param principalDetails
     * @return
     */
    @PostMapping("/reservation/new")
    public String reservationSuccess(Model model, ReservationDto reservationDto, @AuthenticationPrincipal PrincipalDetails principalDetails){
        try {
            reservationService.saveReservation(reservationDto);
        } catch (RuntimeException e){
            Store store = storeService.findByName(reservationDto.getStoreName());
            String uid = principalDetails.getUsername();
            String userName = principalDetails.getName();
            String tel = principalDetails.getTel();

            model.addAttribute("store", store);
            model.addAttribute("uid", uid);
            model.addAttribute("userName", userName);
            model.addAttribute("tel", tel);
            model.addAttribute("failMsg", "이미 동일 매장에 예약 정보가 있습니다. 이용 후 다시 예약 부탁드립니다.");
            return "/reservation/reservationForm";
        }
        return "redirect:/";
    }


    /**
     * 예약 삭제 기능 단, 해당 매장을 이용하면 예약 삭제 불가능
     * @param model
     * @param id
     * @param check
     * @param principalDetails
     * @return
     */
    @GetMapping("/reservation/delete")
    public String deleteStore(Model model, Long id, boolean check, @AuthenticationPrincipal PrincipalDetails principalDetails){
        String uid = principalDetails.getUsername();

        if(!check) {
            reservationService.deleteReservation(id);
            List<Reservation> reservations = reservationService.findReservation(uid);
            model.addAttribute("reservations", reservations);

            return "/reservation/reservationCheck";
        } else {
            List<Reservation> reservations = reservationService.findReservation(uid);
            model.addAttribute("reservations", reservations);
            model.addAttribute("failMsg", "이미 이용하신 예약내역은 삭제할 수 없습니다.");

            return "/reservation/reservationCheck";
        }
    }


    /**
     * 매장에 입장하기 위한 페이지 이동(/entrance/entranceForm.html)
     * @param model
     * @param name
     * @return
     */
    @GetMapping("/entrance")
    public String entranceShop(Model model, @RequestParam("name") String name){
        Store store = storeService.findByName(name);
        model.addAttribute("store", store);

        return "/entrance/entranceForm";
    }


    /**
     * 매장 입장 페이지에 예약 정보들을 작성 후 예약 정보가 일치하면 입장 성공, 일치하지 않으면 입장 실패
     * 입장에 성공하면 성공 페이지(/entrance/entranceSuccess.html)로 이동
     * @param model
     * @param entranceDto
     * @param principalDetails
     * @return
     */
    @PostMapping("/entrance/new")
    public String entrance(Model model, EntranceDto entranceDto, @AuthenticationPrincipal PrincipalDetails principalDetails) {
        int minute = Math.abs(reservationService.getMinute(entranceDto.getStoreName(), principalDetails.getUsername()));
        Store store = storeService.findByName(entranceDto.getStoreName());

        if (!Objects.equals(entranceDto.getName(), principalDetails.getName()) || !Objects.equals(entranceDto.getTel(), principalDetails.getTel())) {
            model.addAttribute("store", store);
            model.addAttribute("errorAuthMsg", "본인 인증에 실패하였습니다. 다시 시도해 주세요.");
            return "/entrance/entranceForm";
        } else if (entranceDto.getName().equals(principalDetails.getName()) && entranceDto.getTel().equals(principalDetails.getTel()) && minute > 10) {
            model.addAttribute("store", store);
            model.addAttribute("errorTimeMsg", "입장 시간은 예약시간 10분 전부터 10분 후까지 가능합니다.");
            return "/entrance/entranceForm";
        } else {
            reservationService.reservationCheck(entranceDto.getStoreName());
            return "/entrance/entranceSuccess";
        }
    }
}
