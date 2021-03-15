package com.laboration.jwt;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.laboration.models.User;
import com.laboration.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


@Service
public class JwtInMemoryUserDetailsService implements UserDetailsService {

  @Autowired
  private UserRepository userRepo;

  /**
   * Locates the user based on the username.
   * If we find the user from the DB query, return with type "JwtUserDetails"
   * @param username
   * @return
   * @throws UsernameNotFoundException
   */
  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

    User user = null;

    try{
      user = userRepo.findByUsername(username);
      System.out.println(user.getUsername());
    }catch(NullPointerException e){
      throw new UsernameNotFoundException(String.format("USER_NOT_FOUND '%s'.", username));
    }

    return new JwtUserDetails(user.getId(), user.getUsername(), user.getPassword(), "ROLE_USER_2");

  }

}


