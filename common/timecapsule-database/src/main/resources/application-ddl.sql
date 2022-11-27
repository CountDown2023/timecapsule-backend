CREATE TABLE capsule(
                        id            BIGINT primary key not null auto_increment,
                        member_id     BIGINT             not null,
                        name          VARCHAR(250)       not null,
                        bottle_choice INT                not null,
                        bottle_color  INT                not null,
                        letter_paper  INT                not null,
                        letter_line   INT                not null,
                        content       VARCHAR(2000)      not null,
                        goals         VARCHAR(2000)      not null,
                        created_at    DATETIME,
                        updated_at    DATETIME
);

CREATE TABLE capsule_delivery
(
    id         BIGINT primary key not null auto_increment,
    member_id  BIGINT             not null,
    capsule_id BIGINT             not null,
    count      BIGINT             not null,
    created_at DATETIME,
    updated_at DATETIME
);

CREATE TABLE member
(
    id         BIGINT primary key not null auto_increment,
    nickname   VARCHAR(250)       not null,
    password   VARCHAR(250)       not null,
    created_at DATETIME,
    updated_at DATETIME
);


CREATE TABLE refresh_token
(
    id        BIGINT primary key not null auto_increment,
    member_id BIGINT             not null,
    token     VARCHAR(1000)      not null
);

create table hibernate_sequence ( next_val bigint );
