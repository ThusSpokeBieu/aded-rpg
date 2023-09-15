CREATE TABLE turn_of (
    turn_of varchar(255) PRIMARY KEY
);

INSERT INTO turn_of (turn_of) VALUES ('CONTENDER'), ('CONTESTED');

CREATE TABLE battle_turn(
    turn varchar(255) PRIMARY KEY
);

INSERT INTO battle_turn (turn) VALUES ('INITIATIVE'), ('ATTACK'), ('DEFENSE'), ('DAMAGE'), ('CLOSED');

CREATE TABLE battles (
    id uuid DEFAULT gen_random_uuid() PRIMARY KEY,
    code varchar(5) NOT NULL,
    contender varchar(255) NOT NULL,
    contested varchar(255) NOT NULL,
    contender_character_id uuid NOT NULL REFERENCES characters(id),
    contested_character_id uuid NOT NULL REFERENCES characters(id),
    contender_current_hp smallint NOT NULL,
    contested_current_hp smallint NOT NULL,
    round smallint NOT NULL DEFAULT 1,
    battle_turn varchar(255) REFERENCES battle_turn(turn),
    turn_of varchar(255) REFERENCES turn_of(turn_of),
    is_active bool NOT NULL,
    winner varchar(255) DEFAULT '---',
    started_at timestamptz DEFAULT current_timestamp,
    last_move_at timestamptz DEFAULT current_timestamp,
    ended_at timestamptz
);

CREATE INDEX battle_code ON battles (code);
CREATE INDEX contender_index ON battles (contender);
CREATE INDEX contested_index ON battles (contested);
CREATE INDEX winner_index ON battles (winner);

CREATE TABLE actions (
    id uuid DEFAULT gen_random_uuid() PRIMARY KEY,
    battle_id uuid NOT NULL REFERENCES battles(id),
    player varchar(255) NOT NULL,
    character_id uuid NOT NULL REFERENCES characters(id),
    round smallint,
    turn varchar(255) REFERENCES battle_turn(turn),
    dices_quantity smallint,
    dice varchar(5) references dices(dice_type),
    results varchar(255),
    calculus varchar(255),
    modifier_total smallint,
    total_result smallint,
    at timestamptz DEFAULT current_timestamp
);

CREATE INDEX player_name ON actions (player);