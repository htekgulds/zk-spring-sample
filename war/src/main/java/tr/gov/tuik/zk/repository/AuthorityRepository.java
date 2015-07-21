package tr.gov.tuik.zk.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tr.gov.tuik.zk.domain.Authority;

/**
 * Created by Hasan TEKGÜL
 * on 7/14/2015
 */
public interface AuthorityRepository extends JpaRepository<Authority, String> {}
