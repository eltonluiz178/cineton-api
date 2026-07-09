-- =====================================================================
-- Migration: V1__Create_extensions.sql
-- Projeto  : Cineton
-- Autor    : Elton Luiz Alves da Silva
-- Objetivo : Habilitar extensões utilizadas pela aplicação.
-- =====================================================================

CREATE EXTENSION IF NOT EXISTS pgcrypto;

COMMENT ON EXTENSION pgcrypto IS
'Fornece funções criptográficas e geração de UUID através de gen_random_uuid().';