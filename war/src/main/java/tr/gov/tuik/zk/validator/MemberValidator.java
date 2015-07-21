package tr.gov.tuik.zk.validator;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import tr.gov.tuik.zk.repository.MemberRepository;

import javax.inject.Inject;

/**
 * Created by Hasan TEKGÜL
 * on 7/14/2015
 */
@Component
public class MemberValidator {

    @Inject
    private MemberRepository memberRepository;

    public boolean validateLogin(String username, String password) {
        return validateNotNullOrEmpty(username, password);
    }

    public boolean validateRegister(String username, String password, String passwordMatch, String email) {
        return validateNotNullOrEmpty(username, password, passwordMatch, email) &&
                validateUsernameUnique(username) &&
                validateEmailUnique(email) &&
                validatePasswordsMatch(password, passwordMatch);
    }

    public boolean validateNotNullOrEmpty(String username, String password, String passwordMatch, String email) {
        // Hiçbiri null veya boş olamaz
        return !(username == null || password == null || email == null || passwordMatch == null) &&
                !(username.equals("") || password.equals("") || email.equals("") || passwordMatch.equals(""));
    }

    public boolean validateNotNullOrEmpty(String username, String password) {
        // Hiçbiri null veya boş olamaz
        return !(username == null || password == null) &&
                !(username.equals("") || password.equals(""));
    }

    @Transactional
    public boolean validateUsernameUnique(String username) {
        return !memberRepository.findOneByUsername(username).isPresent();
    }

    @Transactional
    public boolean validateEmailUnique(String email) {
        return !memberRepository.findOneByEmail(email).isPresent();
    }

    public boolean validatePasswordsMatch(String password, String passwordMatch) {
        return password.equals(passwordMatch);
    }
}
