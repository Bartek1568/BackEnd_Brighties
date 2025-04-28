INSERT INTO users (id, name, surname, email, password, phone_number, role, user_type)
VALUES
    (1, 'Jan', 'Kowalski', 'admin@brighties.com',
     '$2a$10$xS6hJf6Zz5Q2ZrZw5q3Y.OUYQJN5hR7k6Z9JtYb6Q9dK1X2vL6XeG',
     123456789, 'ADMIN', 'ADMIN');

-- Nauczyciele
INSERT INTO users (id, name, surname, email, password, phone_number, role, user_type, description)
VALUES
    (2, 'Anna', 'Nowak', 'anna.nowak@brighties.com',
     '$2a$10$xS6hJf6Zz5Q2ZrZw5q3Y.OUYQJN5hR7k6Z9JtYb6Q9dK1X2vL6XeG',
     987654321, 'TEACHER', 'TEACHER', 'Doświadczony nauczyciel matematyki'),

    (3, 'Piotr', 'Wiśniewski', 'piotr.wisniewski@brighties.com',
     '$2a$10$xS6hJf6Zz5Q2ZrZw5q3Y.OUYQJN5hR7k6Z9JtYb6Q9dK1X2vL6XeG',
     555666777, 'TEACHER', 'TEACHER', 'Specjalista od fizyki kwantowej');

-- Studenci
INSERT INTO users (id, name, surname, email, password, phone_number, role, user_type, age, goal, course, school_type, grade)
VALUES
    (4, 'Marek', 'Zieliński', 'marek.zielinski@brighties.com',
     '$2a$10$xS6hJf6Zz5Q2ZrZw5q3Y.OUYQJN5hR7k6Z9JtYb6Q9dK1X2vL6XeG',
     111222333, 'STUDENT', 'STUDENT', 18, 'Zdanie matury', 'Matematyka rozszerzona', 'LICEUM', 4),

    (5, 'Katarzyna', 'Wójcik', 'katarzyna.wojcik@brighties.com',
     '$2a$10$xS6hJf6Zz5Q2ZrZw5q3Y.OUYQJN5hR7k6Z9JtYb6Q9dK1X2vL6XeG',
     444555666, 'STUDENT', 'STUDENT', 16, 'Poprawa ocen', 'Język polski', 'LICEUM', 3);