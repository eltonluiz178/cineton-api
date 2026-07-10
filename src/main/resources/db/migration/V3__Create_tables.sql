-- ====================================================================================================
-- Migration : V3__Create_tables.sql (Parte 1)
-- Projeto   : Cineton
-- Objetivo  : Criação das tabelas base do sistema.
-- ====================================================================================================

-- =====================================================================================
-- USERS
-- =====================================================================================

CREATE TABLE users (
                       id UUID PRIMARY KEY DEFAULT gen_random_uuid(),

                       name VARCHAR(100) NOT NULL,

                       email VARCHAR(255) NOT NULL,

                       password VARCHAR(255) NOT NULL,

                       role user_role NOT NULL DEFAULT 'CUSTOMER',

                       created_at TIMESTAMPTZ NOT NULL DEFAULT NOW(),
                       updated_at TIMESTAMPTZ NOT NULL DEFAULT NOW()
);

COMMENT ON TABLE users IS
'Usuários da plataforma (clientes, funcionários e administradores).';

COMMENT ON COLUMN users.id IS 'Identificador único.';
COMMENT ON COLUMN users.name IS 'Nome.';
COMMENT ON COLUMN users.email IS 'E-mail utilizado para login.';
COMMENT ON COLUMN users.password IS 'Senha criptografada (BCrypt).';
COMMENT ON COLUMN users.role IS 'Perfil de acesso.';
COMMENT ON COLUMN users.created_at IS 'Data de criação.';
COMMENT ON COLUMN users.updated_at IS 'Última atualização.';



-- =====================================================================================
-- GENRES
-- =====================================================================================

CREATE TABLE genres (

                        id UUID PRIMARY KEY DEFAULT gen_random_uuid(),

                        name VARCHAR(100) NOT NULL,

                        description TEXT,

                        created_at TIMESTAMPTZ NOT NULL DEFAULT NOW(),
                        updated_at TIMESTAMPTZ NOT NULL DEFAULT NOW()

);

COMMENT ON TABLE genres IS
'Categorias dos filmes.';

COMMENT ON COLUMN genres.id IS 'Identificador único.';
COMMENT ON COLUMN genres.name IS 'Nome do gênero.';
COMMENT ON COLUMN genres.description IS 'Descrição do gênero.';
COMMENT ON COLUMN genres.created_at IS 'Data de criação.';
COMMENT ON COLUMN genres.updated_at IS 'Última atualização.';



-- =====================================================================================
-- ROOMS
-- =====================================================================================

CREATE TABLE rooms (

                       id UUID PRIMARY KEY DEFAULT gen_random_uuid(),

                       name VARCHAR(100) NOT NULL,

                       capacity INTEGER NOT NULL,

                       created_at TIMESTAMPTZ NOT NULL DEFAULT NOW(),
                       updated_at TIMESTAMPTZ NOT NULL DEFAULT NOW(),

                       CONSTRAINT chk_room_capacity
                           CHECK (capacity > 0)

);

COMMENT ON TABLE rooms IS
'Salas do cinema.';

COMMENT ON COLUMN rooms.id IS 'Identificador único.';
COMMENT ON COLUMN rooms.name IS 'Nome da sala.';
COMMENT ON COLUMN rooms.capacity IS 'Capacidade máxima.';
COMMENT ON COLUMN rooms.created_at IS 'Data de criação.';
COMMENT ON COLUMN rooms.updated_at IS 'Última atualização.';



-- =====================================================================================
-- FILMS
-- =====================================================================================

CREATE TABLE films (

                       id UUID PRIMARY KEY DEFAULT gen_random_uuid(),

                       title VARCHAR(255) NOT NULL,

                       synopsis TEXT,

                       duration_minutes INTEGER NOT NULL,

                       age_rating VARCHAR(10),

                       release_date DATE,

                       poster_url TEXT,

                       trailer_url TEXT,

                       status film_status NOT NULL DEFAULT 'COMING_SOON',

                       created_at TIMESTAMPTZ NOT NULL DEFAULT NOW(),
                       updated_at TIMESTAMPTZ NOT NULL DEFAULT NOW(),

                       CONSTRAINT chk_film_duration
                           CHECK (duration_minutes > 0)

);

COMMENT ON TABLE films IS
'Filmes cadastrados na plataforma.';

COMMENT ON COLUMN films.id IS 'Identificador único.';
COMMENT ON COLUMN films.title IS 'Título do filme.';
COMMENT ON COLUMN films.synopsis IS 'Sinopse.';
COMMENT ON COLUMN films.duration_minutes IS 'Duração em minutos.';
COMMENT ON COLUMN films.age_rating IS 'Classificação indicativa.';
COMMENT ON COLUMN films.release_date IS 'Data de lançamento.';
COMMENT ON COLUMN films.poster_url IS 'URL do pôster armazenado no MinIO.';
COMMENT ON COLUMN films.trailer_url IS 'URL do trailer.';
COMMENT ON COLUMN films.status IS 'Status de exibição.';
COMMENT ON COLUMN films.created_at IS 'Data de criação.';
COMMENT ON COLUMN films.updated_at IS 'Última atualização.';

