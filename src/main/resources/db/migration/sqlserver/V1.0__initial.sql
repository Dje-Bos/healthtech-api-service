create table glucose_measurements (
                                      glucose float not null,
                                      uid binary(255) not null,
                                      primary key (uid)
);

create table measurements (
                              uid binary(255) not null,
                              created_time datetime2,
                              unit varchar(255),
                              version int,
                              user_id bigint not null,
                              primary key (uid)
);

create table pressure_measurements (
                                       diastolic int not null,
                                       systolic int not null,
                                       uid binary(255) not null,
                                       primary key (uid)
);

create table pulse_measurements (
                                    pulse int not null,
                                    uid binary(255) not null,
                                    primary key (uid)
);

create table roles (
                       id bigint identity not null,
                       creation_time datetime2,
                       description varchar(255),
                       uid varchar(255),
                       primary key (id)
);

create table temp_measurements (
                                   temp float not null,
                                   uid binary(255) not null,
                                   primary key (uid)
);

create table users (
                       id bigint identity not null,
                       auth varchar(255),
                       creation_time datetime2,
                       email varchar(255) not null,
                       is_active bit default 1,
                       name varchar(255),
                       password varchar(255),
                       picture_url varchar(255),
                       primary key (id)
);

create table users_roles (
                             user_id bigint not null,
                             role_id bigint not null,
                             primary key (user_id, role_id)
);

create table weight_measurements (
                                     weight float not null,
                                     uid binary(255) not null,
                                     primary key (uid)
);

alter table roles
    add constraint roles_uid_uc unique (uid);

alter table users
    add constraint users_email_uc unique (email);

alter table glucose_measurements
    add constraint glucose_root_measurement_fk
        foreign key (uid) references measurements;

alter table measurements
    add constraint user_id_fk
        foreign key (user_id) references users;

alter table pressure_measurements
    add constraint pressure_root_measurement_fk
        foreign key (uid) references measurements;

alter table pulse_measurements
    add constraint pulse_root_measurement_fk
        foreign key (uid) references measurements;

alter table temp_measurements
    add constraint temp_root_measurement_fk
        foreign key (uid) references measurements;

alter table users_roles
    add constraint users_roles_role_id_fk
        foreign key (role_id) references roles;

alter table users_roles
    add constraint users_roles_user_id_fk
        foreign key (user_id) references users;

alter table weight_measurements
    add constraint weight_root_measurement_fk
        foreign key (uid) references measurements;
