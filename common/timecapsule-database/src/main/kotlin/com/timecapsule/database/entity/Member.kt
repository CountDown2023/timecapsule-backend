package com.timecapsule.database.entity

import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id

@Entity
data class Member(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private val id: Long? = null,
    @javax.persistence.Column(unique = true)
    private val nickName: String,
    private val password: String,
    private val email: String,
): BaseTimeEntity()
