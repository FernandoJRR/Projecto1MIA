CREATE DATABASE proyecto1_mia_db;
CREATE ROLE vendedor_electronic_homes; CREATE ROLE gestor_inventario_electronic_homes; CREATE ROLE gestor_bodega_electronic_homes;
CREATE ROLE administrador_electronic_homes;

GRANT CONNECT ON DATABASE proyecto1_mia_db TO vendedor_electronic_homes;
GRANT CONNECT ON DATABASE proyecto1_mia_db TO gestor_inventario_electronic_homes;
GRANT CONNECT ON DATABASE proyecto1_mia_db TO gestor_bodega_electronic_homes;
GRANT CONNECT ON DATABASE proyecto1_mia_db TO administrador_electronic_homes;

\c proyecto1_mia

CREATE SCHEMA control_usuarios;

CREATE TABLE control_usuarios.usuario (
  -- administrador, vendedor, bodega, inventario
  tipo VARCHAR NOT NULL,
  username VARCHAR NOT NULL,
  password VARCHAR NOT NULL,
  -- norte, sur, central, null
  sucursal VARCHAR,

  PRIMARY KEY(username)
);

INSERT INTO control_usuarios.usuario (
  tipo, username, password, sucursal
) VALUES 
--NOTA: Los password son el username pasado por el algoritmo SHA256
--Administrador inicial
( 'administrador', 'admin01', 'CHbfym1v7fmbKrh7bi/tS9QFHt54qKkTW1ALLpTZm4g', null ),

--Encargados de bodega iniciales
( 'bodega', 'bodega01', 'FgC7RJtCtAJZG9mO9DePXZDx5rcTAUC7gHjjrcHVHkU', null ),
( 'bodega', 'bodega02', '0G6jKjn+eMY4/OYNGqGbHgfiDzk+CdPuCtLlKAuMdGI', null ),
( 'bodega', 'bodega03', 'w68KBH/BgUHDUPkyvao13NNW1KJ/eMBwDlhKm3XWmmo', null ),
( 'bodega', 'bodega04', 'IRCT+WT90EZ1hObrQ7x4nrqjp170GJ2Vtm1AXdMTZeE', null ),

--Sucursal Norte
( 'vendedor', 'v_norte01', 'rlnV4DnXpbHYrK9GHn1AVNgqS6/IWWqhM+EAfO87Fls', 'norte' ),
( 'vendedor', 'v_norte02', 'pzzy3Rz8HZf0QVq2WDUmRjlerjhEW4mVJSZM3pu4TNs', 'norte' ),
( 'vendedor', 'v_norte03', '/B8XuUunPXIrKaIxutxZmSJ2uD3d/HStBLi9sMkfyYY', 'norte' ),
( 'inventario', 'i_norte01', 'LsGD5/pY1aLU3NwUFFoqob62wBjZqzLbiAJFqBJzpH4', 'norte' ),

--Sucursal Sur
( 'vendedor', 'v_sur01', '738DYpZ/rMlPJE/Lmzr2qDfI1Tk5cXeKrwwftY1rZc4', 'sur' ),
( 'vendedor', 'v_sur02', 'v/fbSPotXzci56J/SCzVzRt+iOGSEO5ta93sGHwAiWo', 'sur' ),
( 'vendedor', 'v_sur03', 'F0gObfIlmD6eA+/8YwcPFM3i3F/1K7yEIFKxwE0Coo0', 'sur' ),
( 'inventario', 'i_sur01', 'sSBNEcXcOIIBrxOg6Zp/CwLJbpMYNfkD7m/6nRt50eg', 'sur' ),

--Sucursal Central
( 'vendedor', 'v_central01', 'Bl4J4wXMmbMtsyFxUVeG+jbg8dCsjkaL1NmkYEMb4J8', 'central' ),
( 'vendedor', 'v_central02', 'XU9wvzBBj78QFl9WLNFHwsL31OpPVa3lTiYe9KmfUcU', 'central' ),
( 'vendedor', 'v_central03', '/BaJx+sbaJepyY+r772oD6tgrln/8gLuP3jbHMX+3Ks', 'central' ),
( 'inventario', 'i_central01', 'N6crqzl4Tr2jYM8Pa+JPoXIpM+6P/RswJaNUVnALJt0', 'central' );

-- Permisos de roles
-- Administrador
GRANT USAGE ON SCHEMA control_usuarios TO administrador_electronic_homes;
GRANT INSERT,SELECT ON TABLE control_usuarios.usuario TO administrador_electronic_homes;

CREATE USER administrador01 WITH PASSWORD 'admin_01';
GRANT administrador_electronic_homes TO administrador01;
