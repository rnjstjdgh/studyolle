package com.soungho.studyolle.account

import com.soungho.studyolle.domian.Account
import org.springframework.mail.SimpleMailMessage
import org.springframework.mail.javamail.JavaMailSender
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.validation.Errors
import org.springframework.web.bind.WebDataBinder
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.InitBinder
import org.springframework.web.bind.annotation.PostMapping
import javax.validation.Valid

@Controller
class AccountController(
    private val signupFormValidator: SignUpFormValidator,
    private val accountRepository: AccountRepository,
    private val mailSender: JavaMailSender
) {

    @InitBinder("signUpForm")
    fun initBinder(webDataBinder: WebDataBinder) {
        webDataBinder.addValidators(signupFormValidator)

    }

    @GetMapping("/sign-up")
    fun signUpForm(
        model: Model
    ): String {
        model.addAttribute(SignUpForm())
        return "account/sign-up"
    }

    @PostMapping("/sign-up")
    fun signUpSubmit(
        @Valid signUpForm: SignUpForm,
        errors: Errors
    ): String {
        if (errors.hasErrors()) {
            return "account/sign-up";
        }
        val account = Account(
            email = signUpForm.email,
            nickname = signUpForm.nickname,
            password = signUpForm.password, // todo: encoding
            studyCreatedByWeb = true,
            studyEnrollmentResultByWeb = true,
            studyUpdatedByWeb = true
        )
        val newAccount = accountRepository.save(account)
        val mailMessage = SimpleMailMessage()
        mailMessage.setTo(newAccount.email)
        mailMessage.subject = "스터디올래, 회원 가입 인증"
        mailMessage.text = "/check-email-token?token=${newAccount.emailCheckToken}&email=${newAccount.email}"
        mailSender.send(mailMessage)
        return "redirect:/"
    }
}