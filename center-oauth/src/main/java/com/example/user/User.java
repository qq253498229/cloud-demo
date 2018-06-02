package com.example.user;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Package com.example.user
 * Module
 * Project cloud-demo
 * Email 253498229@qq.com
 * Created on 2018/5/9 下午11:27
 *
 * @author wangbin
 */
@Entity
@Table(name = "t_user")
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(value = {"users"})
public class User implements UserDetails, Serializable {
  @Id
  @GenericGenerator(name = "uuid", strategy = "uuid")
  @GeneratedValue(generator = "uuid")
  @Column(length = 32)
  private String id;

  @Column(unique = true, nullable = false)
  private String username;

  @Column(nullable = false)
  private String password;

  @Column
  private Boolean enabled = true;

  @ManyToMany
  @JoinTable(
          name = "t_user_role",
          joinColumns = @JoinColumn(name = "user_id"),
          inverseJoinColumns = @JoinColumn(name = "role_id")
  )
  private List<Role> roles = new ArrayList<>();

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    Collection<GrantedAuthority> authorities = new ArrayList<>();
    this.roles.forEach(role -> authorities.add(role::getName));
    return authorities;
  }

  public User(String username) {
    this.username = username;
  }


  public User(String username, String password, Boolean enabled, List<Role> roles) {
    this.username = username;
    this.password = password;
    this.enabled = enabled;
    this.roles = roles;
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
    return this.enabled;
  }

}
