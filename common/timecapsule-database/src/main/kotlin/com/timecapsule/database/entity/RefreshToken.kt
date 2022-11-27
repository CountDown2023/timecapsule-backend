package com.timecapsule.database.entity

import javax.persistence.*

@Entity
data class RefreshToken(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private val id: Long? = null,
    @Column(nullable = false, unique = true)
    val memberId: Long,
    @Column(nullable = false, unique = true)
    var token: String,
)
