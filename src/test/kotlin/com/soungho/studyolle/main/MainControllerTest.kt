package com.soungho.studyolle.main

import com.soungho.studyolle.account.AccountRepository
import com.soungho.studyolle.account.AccountService
import com.soungho.studyolle.account.SignUpForm
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf
import org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.authenticated
import org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.unauthenticated
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.post

@SpringBootTest
@AutoConfigureMockMvc
internal class MainControllerTest @Autowired constructor(
    private val mockMvc: MockMvc,
    private val accountService: AccountService,
    private val accountRepository: AccountRepository
) {
    val givenNickname = "soungho"
    val givenEmail = "gshgsh0831@gmail.com"
    val givenPassword = "12345678"

    @BeforeEach
    fun beforeEach() {
        val givenSignUpForm = SignUpForm(
            nickname = givenNickname,
            email = givenEmail,
            password = givenPassword
        )
        accountService.processNewAccount(givenSignUpForm)
    }

    @AfterEach
    fun afterEach() {
        accountRepository.deleteAll()
    }

    @DisplayName("이메일로 로그인 성공")
    @Test
    fun login_with_email() {
        mockMvc.post("/login") {
            param("username", givenEmail)
            param("password", givenPassword)
            with(csrf())
        }.andExpect {
            status { is3xxRedirection() }
            redirectedUrl("/")
            authenticated().withUsername(givenNickname)
        }
    }

    @DisplayName("닉네임으로 로그인 성공")
    @Test
    fun login_with_nickname() {
        mockMvc.post("/login") {
            param("username", givenNickname)
            param("password", givenPassword)
            with(csrf())
        }.andExpect {
            status { is3xxRedirection() }
            redirectedUrl("/")
            authenticated().withUsername(givenNickname)
        }
    }

    @DisplayName("로그인 실패")
    @Test
    fun login_fail() {
        mockMvc.post("/login") {
            param("username", "givenNickname")
            param("password", givenPassword)
            with(csrf())
        }.andExpect {
            status { is3xxRedirection() }
            redirectedUrl("/login?error")
            unauthenticated()
        }
    }

    @DisplayName("로그아웃")
    @Test
    fun logout() {
        mockMvc.post("/logout") {
            with(csrf())
        }.andExpect {
            status { is3xxRedirection() }
            redirectedUrl("/")
            unauthenticated()
        }
    }

}