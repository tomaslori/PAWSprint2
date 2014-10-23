-- Users    -------------------------------------------------------

INSERT INTO users(id, name, surname, password, email, secretquestion, secretanswer, birthdate, vip)
    VALUES (1, 'Tomas', 'Lori', 'password', 'tomas@email.com', 'question', 'answer', TO_DATE('19920828', 'YYYYMMDD'), false);

INSERT INTO users(id, name, surname, password, email, secretquestion, secretanswer, birthdate, vip)
    VALUES (2, 'Mariano', 'BlaBla', 'password', 'mariano@email.com', 'question', 'answer', TO_DATE('19850402', 'YYYYMMDD'), false);

INSERT INTO users(id, name, surname, password, email, secretquestion, secretanswer, birthdate, vip)
    VALUES (3, 'Nicolas', 'Apellido', 'password', 'nicolas@email.com', 'question', 'answer', TO_DATE('19641019', 'YYYYMMDD'), true);

-- Movies   -------------------------------------------------------

INSERT INTO movies(id, name, genre, director, duration, description, releasedate)
    VALUES (1, 'DisneyMovieGeneric', 'Sci-Fi', 'Micheal Bay', 200, 'Frozen meets Transformers', TO_DATE('20130515', 'YYYYMMDD'));

INSERT INTO movies(id, name, genre, director, duration, description, releasedate)
    VALUES (2, 'WestDoor', 'OPGG', 'AHQ', 220, 'WestDoor trying to carry', TO_DATE('20150515', 'YYYYMMDD'));

INSERT INTO movies(id, name, genre, director, duration, description, releasedate)
    VALUES (3, 'SuperLoveStory', 'Romance', 'Micheal Bay', 130, 'Very heart warming', TO_DATE('20140215', 'YYYYMMDD'));

INSERT INTO movies(id, name, genre, director, duration, description, releasedate)
    VALUES (4, 'Detective', 'thriller', 'Micheal Bay', 180, 'Solving cases', TO_DATE('20140525', 'YYYYMMDD'));

INSERT INTO movies(id, name, genre, director, duration, description, releasedate)
    VALUES (5, 'DisneyMovieGenericII', 'Terror', 'Stephen Spilbergo', 200, 'Much scare. wow.', TO_DATE('20120723', 'YYYYMMDD'));

INSERT INTO movies(id, name, genre, director, duration, description, releasedate)
    VALUES (6, 'High School Musical', 'Bad', 'He who shall not be named', 150, 'singing and dancing', TO_DATE('20150108', 'YYYYMMDD'));


INSERT INTO movies(id, name, genre, director, duration, description, releasedate)
    VALUES (7, 'Whatever', 'Random', 'AHQ', 190, 'A ramdom ver randomy', TO_DATE('20110416', 'YYYYMMDD'));


-- Comments -------------------------------------------------------

INSERT INTO comments("user", movie, rating, description, id)
    VALUES (1, 1, 3, 'reasonably good.', 1);

INSERT INTO comments("user", movie, rating, description, id)
    VALUES (1, 3, 3, 'reasonably good.', 3);

INSERT INTO comments("user", movie, rating, description, id)
    VALUES (1, 4, 5, 'Excelent!', 4);

INSERT INTO comments("user", movie, rating, description, id)
    VALUES (1, 5, 3, 'reasonably good.', 5);

INSERT INTO comments("user", movie, rating, description, id)
    VALUES (2, 1, 1, 'Horribly bad', 6);

INSERT INTO comments("user", movie, rating, description, id)
    VALUES (2, 3, 1, 'Massive waste of time.', 7);

INSERT INTO comments("user", movie, rating, description, id)
    VALUES (3, 2, 4, 'Great film!', 8);

INSERT INTO comments("user", movie, rating, description, id)
    VALUES (3, 4, 5, 'Bravo!', 9);