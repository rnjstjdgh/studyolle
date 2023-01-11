package com.soungho.studyolle.account

import com.soungho.studyolle.domian.Account
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.User

class UserAccount(
    val account: Account
): User(
    account.nickname,
    account.password,
    arrayListOf(SimpleGrantedAuthority("ROLE_USER"))
)