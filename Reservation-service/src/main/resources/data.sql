INSERT INTO reservation (teacher_id, student_id, availability_slot_id, status, note)
VALUES
    (1, 3, 1, 'CONFIRMED', 'Pierwsza lekcja z nauczycielem 1'),
    (2, 5, 2, 'PENDING', 'Sprawdzenie poziomu ucznia'),
    (3, 1, 3, 'CONFIRMED', 'Utrwalenie gramatyki'),
    (4, 8, 4, 'CANCELLED', 'Uczeń nie mógł przyjść'),
    (5, 2, 5, 'CONFIRMED', 'Powtórka przed sprawdzianem'),
    (6, 6, 6, 'CONFIRMED', 'Przygotowanie do olimpiady'),
    (7, 10, 7, 'PENDING', 'Nowy uczeń'),
    (8, 4, 8, 'CONFIRMED', 'Korepetycje z matematyki'),
    (9, 7, 9, 'CONFIRMED', 'Rozwiązywanie zadań maturalnych'),
    (10, 9, 10, 'PENDING', 'Spotkanie wprowadzające');