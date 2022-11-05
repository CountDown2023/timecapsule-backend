package com.timecapsule.database.entity

import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.time.LocalDateTime
import javax.persistence.EntityListeners
import javax.persistence.MappedSuperclass

@MappedSuperclass
@EntityListeners(AuditingEntityListener::class)
abstract class BaseTimeEntity  {
    // Entity가 생성되어 저장될 때 시간이 자동 저장됩니다.
    @CreatedDate
    var createdAt: LocalDateTime = LocalDateTime.now()

    // 조회한 Entity 값을 변경할 때 시간이 자동 저장됩니다.
    @LastModifiedDate
    var updatedAt: LocalDateTime = LocalDateTime.now()
}
