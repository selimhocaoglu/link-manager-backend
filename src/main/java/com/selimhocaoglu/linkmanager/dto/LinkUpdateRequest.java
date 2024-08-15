package com.selimhocaoglu.linkmanager.dto;

import lombok.Data;

@Data
public class LinkUpdateRequest {
    private String url;
    private String description;
}
