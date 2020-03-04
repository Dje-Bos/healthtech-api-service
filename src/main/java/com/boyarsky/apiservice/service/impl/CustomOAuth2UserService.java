package com.boyarsky.apiservice.service.impl;

import com.boyarsky.apiservice.dto.OAuth2UserInfoDto;
import com.boyarsky.apiservice.entity.AuthType;
import com.boyarsky.apiservice.entity.User;
import com.boyarsky.apiservice.exception.OAuth2AuthenticationProcessingException;
import com.boyarsky.apiservice.repository.UserRepository;
import com.boyarsky.apiservice.security.UserPrincipal;
import com.boyarsky.apiservice.service.UserRolesService;
import com.boyarsky.apiservice.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Set;

@Service
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

    private UserRepository userRepository;
    private UserRolesService userRolesService;

    @Autowired
    public CustomOAuth2UserService(UserRepository userRepository, UserRolesService userRolesService) {
        this.userRepository = userRepository;
        this.userRolesService = userRolesService;
    }

    @Override
    @Transactional
    public OAuth2User loadUser(OAuth2UserRequest oAuth2UserRequest) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = super.loadUser(oAuth2UserRequest);

        try {
            return processOAuth2User(oAuth2UserRequest, oAuth2User);
        } catch (AuthenticationException ex) {
            throw ex;
        } catch (Exception ex) {
            // Throwing an instance of AuthenticationException will trigger the OAuth2AuthenticationFailureHandler
            throw new InternalAuthenticationServiceException(ex.getMessage(), ex.getCause());
        }
    }

    private OAuth2User processOAuth2User(OAuth2UserRequest oAuth2UserRequest, OAuth2User oAuth2User) {
        String upperCaseRegistration = oAuth2UserRequest.getClientRegistration().getRegistrationId().toUpperCase();
        OAuth2UserInfoDto oAuth2UserInfoDTO = OAuth2UserInfoFactory.getOAuth2UserInfo(upperCaseRegistration, oAuth2User.getAttributes());
        if (StringUtil.isBlank(oAuth2UserInfoDTO.getEmail())) {
            throw new OAuth2AuthenticationProcessingException("Email not found from OAuth2 provider");
        }

        User user = userRepository.getUserByEmail(oAuth2UserInfoDTO.getEmail());
        if (user != null) {
            if (!user.getAuth().equals(AuthType.valueOf(upperCaseRegistration))) {
                throw new OAuth2AuthenticationProcessingException("Looks like you're signed up with " +
                        user.getAuth() + " account. Please use your " + user.getAuth() +
                        " account to login.");
            }
            user = updateExistingUser(user, oAuth2UserInfoDTO);
        } else {
            user = registerNewUser(oAuth2UserRequest, oAuth2UserInfoDTO);
        }

        return UserPrincipal.create(user, oAuth2User.getAttributes());
    }

    private User registerNewUser(OAuth2UserRequest oAuth2UserRequest, OAuth2UserInfoDto oAuth2UserInfoDTO) {
        User user = new User();

        user.setAuth(AuthType.valueOf(oAuth2UserRequest.getClientRegistration().getRegistrationId().toUpperCase()));
        user.setName(oAuth2UserInfoDTO.getName());
        user.setEmail(oAuth2UserInfoDTO.getEmail());
        user.setPictureUrl(oAuth2UserInfoDTO.getImageUrl());

        user.setRoles(Set.of(userRolesService.createOrGetByUid("USER")));
        user.setPictureUrl(oAuth2UserInfoDTO.getImageUrl());
        return userRepository.save(user);
    }

    private User updateExistingUser(User existingUser, OAuth2UserInfoDto oAuth2UserInfoDTO) {
        existingUser.setName(oAuth2UserInfoDTO.getName());
        existingUser.setPictureUrl(oAuth2UserInfoDTO.getImageUrl());
        return userRepository.save(existingUser);
    }

}