-- =====================================================================================
-- SEATS
-- =====================================================================================

CREATE TABLE seats (

                       id UUID PRIMARY KEY DEFAULT gen_random_uuid(),

                       room_id UUID NOT NULL,

                       code VARCHAR(10) NOT NULL,

                       row VARCHAR(5) NOT NULL,

                       seat_number INTEGER NOT NULL,

                       created_at TIMESTAMPTZ NOT NULL DEFAULT NOW(),
                       updated_at TIMESTAMPTZ NOT NULL DEFAULT NOW(),

                       CONSTRAINT chk_seat_number
                           CHECK (seat_number > 0)

);

COMMENT ON TABLE seats IS
'Assentos físicos pertencentes às salas do cinema.';

COMMENT ON COLUMN seats.id IS 'Identificador único.';
COMMENT ON COLUMN seats.room_id IS 'Sala à qual o assento pertence.';
COMMENT ON COLUMN seats.code IS 'Código identificador do assento (Ex: A01).';
COMMENT ON COLUMN seats.row IS 'Fila do assento.';
COMMENT ON COLUMN seats.seat_number IS 'Número do assento.';
COMMENT ON COLUMN seats.created_at IS 'Data de criação.';
COMMENT ON COLUMN seats.updated_at IS 'Última atualização.';



-- =====================================================================================
-- SESSIONS
-- =====================================================================================

CREATE TABLE sessions (

                          id UUID PRIMARY KEY DEFAULT gen_random_uuid(),

                          film_id UUID NOT NULL,

                          room_id UUID NOT NULL,

                          starts_at TIMESTAMPTZ NOT NULL,

                          ends_at TIMESTAMPTZ NOT NULL,

                          base_price NUMERIC(10,2) NOT NULL,

                          status session_status NOT NULL DEFAULT 'SCHEDULED',

                          created_at TIMESTAMPTZ NOT NULL DEFAULT NOW(),
                          updated_at TIMESTAMPTZ NOT NULL DEFAULT NOW(),

                          CONSTRAINT chk_session_price
                              CHECK (base_price >= 0),

                          CONSTRAINT chk_session_dates
                              CHECK (ends_at > starts_at)

);

COMMENT ON TABLE sessions IS
'Sessões de exibição dos filmes.';

COMMENT ON COLUMN sessions.id IS 'Identificador único.';
COMMENT ON COLUMN sessions.film_id IS 'Filme exibido.';
COMMENT ON COLUMN sessions.room_id IS 'Sala onde ocorrerá a sessão.';
COMMENT ON COLUMN sessions.starts_at IS 'Data e hora de início.';
COMMENT ON COLUMN sessions.ends_at IS 'Data e hora de término.';
COMMENT ON COLUMN sessions.base_price IS 'Preço base do ingresso.';
COMMENT ON COLUMN sessions.status IS 'Status operacional da sessão.';
COMMENT ON COLUMN sessions.created_at IS 'Data de criação.';
COMMENT ON COLUMN sessions.updated_at IS 'Última atualização.';



-- =====================================================================================
-- FILM_GENRES
-- =====================================================================================

CREATE TABLE film_genres (

                             film_id UUID NOT NULL,

                             genre_id UUID NOT NULL,

                             created_at TIMESTAMPTZ NOT NULL DEFAULT NOW(),

                             PRIMARY KEY (
                                          film_id,
                                          genre_id
                             )

);

COMMENT ON TABLE film_genres IS
'Tabela de relacionamento N:N entre filmes e gêneros.';

COMMENT ON COLUMN film_genres.film_id IS
'Filme relacionado.';

COMMENT ON COLUMN film_genres.genre_id IS
'Gênero relacionado.';

-- =====================================================================================
-- SESSION_SEATS
-- =====================================================================================

CREATE TABLE session_seats (

                               id UUID PRIMARY KEY DEFAULT gen_random_uuid(),

                               session_id UUID NOT NULL,

                               seat_id UUID NOT NULL,

                               status session_seat_status NOT NULL DEFAULT 'AVAILABLE',

                               created_at TIMESTAMPTZ NOT NULL DEFAULT NOW(),
                               updated_at TIMESTAMPTZ NOT NULL DEFAULT NOW()
);

COMMENT ON TABLE session_seats IS
'Representa um assento disponível para uma sessão específica.';

COMMENT ON COLUMN session_seats.id IS 'Identificador único.';
COMMENT ON COLUMN session_seats.session_id IS 'Sessão à qual o assento pertence.';
COMMENT ON COLUMN session_seats.seat_id IS 'Assento físico.';
COMMENT ON COLUMN session_seats.status IS 'Status do assento durante a sessão.';
COMMENT ON COLUMN session_seats.created_at IS 'Data de criação.';
COMMENT ON COLUMN session_seats.updated_at IS 'Última atualização.';



