CREATE KEYSPACE IF NOT EXISTS defaultKeyspace WITH replication = {'class':'SimpleStrategy', 'replication_factor' : 1};

USE defaultKeyspace;

CREATE TABLE IF NOT EXISTS defaultKeyspace.url_entity
(
    id      uuid,
    longUrl text,
    PRIMARY KEY ( (longUrl), id )
);
