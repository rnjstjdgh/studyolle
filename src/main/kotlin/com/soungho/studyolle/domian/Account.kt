package com.soungho.studyolle.domian

import lombok.Builder
import lombok.EqualsAndHashCode
import java.time.LocalDateTime
import java.util.*
import javax.persistence.*
import javax.persistence.FetchType.EAGER

/***
 * 주 생성자에 프로퍼티를 선언하여 생성자와 getter & setter 를 간결하게 작성할 수 있다
 * 그러나, 게터는 열어두고 세터는 닫아두고 싶은데 요 부분은 잘 지원하지 못함
 * 부가 코드를 넣어서 세터를 닫는게 좋을지 아니면, 세터를 그냥 열어두고 사용하는쪽에서 조심하는게 좋을지 논의해보면 좋을것 같음
 */
@Entity
@Builder
@EqualsAndHashCode(of = ["id"])
class Account(
    @Id @GeneratedValue
    var id: Long? = null,
    @Column(unique = true)
    var email: String,
    @Column(unique = true)
    var nickname: String,
    var password: String,
    var emailVerified: Boolean = false,
    var emailCheckToken: String = "",
    var joinedAt: LocalDateTime? = null,
    var bio: String = "",
    var url: String? = null,   // fixme: 요런 요소들은 회원 객체에 없을 수도 있는 요소? <- 의미없는 디폴트값을 설정하기보단 null 을 넣어서 비즈니스로직에서 확인하도록 하는게 더 좋을까?
    var occupation: String = "",
    var location: String = "",
    @Lob @Basic(fetch = EAGER)
    var profileImage: String = "",
    var studyCreatedByEmail: Boolean = false,
    var studyCreatedByWeb: Boolean = false,
    var studyEnrollmentResultByEmail: Boolean = false,
    var studyEnrollmentResultByWeb: Boolean = false,
    var studyUpdatedByEmail: Boolean = false,
    var studyUpdatedByWeb: Boolean = false,
) {

    fun generateEmailCheckToken() {
        this.emailCheckToken = UUID.randomUUID().toString()
    }

    fun completeSignUp() {
        this.emailVerified = true
        this.joinedAt = LocalDateTime.now()
    }

    fun isValidToken(token: String) = this.emailCheckToken == token
}