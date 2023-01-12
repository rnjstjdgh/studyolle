package com.soungho.studyolle.domian

import java.time.LocalDateTime
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.Table

@Table(name = "persistent_logins")
@Entity
class PersistentLogins(

    @Id
    @Column(length = 64)
    var series: String? = null,

    @Column(nullable = false, length = 64)
    var username: String? = null,

    @Column(nullable = false, length = 64)
    var token: String? = null,

    @Column(name = "last_used", nullable = false, length = 64)
    val lastUsed: LocalDateTime? = null
)