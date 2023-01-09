package com.soungho.studyolle.main

import com.soungho.studyolle.account.CurrentUser
import com.soungho.studyolle.domian.Account
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping

@Controller
class MainController {

    @GetMapping("/")
    fun home(
        @CurrentUser account: Account?,
        model: Model
    ): String {
        if(account != null) {
            model.addAttribute(account)
        }

        return "index"
    }

    @GetMapping("/login")
    fun login(): String {
        return "login"
    }
}