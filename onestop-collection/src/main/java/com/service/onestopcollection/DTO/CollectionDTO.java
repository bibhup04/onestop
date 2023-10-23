package com.service.onestopcollection.DTO;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CollectionDTO {

    private Long subscriptionId;

    private Long userId;

    private Double amountCollected;

    private Long billId;
    
}
