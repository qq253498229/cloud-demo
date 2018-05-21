package com.example.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 角色实体
 *
 * @author wangbin
 */
@Entity
@Table(name = "t_role")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Role implements Serializable {

  @Id
  @GenericGenerator(name = "uuid", strategy = "uuid")
  @GeneratedValue(generator = "uuid")
  @Column(length = 32)
  private String id;

  @Column(nullable = false, unique = true)
  private String name;

  @ManyToMany(mappedBy = "roles")
  @JsonIgnore
  private List<User> users = new ArrayList<>();

  public Role(String name) {
    this.name = name;
  }
}
