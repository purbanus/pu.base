CREATE TABLE blob
(
  id BIGINT NOT NULL,
  blob bytea,
  clob text,
  CONSTRAINT pk_blob PRIMARY KEY (id)
--)
--WITH (
--  OIDS=FALSE
);
--ALTER TABLE imagedb
--  OWNER TO postgres;

