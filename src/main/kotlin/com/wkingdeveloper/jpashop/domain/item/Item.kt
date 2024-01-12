package com.wkingdeveloper.jpashop.domain.item

import com.wkingdeveloper.jpashop.domain.Category
import jakarta.persistence.*

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "dtype")
abstract class Item(
    @Id
    @GeneratedValue
    @Column(name = "item_id")
    val id: Long = 0,

    val name: String,
    val stockQuantity: Int,

    ) {

    @ManyToMany(mappedBy = "items", targetEntity = Category::class)
    var categories: List<Category> = emptyList()
}
