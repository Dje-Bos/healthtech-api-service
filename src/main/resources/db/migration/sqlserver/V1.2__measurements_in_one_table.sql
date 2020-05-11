truncate table temp_measurements;
truncate table weight_measurements;
truncate table glucose_measurements;
truncate table pressure_measurements;
truncate table pulse_measurements;

drop table if exists glucose_measurements;
drop table if exists pulse_measurements;
drop table if exists weight_measurements;
drop table if exists pressure_measurements;
drop table if exists temp_measurements;

truncate table measurements;

    alter table measurements
       add dtype varchar(31) not null;

    alter table measurements
       add glucose float;

    alter table measurements
       add diastolic int;

    alter table measurements
       add systolic int;

    alter table measurements
       add pulse int;

    alter table measurements
       add temp float;

    alter table measurements
       add weight float;
