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
         )
ON CONFLICT (dish_id) DO NOTHING;

CREATE TABLE IF NOT EXISTS kdg_restaurant.restaurant_workload (
                                                                  restaurant_id UUID PRIMARY KEY,
                                                                  pending_orders INT NOT NULL
);

CREATE SCHEMA IF NOT EXISTS kdg_order;

CREATE TABLE IF NOT EXISTS kdg_order.orders (
                                                order_id UUID PRIMARY KEY,
                                                restaurant_id UUID NOT NULL,
                                                name VARCHAR(255) NOT NULL,
                                                email VARCHAR(255) NOT NULL,
                                                street VARCHAR(255),
                                                number VARCHAR(50),
                                                postal_code VARCHAR(50),
                                                city VARCHAR(255),
                                                country VARCHAR(255),
                                                total_price FLOAT NOT NULL,
                                                status VARCHAR(32) NOT NULL,
                                                payment_id UUID NOT NULL,
                                                provider VARCHAR(100) NOT NULL,
                                                payment_status VARCHAR(32) NOT NULL
);

CREATE TABLE IF NOT EXISTS kdg_order.order_lines (
                                                     order_id UUID NOT NULL REFERENCES kdg_order.orders(order_id),
                                                     dish_id UUID NOT NULL,              -- snake_case
                                                     quantity INT NOT NULL,
                                                     price_at_checkout NUMERIC(10,2) NOT NULL -- snake_case
);


CREATE TABLE IF NOT EXISTS kdg_restaurant.owners (
                                                     id UUID PRIMARY KEY,
                                                     email VARCHAR(255) UNIQUE NOT NULL,
                                                     password VARCHAR(255) NOT NULL,
                                                     name VARCHAR(255) NOT NULL
);

CREATE TABLE IF NOT EXISTS kdg_restaurant.restaurants (
                                                          restaurant_id UUID PRIMARY KEY,
                                                          owner_id UUID NOT NULL REFERENCES kdg_restaurant.owners(id),
                                                          name VARCHAR(255) NOT NULL,
                                                          contact_email VARCHAR(255),
                                                          picture_url TEXT,
                                                          type_of_cuisine VARCHAR(100),
                                                          default_preparation_time INT NOT NULL
);

ALTER TABLE kdg_restaurant.dishes
    DROP COLUMN food_tags;

ALTER TABLE kdg_restaurant.dishes
    ADD COLUMN food_tags VARCHAR(255) DEFAULT '';

INSERT INTO kdg_restaurant.dishes (
    dish_id, restaurant_id, name, type, description, price, picture_url,
    availability, stock_status, food_tags
) VALUES (
             'ffffffff-1111-2222-3333-444444444445',
             '396591b9-4b97-4354-a8b1-0e15372bc09d',
             'Truffle risotto draft dish',
             'MAIN',
             'Creamy risotto with black truffle essence.',
             24.50,
             'https://cheframsayrecipes.com/wp-content/uploads/2025/01/1-90.png',
             'PUBLISHED',
             'IN_STOCK',
             '{GLUTEN_FREE}'
         );

