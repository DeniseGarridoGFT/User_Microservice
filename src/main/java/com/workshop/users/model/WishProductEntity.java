package com.workshop.users.model;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;

import java.io.Serializable;

@Entity
@Table(name="WISHES")
@Data
public class WishProductEntity implements Serializable {
    @EmbeddedId
    private WishProductPK wishProductPK;

}
