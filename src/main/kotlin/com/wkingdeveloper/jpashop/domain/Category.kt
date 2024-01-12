package com.wkingdeveloper.jpashop.domain

import com.wkingdeveloper.jpashop.domain.item.Item
import jakarta.persistence.*

@Entity
class Category(
    @Id @GeneratedValue
    @Column(name = "category_id")
    val id: Long = 0,

    val name: String,

    @ManyToMany
    @JoinTable(
        name = "category_item",
        joinColumns = [JoinColumn(name = "category_id")],
        inverseJoinColumns = [JoinColumn(name = "item_id")]
    )
    val items: MutableList<Item>,

    ) {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id")
    var parent: Category? = null

    @OneToMany(mappedBy = "parent")
    val child: MutableList<Category> = mutableListOf()

    fun addChildCategory(child: Category) {
        this.child.add(child)
        child.parent = this
    }
}
