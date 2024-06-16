CREATE TABLE IF NOT EXISTS public.locations (
    id SERIAL PRIMARY KEY,
    place VARCHAR(100) NOT NULL,
    time_arrival TIMESTAMP,
    time_departure TIMESTAMP
);