-- initializing roles
INSERT INTO roles VALUES
(1,"USER"),
(2, "ADMIN");

--initializing roles to users
INSERT INTO user_role VALUES
(1, 1),
(2, 2);

-- initializing users
INSERT INTO users (password, username) VALUES
("$2a$12$e9eE40Tw/18mhoRoLIW0ieSRIVLy6BZDm93AsggASZazQnr/.0jPW", "user"),     -- haslo: user
("$2a$12$3DPQYfLWELIs3.gA9n7mA.NgMPPx475fLUdiAS1i/huXct961pKzS", "admin");    -- haslo: admin

-- initializing books
INSERT INTO books (name) VALUES
("Haryy Potter"),
("Wladca Pierscieni"),
("Piraci Z Karaibow"),
("Jas i Malgosia"),
("Pies ktory jezdzi koleja");