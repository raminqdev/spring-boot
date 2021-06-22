package com.raminq.security.domain.entity;

import lombok.Data;

import javax.persistence.*;

@Data
@Embeddable
public class Address {

   private String street;
   private String zipCode;
}
