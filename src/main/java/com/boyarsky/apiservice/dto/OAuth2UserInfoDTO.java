package com.boyarsky.apiservice.dto;

import java.util.Map;

public abstract class OAuth2UserInfoDTO {
    protected Map<String, Object> attributes;

    public OAuth2UserInfoDTO(Map<String, Object> attributes) {
        this.attributes = attributes;
    }

    public Map<String, Object> getAttributes() {
        return attributes;
    }

    public abstract String getId();

    public abstract String getName();

    public abstract String getEmail();

    public abstract String getImageUrl();
}
