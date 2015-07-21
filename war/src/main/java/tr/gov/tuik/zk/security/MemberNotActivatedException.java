package tr.gov.tuik.zk.security;

import org.springframework.security.core.AuthenticationException;

/**
 * Created by Hasan TEKGÜL
 * on 7/14/2015
 */
public class MemberNotActivatedException extends AuthenticationException {

    public MemberNotActivatedException(String message) {
        super(message);
    }

    public MemberNotActivatedException(String message, Throwable t) {
        super(message, t);
    }
}
