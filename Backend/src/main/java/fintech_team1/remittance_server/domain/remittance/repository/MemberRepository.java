package fintech_team1.remittance_server.domain.remittance.repository;

import fintech_team1.remittance_server.domain.remittance.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {
    /*
     * 회원가입 시 유효성 체크
     */
    boolean existsByUserid(String userid);
    boolean existsByEmail(String email);
    Optional<Member> findByEmail(String userid);
    Optional<Member> getMemberByUserid(String userid);
}
