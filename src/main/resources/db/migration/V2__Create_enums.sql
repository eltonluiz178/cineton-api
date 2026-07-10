-- ====================================================================================================
-- Migration : V2__Create_enums.sql
-- Projeto   : Cineton
-- Autor     : Elton Luiz Alves da Silva
--
-- Objetivo:
--     Criação de todos os ENUMs utilizados pela aplicação.
--
-- Dependências:
--     V1__Create_extensions.sql
-- ====================================================================================================

-- =====================================================================================
-- FILM STATUS
-- =====================================================================================

CREATE TYPE film_status AS ENUM (
    'COMING_SOON',
    'NOW_SHOWING',
    'ENDED'
);

COMMENT ON TYPE film_status IS
'Status atual de exibição do filme.';


-- =====================================================================================
-- USER ROLE
-- =====================================================================================

CREATE TYPE user_role AS ENUM (
    'ADMIN',
    'EMPLOYEE',
    'CUSTOMER'
);

COMMENT ON TYPE user_role IS
'Define o perfil de acesso do usuário na aplicação.';


-- =====================================================================================
-- SESSION STATUS
-- =====================================================================================

CREATE TYPE session_status AS ENUM (
    'SCHEDULED',
    'OPEN',
    'IN_PROGRESS',
    'FINISHED',
    'CANCELED'
);

COMMENT ON TYPE session_status IS
'Representa o estado operacional de uma sessão.';


-- =====================================================================================
-- SESSION SEAT STATUS
-- =====================================================================================

CREATE TYPE session_seat_status AS ENUM (
    'AVAILABLE',
    'RESERVED',
    'OCCUPIED',
    'BLOCKED'
);

COMMENT ON TYPE session_seat_status IS
'Disponibilidade de um assento para determinada sessão.';


-- =====================================================================================
-- RESERVATION STATUS
-- =====================================================================================

CREATE TYPE reservation_status AS ENUM (
    'PENDING_PAYMENT',
    'CONFIRMED',
    'CANCELED',
    'EXPIRED'
);

COMMENT ON TYPE reservation_status IS
'Estado atual da reserva realizada pelo cliente.';


-- =====================================================================================
-- PAYMENT STATUS
-- =====================================================================================

CREATE TYPE payment_status AS ENUM (
    'PENDING',
    'APPROVED',
    'REFUSED',
    'REFUNDED'
);

COMMENT ON TYPE payment_status IS
'Status do processamento do pagamento.';


-- =====================================================================================
-- PAYMENT METHOD
-- =====================================================================================

CREATE TYPE payment_method AS ENUM (
    'PIX',
    'CREDIT_CARD',
    'DEBIT_CARD',
    'CASH'
);

COMMENT ON TYPE payment_method IS
'Métodos de pagamento suportados pelo sistema.';


-- =====================================================================================
-- TICKET TYPE
-- =====================================================================================

CREATE TYPE ticket_type AS ENUM (
    'NORMAL',
    'HALF',
    'FREE'
);

COMMENT ON TYPE ticket_type IS
'Tipos de ingresso do sistema.';