package com.wkingdeveloper.jpashop.serivce

import com.wkingdeveloper.jpashop.domain.item.Item
import com.wkingdeveloper.jpashop.repository.ItemRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true)
class ItemService(
    private val itemRepository: ItemRepository
) {

    @Transactional
    fun saveItem(item: Item) {
        itemRepository.save(item)
    }

    @Transactional
    fun updateItem(itemId: Long, stockQuantity: Int) {
        val findItem = itemRepository.findOne(itemId)
        findItem.stockQuantity = stockQuantity
    }

    fun findItems(): List<Item> {
        return itemRepository.findAll()
    }

    fun findOne(id: Long): Item {
        return itemRepository.findOne(id)
    }
}
