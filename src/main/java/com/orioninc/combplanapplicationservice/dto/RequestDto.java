package com.orioninc.combplanapplicationservice.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class RequestDto extends AbstractDto {
    private RequestStatus status;
    private OrganizationDto organization;
    private UserDto applicant;
    private PublicationDto publication;
}
