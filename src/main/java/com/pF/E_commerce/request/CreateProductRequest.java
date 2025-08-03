package com.pF.E_commerce.request;

import lombok.Data;

import java.math.BigInteger;
import java.sql.Date;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


import com.pF.E_commerce.modal.Category;
import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateProductRequest {
    private String title;
    private String image_url;
    private double selling_price;
    private BigInteger category;
    private Number quantity;
    private Date created_at;
}
