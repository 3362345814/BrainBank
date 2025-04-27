package com.cityseason.content.domain.dto;

import lombok.Data;

@Data
public class ImageDTO {
    private Long id;
    private String imageUrl;
    private Long contentId;
    private int order;
}