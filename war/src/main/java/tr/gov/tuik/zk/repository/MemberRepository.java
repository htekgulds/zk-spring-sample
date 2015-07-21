package tr.gov.tuik.zk.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tr.gov.tuik.zk.domain.Member;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

/**
 * Created by Hasan TEKGÜL
 * on 7/10/2015
 */
public interface MemberRepository extends JpaRepository<Member, Integer> {

    Optional<Member> findOneByUsername(String username);

    Optional<Member> findOneByEmail(String email);
}
