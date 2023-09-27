package com.waracle.cakemgr.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CakeDTO {
    private Integer id;
    private String title;
    private String imageURL;
    private String description;
}
