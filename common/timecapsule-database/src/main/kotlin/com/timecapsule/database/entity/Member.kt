package com.timecapsule.database.entity

import javax.persistence.*

@Entity
data class Member(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private val id: Long? = null,
    @Column(unique = true)
    private val nickName: String,
    private val password: String,
    @Column(unique = true)
    private val email: String,
): BaseTimeEntity()
