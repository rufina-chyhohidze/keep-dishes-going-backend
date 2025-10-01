-- schema: kdg_restaurant
CREATE SCHEMA IF NOT EXISTS kdg_restaurant;

CREATE TABLE IF NOT EXISTS kdg_restaurant.dishes (
                                                     dish_id UUID PRIMARY KEY,
                                                     restaurant_id UUID NOT NULL,
                                                     name VARCHAR(255) NOT NULL,
    type VARCHAR(32) NOT NULL,
    description TEXT,
    price NUMERIC(10,2) NOT NULL,
    picture_url TEXT,
    availability VARCHAR(32) NOT NULL,
    stock_status VARCHAR(32) NOT NULL,
    food_tags TEXT[] NOT NULL DEFAULT '{}'
    );

CREATE INDEX IF NOT EXISTS idx_dishes_restaurant
    ON kdg_restaurant.dishes(restaurant_id);


INSERT INTO kdg_restaurant.dishes (
    dish_id, restaurant_id, name, type, description, price, picture_url,
    availability, stock_status, food_tags
) VALUES (
             'ffffffff-1111-2222-3333-444444444444',
             'aaaaaaaa-bbbb-cccc-dddd-eeeeeeeeeeee',
             'Draft Dish',
             'MAIN',
             'Initial draft dish',
             12.50,
             'https://example.com/dish.jpg',
             'DRAFT',
             'IN_STOCK',
             ARRAY['VEGAN','GLUTEN_FREE']
         );
