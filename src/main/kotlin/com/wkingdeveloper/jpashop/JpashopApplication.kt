package com.wkingdeveloper.jpashop

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class JpashopApplication

/**
 * Todo
 * 1. delivery 호출 시 order도 한번 더 조회하는 이슈 확인
 * 2. OrderItem은 자동으로 호출되는 이슈 확인
 */
fun main(args: Array<String>) {
    runApplication<JpashopApplication>(*args)
}


