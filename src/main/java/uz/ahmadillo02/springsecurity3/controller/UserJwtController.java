package uz.ahmadillo02.springsecurity3.controller;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uz.ahmadillo02.springsecurity3.security.TokenProvider;
import uz.ahmadillo02.springsecurity3.vm.LoginVM;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class UserJwtController {


    private final TokenProvider tokenProvider;

    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    @PostMapping("/authenticate")
    public ResponseEntity<JwtToken> authorize(@RequestBody LoginVM loginVM) {

        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                loginVM.getUsername(),
                loginVM.getPassword()
        );

        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = tokenProvider.createToken(authentication, loginVM.isRememberMe());
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Authorization", "Bearer " + jwt);
        return new ResponseEntity<>(new JwtToken(jwt), httpHeaders, HttpStatus.OK);

    }

    static  class JwtToken{

        private String token;

        public JwtToken(String token) {
            this.token = token;
        }

        @JsonProperty("jwt_token")
        public String getToken() {
            return token;
        }


    }
}
