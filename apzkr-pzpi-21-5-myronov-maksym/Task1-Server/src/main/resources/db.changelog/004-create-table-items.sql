CREATE TABLE IF NOT EXISTS public.items (
    id SERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    type VARCHAR(50),
    color VARCHAR(50),
    brand VARCHAR(50),
    image VARCHAR(255),
    package_id INT,
    is_sold BOOLEAN DEFAULT FALSE,
    CONSTRAINT fk_package FOREIGN KEY (package_id) REFERENCES packages(id) ON DELETE SET NULL
);
