CREATE TABLE IF NOT EXISTS teacherProfile(
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255),
    surname VARCHAR(255),
    email VARCHAR(255),
    age INTEGER,
    phone_number INTEGER,
    description varchar(255)
);