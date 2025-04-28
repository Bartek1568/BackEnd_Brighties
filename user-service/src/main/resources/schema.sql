-- Tabela główna dla wszystkich użytkowników
CREATE TABLE IF NOT EXISTS users(
                       id BIGINT PRIMARY KEY AUTO_INCREMENT,
                       name VARCHAR(255),
                       surname VARCHAR(255),
                       email VARCHAR(255) UNIQUE NOT NULL,
                       password VARCHAR(255) NOT NULL,
                       phone_number INT UNIQUE,
                       role VARCHAR(50) NOT NULL,

    -- Kolumna dyskryminująca
                       user_type VARCHAR(20) NOT NULL, -- 'TEACHER', 'STUDENT', 'ADMIN'

    -- Pola specyficzne dla Teacher
                       description TEXT,

    -- Pola specyficzne dla Student
                       age INT,
                       goal TEXT,
                       course VARCHAR(255),
                       school_type VARCHAR(50),
                       grade INT
);

-- Tabela dla specjalizacji nauczycieli
CREATE TABLE IF NOT EXISTS teacher_specializations (
                                         teacher_id BIGINT NOT NULL,
                                         specialization VARCHAR(255) NOT NULL,
                                         PRIMARY KEY (teacher_id, specialization),
                                         FOREIGN KEY (teacher_id) REFERENCES users(id) ON DELETE CASCADE
);

