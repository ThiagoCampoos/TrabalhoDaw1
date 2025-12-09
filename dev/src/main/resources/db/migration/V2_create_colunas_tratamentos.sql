ALTER TABLE tratamentos
    ADD COLUMN IF NOT EXISTS sessoes_realizadas INTEGER DEFAULT 0,
    ADD COLUMN IF NOT EXISTS data_inicio DATE,
    ADD COLUMN IF NOT EXISTS data_fim_prevista DATE;