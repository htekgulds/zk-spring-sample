package tr.gov.tuik.zk.service;

import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tr.gov.tuik.zk.domain.Authority;
import tr.gov.tuik.zk.domain.Member;
import tr.gov.tuik.zk.repository.AuthorityRepository;
import tr.gov.tuik.zk.repository.MemberRepository;
import tr.gov.tuik.zk.security.SecurityUtils;

import javax.inject.Inject;
import java.util.*;

/**
 * Created by Hasan TEKGÜL
 * on 7/10/2015
 */

@Service
@Transactional
public class MemberService {
    private static final Logger log = LoggerFactory.getLogger(MemberService.class);

    @Inject
    private PasswordEncoder passwordEncoder;

    @Inject
    private MemberRepository memberRepository;

    @Inject
    private AuthorityRepository authorityRepository;

    // REGISTER
    public Member createMember(String username, String password, String firstName, String lastName, String email) {
        Member newMember = new Member();
        Authority authority = authorityRepository.findOne("ROLE_USER");
        Set<Authority> authorities = new HashSet<Authority>();
        String encryptedPassword = passwordEncoder.encode(password);

        newMember.setUsername(username);
        newMember.setPassword(encryptedPassword);
        newMember.setFirstName(firstName);
        newMember.setLastName(lastName);
        newMember.setEmail(email);
        authorities.add(authority);
        newMember.setAuthorities(authorities);
        memberRepository.save(newMember);
        log.debug("Kullanıcı bilgisi oluşturuldu:  {}", newMember);
        return newMember;
    }

    public void updateMember(String firstName, String lastName, String email) {
        memberRepository.findOneByUsername(SecurityUtils.getCurrentLogin()).ifPresent(member -> {
            member.setFirstName(firstName);
            member.setLastName(lastName);
            member.setEmail(email);
            memberRepository.save(member);
            log.debug("Kullanıcı bilgisi güncellendi: {}", member);
        });
    }

    public void changePassword(String password) {
        memberRepository.findOneByUsername(SecurityUtils.getCurrentLogin()).ifPresent(member -> {
            String encryptedPassword = passwordEncoder.encode(password);
            member.setPassword(encryptedPassword);
            memberRepository.save(member);
            log.debug("Kullanıcının şifresi değiştirildi: {}", member);
        });
    }

    @Transactional(readOnly = true)
    public Member getMemberWithAuthorities() {
        Member currentLogin = memberRepository.findOneByUsername(SecurityUtils.getCurrentLogin()).get();
        currentLogin.getAuthorities().size(); // Bağlantıyı Eager load etmek için.
        return currentLogin;
    }

    @Scheduled(cron = "0 0/1 * 1/1 * ? *")
    public void scheduledTestMethod() {
        log.debug("Zaman ayarlı metod 'SecurityUtils::scheduledTestMethod() çalıştırıldı: {}", DateTime.now());
    }
}
