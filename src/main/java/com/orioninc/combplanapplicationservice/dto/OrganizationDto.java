package com.orioninc.combplanapplicationservice.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Column;

@EqualsAndHashCode(callSuper = true)
@Data
public class OrganizationDto extends AbstractDto {
    @Column(name = "name")
    private String name;
}
