package com.increff.pos.pojo;

import com.sun.istack.NotNull;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter @Setter
@Table(name = "brand_table",uniqueConstraints = { @UniqueConstraint(columnNames = { "brand", "category" }) })
public class BrandPojo extends AbstractDatePojo{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @NotNull
    private Integer id;
    @NotNull
    private String brand;
    @NotNull
    private String category;

}
