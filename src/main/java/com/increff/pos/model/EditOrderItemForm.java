package com.increff.pos.model;


import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class EditOrderItemForm {
    private Integer orderItemId;
    private Integer quantity;
}