package com.soungho.studyolle.account

import com.soungho.studyolle.modules.account.form.SignUpForm
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping

@Controller
class AccountController {

    @GetMapping("/sign-up")
    fun signUpForm(model: Model): String {
        model.addAttribute(SignUpForm())
        return "account/sign-up"
    }
}