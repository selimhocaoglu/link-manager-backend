package com.selimhocaoglu.linkmanager.dto;

import lombok.Data;

@Data
public class LinkCreateRequest {
    private String url;
    private String description;
}
