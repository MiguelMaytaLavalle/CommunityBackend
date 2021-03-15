package com.laboration.jwt.resource;

import com.laboration.jwt.JwtTokenUtil;
import com.laboration.jwt.JwtUserDetails;
import com.laboration.models.User;
import com.laboration.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Objects;

@RestController
@CrossOrigin
public class JwtAuthenticationRestController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private Authentication authentication;

    @Value("${jwt.http.request.header}")
    private String tokenHeader;

    @Autowired
    private UserRepository userRepo;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private UserDetailsService jwtInMemoryUserDetailsService;


    /**
     * When the user gets authenticated a jwt token is generated for the user.
     * Send an request with username and password. If this user is authenticated
     * it will load the userdetails and respond by generating a Jwt token.
     * @param authenticationRequest
     * @return
     * @throws AuthenticationException
     */
    //@PostMapping(path="/authenticate")
    @RequestMapping(value = "${jwt.get.token.uri}", method = RequestMethod.POST)
    public ResponseEntity<?> createAuthenticationToken(@RequestBody JwtTokenRequest authenticationRequest)
            throws AuthenticationException {

        logger.warn("Authenticate");

        authenticate(authenticationRequest.getUsername(), authenticationRequest.getPassword());

        final UserDetails userDetails = jwtInMemoryUserDetailsService.loadUserByUsername(authenticationRequest.getUsername());

        //Ignore this
        //final JwtUserDetails userDetails = (JwtUserDetails) authentication.getPrincipal();

        final String token = jwtTokenUtil.generateToken(userDetails);

        return ResponseEntity.ok(new JwtTokenResponse(token));
    }

    /*@PostMapping(path="/signup")
    public ResponseEntity<?> registerUser(@Validated @RequestBody JwtTokenRequest signupRequest){
        User user = new User(signupRequest.getUsername(), signupRequest.getPassword());
        userRepo.save(user);
        return ResponseEntity.ok("User registered successfully!");
    }*/


    /**
     * To refresh the generated JWT token for the user with a new token and expiration date
     * It will first check if token is valid. If so it will get the userdetails and check
     * the expiration date on the token.
     * @param request
     * @return
     */
    //@GetMapping(path="/refresh")
    @RequestMapping(value = "${jwt.refresh.token.uri}", method = RequestMethod.GET)
    public ResponseEntity<?> refreshAndGetAuthenticationToken(HttpServletRequest request) {
        logger.warn("Refresh token");
        String authToken = request.getHeader(tokenHeader);
        final String token = authToken.substring(7);
        String username = jwtTokenUtil.getUsernameFromToken(token);
        JwtUserDetails user = (JwtUserDetails) jwtInMemoryUserDetailsService.loadUserByUsername(username);

        if (jwtTokenUtil.canTokenBeRefreshed(token)) {
            String refreshedToken = jwtTokenUtil.refreshToken(token);
            return ResponseEntity.ok(new JwtTokenResponse(refreshedToken));
        } else {
            return ResponseEntity.badRequest().body(null);
        }

    }

    /**
     * Whenever there is an exception, we have an specific exception "AuthenticationException" for authentication
     * which will be thrown.
     * @param e
     * @return
     */
    @ExceptionHandler({AuthenticationException.class})
    public ResponseEntity<String> handleAuthenticationException(AuthenticationException e) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
    }

    private void authenticate(String username, String password) {

        Objects.requireNonNull(username);
        Objects.requireNonNull(password);

        logger.warn(Objects.requireNonNull(username));
        logger.warn(Objects.requireNonNull(password));
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        } catch (DisabledException e) {
            throw new AuthenticationException("USER_DISABLED", e);
        } catch (BadCredentialsException e) {
            throw new AuthenticationException("INVALID_CREDENTIALS", e);
        }
    }
}
