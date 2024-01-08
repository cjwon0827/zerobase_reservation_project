package zerobase.reservation.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import zerobase.reservation.dto.ReservationDto;
import zerobase.reservation.entity.Reservation;
import zerobase.reservation.repository.ReservationRepository;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * 예약정보를 관리하기 위한 Service
 */
@Service
@RequiredArgsConstructor
public class ReservationService {
    private final ReservationRepository reservationRepository;

    /**
     * 일반 사용자가 작성 한 예약 정보를 예약정보(reservation_info) 테이블에 저장하는 기능
     * 단, 동일 매장 다중 예약은 안되며 매장 이용 후 다시 예약 가능함(노쇼 방지)
     * @param reservationDto
     */
    @Transactional
    public void saveReservation(ReservationDto reservationDto) {
        List<Reservation> list = findReservation(reservationDto.getUid());
        if(!list.isEmpty()){
            for(Reservation reservations : list){
                if(!reservations.isReservationCheck() && reservations.getStoreName().equals(reservationDto.getStoreName())){
                    throw new RuntimeException("이미 동일 매장에 예약 정보가 있습니다. 이용 후 다시 예약 부탁드립니다.");
                }
            }
            Reservation reservation = getReservation(reservationDto);
            reservationRepository.save(reservation);
        } else {
            Reservation reservation = getReservation(reservationDto);
            reservationRepository.save(reservation);
        }

    }


    /**
     * 예약 시간과 현재 시각의 차이를 분 단위로 계산하여 차이 값을 반환(반환 값이 10 이상이면 매장 이용 불가)
     * @param storeName
     * @param uid
     * @return
     */
    public int getMinute(String storeName, String uid){
        return reservationRepository.findMinute(storeName, uid);
    }

    /**
     * 사용자가 예약한 매장을 이용하면 이용했다고 Update(이 정보가 Update 돼야만 리뷰 작성 가능)
     * @param storeName
     */
    public void reservationCheck(String storeName){
        reservationRepository.reservationCheck(storeName);
    }

    /**
     * 사용자에게 작성 가능한 리뷰를 보여주는 기능
     * @param uid
     * @return
     */
    public List<Reservation> writableReview(String uid){
        return reservationRepository.writableReview(uid);
    }

    /**
     * 사용자에게 예약 내역을 보여주는 기능
     * @param uid
     * @return
     */
    public List<Reservation> findReservation(String uid){
        return reservationRepository.findAllByUid(uid);
    }


    /**
     * 사용자에게 예약 삭제를 하기 위한 기능
     * 단, 이용하였으면 삭제 불가
     * @param id
     */
    @Transactional
    public void deleteReservation(Long id) {
        reservationRepository.deleteReservation(id);
    }


    /**
     * 사용자에게 이용한 매장 내역을 보여주는 기능
     * @param uid
     * @return
     */
    public List<Reservation> useHistory(String uid){
        return reservationRepository.findAllByUidAndCheck(uid);
    }


    /**
     * 관리자에게 모든 사용자의 예약 내역을 보여주는 기능
     * @return
     */
    public List<Reservation> findAllReservation(){
        return reservationRepository.findAll();
    }


    private static Reservation getReservation(ReservationDto reservationDto) {
        Reservation reservation = new Reservation();
        LocalDateTime reservationDate = LocalDateTime.parse(reservationDto.getDate(), DateTimeFormatter.ISO_DATE_TIME);

        reservation.setUid(reservationDto.getUid());
        reservation.setReservationDate(reservationDate);
        reservation.setTel(reservationDto.getTel());
        reservation.setUserName(reservationDto.getUserName());
        reservation.setStoreName(reservationDto.getStoreName());
        reservation.setReservationCheck(false);
        reservation.setReviewCheck(false);

        return reservation;
    }
}
