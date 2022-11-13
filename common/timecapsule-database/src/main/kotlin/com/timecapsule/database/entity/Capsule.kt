package com.timecapsule.database.entity

import com.fasterxml.jackson.annotation.JsonIgnore
import com.timecapsule.database.converter.StringListConverter
import java.lang.IllegalStateException
import javax.persistence.*

@Entity
data class Capsule(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,
    val memberId: Long,
    val name: String,
    val bottleChoice: Int, // 0~7
    val bottleColor: Int,
    val letterPaper: Int,
    val letterLine: Int,
    val content: String,
    @Convert(converter = StringListConverter::class)
    var goals: List<String>,
) : BaseTimeEntity() {
    @get:JsonIgnore
    val nonNullId: Long
        get() = id ?: throw IllegalStateException("Capsule Id는 null일 수 없습니다.")
}
