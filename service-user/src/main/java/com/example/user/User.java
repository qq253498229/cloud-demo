package com.example.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author wangbin
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {
  private String id;
  private String username;
  private String password;
  private Boolean enabled;
}
