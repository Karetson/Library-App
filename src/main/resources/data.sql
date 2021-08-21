-- initializing roles
INSERT INTO roles VALUES
(1,"USER"),
(2, "ADMIN"),
(3, "BLOCKED");

--initializing roles to users
INSERT INTO user_role VALUES
(1, 1),
(2, 2);

-- initializing users
INSERT INTO users (password, username) VALUES
("$2a$12$e9eE40Tw/18mhoRoLIW0ieSRIVLy6BZDm93AsggASZazQnr/.0jPW", "user"),     -- haslo: user
("$2a$12$3DPQYfLWELIs3.gA9n7mA.NgMPPx475fLUdiAS1i/huXct961pKzS", "admin");    -- haslo: admin

-- initializing book categories
INSERT INTO categories (name) VALUES
("Comedy"),
("Fantasy"),
("Adventure"),
("Biographical"),
("Criminal"),
("Natural"),
("Historical"),
("Detective");

-- initializing books
INSERT INTO books (title, author, available) VALUES
("Harry Potter", "J.K Rowlong", true),
("Wladca Pierscieni", "Ktos tam", true),
("Piraci Z Karaibow", "ktos inny", true),
("Jas i Malgosia", "Krolewna sniezka", true),
("Pies ktory jezdzi koleja", "Dog", false);

-- initializing categories to books
INSERT INTO book_categories (book_id, category_id) VALUES
(1, 4),
(1, 3),
(2, 6),
(3, 2),
(4, 5),
(5, 2),
(5, 3),
(5, 7);