package com.boyarsky.apiservice.entity.measurement;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;

@Entity
@Getter
@Setter
@ToString
@Table(name = "pressure_measurements")
public class PressureMeasurement extends Measurement {
    @Column(nullable = false)
    private Integer systolic;
    @Column(nullable = false)
    private Integer diastolic;
}
