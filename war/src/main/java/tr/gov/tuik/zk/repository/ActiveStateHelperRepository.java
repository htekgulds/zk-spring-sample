package tr.gov.tuik.zk.repository;

import java.util.List;
import java.util.Optional;

/**
 * Created by Hasan TEKGÜL
 * on 7/14/2015
 */
public interface ActiveStateHelperRepository<T> {

    Optional<T> findOneByFieldActive(boolean state);

    List<T> findAllByFieldActive(boolean state);
}
