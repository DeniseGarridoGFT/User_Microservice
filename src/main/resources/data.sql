INSERT INTO Countries (name, tax, prefix, time_zone) VALUES
    ('España', 21, '+34', 'Europe/Madrid'),
    ('Estonia', 20, '+372', 'Europe/Tallinn'),
    ('Finlandia', 24, '+358', 'Europe/Helsinki'),
    ('Francia', 20, '+33', 'Europe/Paris'),
    ('Italia', 22, '+39', 'Europe/Rome'),
    ('Portugal', 20, '+351', 'Europe/Lisbon'),
    ('Grecia', 23, '+30', 'Europe/Athens');


INSERT INTO ADDRESSES (city_name, number, door, street, zip_code) VALUES
    ('Madrid',32,'1A','C/ La Coma','47562');

INSERT INTO users (name, last_name, email, password, fidelity_points, birth_date, phone,country_id,address_id)
VALUES ('Juan', 'García', 'juangarcia@example.com', '$2a$10$OyJUHBSm0sU8eF8os0ZuoOwDRmgg8ns4owWtIXItlYmN.1pDVxve6', 100, '1990-01-01', '123456789',1,1);
