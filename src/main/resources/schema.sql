-- Table: COUNTRIES
CREATE TABLE COUNTRIES(
    ID BIGINT AUTO_INCREMENT PRIMARY KEY,
    NAME VARCHAR(255) NOT NULL,
    TAX FLOAT,
    PREFIX VARCHAR(255),
    TIME_ZONE VARCHAR(255)
);

-- Table: ADDRESSES
CREATE TABLE ADDRESSES(
    ID BIGINT AUTO_INCREMENT PRIMARY KEY,
    CITY_NAME VARCHAR(255),
    ZIP_CODE VARCHAR(255),
    STREET VARCHAR(255),
    NUMBER INTEGER,
    DOOR VARCHAR(255)
);


-- Table: USERSCREATE TABLE USERS(
CREATE TABLE USERS(
    ID BIGINT AUTO_INCREMENT PRIMARY KEY,
    NAME VARCHAR(255) NOT NULL,
    LAST_NAME VARCHAR(255) NOT NULL,
    EMAIL VARCHAR(255) NOT NULL,
    PASSWORD VARCHAR(255) NOT NULL,
    COUNTRY_ID BIGINT NOT NULL,
    FIDELITY_POINTS INTEGER DEFAULT 0,
    BIRTH_DATE DATE,
    PHONE VARCHAR(255) NOT NULL,
    ADDRESS_ID BIGINT,
    FOREIGN KEY (COUNTRY_ID) REFERENCES COUNTRIES(ID),
    FOREIGN KEY (ADDRESS_ID) REFERENCES ADDRESSES(ID)
);

-- Table: WISHES
CREATE TABLE WISHES(
    USER_ID BIGINT NOT NULL,
    PRODUCT_ID BIGINT NOT NULL,
    PRIMARY KEY (USER_ID,PRODUCT_ID)
);

ALTER TABLE WISHES
ADD CONSTRAINT FK_WISHES_USER
FOREIGN KEY (USER_ID) REFERENCES USERS(ID);


