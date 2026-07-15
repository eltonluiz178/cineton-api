-- ====================================================================================================
-- Migration : V4__Create_constraints_and_indexes.sql
-- Projeto   : Cineton
-- Objetivo  : Criação das Foreign Keys, Constraints e Índices.
-- ====================================================================================================

-- =====================================================================================
-- FOREIGN KEYS
-- =====================================================================================

-- Seats
ALTER TABLE seats
    ADD CONSTRAINT fk_seats_room
        FOREIGN KEY (room_id)
            REFERENCES rooms(id)
            ON DELETE RESTRICT;


-- Sessions
ALTER TABLE sessions
    ADD CONSTRAINT fk_sessions_film
        FOREIGN KEY (film_id)
            REFERENCES films(id)
            ON DELETE RESTRICT;

ALTER TABLE sessions
    ADD CONSTRAINT fk_sessions_room
        FOREIGN KEY (room_id)
            REFERENCES rooms(id)
            ON DELETE RESTRICT;


-- Film Genres
ALTER TABLE film_genres
    ADD CONSTRAINT fk_film_genres_film
        FOREIGN KEY (film_id)
            REFERENCES films(id)
            ON DELETE CASCADE;

ALTER TABLE film_genres
    ADD CONSTRAINT fk_film_genres_genre
        FOREIGN KEY (genre_id)
            REFERENCES genres(id)
            ON DELETE CASCADE;


-- Session Seats
ALTER TABLE session_seats
    ADD CONSTRAINT fk_session_seats_session
        FOREIGN KEY (session_id)
            REFERENCES sessions(id)
            ON DELETE CASCADE;

ALTER TABLE session_seats
    ADD CONSTRAINT fk_session_seats_seat
        FOREIGN KEY (seat_id)
            REFERENCES seats(id)
            ON DELETE RESTRICT;


-- Reservations
ALTER TABLE reservations
    ADD CONSTRAINT fk_reservations_user
        FOREIGN KEY (user_id)
            REFERENCES users(id)
            ON DELETE RESTRICT;


-- Reservation Seats
ALTER TABLE reservation_seats
    ADD CONSTRAINT fk_reservation_seats_reservation
        FOREIGN KEY (reservation_id)
            REFERENCES reservations(id)
            ON DELETE CASCADE;

ALTER TABLE reservation_seats
    ADD CONSTRAINT fk_reservation_seats_session_seat
        FOREIGN KEY (session_seat_id)
            REFERENCES session_seats(id)
            ON DELETE RESTRICT;


-- Payments
ALTER TABLE payments
    ADD CONSTRAINT fk_payments_reservation
        FOREIGN KEY (reservation_id)
            REFERENCES reservations(id)
            ON DELETE CASCADE;


-- Email Confirmations
ALTER TABLE email_confirmations
    ADD CONSTRAINT fk_email_confirmations_user
        FOREIGN KEY (user_id)
            REFERENCES users(id)
            ON DELETE CASCADE;

-- =====================================================================================
-- UNIQUE CONSTRAINTS
-- =====================================================================================

-- Users
ALTER TABLE users
    ADD CONSTRAINT uq_users_email
        UNIQUE (email);


-- Genres
ALTER TABLE genres
    ADD CONSTRAINT uq_genres_name
        UNIQUE (name);


-- Rooms
ALTER TABLE rooms
    ADD CONSTRAINT uq_rooms_name
        UNIQUE (name);


-- Seats
ALTER TABLE seats
    ADD CONSTRAINT uq_seats_room_code
        UNIQUE (room_id, code);


-- Session Seats
ALTER TABLE session_seats
    ADD CONSTRAINT uq_session_seat
        UNIQUE (session_id, seat_id);


-- Reservations
ALTER TABLE reservations
    ADD CONSTRAINT uq_reservation_code
        UNIQUE (reservation_code);


-- Reservation Seats
ALTER TABLE reservation_seats
    ADD CONSTRAINT uq_reservation_seat
        UNIQUE (reservation_id, session_seat_id);


-- Payments
ALTER TABLE payments
    ADD CONSTRAINT uq_payment_reservation
        UNIQUE (reservation_id);


-- =====================================================================================
-- INDEXES
-- =====================================================================================

-- Users
CREATE INDEX idx_users_role
    ON users(role);


-- Films
CREATE INDEX idx_films_status
    ON films(status);

CREATE INDEX idx_films_release_date
    ON films(release_date);


-- Sessions
CREATE INDEX idx_sessions_film
    ON sessions(film_id);

CREATE INDEX idx_sessions_room
    ON sessions(room_id);

CREATE INDEX idx_sessions_status
    ON sessions(status);

CREATE INDEX idx_sessions_start
    ON sessions(starts_at);


-- Seats
CREATE INDEX idx_seats_room
    ON seats(room_id);


-- Session Seats
CREATE INDEX idx_session_seats_session
    ON session_seats(session_id);

CREATE INDEX idx_session_seats_seat
    ON session_seats(seat_id);

CREATE INDEX idx_session_seats_status
    ON session_seats(status);


-- Reservations
CREATE INDEX idx_reservations_user
    ON reservations(user_id);

CREATE INDEX idx_reservations_status
    ON reservations(status);

CREATE INDEX idx_reservations_reserved_at
    ON reservations(reserved_at);

CREATE INDEX idx_reservations_expires_at
    ON reservations(expires_at);


-- Reservation Seats
CREATE INDEX idx_reservation_seats_reservation
    ON reservation_seats(reservation_id);

CREATE INDEX idx_reservation_seats_session_seat
    ON reservation_seats(session_seat_id);

CREATE INDEX idx_reservation_seats_ticket_type
    ON reservation_seats(ticket_type);


-- Payments
CREATE INDEX idx_payments_status
    ON payments(status);

CREATE INDEX idx_payments_paid_at
    ON payments(paid_at);


-- Film Genres
CREATE INDEX idx_film_genres_film
    ON film_genres(film_id);

CREATE INDEX idx_film_genres_genre
    ON film_genres(genre_id);