package com.soungho.studyolle.account

import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest

@DataJpaTest
internal class AccountRepositoryTest @Autowired constructor(
    private val accountRepository: AccountRepository
) {

    @DisplayName("null 허용 안하는 타입에대해 없을때 어떻게 되는지?")
    @Test
    fun not_allow_null_test() {
        val account = accountRepository.findByEmail("soungho")
        assertNull(account)
    }
}