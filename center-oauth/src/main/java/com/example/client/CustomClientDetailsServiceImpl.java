package com.example.client;

import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.ClientRegistrationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;


/**
 * @author wangbin
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class CustomClientDetailsServiceImpl implements ClientDetailsService {

  @Resource
  private CustomClientDetailsRepository customClientDetailsRepository;

  @Override
  public ClientDetails loadClientByClientId(String clientId) throws ClientRegistrationException {
    return customClientDetailsRepository.findByClientId(clientId);
  }
}
