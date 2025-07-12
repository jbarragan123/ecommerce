-- Insertar productos
INSERT INTO product (active, description, name, price) VALUES
                                                           (TRUE, 'Camiseta deportiva', 'Camiseta', 35000.0),
                                                           (TRUE, 'Tenis running', 'Tenis', 120000.0),
                                                           (FALSE, 'Balón de fútbol', 'Balón', 80000.0),
                                                           (TRUE, 'Gorra ajustable', 'Gorra', 20000.0),
                                                           (TRUE, 'Guantes de ciclismo', 'Guantes', 25000.0),
                                                           (TRUE, 'Pantalón deportivo', 'Pantalón', 60000.0),
                                                           (TRUE, 'Protección UV ocular', 'Gafas de sol', 35000.0);

-- Insertar usuarios
INSERT INTO users (active, email, frequent_client, password, username) VALUES
                                                                           (TRUE, 'admin@test.com', TRUE, '$2a$10$bjZfivRECpmQMxR23oiaFexx9l.CAoe4CvSDEsJd5mp7DEOZm3cce', 'admin'),
                                                                           (TRUE, 'juan@mail.com', TRUE, '$2a$10$bjZfivRECpmQMxR23oiaFexx9l.CAoe4CvSDEsJd5mp7DEOZm3cce', 'juan'),
                                                                           (TRUE, 'ana@mail.com', TRUE, '$2a$10$bjZfivRECpmQMxR23oiaFexx9l.CAoe4CvSDEsJd5mp7DEOZm3cce', 'ana'),
                                                                           (TRUE, 'luis@mail.com', FALSE, '$2a$10$bjZfivRECpmQMxR23oiaFexx9l.CAoe4CvSDEsJd5mp7DEOZm3cce', 'luis'),
                                                                           (TRUE, 'maria@mail.com', TRUE, '$2a$10$bjZfivRECpmQMxR23oiaFexx9l.CAoe4CvSDEsJd5mp7DEOZm3cce', 'maria'),
                                                                           (TRUE, 'pedro@mail.com', FALSE, '$2a$10$bjZfivRECpmQMxR23oiaFexx9l.CAoe4CvSDEsJd5mp7DEOZm3cce', 'pedro');

-- Insertar inventario (asumiendo los IDs autogenerados de productos son 1 a 7 en orden)
INSERT INTO inventory (active, quantity, product_id) VALUES
                                                         (TRUE, 50, 1),
                                                         (TRUE, 30, 2),
                                                         (TRUE, 80, 4),
                                                         (TRUE, 40, 5),
                                                         (TRUE, 25, 7);

-- Insertar órdenes (asumiendo los IDs autogenerados de usuarios son 1 a 6 en orden)
INSERT INTO orders (active, discount_applied, order_date, total_amount, user_id) VALUES
                                                                                     (TRUE, 20000.0, CURRENT_TIMESTAMP, 380000.0, 2),
                                                                                     (TRUE, 6000.0, CURRENT_TIMESTAMP, 114000.0, 3),
                                                                                     (TRUE, 19250.0, CURRENT_TIMESTAMP, 15750.0, 2);

-- Insertar ítems de órdenes (asumiendo las órdenes autogeneran IDs desde 1)
INSERT INTO order_items (active, price, quantity, order_id, product_id) VALUES
                                                                            (TRUE, 20000.0, 2, 1, 4),
                                                                            (TRUE, 120000.0, 3, 1, 2),
                                                                            (TRUE, 120000.0, 1, 2, 2),
                                                                            (TRUE, 35000.0, 1, 3, 1);
