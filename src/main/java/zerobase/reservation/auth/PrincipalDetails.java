package zerobase.reservation.auth;

import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import zerobase.reservation.domain.MemberRole;
import zerobase.reservation.entity.Member;

import java.util.Collection;
import java.util.Collections;

public class PrincipalDetails implements UserDetails {
    private String uid;
    @Getter
    private String name;
    @Getter
    private String tel;
    private String password;
    private MemberRole role;

    public PrincipalDetails(Member member){
        this.uid = member.getUid();
        this.name = member.getName();
        this.tel = member.getTel();
        this.password = member.getPassword();
        this.role = member.getRole();
    }
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singletonList(new SimpleGrantedAuthority(this.role.toString()));
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.uid;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
