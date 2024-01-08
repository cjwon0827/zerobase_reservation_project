package zerobase.reservation.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import zerobase.reservation.entity.Member;

public interface MemberRepository extends JpaRepository<Member, Long> {
    Member findByUid(String username);
}
