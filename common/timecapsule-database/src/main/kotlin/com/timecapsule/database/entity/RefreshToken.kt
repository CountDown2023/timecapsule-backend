package com.timecapsule.database.entity

import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id

@Entity
data class RefreshToken(
    @Id
    @GeneratedValue
    private val id: Long? = null,
    @Column(nullable = false, unique = true)
    val userId: Long,
    @Column(nullable = false, unique = true)
    var token: String,
)
