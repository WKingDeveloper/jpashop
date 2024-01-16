package com.wkingdeveloper.jpashop.domain.item

import com.wkingdeveloper.jpashop.domain.Category
import com.wkingdeveloper.jpashop.exception.NotEnoughStockException
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
    val price: Int,
    var stockQuantity: Int,

    ) {

    @ManyToMany(mappedBy = "items", targetEntity = Category::class)
    var categories: List<Category> = emptyList()

    //==비즈니스 로직==//
    /**
     * stock 증가
     */
    fun addStock(quantity: Int) {
        this.stockQuantity += quantity
    }


    /**
     * stock 감소
     */
    fun removeStock(quantity: Int) {
        val restStock = this.stockQuantity - quantity
        if (restStock < 0) {
            throw NotEnoughStockException("need more stock")
        }

        this.stockQuantity = restStock
    }
}
