-- Step 1: Create the database
CREATE DATABASE vetmais;

-- Step 2: Use the newly created database
USE vetmais;

-- Step 3: Create the 'clients' table
CREATE TABLE clients (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    cpf CHAR(11) NOT NULL UNIQUE,
    email VARCHAR(100) NOT NULL UNIQUE,
    telephone VARCHAR(17) NOT NULL
);

CREATE TABLE animals (
    id INT AUTO_INCREMENT PRIMARY KEY,          -- ID único do animal
    name VARCHAR(100) NOT NULL,                 -- Nome do animal
    birth_date DATE NOT NULL,                   -- Data de nascimento do animal
    breed VARCHAR(100) NOT NULL,                -- Raça do animal
    cpf_proprietario VARCHAR(11) NOT NULL       -- CPF do proprietário (11 caracteres)
);

CREATE TABLE Consultas (
    id INT AUTO_INCREMENT PRIMARY KEY,        -- ID único para a consulta
    veterinario VARCHAR(100) NOT NULL,        -- Nome do veterinário
    data_agendada DATETIME NOT NULL,          -- Data e hora agendada para a consulta
    pet VARCHAR(100) NOT NULL,                -- Nome do pet
    proprietario VARCHAR(100) NOT NULL        -- Nome do proprietário do pet
);

CREATE TABLE users (
    id INT AUTO_INCREMENT PRIMARY KEY,  -- Identificador único para o usuário
    email VARCHAR(255) NOT NULL UNIQUE, -- E-mail do usuário (único)
    password VARCHAR(255) NOT NULL      -- Senha criptografada do usuário
);
