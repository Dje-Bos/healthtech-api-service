truncate table temp_measurements;
truncate table weight_measurements;
truncate table glucose_measurements;
truncate table pressure_measurements;
truncate table pulse_measurements;
truncate table measurements cascade;

alter table if exists measurements
       add column dtype varchar(31) not null;

    alter table if exists measurements
       add column glucose float4;

    alter table if exists measurements
       add column diastolic int4;

    alter table if exists measurements
       add column systolic int4;

    alter table if exists measurements
       add column pulse int4;

    alter table if exists measurements
       add column temp float4;

    alter table if exists measurements
       add column weight float4;

drop table if exists glucose_measurements;
drop table if exists pulse_measurements;
drop table if exists weight_measurements;
drop table if exists pressure_measurements;
drop table if exists temp_measurements;