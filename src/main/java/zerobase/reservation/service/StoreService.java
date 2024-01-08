package zerobase.reservation.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import zerobase.reservation.dto.StoreDto;
import zerobase.reservation.entity.Store;
import zerobase.reservation.repository.StoreRepository;

import java.util.List;

/**
 * 매장 관리를 위한 Service
 */
@Service
@RequiredArgsConstructor
public class StoreService {
    private final StoreRepository storeRepository;

    /**
     * 관리자가 작성 한 매장 정보를 store 테이블에 저장
     * @param storeDto
     */
    @Transactional
    public void registStore(StoreDto storeDto){
        Store store = new Store();
        store.setName(storeDto.getName());
        store.setTel(storeDto.getTel());
        store.setAddress(storeDto.getAddress());
        store.setShopDetail(storeDto.getShopDetail());
        storeRepository.save(store);
    }

    /**
     * 사용자에게 모든 매장 정보를 알려주는 기능
     * @return
     */
    public List<Store> findAllStore() {
        return storeRepository.findAll();
    }

    /**
     * 매장 이름을 비교하여 일치하는 Store 객체 반환
     * @param name
     * @return
     */
    public Store findByName(String name){
        return storeRepository.findByName(name);
    }

    /**
     * 매장 검색을 위한 기능
     * @param name
     * @return
     */
    public List<Store> storeSearch(String name){
        return storeRepository.findByNameContaining(name);
    }


    /**
     * 매장 삭제를 위한 기능
     * @param id
     */
    @Transactional
    public void deleteStore(Long id) {
        storeRepository.deleteById(id);
    }

    /**
     * 매장 ID 값을 비교하여 일치하는 Store 객체 반환
     * @param id
     * @return
     */
    public Store findById(Long id){
        return storeRepository.getById(id);
    }


    /**
     * 관리자가 수정 한 매장 정보를 store 테이블에 저장
     * @param storeDto
     */
    @Transactional
    public void updateStore(StoreDto storeDto) {
        Store nowStore = storeRepository.findByName(storeDto.getName());
        nowStore.setName(storeDto.getName());
        nowStore.setAddress(storeDto.getAddress());
        nowStore.setTel(storeDto.getTel());
        nowStore.setShopDetail(storeDto.getShopDetail());
        storeRepository.save(nowStore);
    }
}
