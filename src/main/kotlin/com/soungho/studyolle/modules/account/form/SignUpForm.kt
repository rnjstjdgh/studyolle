package com.soungho.studyolle.modules.account.form

import org.hibernate.validator.constraints.Length
import javax.validation.constraints.Email
import javax.validation.constraints.NotBlank
import javax.validation.constraints.Pattern

data class SignUpForm(

    @NotBlank
    @Length(min = 3, max = 20)
    @Pattern(regexp = "^[ㄱ-ㅎ가-힣a-z0-9_-]{3,20}$")
    var nickname: String = "",

    @Email
    @NotBlank
    var email: String = "",

    @NotBlank
    @Length(min = 8, max = 50)
    var password: String = "",
) {
}