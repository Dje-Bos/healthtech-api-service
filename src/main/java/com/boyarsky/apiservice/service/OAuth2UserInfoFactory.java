package com.boyarsky.apiservice.service;

import com.boyarsky.apiservice.dto.GoogleOAuth2UserInfoDTO;
import com.boyarsky.apiservice.dto.OAuth2UserInfoDTO;
import com.boyarsky.apiservice.entity.AuthType;
import com.boyarsky.apiservice.exception.OAuth2AuthenticationProcessingException;

import java.util.Map;

public class OAuth2UserInfoFactory {

    public static OAuth2UserInfoDTO getOAuth2UserInfo(String registrationId, Map<String, Object> attributes) {
        if(registrationId.equalsIgnoreCase(AuthType.GOOGLE.toString())) {
            return new GoogleOAuth2UserInfoDTO(attributes);
        } else {
            throw new OAuth2AuthenticationProcessingException("Sorry! Login with " + registrationId + " is not supported yet.");
        }
    }
}
