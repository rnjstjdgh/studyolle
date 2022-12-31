package com.soungho.studyolle.domian

import lombok.Builder
import lombok.EqualsAndHashCode
import java.time.LocalDateTime
import javax.persistence.*
import javax.persistence.FetchType.EAGER

@Entity
@Builder
@EqualsAndHashCode(of = ["id"])
class Account(
    @Id @GeneratedValue
    var id: Long,
    @Column(unique = true)
    var email: String,
    @Column(unique = true)
    var nickname: String,
    var password: String,
    var emailVerified: Boolean,
    var emailCheckToken: String,
    var joinedAt: LocalDateTime,
    var bio: String,
    var url: String,
    var occupation: String,
    var location: String,
    @Lob @Basic(fetch = EAGER)
    var profileImage: String,
    var studyCreatedByEmail: Boolean,
    var studyCreatedByWeb: Boolean,
    var studyEnrollmentResultByEmail: Boolean,
    var studyEnrollmentResultByWeb: Boolean,
    var studyUpdatedByEmail: Boolean,
    var studyUpdatedByWeb: Boolean,
) {
}