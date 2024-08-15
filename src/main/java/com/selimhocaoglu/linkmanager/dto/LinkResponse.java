package com.selimhocaoglu.linkmanager.dto;

import lombok.Data;

@Data
public class LinkResponse {
    private String id;
    private String url;
    private String description;

    public LinkResponse(String id, String url, String description) {
        this.id = id;
        this.url = url;
        this.description = description;
    }
}
