package dev.amiami.Model;

import dev.amiami.Roles.Role;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.*;

@Getter
@Setter
@EqualsAndHashCode
@Entity
public class AmiAmiUser implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true)
    private String username;
    @Column(unique = true)
    private String email;
    @Column
    private String password;
    @Column
    @Enumerated(EnumType.STRING)
    @ElementCollection(fetch = FetchType.EAGER)
    private Set<Role> roles = new HashSet<>();

    @OneToMany(mappedBy = "user"
            , cascade = CascadeType.ALL
            , fetch = FetchType.LAZY)
    private List<AmiAmiImage> images = new ArrayList<>();

    @OneToMany(mappedBy = "user"
            , cascade = CascadeType.ALL
            , fetch = FetchType.LAZY)
    private List<AmiAmiVideo> videos = new ArrayList<>();


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Set<SimpleGrantedAuthority> authorities = new HashSet<>();
        for(Role role : Role.values()) {
            authorities.add(new SimpleGrantedAuthority(role.name()));
        }
        return authorities;
    }

    public void addRole(Role role){
        roles.add(role);
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

    public String toString() {
        return "AmiAmiUser(id=" + this.getId()
                + ", username=" + this.getUsername()
                + ", email=" + this.getEmail()
                + ", password=" + this.getPassword()
                + ", images=" + this.getImages()
                + ", videos=" + this.getVideos() + ")";
    }
}
