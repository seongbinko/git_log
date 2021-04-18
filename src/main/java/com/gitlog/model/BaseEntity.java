package com.gitlog.model;

import lombok.Getter;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;

@EntityListeners(AuditingEntityListener.class)
@MappedSuperclass
@Getter
public abstract class BaseEntity extends BaseTimeEntity{
    //createdBy를 사용하려면 CurrentAuditor의 정보를 SecurityContext 에서 가져와서 주입시켜야 한다.
    //이 프로젝트 같은 경우, app.java 에 설정되어 있다.
    @CreatedBy
    @Column(updatable = false)
    private String createdBy;

    @LastModifiedDate
    private String lastModifiedBy;
}
