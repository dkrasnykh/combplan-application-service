package com.orioninc.combplanapplicationservice.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class RequestDto extends AbstractDto {
    private String title;
    private String description;
}
