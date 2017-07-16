package com.example.oldroy.ssebook.models

import com.example.oldroy.ssebook.Contact
import com.example.oldroy.ssebook.enums.UserEnum

class CartRepo : Contact.Repository<Order> {

    private val orderList = HashMap<String, Order>()

    override fun new(key: String, obj: Order): Boolean {
        if (orderList.containsKey(key)) return false
        else {
            orderList.put(key, obj)
            return true
        }
    }

    override fun all(sortBy: Any?): List<Order> {
        return orderList.values.toList()
    }

    override fun where(filter: Filter): List<Order> {
        when(filter.attribute) {
            UserEnum.NAME -> {
                val list = ArrayList<Order>()
                list.add(orderList.get(filter.value) as Order)
                return list
            }
            else -> return orderList.values.toList()
        }
    }

    override fun where(filter: Filter, sortBy: Any?): List<Order> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun delete(filter: Filter): Boolean {
        when(filter.attribute) {
            UserEnum.NAME -> {
                if(orderList.containsKey(filter.value)) {
                    orderList.remove(filter.value)
                    return true
                } else return false
            }
            else -> return false
        }
    }

    override fun update(filter: Filter): Boolean {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun all(): List<Order> {
        return orderList.values.toList()
    }

    override fun size(): Int {
        return orderList.size
    }



    fun checkOut(userName: String): Double {
        var totalCost: Double = 0.0
        where(Filter(UserEnum.NAME, userName))
                .get(0)
                .bookList.forEach { book -> totalCost += book.price }
        orderList.remove(userName)
        return totalCost
    }
}