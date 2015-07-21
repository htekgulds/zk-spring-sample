package tr.gov.tuik.zk.security;

import org.springframework.data.domain.AuditorAware;
import org.springframework.stereotype.Component;

/**
 * Created by Hasan TEKGÜL
 * on 7/13/2015
 */
@Component
public class SpringSecurityAuditorAware implements AuditorAware<String> {
    public String getCurrentAuditor() {
        String username = SecurityUtils.getCurrentLogin();
        if (username == null || username.isEmpty()) {
            username = "system";
        }
        return username;
    }
}
