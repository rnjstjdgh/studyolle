package com.soungho.studyolle.account

import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping

@Controller
class AccountController {

    @GetMapping("/sign-up")
    fun signUpForm(model: Model): String {
        return "account/sign-up"
    }
}