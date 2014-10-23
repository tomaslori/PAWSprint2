-- Table: users

-- DROP TABLE users;

CREATE TABLE users
(
  id serial NOT NULL,
  name character varying(32) NOT NULL,
  surname character varying(32) NOT NULL,
  password character varying(16) NOT NULL,
  email character varying(64) NOT NULL,
  secretquestion character varying(64) NOT NULL,
  secretanswer character varying(64) NOT NULL,
  birthdate date NOT NULL,
  vip boolean NOT NULL,
  CONSTRAINT users_pkey PRIMARY KEY (id),
  CONSTRAINT users_email_key UNIQUE (email),
  CONSTRAINT users_password_check CHECK (char_length(password::text) >= 8 AND char_length(password::text) <= 16)
)
WITH (
  OIDS=FALSE
);
ALTER TABLE users
  OWNER TO postgres;

-------------------------------------------------------------------------------------

-- Table: movies

-- DROP TABLE movies;

CREATE TABLE movies
(
  id serial NOT NULL,
  name character varying(64) NOT NULL,
  genre character varying(64) NOT NULL,
  director character varying(64) NOT NULL,
  duration integer NOT NULL,
  description character varying(256) NOT NULL,
  releasedate date NOT NULL,
  CONSTRAINT movies_pkey PRIMARY KEY (id),
  CONSTRAINT movies_name_key UNIQUE (name)
)
WITH (
  OIDS=FALSE
);
ALTER TABLE movies
  OWNER TO postgres;

-------------------------------------------------------------------------------------

-- Table: comments

-- DROP TABLE comments;

CREATE TABLE comments
(
  "user" serial NOT NULL,
  movie serial NOT NULL,
  rating integer NOT NULL,
  description character varying(256) NOT NULL,
  id serial NOT NULL,
  CONSTRAINT pk_id PRIMARY KEY (id),
  CONSTRAINT fk_movie FOREIGN KEY (movie)
      REFERENCES movies (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE CASCADE,
  CONSTRAINT fk_user FOREIGN KEY ("user")
      REFERENCES users (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE CASCADE,
  CONSTRAINT rating_range CHECK (rating >= 0 AND rating <= 5)
)
WITH (
  OIDS=FALSE
);
ALTER TABLE comments
  OWNER TO postgres;