INSERT INTO kdg_restaurant.dishes (
    dish_id, restaurant_id, name, type, description, price, picture_url,
    availability, stock_status, food_tags
) VALUES
      ('ffffffff-1111-2222-3333-444444444446',
       '396591b9-4b97-4354-a8b1-0e15372bc09d',
       'Seared Salmon Fillet',
       'MAIN',
       'Pan-seared salmon with lemon butter sauce.',
       18.90,
       'https://www.dinneratthezoo.com/wp-content/uploads/2019/04/pan-seared-salmon-3.jpg',
       'PUBLISHED',
       'IN_STOCK',
       'LACTOSE'),

      ('ffffffff-1111-2222-3333-444444444447',
       '396591b9-4b97-4354-a8b1-0e15372bc09d',
       'Avocado Tartare',
       'STARTER',
       'Fresh avocado with tomato and herbs.',
       9.50,
       'https://heatherchristo.com/wp-content/uploads/2017/08/Salmon-Beet-and-Avocado-Tartar-with-Lemon-Vinaigrette-from-HeatherChristo.com_.jpg',
       'PUBLISHED',
       'IN_STOCK',
       'VEGAN'),

      ('ffffffff-1111-2222-3333-444444444448',
       '396591b9-4b97-4354-a8b1-0e15372bc09d',
       'Chocolate Lava Cake',
       'DESSERT',
       'Warm chocolate cake with molten center.',
       7.80,
       'https://www.tasteofhome.com/wp-content/uploads/2018/01/Lava-Chocolate-Cakes_EXPS_BOBMZ22_43967_E10_06_1b_v2-12.jpg',
       'PUBLISHED',
       'IN_STOCK',
       'NUTS'),
      ('ffffffff-1111-2222-3333-444444444413',
       '396591b9-4b97-4354-a8b1-0e15372bc09d',
       'Burger',
       'MAIN',
       'Pan-seared salmon with lemon butter sauce.',
       18.90,
       'https://recipes.net/wp-content/uploads/2021/10/the-best-grilled-bbq-burger-recipe.jpg',
       'PUBLISHED',
       'IN_STOCK',
       'LACTOSE'),

      ('ffffffff-1111-2222-3333-444444444465',
       '396591b9-4b97-4354-a8b1-0e15372bc09d',
       'Pasta Pesto',
       'STARTER',
       'Fresh avocado with tomato and herbs.',
       9.50,
       'https://vancouverwithlove.com/wp-content/uploads/2023/08/pasta-al-pesto-24-1.jpg',
       'PUBLISHED',
       'IN_STOCK',
       'VEGAN'),

      ('ffffffff-1111-2222-3333-444444444498',
       '396591b9-4b97-4354-a8b1-0e15372bc09d',
       'Tiramisu',
       'DESSERT',
       'Warm chocolate cake with molten center.',
       7.80,
       'https://trinkreif.de/wp-content/uploads/Tiramisu-Rezept-Klassisch-Italienisch.jpg',
       'PUBLISHED',
       'IN_STOCK',
       'NUTS'),
      ('ffffffff-1111-2222-3333-444444444438',
       '396591b9-4b97-4354-a8b1-0e15372bc09d',
       'Salad',
       'MAIN',
       'Pan-seared salmon with lemon butter sauce.',
       18.90,
       'https://www.allrecipes.com/thmb/mXZ0Tulwn3x9_YB_ZbkiTveDYFE=/1500x0/filters:no_upscale():max_bytes(150000):strip_icc()/229063-Classic-Restaurant-Caesar-Salad-ddmfs-4x3-231-89bafa5e54dd4a8c933cf2a5f9f12a6f.jpg',
       'PUBLISHED',
       'IN_STOCK',
       'LACTOSE'),

      ('ffffffff-1111-2222-3333-444444444439',
       '396591b9-4b97-4354-a8b1-0e15372bc09d',
       'Cheese Plate',
       'STARTER',
       'Fresh avocado with tomato and herbs.',
       9.50,
       'https://www.modernhoney.com/wp-content/uploads/2018/12/Charcuterie-Board-Meat-and-Cheese-Platter-10.jpg',
       'PUBLISHED',
       'IN_STOCK',
       'VEGAN'),
      ('ffffffff-1111-2222-3333-444444444234',
       '396591b9-4b97-4354-a8b1-0e15372bc09d',
       'Fries',
       'STARTER',
       'Fresh avocado with tomato and herbs.',
       9.50,
       'https://www.recipesfromeurope.com/wp-content/uploads/2022/11/recipe-greek-fries-new.jpg',
       'PUBLISHED',
       'IN_STOCK',
       'VEGAN')
;
INSERT INTO kdg_restaurant.dishes (
    dish_id, restaurant_id, name, type, description, price, picture_url,
    availability, stock_status, food_tags
) VALUES (
             'ffffffff-1111-2222-3333-444444449999',
             '396591b9-4b97-4354-a8b1-0e15372bc09d',
             'Grilled Octopus',
             'MAIN',
             'Tender grilled octopus with lemon aioli.',
             25.90,
             'https://example.com/octopus.jpg',
             'DRAFT',
             'IN_STOCK',
             'GLUTEN_FREE'
         );


DROP TABLE IF EXISTS kdg_order.order_lines;
DROP TABLE IF EXISTS kdg_order.orders;
DROP SCHEMA IF EXISTS kdg_order CASCADE;
DROP TABLE IF EXISTS kdg_restaurant.dishes;

