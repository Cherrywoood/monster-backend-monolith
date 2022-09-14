CREATE TYPE GENDER AS ENUM ('male', 'female');
CREATE TYPE JOB AS ENUM ('scarer', 'cleaner','scare assistant','disinfector','recruiter');

CREATE TABLE IF NOT EXISTS ROLES
(
    ID         UUID PRIMARY KEY,
    NAME       VARCHAR(16) NOT NULL UNIQUE CHECK (NAME != ''),
    IS_DELETED BOOLEAN     NOT NULL DEFAULT FALSE
);

CREATE TABLE IF NOT EXISTS USERS
(
    ID         UUID PRIMARY KEY,
    LOGIN      VARCHAR(20)                                  NOT NULL UNIQUE,
    PASSWORD   VARCHAR(255)                                 NOT NULL,
    /* add default role */
    ROLE_ID    UUID REFERENCES ROLES (ID) ON UPDATE CASCADE NOT NULL,
    IS_DELETED BOOLEAN                                      NOT NULL DEFAULT FALSE,
    CHECK ((LOGIN != '') AND (PASSWORD != ''))
);

CREATE TABLE IF NOT EXISTS MONSTER
(
    ID            UUID PRIMARY KEY,
    NAME          VARCHAR(16)                                  NOT NULL,
    GENDER        GENDER                                       NOT NULL,
    DATE_OF_BIRTH DATE                                         NOT NULL,
    POSITION      JOB                                          NOT NULL,
    EMAIL         VARCHAR(30)                                  NOT NULL UNIQUE,
    SALARY        INT                                          NOT NULL,
    USER_ID       UUID REFERENCES USERS (ID) ON UPDATE CASCADE NOT NULL,
    IS_DELETED    BOOLEAN                                      NOT NULL DEFAULT FALSE,
    CHECK ((NAME != '') AND (Email != '') AND (SALARY > 0) AND (DATE_OF_BIRTH < CURRENT_DATE))
);

CREATE TABLE IF NOT EXISTS DOOR
(
    ID         UUID PRIMARY KEY,
    STATUS     BOOLEAN NOT NULL,
    IS_DELETED BOOLEAN NOT NULL DEFAULT FALSE
);

CREATE TABLE IF NOT EXISTS CHILD
(
    ID            UUID PRIMARY KEY,
    NAME          VARCHAR(16)                                 NOT NULL,
    GENDER        GENDER                                      NOT NULL,
    DATE_OF_BIRTH DATE                                        NOT NULL,
    DOOR_ID       UUID REFERENCES DOOR (ID) ON UPDATE CASCADE NOT NULL,
    IS_DELETED    BOOLEAN                                     NOT NULL DEFAULT FALSE,
    CHECK ((NAME != '') AND (DATE_OF_BIRTH < CURRENT_DATE))
);

CREATE TABLE IF NOT EXISTS FEAR_ACTION
(
    ID         UUID PRIMARY KEY,
    MONSTER_ID UUID REFERENCES MONSTER (ID) ON UPDATE CASCADE NOT NULL,
    DOOR_ID    UUID REFERENCES DOOR (ID) ON UPDATE CASCADE    NOT NULL,
    DATE       DATE                                           NOT NULL,
    IS_DELETED BOOLEAN                                        NOT NULL DEFAULT FALSE
);

CREATE TABLE IF NOT EXISTS CITY
(
    ID         UUID PRIMARY KEY,
    NAME       VARCHAR(20) NOT NULL UNIQUE CHECK (NAME != ''),
    IS_DELETED BOOLEAN     NOT NULL DEFAULT FALSE
);

CREATE TABLE IF NOT EXISTS ELECTRIC_BALLOON
(
    ID             UUID PRIMARY KEY,
    FEAR_ACTION_ID UUID REFERENCES FEAR_ACTION (ID),
    CITY_ID        UUID REFERENCES CITY (ID) NOT NULL,
    IS_DELETED     BOOLEAN                   NOT NULL DEFAULT FALSE
);

CREATE TABLE IF NOT EXISTS INFECTED_THING
(
    ID         UUID PRIMARY KEY,
    NAME       VARCHAR(16)                                 NOT NULL CHECK (NAME != ''),
    DOOR_ID    UUID REFERENCES DOOR (ID) ON UPDATE CASCADE NOT NULL,
    IS_DELETED BOOLEAN                                     NOT NULL DEFAULT FALSE
);

CREATE TABLE IF NOT EXISTS INFECTION
(
    ID                UUID PRIMARY KEY,
    MONSTER_ID        UUID REFERENCES MONSTER (ID) ON UPDATE CASCADE        NOT NULL,
    INFECTED_THING_ID UUID REFERENCES INFECTED_THING (ID) ON UPDATE CASCADE NOT NULL,
    DATE_OF_POISONING DATE                                                  NOT NULL,
    DATE_OF_CURE      DATE                                                           DEFAULT NULL,
    IS_DELETED        BOOLEAN                                               NOT NULL DEFAULT FALSE,
    CHECK (DATE_OF_CURE >= DATE_OF_POISONING)
);
