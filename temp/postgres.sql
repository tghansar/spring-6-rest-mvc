-- Drop the database if it exists
DO $$ BEGIN
   PERFORM pg_terminate_backend(pid)
   FROM pg_stat_activity
   WHERE datname = 'restdb';
EXECUTE 'DROP DATABASE IF EXISTS restdb';
END $$;

-- Drop the user if it exists
DO $$ BEGIN
   IF EXISTS (SELECT 1 FROM pg_roles WHERE rolname = 'restadmin') THEN
      EXECUTE 'DROP ROLE restadmin';
END IF;
END $$;

-- Create the database
CREATE DATABASE restdb
WITH
   ENCODING = 'UTF8'
   LC_COLLATE = 'en_US.UTF-8'
   LC_CTYPE = 'en_US.UTF-8'
   TEMPLATE template0;

-- Create the user
DO $$ BEGIN
   IF NOT EXISTS (SELECT 1 FROM pg_roles WHERE rolname = 'restadmin') THEN
      EXECUTE 'CREATE ROLE restadmin WITH LOGIN PASSWORD ''password''';
END IF;
END $$;

-- Grant permissions to the user
GRANT CONNECT ON DATABASE restdb TO restadmin;
GRANT SELECT, INSERT, UPDATE, DELETE, CREATE, DROP, REFERENCES, TRIGGER
      ON ALL TABLES IN SCHEMA public TO restadmin;
ALTER DEFAULT PRIVILEGES IN SCHEMA public
GRANT SELECT, INSERT, UPDATE, DELETE, CREATE, DROP, REFERENCES, TRIGGER ON TABLES TO restadmin;


-- ALTER TABLE beer ALTER COLUMN id SET DATA TYPE UUID USING id::uuid;
-- ALTER TABLE customer ALTER COLUMN id SET DATA TYPE UUID USING id::uuid;
