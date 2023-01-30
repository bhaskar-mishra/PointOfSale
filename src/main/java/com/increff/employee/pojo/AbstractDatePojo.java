package com.increff.employee.pojo;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.Version;
import java.time.LocalDateTime;

@MappedSuperclass
public abstract class AbstractDatePojo {
    @CreationTimestamp
    @Getter
    @Setter
    @Column(name= "creation_time")
    private LocalDateTime creation_time;

    @UpdateTimestamp
    @Column(name = "last_updated")
    private LocalDateTime last_updated;

    @Column(name = "version")
    @Version
    private Long version;
}
