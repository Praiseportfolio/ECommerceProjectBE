package com.pF.E_commerce.modal;

import jakarta.persistence.*;
import lombok.*;
import org.antlr.v4.runtime.misc.NotNull;
//import javax.validation.constraints.NotNull;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Table(name = "category")
@Data

public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    private String name;

//    @NotNull
//    @Column(unique = true)
//    private String categoryId;

//    @ManyToOne
//    private Category parentCategory;
//
//    @NotNull
//    private Integer level;



}
