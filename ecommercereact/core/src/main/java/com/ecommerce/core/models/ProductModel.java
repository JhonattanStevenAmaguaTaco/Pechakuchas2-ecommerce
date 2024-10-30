package com.ecommerce.core.models;



import lombok.Data;
@Data
public class ProductModel {
    private int id;
    private String title;
    private double price;
    private String description;
    private String category;
    private String image;
    private RatingModel rating;
}
