-- ====================================================================================================
-- Migration : V5__Seed_admin_user.sql
-- Projeto   : Cineton
-- Objetivo  : Criação do usuário administrador inicial.
-- ====================================================================================================

INSERT INTO users (
    id,
    name,
    email,
    password,
    role,
    created_at,
    updated_at
)
VALUES (
           gen_random_uuid(),
           'Administrator',
           'admin@cineton.com',
           '$2a$10$M9z9Hf8nQJ7w2x4v0QKq7.2X6cA5Y8xYx4n4XQ7M5tO3A1QK8r6kG',
           'ADMIN',
           NOW(),
           NOW()
       )
    ON CONFLICT (email)
DO NOTHING;