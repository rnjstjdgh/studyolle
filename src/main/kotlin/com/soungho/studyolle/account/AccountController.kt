package com.soungho.studyolle.account

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
    private val accountService: AccountService
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
}