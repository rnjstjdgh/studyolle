package com.soungho.studyolle.account

import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.validation.Errors
import org.springframework.web.bind.WebDataBinder
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.InitBinder
import org.springframework.web.bind.annotation.PostMapping
import java.time.LocalDateTime
import javax.validation.Valid

@Controller
class AccountController(
    private val signupFormValidator: SignUpFormValidator,
    private val accountService: AccountService,
    private val accountRepository: AccountRepository,
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

        accountService.processNewAccount(signUpForm)
        return "redirect:/"
    }

    @GetMapping("check-email-token")
    fun checkEmailToken(
        token: String,
        email: String,
        model: Model
    ): String {
        val view = "account/checked-email"
        val account = accountRepository.findByEmail(email)

        if(account == null) {   // 회원이 없다
            model.addAttribute("error", "wrong.email")
            return view
        }

        if(!account.isValidToken(token)) {  // 이메일 인증 토큰이 다르다
            model.addAttribute("error", "wrong.token")
            return view
        }

        account.completeSignUp()
        model.addAttribute("numberOfUser", accountRepository.count())
        model.addAttribute("nickname", account.nickname)
        return view
    }
}