-- =====================================================================================
-- RESERVATIONS
-- =====================================================================================

CREATE TABLE reservations (

                              id UUID PRIMARY KEY DEFAULT gen_random_uuid(),

                              user_id UUID NOT NULL,

                              reservation_code VARCHAR(20) NOT NULL,

                              total NUMERIC(10,2) NOT NULL DEFAULT 0,

                              status reservation_status NOT NULL DEFAULT 'PENDING_PAYMENT',

                              reserved_at TIMESTAMPTZ NOT NULL DEFAULT NOW(),
                              expires_at TIMESTAMPTZ,
                              created_at TIMESTAMPTZ NOT NULL DEFAULT NOW(),
                              updated_at TIMESTAMPTZ NOT NULL DEFAULT NOW(),

                              CONSTRAINT chk_reservation_total
                                  CHECK (total >= 0)
);

COMMENT ON TABLE reservations IS
'Reservas realizadas pelos clientes.';

COMMENT ON COLUMN reservations.id IS 'Identificador único.';
COMMENT ON COLUMN reservations.user_id IS 'Cliente que realizou a reserva.';
COMMENT ON COLUMN reservations.reservation_code IS 'Código público da reserva.';
COMMENT ON COLUMN reservations.reserved_at IS 'Momento em que a reserva foi criada.';
COMMENT ON COLUMN reservations.expires_at IS 'Data de expiração da reserva.';
COMMENT ON COLUMN reservations.status IS 'Status da reserva.';
COMMENT ON COLUMN reservations.created_at IS 'Data de criação.';
COMMENT ON COLUMN reservations.updated_at IS 'Última atualização.';



-- =====================================================================================
-- RESERVATION SEATS
-- =====================================================================================

CREATE TABLE reservation_seats (
                                   id                UUID PRIMARY KEY DEFAULT gen_random_uuid(),

                                   reservation_id    UUID NOT NULL,

                                   session_seat_id   UUID NOT NULL,

                                   ticket_type       ticket_type NOT NULL DEFAULT 'NORMAL',

                                   unit_price        NUMERIC(10,2) NOT NULL,

                                   created_at        TIMESTAMPTZ NOT NULL DEFAULT NOW(),

                                   CONSTRAINT chk_reservation_seat_price
                                       CHECK (unit_price >= 0)
);

COMMENT ON TABLE reservation_seats IS
'Assentos vinculados a uma reserva, com tipo de ingresso e preço unitário.';

COMMENT ON COLUMN reservation_seats.id IS 'Identificador único.';
COMMENT ON COLUMN reservation_seats.reservation_id IS 'Reserva à qual o assento pertence.';
COMMENT ON COLUMN reservation_seats.session_seat_id IS 'Assento da sessão reservado.';
COMMENT ON COLUMN reservation_seats.ticket_type IS 'Tipo de ingresso: NORMAL, HALF ou FREE.';
COMMENT ON COLUMN reservation_seats.unit_price IS 'Preço unitário calculado no momento da reserva.';
COMMENT ON COLUMN reservation_seats.created_at IS 'Data de criação.';



-- =====================================================================================
-- PAYMENTS
-- =====================================================================================

CREATE TABLE payments (

                          id UUID PRIMARY KEY DEFAULT gen_random_uuid(),

                          reservation_id UUID NOT NULL,

                          amount NUMERIC(10,2) NOT NULL,

                          payment_method payment_method NOT NULL,

                          transaction_id VARCHAR(255),

                          paid_at TIMESTAMPTZ,

                          status payment_status NOT NULL DEFAULT 'PENDING',

                          created_at TIMESTAMPTZ NOT NULL DEFAULT NOW(),
                          updated_at TIMESTAMPTZ NOT NULL DEFAULT NOW(),

                          CONSTRAINT chk_payment_amount
                              CHECK (amount >= 0)

);

COMMENT ON TABLE payments IS
'Pagamentos associados às reservas.';

COMMENT ON COLUMN payments.id IS 'Identificador único.';
COMMENT ON COLUMN payments.reservation_id IS 'Reserva relacionada.';
COMMENT ON COLUMN payments.amount IS 'Valor pago.';
COMMENT ON COLUMN payments.payment_method IS 'Método de pagamento.';
COMMENT ON COLUMN payments.transaction_id IS 'Identificador retornado pelo gateway.';
COMMENT ON COLUMN payments.paid_at IS 'Momento da confirmação do pagamento.';
COMMENT ON COLUMN payments.status IS 'Status do pagamento.';
COMMENT ON COLUMN payments.created_at IS 'Data de criação.';
COMMENT ON COLUMN payments.updated_at IS 'Última atualização.';
