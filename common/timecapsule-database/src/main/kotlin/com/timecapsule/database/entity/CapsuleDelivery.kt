package com.timecapsule.database.entity

import com.fasterxml.jackson.annotation.JsonIgnore
import java.lang.IllegalStateException
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id

@Entity
data class CapsuleDelivery(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,
    val capsuleId: Long,
    val memberId: Long,
    var count: Long,
) : BaseTimeEntity() {
    @get:JsonIgnore
    val nonNullId: Long
        get() = id ?: throw IllegalStateException("Capsule Delivery Id는 null일 수 없습니다.")

    fun increaseDeliveryCount() {
        this.count += 1
    }
}
