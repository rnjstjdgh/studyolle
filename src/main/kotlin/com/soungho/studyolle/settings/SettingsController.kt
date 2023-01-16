package com.soungho.studyolle.settings

import com.soungho.studyolle.account.CurrentUser
import com.soungho.studyolle.domian.Account
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping

@Controller
class SettingsController {

    @GetMapping("/settings/profile")
    fun profileUpdateForm(
        @CurrentUser account: Account,
        model: Model
    ):String {
        model.apply {
            addAttribute(account)
            addAttribute(Profile(account))
        }

        return "settings/profile"
    }
}