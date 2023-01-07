package com.soungho.studyolle.account

import org.springframework.stereotype.Component
import org.springframework.validation.Errors
import org.springframework.validation.Validator

@Component
class SignUpFormValidator(
    private val accountRepository: AccountRepository
): Validator {

    override fun supports(clazz: Class<*>) = clazz.isAssignableFrom(SignUpForm::class.java)

    override fun validate(target: Any, errors: Errors) {
        if(target is SignUpForm) {
            target.let {
                if(accountRepository.existsByEmail(it.email)) {
                    errors.rejectValue("email", "invalid.email", "이미 사용중인 이메일입니다.")
                }
                if(accountRepository.existsByNickname(it.nickname)) {
                    errors.rejectValue("nickname", "invalid.nickname", "이미 사용중인 닉네임입니다.")
                }
            }
        }
    }
}