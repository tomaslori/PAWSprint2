
-- Table: movies_distinctions

-- DROP TABLE movies_distinctions;

CREATE TABLE movies_distinctions
(
  movies_id integer NOT NULL,
  gotprized boolean NOT NULL,
  name character varying(255) NOT NULL,
  CONSTRAINT fkf1bda1e3b6daa239 FOREIGN KEY (movies_id)
      REFERENCES movies (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
)
WITH (
  OIDS=FALSE
);
ALTER TABLE movies_distinctions
  OWNER TO paw;

-----------------------------------------------------------------------

-- Table: reviews

-- DROP TABLE reviews;

CREATE TABLE reviews
(
  id serial NOT NULL,
  rating integer NOT NULL,
  comment_id integer,
  movie_id integer,
  owner_id integer,
  CONSTRAINT reviews_pkey PRIMARY KEY (id),
  CONSTRAINT fk418ff41b24b1d040 FOREIGN KEY (owner_id)
  REFERENCES users (id) MATCH SIMPLE
  ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT fk418ff41b6a0a672c FOREIGN KEY (movie_id)
  REFERENCES movies (id) MATCH SIMPLE
  ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT fk418ff41ba23a504c FOREIGN KEY (comment_id)
  REFERENCES comments (id) MATCH SIMPLE
  ON UPDATE NO ACTION ON DELETE NO ACTION
)
WITH (
  OIDS=FALSE
);
ALTER TABLE reviews
  OWNER TO paw;

------------------------------------------------------------------------

-- Table: movies_genres

-- DROP TABLE movies_genres;

CREATE TABLE movies_genres
(
  movies_id integer NOT NULL,
  name character varying(255) NOT NULL,
  CONSTRAINT movies_genres_pkey PRIMARY KEY (movies_id, name),
  CONSTRAINT fk37775bccb6daa239 FOREIGN KEY (movies_id)
  REFERENCES movies (id) MATCH SIMPLE
  ON UPDATE NO ACTION ON DELETE NO ACTION
)
WITH (
  OIDS=FALSE
);
ALTER TABLE movies_genres
	  OWNER TO paw;

