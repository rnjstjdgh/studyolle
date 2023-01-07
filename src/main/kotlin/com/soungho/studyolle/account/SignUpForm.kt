package com.soungho.studyolle.account

import org.hibernate.validator.constraints.Length
import javax.validation.constraints.Email
import javax.validation.constraints.NotBlank
import javax.validation.constraints.Pattern

data class SignUpForm(

    @field:NotBlank
    @field:Length(min = 3, max = 20)
    @field:Pattern(regexp = "^[ㄱ-ㅎ가-힣a-z0-9_-]{3,20}$")
    var nickname: String = "",

    @field:Email
    @field:NotBlank
    var email: String = "",

    @field:NotBlank
    @field:Length(min = 8, max = 50)
    var password: String = "",
) {
}