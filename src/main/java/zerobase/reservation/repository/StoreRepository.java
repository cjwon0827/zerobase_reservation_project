package zerobase.reservation.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import zerobase.reservation.entity.Store;

import java.util.List;

public interface StoreRepository extends JpaRepository<Store, Long> {
    Store findByName(String name);
    List<Store> findByNameContaining(String name);
}
