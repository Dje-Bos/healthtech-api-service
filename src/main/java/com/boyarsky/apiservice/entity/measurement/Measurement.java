package com.boyarsky.apiservice.entity.measurement;

import com.boyarsky.apiservice.entity.user.User;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Getter
@Setter
@ToString
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@EntityListeners(AuditingEntityListener.class)
@Table(name = "measurements")
public abstract class Measurement {
    @Id
    @GeneratedValue
    private UUID uid;

    @CreatedDate
    private LocalDateTime createdTime;

    @Enumerated(EnumType.STRING)
    private MeasurementUnit unit;

    @ManyToOne(optional = false)
    @JoinColumn(name = "user_id", foreignKey = @ForeignKey(name = "user_id_fk"))
    private User user;

    @Version
    private Integer version;
}
