package tr.gov.tuik.zk.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import tr.gov.tuik.zk.domain.Authority;
import tr.gov.tuik.zk.domain.Member;
import tr.gov.tuik.zk.repository.AuthorityRepository;
import tr.gov.tuik.zk.repository.MemberRepository;
import tr.gov.tuik.zk.service.MemberService;

import javax.inject.Inject;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Created by Hasan TEKGÜL
 * on 7/13/2015
 */
@Component("userDetailsService")
public class CustomUserDetailsService implements UserDetailsService {
    private static final Logger log = LoggerFactory.getLogger(CustomUserDetailsService.class);

    private MemberRepository memberRepository;

    @Inject
    public CustomUserDetailsService(MemberRepository memberRepository, MemberService memberService, AuthorityRepository authorityRepository) {
        this.memberRepository = memberRepository;

        createRole(authorityRepository);

        if (!memberRepository.findOneByUsername("admin").isPresent()) {
            Member admin = memberService.createMember("admin", "123456", "Admin", "ADMIN", "admin@local.com");
            updateMember(admin);
        }
    }

    @Transactional
    private void updateMember(Member admin) {
        admin.setActivated(true);
        memberRepository.save(admin);
    }

    @Transactional
    public void createRole(AuthorityRepository authorityRepository) {
        if (authorityRepository.findOne(AuthoritiesConstants.USER) == null) {
            Authority role = new Authority();
            role.setName(AuthoritiesConstants.USER);
            authorityRepository.save(role);
        }
    }

    @Transactional
    public UserDetails loadUserByUsername(String username) {
        log.debug("Kullanıcı doğrulanıyor: {}", username);
        String lowercaseUsername = username.toLowerCase(Locale.forLanguageTag("tr-TR"));
        Optional<Member> memberFromDatabase = memberRepository.findOneByUsername(lowercaseUsername);
        return memberFromDatabase.map(member -> {
            if (!member.isActivated()) {
                throw new MemberNotActivatedException("Kullanıcı " + lowercaseUsername + " aktif edilmemiş.");
            }
            List<GrantedAuthority> grantedAuthorities = member.getAuthorities().stream()
                    .map(authority -> new SimpleGrantedAuthority(authority.getName()))
                    .collect(Collectors.toList());

            return new User(lowercaseUsername, member.getPassword(), grantedAuthorities);
        }).orElseThrow(() -> new UsernameNotFoundException("Kullanıcı " + lowercaseUsername + " veritabanında bulunamadı."));
    }
}
