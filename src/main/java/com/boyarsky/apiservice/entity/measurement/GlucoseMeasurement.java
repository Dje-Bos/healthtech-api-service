package com.boyarsky.apiservice.entity.measurement;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Getter
@Setter
@ToString
@Table(name = "glucose_measurements")
public class GlucoseMeasurement extends Measurement {
    @Column(nullable = false)
    private Float glucose;
}
