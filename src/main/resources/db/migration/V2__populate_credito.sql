-- Script de População da Tabela via Flyway (idempotente)
-- Insere apenas se o registro ainda não existir, evitando duplicidade ao adotar Flyway em esquemas já populados

INSERT INTO credito (numero_credito, numero_nfse, data_constituicao, valor_issqn, tipo_credito, simples_nacional, aliquota, valor_faturado, valor_deducao, base_calculo)
SELECT '123456', '7891011', '2024-02-25', 1500.75, 'ISSQN', true, 5.0, 30000.00, 5000.00, 25000.00
WHERE NOT EXISTS (
    SELECT 1 FROM credito c WHERE c.numero_credito = '123456' AND c.numero_nfse = '7891011'
);

INSERT INTO credito (numero_credito, numero_nfse, data_constituicao, valor_issqn, tipo_credito, simples_nacional, aliquota, valor_faturado, valor_deducao, base_calculo)
SELECT '789012', '7891011', '2024-02-26', 1200.50, 'ISSQN', false, 4.5, 25000.00, 4000.00, 21000.00
WHERE NOT EXISTS (
    SELECT 1 FROM credito c WHERE c.numero_credito = '789012' AND c.numero_nfse = '7891011'
);

INSERT INTO credito (numero_credito, numero_nfse, data_constituicao, valor_issqn, tipo_credito, simples_nacional, aliquota, valor_faturado, valor_deducao, base_calculo)
SELECT '654321', '1122334', '2024-01-15', 800.50, 'Outros', true, 3.5, 20000.00, 3000.00, 17000.00
WHERE NOT EXISTS (
    SELECT 1 FROM credito c WHERE c.numero_credito = '654321' AND c.numero_nfse = '1122334'
);
