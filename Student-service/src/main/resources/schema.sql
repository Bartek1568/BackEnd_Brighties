CREATE TABLE IF NOT EXISTS studentProfile (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255),
    surname VARCHAR(255),
    age INTEGER,
    email VARCHAR(255),
    phone_number INTEGER,
    goal VARCHAR(255),
    course VARCHAR(255),
    grade INTEGER,
    school_type VARCHAR(255)

);
