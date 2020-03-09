package com.boyarsky.apiservice.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.OffsetDateTime;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@Entity
@EntityListeners(AuditingEntityListener.class)
public class MeasurementEntry {
    @Id
    @GeneratedValue
    private UUID uid;

    @Enumerated(EnumType.STRING)
    private MeasurementType measurementType;

    @CreatedDate
    private OffsetDateTime created;

    private String value;

    @Enumerated(EnumType.STRING)
    private MeasurementUnit unit;

    @ManyToOne(optional = false)
    @JoinColumn(name = "user_id", foreignKey = @ForeignKey(name = "user_id_fk"))
    private User user;

}
