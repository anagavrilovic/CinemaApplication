-- users
INSERT INTO public.users (email, first_name, last_name, "password", "role", username)
VALUES ('admin@gmail.com', 'Admin', 'Admin', '$2a$10$E5bwurPH0uml1WmBsk/ooeIo/2aV2U4VOj93GOMgFQx5WQ.JcGNGS', 'ADMIN', 'admin'),
       ('ana@gmail.com', 'Ana', 'Gavrilovic', '$2a$10$E5bwurPH0uml1WmBsk/ooeIo/2aV2U4VOj93GOMgFQx5WQ.JcGNGS', 'USER', 'ana'),
       ('sanja@gmail.com', 'Sanja', 'Drinic', '$2a$10$E5bwurPH0uml1WmBsk/ooeIo/2aV2U4VOj93GOMgFQx5WQ.JcGNGS', 'USER', 'sanja');

--  theatres
INSERT INTO public.theater("name", number_of_seats)
VALUES ('Grand Room', 50),
       ('Indie Room', 10),
       ('Couple Room', 2);

-- movies
INSERT INTO public.movie (deleted, director, length, name, description)
VALUES (false, 'Christopher Nolan', 148, 'Inception', 'A thief who steals corporate secrets through the use of dream-sharing technology is given the inverse task of planting an idea into the mind of a C.E.O., but his tragic past may doom the project and his team to disaster.'),
       (false, 'Chris Columbus', 143, 'Harry Potter and the Sorcerers Stone', 'An orphaned boy enrolls in a school of wizardry, where he learns the truth about himself, his family and the terrible evil that haunts the magical world.'),
       (false, 'Robert Zemeckis', 142, 'Forrest Gump', 'The presidencies of Kennedy and Johnson, the Vietnam War, the Watergate scandal and other historical events unfold from the perspective of an Alabama man with an IQ of 75, whose only desire is to be reunited with his childhood sweetheart.');

-- genres
INSERT INTO public.movie_genres (movie_id, genres)
VALUES (1, 'ACTION'),
       (1, 'ADVENTURE'),
       (1, 'SCIENCE_FICTION'),
       (2, 'ADVENTURE'),
       (2, 'FANTASY'),
       (3, 'ROMANCE'),
       (3, 'DRAMA');

-- projections
INSERT INTO public.projection(deleted, number_of_available_seats, ticket_price, movie_id, start_date_and_time, theater_id)
VALUES (false, 50, 200, 1, '2023-10-22T15:00:00.000', 1),
       (false, 2, 500, 2, '2023-10-22T15:00:00.000', 3);
