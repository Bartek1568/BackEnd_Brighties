CREATE TABLE IF NOT EXISTS  reservation (
                             id BIGINT PRIMARY KEY AUTO_INCREMENT,
                             teacher_id BIGINT NOT NULL,
                             student_id BIGINT NOT NULL,
                             availability_slot_id BIGINT NOT NULL,
                             status VARCHAR(255),
                             note TEXT
);
