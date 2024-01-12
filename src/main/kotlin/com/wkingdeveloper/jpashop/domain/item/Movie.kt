package com.wkingdeveloper.jpashop.domain.item

import com.wkingdeveloper.jpashop.domain.Category
import jakarta.persistence.Column
import jakarta.persistence.DiscriminatorValue
import jakarta.persistence.Entity


@Entity
@DiscriminatorValue("M")
class Movie(
    id: Long,
    name: String,
    stockQuantity: Int,
    categories: List<Category>,
    @Column(nullable = false)
    val director: String,
    @Column(nullable = false)
    val actor: String,
) : Item(id, name, stockQuantity) {
    init {
        super.categories = categories
    }
}
