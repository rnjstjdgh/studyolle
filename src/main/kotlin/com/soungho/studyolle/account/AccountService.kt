package com.soungho.studyolle.account

import com.soungho.studyolle.domian.Account
import org.springframework.mail.SimpleMailMessage
import org.springframework.mail.javamail.JavaMailSender
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class AccountService(
    private val accountRepository: AccountRepository,
    private val mailSender: JavaMailSender,
    private val passwordEncoder: PasswordEncoder
) {

    @Transactional
    fun processNewAccount(signUpForm: SignUpForm): Account {
        val newAccount = saveNewAccount(signUpForm)
        newAccount.generateEmailCheckToken()
        sendSignUpConfirmEmail(newAccount)
        return newAccount
    }

    private fun saveNewAccount(signUpForm: SignUpForm): Account {
        val account = Account(
            email = signUpForm.email,
            nickname = signUpForm.nickname,
            password = passwordEncoder.encode(signUpForm.password),
            studyCreatedByWeb = true,
            studyEnrollmentResultByWeb = true,
            studyUpdatedByWeb = true
        )
        return accountRepository.save(account)
    }

    private fun sendSignUpConfirmEmail(newAccount: Account) {
        val mailMessage = SimpleMailMessage().apply {
            setTo(newAccount.email)
            subject = "스터디올래, 회원 가입 인증"
            text = "/check-email-token?token=${newAccount.emailCheckToken}&email=${newAccount.email}"
        }
        mailSender.send(mailMessage)
    }

    fun login(account: Account) {
        val token = UsernamePasswordAuthenticationToken(
            UserAccount(account),
            account.password,
            arrayListOf(SimpleGrantedAuthority("ROLE_USER"))
        )
        SecurityContextHolder.getContext().authentication = token
    }
}