package com.wkingdeveloper.jpashop.controller

import com.wkingdeveloper.jpashop.repository.OrderSearch
import com.wkingdeveloper.jpashop.serivce.ItemService
import com.wkingdeveloper.jpashop.serivce.MemberService
import com.wkingdeveloper.jpashop.serivce.OrderService
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.*

@Controller
class OrderController(
    private val orderService: OrderService,
    private val memberService: MemberService,
    private val itemService: ItemService
) {

    @GetMapping("/order")
    fun createForm(model: Model): String {
        val members = memberService.findMembers()
        val items = itemService.findItems()

        model.addAttribute("members", members)
        model.addAttribute("items", items)

        return "order/orderForm"
    }

    @PostMapping("/order")
    fun create(
        @RequestParam("memberId") memberId: Long, @RequestParam("itemId") itemId: Long,
        @RequestParam("count") count: Int
    ): String {

        orderService.order(memberId, itemId, count)

        return "redirect:/orders"
    }

//    @GetMapping("/orders")
//    fun orderList(@ModelAttribute("orderSearch") orderSearch: OrderSearch, model: Model): String {
//        val orders = orderService.findOrders(orderSearch)
//        model.addAttribute("orders", orders)
//        return "order/orderList"
//    }

    @PostMapping("/orders/{orderId}/cancel")
    fun cancelOrder(@PathVariable("orderId") orderId: Long): String {
        orderService.cancelOrder(orderId)
        return "redirect:/orders"
    }
}
