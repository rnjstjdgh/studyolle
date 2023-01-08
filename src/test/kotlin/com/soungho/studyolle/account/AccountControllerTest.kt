package com.soungho.studyolle.account

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.mockito.ArgumentMatchers.any
import org.mockito.BDDMockito.then
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.mail.SimpleMailMessage
import org.springframework.mail.javamail.JavaMailSender
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.get
import org.springframework.test.web.servlet.post
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.view

@SpringBootTest
@AutoConfigureMockMvc
internal class AccountControllerTest @Autowired constructor(
    private val mockMvc: MockMvc,
    private val accountRepository: AccountRepository,
    @MockBean private val javaMailSender: JavaMailSender
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
                model { attributeExists("signUpForm") }
            }
    }

    @DisplayName("회원 가입 처리 - 입력값 오류")
    @Test
    fun signUpSubmit_with_wrong_input() {
        mockMvc.post("/sign-up") {
            param("nickname", "soungho")
            param("email", "email...")
            param("password", "12345")
            with(csrf())
        }.andExpect {
            status { isOk() }
            view { name("account/sign-up") }
        }
    }

    @DisplayName("회원 가입 처리 - 입력값 정상")
    @Test
    fun signUpSubmit_with_correct_input() {
        val givenEmail = "gshgsh0831@gmail.com"
        val givenPassword = "123456789"

        mockMvc.post("/sign-up") {
            param("nickname", "soungho")
            param("email", givenEmail)
            param("password", givenPassword)
            with(csrf())
        }.andExpect {
            status { is3xxRedirection() }
            view { name("redirect:/") }
        }

        val account = accountRepository.findByEmail(givenEmail)
        assertNotNull(account)
        assertNotNull(account?.emailCheckToken)
        assertNotEquals(account?.password, givenPassword)

        then(javaMailSender).should().send(any(SimpleMailMessage::class.java))
    }
}