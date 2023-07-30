-- users
INSERT INTO public.users (id, email, first_name, last_name, "password", "role", username)
VALUES (nextval('user_sequence_generator'), 'admin@gmail.com', 'Admin', 'Admin', '123', 'ADMIN', 'admin'),
       (nextval('user_sequence_generator'), 'ana@valcon.com', 'Ana', 'Gavrilovic', '123', 'USER', 'ana'),
       (nextval('user_sequence_generator'), 'sanja@gmail.com', 'Sanja', 'Drinic', '123', 'USER', 'sanja');

--  theatres
INSERT INTO public.theater(id, "name", number_of_seats)
VALUES (nextval('theater_sequence_generator'), 'Grand Room', 50),
       (nextval('theater_sequence_generator'), 'Indie Room', 10),
       (nextval('theater_sequence_generator'), 'Couple Room', 2);

-- movies
INSERT INTO public.movie (id, deleted, director, length, name, description)
VALUES (nextval('movie_sequence_generator'), false, 'Christopher Nolan', 148, 'Inception', 'A thief who steals corporate secrets through the use of dream-sharing technology is given the inverse task of planting an idea into the mind of a C.E.O., but his tragic past may doom the project and his team to disaster.'),
       (nextval('movie_sequence_generator'), false, 'Chris Columbus', 143, 'Harry Potter and the Sorcerers Stone', 'An orphaned boy enrolls in a school of wizardry, where he learns the truth about himself, his family and the terrible evil that haunts the magical world.'),
       (nextval('movie_sequence_generator'), false, 'Robert Zemeckis', 142, 'Forrest Gump', 'The presidencies of Kennedy and Johnson, the Vietnam War, the Watergate scandal and other historical events unfold from the perspective of an Alabama man with an IQ of 75, whose only desire is to be reunited with his childhood sweetheart.');

-- genres
INSERT INTO public.movie_genres (movie_id, genres)
VALUES (1, 'ACTION'),
       (1, 'ADVENTURE'),
       (1, 'SCIENCE_FICTION'),
       (2, 'ADVENTURE'),
       (2, 'FANTASY'),
       (3, 'ROMANCE'),
       (3, 'DRAMA');