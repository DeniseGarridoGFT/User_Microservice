-- Table: COUNTRIES
CREATE TABLE COUNTRIES IF NOT EXISTS(
    ID BIGINT AUTO_INCREMENT PRIMARY KEY,
    NAME VARCHAR(255) NOT NULL,
    TAX FLOAT,
    PREFIX VARCHAR(255),
    TIME_ZONE VARCHAR(255)
);

-- Table: ADDRESSES
CREATE TABLE ADDRESSES IF NOT EXISTS(
    ID BIGINT AUTO_INCREMENT PRIMARY KEY,
    CITY_NAME VARCHAR(255),
    ZIP_CODE VARCHAR(255),
    STREET VARCHAR(255),
    NUMBER INTEGER,
    DOOR VARCHAR(255)
);


-- Table: USERS
CREATE TABLE USERS IF NOT EXISTS(
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
CREATE TABLE WISHES IF NOT EXISTS(
    USER_ID BIGINT NOT NULL,
    PRODUCT_ID BIGINT NOT NULL,
    PRIMARY KEY (USER_ID,PRODUCT_ID)
);

ALTER TABLE WISHES
ADD CONSTRAINT FK_WISHES_USER
FOREIGN KEY (USER_ID) REFERENCES USERS(ID);

INSERT INTO COUNTRIES (name, tax, prefix, time_zone) VALUES
    ('España', 21, '+34', 'Europe/Madrid'),
    ('Estonia', 20, '+372', 'Europe/Tallinn'),
    ('Finlandia', 24, '+358', 'Europe/Helsinki'),
    ('Francia', 20, '+33', 'Europe/Paris'),
    ('Italia', 22, '+39', 'Europe/Rome'),
    ('Portugal', 20, '+351', 'Europe/Lisbon'),
    ('Grecia', 23, '+30', 'Europe/Athens');


INSERT INTO ADDRESSES (city_name, number, door, street, zip_code) VALUES
    ('Madrid',32,'1A','C/ La Coma','47562');
INSERT INTO ADDRESSES (city_name, number, door, street, zip_code) VALUES
        ('Madrid',32,'1A','C/ La Coma','47562');

INSERT INTO USERS (name, last_name, email, password, fidelity_points, birth_date, phone,country_id,address_id)
VALUES ('Juan', 'García', 'juangarcia@example.com', '$2a$10$OyJUHBSm0sU8eF8os0ZuoOwDRmgg8ns4owWtIXItlYmN.1pDVxve6', 100, '1990-01-01', '123456789',1,1);
INSERT INTO users (name, last_name, email, password, fidelity_points, birth_date, phone,country_id,address_id)
VALUES ('Manolo', 'García', 'manolo@example.com', '$2a$10$OyJUHBSm0sU8eF8os0ZuoOwDRmgg8ns4owWtIXItlYmN.1pDVxve6', 100, '1990-01-01', '123456789',1,2);

INSERT INTO WISHES(USER_ID,PRODUCT_ID) VALUES (1,8);
INSERT INTO WISHES(USER_ID,PRODUCT_ID) VALUES (1,5);





