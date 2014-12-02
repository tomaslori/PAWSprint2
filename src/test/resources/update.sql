ALTER TABLE users
  ADD COLUMN admin boolean DEFAULT FALSE NOT NULL;

INSERT INTO movies_genres(movies_id, name)
SELECT id, genre FROM movies;

ALTER TABLE movies
  DROP COLUMN genre;

ALTER TABLE movies
  ADD COLUMN image bytea;



ALTER TABLE comments
  RENAME COLUMN "user" to owner_id;


ALTER TABLE comments
  RENAME COLUMN movie to movie_id;