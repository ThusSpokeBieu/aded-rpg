CREATE TABLE dices (
    dice_type varchar(5) PRIMARY KEY,
    sides smallint NOT NULL CHECK (sides > 0)
);

INSERT INTO dices (dice_type, sides) VALUES
                                    ('D4', 4),
                                    ('D6', 6),
                                    ('D8', 8),
                                    ('D10', 10),
                                    ('D12', 12),
                                    ('D20', 20),
                                    ('D100', 100);

CREATE TABLE archetypes (
    archetype varchar(255) PRIMARY KEY
);

INSERT INTO archetypes (archetype) VALUES ('HERO'), ('MONSTER');

CREATE TABLE characters (
    id uuid DEFAULT gen_random_uuid() PRIMARY KEY,
    character_class varchar(255) UNIQUE NOT NULL,
    archetype varchar(255) references archetypes(archetype),
    hp smallint NOT NULL CHECK (hp > 0 AND hp <= 100),
    strength smallint NOT NULL CHECK (strength >= 0 AND strength <= 10),
    defense smallint NOT NULL CHECK (defense >= 0 AND defense <= 10),
    agility smallint NOT NULL CHECK (agility >= 0 AND agility <= 10),
    dices_quantity smallint NOT NULL CHECK(dices_quantity > 0 AND dices_quantity <= 10),
    dice varchar(5) references dices(dice_type),
    created_at timestamptz DEFAULT current_timestamp,
    updated_at timestamptz DEFAULT current_timestamp
);

INSERT INTO characters
    (archetype, character_class, hp, strength, defense, agility, dices_quantity, dice)
VALUES
    ('HERO', 'Warrior', 20, 7, 5, 6, 1, 'D12'),
    ('HERO', 'Barbarian', 21, 10, 2, 5, 2, 'D8'),
    ('HERO', 'Knight', 26, 6, 8, 3, 2, 'D6'),
    ('MONSTER', 'Orc', 42, 7, 1, 2, 3, 'D4'),
    ('MONSTER', 'Giant', 34, 10, 4, 4, 2, 'D6'),
    ('MONSTER', 'Werewolf', 34, 7, 4, 7, 2, 'D4');