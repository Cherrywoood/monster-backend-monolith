CREATE TYPE GENDER AS ENUM ('male', 'female');
CREATE TYPE JOB AS ENUM ('scarer', 'cleaner','scare assistant','disinfector','recruiter');

CREATE TABLE IF NOT EXISTS ROLES
(
    ID   UUID PRIMARY KEY,
    NAME VARCHAR(16) NOT NULL UNIQUE CHECK (NAME != '')
);

CREATE TABLE IF NOT EXISTS USERS
(
    ID       UUID PRIMARY KEY,
    LOGIN    VARCHAR(20)                                                        NOT NULL UNIQUE,
    PASSWORD VARCHAR(255)                                                       NOT NULL,
    /* add default role */
    ROLE_ID  UUID REFERENCES ROLES (ID) ON DELETE SET DEFAULT ON UPDATE CASCADE NOT NULL,
    CHECK ((LOGIN != '') AND (PASSWORD != ''))
);

CREATE TABLE IF NOT EXISTS MONSTER
(
    ID            UUID PRIMARY KEY,
    NAME          VARCHAR(16)                                                    NOT NULL,
    GENDER        GENDER                                                         NOT NULL,
    DATE_OF_BIRTH DATE                                                           NOT NULL,
    JOB           JOB                                                            NOT NULL,
    EMAIL         VARCHAR(30)                                                    NOT NULL UNIQUE,
    SALARY        INT                                                            NOT NULL,
    USER_ID       UUID REFERENCES USERS (ID) ON DELETE CASCADE ON UPDATE CASCADE NOT NULL,
    CHECK ((NAME != '') AND (Email != '') AND (SALARY > 0) AND (DATE_OF_BIRTH < CURRENT_DATE))
);

CREATE TABLE IF NOT EXISTS REWARD
(
    ID            UUID PRIMARY KEY,
    BALLOON_COUNT INT NOT NULL UNIQUE,
    MONEY         INT NOT NULL,
    CHECK ((BALLOON_COUNT > 0) AND (MONEY > 0))
);

CREATE TABLE IF NOT EXISTS MONSTER_REWARD
(
    MONSTER_ID UUID REFERENCES MONSTER (ID) ON DELETE NO ACTION ON UPDATE CASCADE,
    REWARD_ID  UUID REFERENCES REWARD (ID) ON DELETE NO ACTION ON UPDATE CASCADE,
    PRIMARY KEY (MONSTER_ID, REWARD_ID)
);

CREATE TABLE IF NOT EXISTS DOOR
(
    ID     UUID PRIMARY KEY,
    STATUS BOOLEAN NOT NULL
);

CREATE TABLE IF NOT EXISTS CHILD
(
    ID            UUID PRIMARY KEY,
    NAME          VARCHAR(16)                                                   NOT NULL,
    GENDER        GENDER                                                        NOT NULL,
    DATE_OF_BIRTH DATE                                                          NOT NULL,
    DOOR_ID       UUID REFERENCES DOOR (ID) ON DELETE CASCADE ON UPDATE CASCADE NOT NULL,
    CHECK ((NAME != '') AND (DATE_OF_BIRTH < CURRENT_DATE))
);

CREATE TABLE IF NOT EXISTS FEAR_ACTION
(
    ID         UUID PRIMARY KEY,
    MONSTER_ID UUID REFERENCES MONSTER (ID) ON DELETE NO ACTION ON UPDATE CASCADE NOT NULL,
    DOOR_ID    UUID REFERENCES DOOR (ID) ON DELETE NO ACTION ON UPDATE CASCADE    NOT NULL,
    DATE       DATE                                                               NOT NULL
);

CREATE TABLE IF NOT EXISTS CITY
(
    ID   UUID PRIMARY KEY,
    NAME VARCHAR(20) NOT NULL UNIQUE CHECK (NAME != '')
);

CREATE TABLE IF NOT EXISTS ELECTRIC_BALLOON
(
    ID             UUID PRIMARY KEY,
    FEAR_ACTION_ID UUID REFERENCES FEAR_ACTION (ID),
    CITY_ID        UUID REFERENCES CITY (ID) NOT NULL
);

CREATE TABLE IF NOT EXISTS INFECTED_THING
(
    ID      UUID PRIMARY KEY,
    NAME    VARCHAR(16)                                                    NOT NULL CHECK (NAME != ''),
    DOOR_ID UUID REFERENCES DOOR (ID) ON DELETE SET NULL ON UPDATE CASCADE NOT NULL
);

CREATE TABLE IF NOT EXISTS INFECTION
(
    ID                UUID PRIMARY KEY,
    MONSTER_ID        UUID REFERENCES MONSTER (ID) ON DELETE NO ACTION ON UPDATE CASCADE        NOT NULL,
    INFECTED_THING_ID UUID REFERENCES INFECTED_THING (ID) ON DELETE NO ACTION ON UPDATE CASCADE NOT NULL,
    INFECTION_DATE    DATE                                                                      NOT NULL,
    CURE_DATE         DATE DEFAULT NULL,
    CHECK (CURE_DATE >= INFECTION_DATE)
);
