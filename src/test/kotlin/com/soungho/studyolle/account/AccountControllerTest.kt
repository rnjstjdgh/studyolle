package com.soungho.studyolle.account

import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.get
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.view

@SpringBootTest
@AutoConfigureMockMvc
internal class AccountControllerTest @Autowired constructor(
    private val mockMvc: MockMvc,
) {

    @DisplayName("회원 가입 화면 보이는지 테스트(예전 스타일)")
    @Test
    fun signUpForm_oldStyle() {
        mockMvc.perform(get("/sign-up"))
            .andExpect(status().isOk)
            .andExpect(view().name("account/sign-up"))
    }

    /***
     * 참고: https://www.baeldung.com/kotlin/mockmvc-kotlin-dsl
     */
    @DisplayName("회원 가입 화면 보이는지 테스트")
    @Test
    fun signUpForm() {
        mockMvc.get("/sign-up")
            .andExpect {
                status { isOk() }
                view { name("account/sign-up") }
            }
    }


}