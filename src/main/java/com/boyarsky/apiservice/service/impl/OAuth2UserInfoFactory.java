package com.boyarsky.apiservice.service.impl;

import com.boyarsky.apiservice.dto.GoogleOAuth2UserInfoDto;
import com.boyarsky.apiservice.dto.OAuth2UserInfoDto;
import com.boyarsky.apiservice.entity.user.AuthType;
import com.boyarsky.apiservice.exception.OAuth2AuthenticationProcessingException;

import java.util.Map;

public class OAuth2UserInfoFactory {

    public static OAuth2UserInfoDto getOAuth2UserInfo(String registrationId, Map<String, Object> attributes) {
        if(registrationId.equalsIgnoreCase(AuthType.GOOGLE.toString())) {
            return new GoogleOAuth2UserInfoDto(attributes);
        } else {
            throw new OAuth2AuthenticationProcessingException("Sorry! Login with " + registrationId + " is not supported yet.");
        }
    }
}
