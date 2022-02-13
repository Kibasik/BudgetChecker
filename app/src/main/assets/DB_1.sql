CREATE TABLE "users" (
	"user_id"	INTEGER NOT NULL UNIQUE PRIMARY KEY,
	"user_login"	TEXT NOT NULL,
	"user_pass"	TEXT NOT NULL
);