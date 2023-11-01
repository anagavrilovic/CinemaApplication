-- users
INSERT INTO users (email, first_name, last_name, password, role, username)
VALUES ('admin@gmail.com', 'Admin', 'Admin', '$2a$10$E5bwurPH0uml1WmBsk/ooeIo/2aV2U4VOj93GOMgFQx5WQ.JcGNGS', 'ADMIN', 'admin'),
       ('user@gmail.com', 'User', 'User', '$2y$10$QTaWTVMY4Vk1C8TkLOeC9enSmQbnL9h31g.9gFcjCY4iluXhpaLMq', 'USER', 'user'),
       ('ana@gmail.com', 'Ana', 'Gavrilovic', '$2a$10$E5bwurPH0uml1WmBsk/ooeIo/2aV2U4VOj93GOMgFQx5WQ.JcGNGS', 'USER', 'ana'),
       ('sanja@gmail.com', 'Sanja', 'Drinic', '$2a$10$E5bwurPH0uml1WmBsk/ooeIo/2aV2U4VOj93GOMgFQx5WQ.JcGNGS', 'USER', 'sanja');

--  theatres
INSERT INTO theater(name, number_of_seats)
VALUES ('Grand Room', 50),
       ('Indie Room', 10),
       ('Couple Room', 2);

-- movies
INSERT INTO movie (deleted, director, length, name, description)
VALUES (false, 'Christopher Nolan', 148, 'Inception', 'A thief who steals corporate secrets through the use of dream-sharing technology is given the inverse task of planting an idea into the mind of a C.E.O., but his tragic past may doom the project and his team to disaster.'),
       (false, 'Chris Columbus', 143, 'Harry Potter and the Sorcerers Stone', 'An orphaned boy enrolls in a school of wizardry, where he learns the truth about himself, his family and the terrible evil that haunts the magical world.'),
       (false, 'Robert Zemeckis', 142, 'Forrest Gump', 'The history of the United States from the 1950s to the 70s unfolds from the perspective of an Alabama man with an IQ of 75, who yearns to be reunited with his childhood sweetheart.'),
       (false, 'Frank Darabont', 142, 'The Shawshank Redemption', 'Over the course of several years, two convicts form a friendship, seeking consolation and, eventually, redemption through basic compassion.'),
       (false, 'Francis Ford Coppola', 175, 'The Godfather', 'Don Vito Corleone, head of a mafia family, decides to hand over his empire to his youngest son Michael. However, his decision unintentionally puts the lives of his loved ones in grave danger.'),
       (false, 'Christopher Nolan', 152, 'The Dark Knight', 'When the menace known as the Joker wreaks havoc and chaos on the people of Gotham, Batman must accept one of the greatest psychological and physical tests of his ability to fight injustice.'),
       (false, 'Steven Spielberg', 195, 'Schindlers List', 'In German-occupied Poland during World War II, industrialist Oskar Schindler gradually becomes concerned for his Jewish workforce after witnessing their persecution by the Nazis.');

-- genres
INSERT INTO movie_genres (movie_id, genres)
VALUES (1, 'ACTION'),
       (1, 'ADVENTURE'),
       (1, 'SCIENCE_FICTION'),
       (2, 'ADVENTURE'),
       (2, 'FANTASY'),
       (3, 'ROMANCE'),
       (3, 'DRAMA'),
       (4, 'DRAMA'),
       (5, 'CRIME'),
       (5, 'DRAMA'),
       (6, 'ACTION'),
       (6, 'CRIME'),
       (6, 'DRAMA'),
       (7, 'DRAMA');

-- projections
INSERT INTO projection(deleted, number_of_available_seats, ticket_price, movie_id, start_date_and_time, theater_id)
VALUES (false, 50, 200, 1, '2023-10-22T15:00:00.000', 1),
       (false, 2, 500, 2, '2023-10-22T15:00:00.000', 3),
       (false, 4, 500, 7, DATEADD('DAY',1, NOW()), 2);

-- reservations
INSERT INTO reservation(user_id, projection_id, canceled)
VALUES (3, 3, false),
       (3, 3, false),
       (3, 3, false),
       (3, 3, false),
       (3, 3, false),
       (4, 3, false);