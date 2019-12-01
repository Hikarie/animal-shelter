-- 超级管理员
create table ADMIN
(
    ANAME VARCHAR2(30) not null,
    PWD   VARCHAR2(20) not null,
    ID    VARCHAR2(8)  not null
        constraint PK_ADMIN
            primary key
)

-- 动物信息表
create table ANIMAL
(
    ID    VARCHAR2(20) not null
        constraint ANIMAO_PK
            primary key,
    ANO   VARCHAR2(20) not null,
    ANAME VARCHAR2(20) not null,
    TYPE  VARCHAR2(20) not null,
    SEX   VARCHAR2(10) not null,
    AGE   NUMBER       not null,
    IMAGE VARCHAR2(20),
    SID   VARCHAR2(20) not null
        constraint ANIMAL_FK1
            references SHELTER
)

-- 工作人员信息表
create table STAFF
(
    ID    VARCHAR2(20) not null
        constraint STAFF_PK
            primary key,
    SNAME VARCHAR2(20) not null,
    PWD   VARCHAR2(20) not null,
    EMAIL VARCHAR2(20),
    PHONE VARCHAR2(20),
    SID   VARCHAR2(20) not null
        constraint SHELTER_ID
            references SHELTER,
    TYPE  VARCHAR2(20),
    constraint STAFF_UK1
        unique (SNAME, EMAIL, PHONE)
)

-- 收容所信息表
create table SHELTER
(
    ID     VARCHAR2(20) not null
        constraint SHELTER_PK
            primary key,
    NAME   VARCHAR2(20) not null,
    ADDR   VARCHAR2(20) not null,
    CODE   VARCHAR2(20),
    TOTAL  NUMBER       not null,
    REST   NUMBER,
    REMARK VARCHAR2(20)
)


-- 健康检查信息表
create table HEALTH
(
    ID        VARCHAR2(20)  not null
        constraint HEALTH_PK
            primary key,
    AID       VARCHAR2(20)  not null
        constraint HEALTH_FK1
            references ANIMAL,
    STID      VARCHAR2(20)
        constraint HEALTH_FK2
            references STAFF,
    STATUS    VARCHAR2(20)  not null,
    CHECKDATE VARCHAR2(100) not null,
    REMARK    VARCHAR2(20)
)

-- 疫苗接种信息表
create table VACCINE
(
    ID     VARCHAR2(20)  not null
        constraint VACCINE_PK
            primary key,
    AID    VARCHAR2(20)  not null
        constraint VACCINE_FK1
            references ANIMAL,
    STID   VARCHAR2(20)  not null
        constraint VACCINE_FK2
            references STAFF,
    VNAME  VARCHAR2(20)  not null,
    VTIME  VARCHAR2(100) not null,
    REMARK VARCHAR2(20)
)
