package com.example.gadgetsore.config;

import com.example.gadgetsore.entity.User;
import com.example.gadgetsore.repository.UserRepository;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.LocalDateTime;

@Component
public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    private final UserRepository userRepository;

    public CustomAuthenticationSuccessHandler(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void onAuthenticationSuccess(
            HttpServletRequest request,
            HttpServletResponse response,
            Authentication authentication) throws IOException, ServletException {

        OAuth2AuthenticationToken oauth2AuthenticationToken = (OAuth2AuthenticationToken) authentication;
        OAuth2User oauth2User = oauth2AuthenticationToken.getPrincipal();

        addAuthenticationUser(oauth2User);

        response.sendRedirect("/");
    }

    private void addAuthenticationUser(OAuth2User oAuth2User){
        String id = oAuth2User.getAttribute("sub");

        User user = userRepository.findById(id).orElseGet(() -> {
            User newUser = new User();

            newUser.setId(id);
            newUser.setName(oAuth2User.getAttribute("name"));
            newUser.setEmail(oAuth2User.getAttribute("email"));
            newUser.setGender(oAuth2User.getAttribute("gender"));
            newUser.setLocale(oAuth2User.getAttribute("locale"));
            newUser.setUserpic(oAuth2User.getAttribute("picture"));

            return newUser;
        });

        user.setLastVisit(LocalDateTime.now());

        userRepository.save(user);
    }
}

