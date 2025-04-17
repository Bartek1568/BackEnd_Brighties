INSERT INTO reservation (teacher_id, student_id, availability_slot_id, date, start_time, end_time, status, note)
VALUES
    (1, 3, 1, '2025-04-18', '10:00:00', '10:30:00', 'CONFIRMED', 'Pierwsza lekcja z nauczycielem 1'),
    (2, 5, 2, '2025-04-18', '11:00:00', '11:30:00', 'PENDING', 'Sprawdzenie poziomu ucznia'),
    (3, 1, 3, '2025-04-19', '12:00:00', '12:30:00', 'CONFIRMED', 'Utrwalenie gramatyki'),
    (4, 8, 4, '2025-04-20', '13:00:00', '13:30:00', 'CANCELLED', 'Uczeń nie mógł przyjść'),
    (5, 2, 5, '2025-04-20', '14:00:00', '14:30:00', 'CONFIRMED', 'Powtórka przed sprawdzianem'),
    (6, 6, 6, '2025-04-21', '15:00:00', '15:30:00', 'CONFIRMED', 'Przygotowanie do olimpiady'),
    (7, 10, 7, '2025-04-21', '16:00:00', '16:30:00', 'PENDING', 'Nowy uczeń'),
    (8, 4, 8, '2025-04-22', '17:00:00', '17:30:00', 'CONFIRMED', 'Korepetycje z matematyki'),
    (9, 7, 9, '2025-04-22', '18:00:00', '18:30:00', 'CONFIRMED', 'Rozwiązywanie zadań maturalnych'),
    (10, 9, 10, '2025-04-23', '09:00:00', '09:30:00', 'PENDING', 'Spotkanie wprowadzające');

