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
    status,
    created_at,
    updated_at
)
VALUES (
           gen_random_uuid(),
           'Administrator',
           'admin@cineton.com',
           '$2a$10$TAMnfE8tI4gLuxZRSCK3xu1i3DZB6ZY9aWks/i.w5F6eiRn9kO/7e',
           'ADMIN',
           'ACTIVE',
           NOW(),
           NOW()
       )
    ON CONFLICT (email)
DO NOTHING;