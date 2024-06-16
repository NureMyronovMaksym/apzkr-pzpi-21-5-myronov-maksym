CREATE TABLE public.packages (
    id SERIAL PRIMARY KEY,
    items_amount INT,
    status VARCHAR(50),
    release_date TIMESTAMP,
    user_id INT,
    location_id INT,
    volume INT,
    temperature INT,
    CONSTRAINT fk_user FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE SET NULL,
    CONSTRAINT fk_location FOREIGN KEY (location_id) REFERENCES locations(id) ON DELETE SET NULL
);