package com.timecapsule.database.entity

import com.timecapsule.database.converter.StringListConverter
import javax.persistence.*

@Entity
data class Capsule(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private val id: Long? = null,
    private val memberId: Long,
    private val name: String,
    private val bottleChoice: Int, // 0~7
    private val bottleColor: Int,
    private val letterPaper: Int,
    private val letterLine: Int,
    private val toMe: String,
    @Convert(converter = StringListConverter::class)
    private var goals: List<String>,
): BaseTimeEntity()
