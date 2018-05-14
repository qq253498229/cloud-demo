package com.example.user;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;

/**
 * 角色实体
 *
 * @author wangbin
 */
@Entity
@Table(name = "t_role")
@Data
@NoArgsConstructor
public class Role implements Serializable {

  @Id
  @GenericGenerator(name = "uuid", strategy = "uuid")
  @GeneratedValue(generator = "uuid")
  @Column(length = 32)
  private String id;

  @Column(nullable = false, unique = true)
  private String name;

  public Role(String name) {
    this.name = name;
  }
}
