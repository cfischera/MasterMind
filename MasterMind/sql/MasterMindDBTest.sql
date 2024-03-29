DROP DATABASE IF EXISTS MasterMindDBTest;
CREATE DATABASE MasterMindDBTest;

USE MasterMindDBTest;

DROP TABLE IF EXISTS Game;
CREATE TABLE Game (
	GameID INT PRIMARY KEY AUTO_INCREMENT,
    Answer CHAR(4),
    IsFinished BOOL
);

DROP TABLE IF EXISTS Round;
CREATE TABLE Round (
	RoundID INT PRIMARY KEY AUTO_INCREMENT,
    UserGuess CHAR(4),
    GuessTime TIMESTAMP,
    Result CHAR(7),
    GameID INT,
    FOREIGN KEY fk_game (GameID) REFERENCES Game(GameID)
);