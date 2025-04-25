CREATE TABLE IF NOT EXISTS availability_slot (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    teacher_id BIGINT NOT NULL,
    date DATE,
    day_of_week VARCHAR(20),
    start_time TIME,
    end_time TIME,
    is_available BOOLEAN DEFAULT TRUE,
    is_reoccurring_weekly BOOLEAN DEFAULT FALSE


);
