package com.wkingdeveloper.jpashop.domain.item

import com.wkingdeveloper.jpashop.domain.Category
import jakarta.persistence.Column
import jakarta.persistence.DiscriminatorValue
import jakarta.persistence.Entity

@Entity
@DiscriminatorValue("A")
class Album(
    id: Long,
    name: String,
    stockQuantity: Int,
    categories: List<Category>,
    @Column(nullable = false)
    val artist: String,
    @Column(nullable = false)
    val etc: String,
) : Item(id, name, stockQuantity) {
    init {
        super.categories = categories
    }
}
