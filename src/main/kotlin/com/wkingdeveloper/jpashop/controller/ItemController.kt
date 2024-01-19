package com.wkingdeveloper.jpashop.controller

import com.wkingdeveloper.jpashop.domain.item.Book
import com.wkingdeveloper.jpashop.serivce.ItemService
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.ModelAttribute
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping

@Controller
class ItemController(
    private val itemService: ItemService
) {

    @GetMapping("/items/new")
    fun createForm(model: Model): String {
        model.addAttribute("form", BookForm())
        return "items/createItemForm"
    }

    @PostMapping("/items/new")
    fun create(form: BookForm): String {
        val book = Book(
            name = form.name,
            price = form.price,
            stockQuantity = form.stockQuantity,
            author = form.author,
            isbn = form.isbn
        )

        itemService.saveItem(book)
        return "redirect:/"
    }

    @GetMapping("/items")
    fun list(model: Model): String {
        val items = itemService.findItems()
        model.addAttribute("items", items)
        return "items/itemsList"
    }

    @GetMapping("/items/{itemId}/edit")
    fun updateItemForm(@PathVariable("itemId") itemId: Long, model: Model): String {
        val item = itemService.findOne(itemId) as Book
        val form = BookForm()
        form.apply {
            id = item.id
            name = item.name
            price = item.price
            stockQuantity = item.stockQuantity
            author = item.author
            isbn = item.isbn
        }

        model.addAttribute("form", form)
        return "items/updateItemForm"
    }

    @PostMapping("/items/{itemId}/edit")
    fun updateItem(@ModelAttribute form: BookForm, @PathVariable itemId: Long): String {
        /**
         * Book의 대부분의 필드는 val이나 이렇게 새로운 객체로 만들고 준영속상태로 머지를 하니 기존 값을 바꿀 수 있음.
         */
//        val book = Book(
//            id = form.id,
//            name = form.name,
//            price = form.price,
//            stockQuantity = form.stockQuantity,
//            author = form.author,
//            isbn = form.isbn
//        )
//        itemService.saveItem(book)

        itemService.updateItem(itemId, form.stockQuantity)

        return "redirect:/items"
    }
}
