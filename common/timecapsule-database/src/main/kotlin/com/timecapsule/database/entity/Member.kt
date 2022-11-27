package com.timecapsule.database.entity

import com.fasterxml.jackson.annotation.JsonIgnore
import java.lang.IllegalStateException
import javax.persistence.*

@Entity
data class Member(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,
    @Column(unique = true)
    val nickname: String,
    var password: String,
    @Column(unique = true)
    val email: String,
): BaseTimeEntity() {
    @get:JsonIgnore
    val nonNullId: Long
        get() = id ?: throw IllegalStateException("member id는 null일 수 없습니다.")
}
