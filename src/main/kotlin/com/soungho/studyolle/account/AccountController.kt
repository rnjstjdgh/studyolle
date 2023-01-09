package com.soungho.studyolle.account

import com.soungho.studyolle.domian.Account
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

        val newAccount = accountService.processNewAccount(signUpForm)
        accountService.login(newAccount)
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
        accountService.login(account)
        model.addAttribute("numberOfUser", accountRepository.count())
        model.addAttribute("nickname", account.nickname)
        return view
    }

    @GetMapping("/check-email")
    fun checkEmail(
        @CurrentUser account: Account,
        model: Model
    ): String {
        model.addAttribute("email", account.email)
        return "account/check-email"
    }

    @GetMapping("resend-confirm-email")
    fun resendConfirmEmail(
        @CurrentUser account: Account,
        model: Model
    ): String {
        if(!account?.canSendConfirmEmail()!!) {
            model.addAttribute("error", "인증 이메일은 1시간에 한번만 전송할 수 있습니다.")
            model.addAttribute("email", account.email)
            return "account/check-email"
        }

        accountService.sendSignUpConfirmEmail(account)
        return "redirect:/"
    }
}