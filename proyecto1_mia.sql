CREATE DATABASE proyecto1_mia_db;
CREATE ROLE vendedor_electronic_homes; 
CREATE ROLE gestor_inventario_electronic_homes; 
CREATE ROLE gestor_bodega_electronic_homes;
CREATE ROLE administrador_electronic_homes;

GRANT CONNECT ON DATABASE proyecto1_mia_db TO vendedor_electronic_homes;
GRANT CONNECT ON DATABASE proyecto1_mia_db TO gestor_inventario_electronic_homes;
GRANT CONNECT ON DATABASE proyecto1_mia_db TO gestor_bodega_electronic_homes;
GRANT CONNECT ON DATABASE proyecto1_mia_db TO administrador_electronic_homes;

\c proyecto1_mia

CREATE SCHEMA control_sucursales;

CREATE TABLE control_sucursales.sucursal (
  nombre VARCHAR NOT NULL,

  PRIMARY KEY(nombre)
);

INSERT INTO control_sucursales.sucursal ( nombre ) VALUES 
( 'norte' ),
( 'sur' ),
( 'central' );

CREATE SCHEMA control_usuarios;

CREATE TABLE control_usuarios.usuario (
  -- administrador, vendedor, bodega, inventario
  tipo VARCHAR NOT NULL,
  username VARCHAR NOT NULL,
  password VARCHAR NOT NULL,
  -- norte, sur, central, null
  sucursal VARCHAR,

  PRIMARY KEY(username),
  FOREIGN KEY(sucursal) REFERENCES control_sucursales.sucursal(nombre)
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


--Clientes
CREATE SCHEMA control_clientes;

CREATE TABLE control_clientes.cliente (
  nit INT NOT NULL,
  nombre VARCHAR NOT NULL,

  PRIMARY KEY(nit)
);

INSERT INTO control_clientes.cliente ( nit, nombre ) VALUES 
( 1551906, 'Alfonso Gutierritos' ),
( 5221524, 'Mariela Sazo' ),
( 5437845, 'Telesforo Aguilar' ),
( 7956834, 'Marco Alfonzelo' ),
( 5690304, 'Rosalia Tobar' ),
( 3454367, 'Elendor Matias' ),
( 8465161, 'Emilia Clark' );

--Ventas
CREATE SCHEMA control_ventas;

CREATE TABLE control_ventas.venta (
  codigo SERIAL NOT NULL,
  vendedor VARCHAR NOT NULL,
  cliente INT,
  descuento FLOAT NOT NULL,

  PRIMARY KEY(codigo),
  FOREIGN KEY(vendedor) REFERENCES control_usuarios.usuario(username),
  FOREIGN KEY(cliente) REFERENCES control_clientes.cliente(nit) ON UPDATE CASCADE
);

INSERT INTO control_ventas.venta ( vendedor, cliente, descuento ) VALUES 
( 'v_norte01', 5437845, 0 ),
( 'v_norte02', 8465161, 0 ),
( 'v_norte02', 5437845, 0 ),
( 'v_norte02', 3454367, 0 ),
( 'v_norte03', 1551906, 0 ),

( 'v_sur01', 1551906, 0 ),
( 'v_sur01', 5221524, 0 ),
( 'v_sur01', 1551906, 0 ),
( 'v_sur02', 5690304, 0 ),
( 'v_sur02', 7956834, 0 ),

( 'v_central01', 1551906, 0 ),
( 'v_central02', 7956834, 0 ),
( 'v_central03', 8465161, 0 ),
( 'v_central03', 5221524, 0 ),
( 'v_central03', 5690304, 0 );

--Productos
CREATE SCHEMA control_productos;

CREATE TABLE control_productos.producto (
  codigo VARCHAR NOT NULL,
  nombre VARCHAR NOT NULL,
  precio DECIMAL(20,2) NOT NULL,

  PRIMARY KEY(codigo)
);

INSERT INTO control_productos.producto ( codigo, nombre, precio ) VALUES 
( '383838', 'Pelador de Cables Truper', 220.0 ),
( '458458', 'Audifonos JBL', 500.0 ),
( '100020', 'Cable de Ethernet', 20.0 ),
( '786378', 'Bombilla LED', 150.0 ),
( '968585', 'Conector Ethernet', 30.0 ),
( '552455', 'Enchufe de Pared', 25.0 ),
( '675868', 'Bombilla Incandescente', 70.0 ),
( '451232', 'Tubo de PVC', 25.0 );

CREATE TABLE control_productos.producto_ingresado (
  id SERIAL NOT NULL,
  codigo_producto VARCHAR NOT NULL,
  sucursal VARCHAR,
  en_bodega BOOLEAN NOT NULL,
  codigo_venta INT,

  PRIMARY KEY(id),
  FOREIGN KEY(codigo_producto) REFERENCES control_productos.producto(codigo) 
    ON UPDATE CASCADE,
  FOREIGN KEY(sucursal) REFERENCES control_sucursales.sucursal(nombre),
  FOREIGN KEY(codigo_venta) REFERENCES control_ventas.venta(codigo)
);

INSERT INTO control_productos.producto_ingresado ( 
  codigo_producto, 
  sucursal, 
  en_bodega, 
  codigo_venta 
) VALUES 
-- Ingreso de productos aun no vendidos
-- Central - 30 productos
( '383838', 'central', false, null ),
( '383838', 'central', false, null ),
( '383838', 'central', false, null ),
( '383838', 'central', false, null ),
( '458458', 'central', false, null ),
( '458458', 'central', false, null ),
( '458458', 'central', false, null ),
( '100020', 'central', false, null ),
( '100020', 'central', false, null ),
( '100020', 'central', false, null ),
( '100020', 'central', false, null ),
( '100020', 'central', false, null ),
( '100020', 'central', false, null ),
( '786378', 'central', false, null ),
( '786378', 'central', false, null ),
( '786378', 'central', false, null ),
( '968585', 'central', false, null ),
( '968585', 'central', false, null ),
( '968585', 'central', false, null ),
( '968585', 'central', false, null ),
( '968585', 'central', false, null ),
( '968585', 'central', false, null ),
( '968585', 'central', false, null ),
( '552455', 'central', false, null ),
( '552455', 'central', false, null ),
( '552455', 'central', false, null ),
( '552455', 'central', false, null ),
( '675868', 'central', false, null ),
( '675868', 'central', false, null ),
( '675868', 'central', false, null ),

-- Norte - 25 productos
( '383838', 'norte', false, null ),
( '383838', 'norte', false, null ),
( '383838', 'norte', false, null ),
( '383838', 'norte', false, null ),
( '383838', 'norte', false, null ),
( '383838', 'norte', false, null ),
( '458458', 'norte', false, null ),
( '458458', 'norte', false, null ),
( '100020', 'norte', false, null ),
( '100020', 'norte', false, null ),
( '100020', 'norte', false, null ),
( '100020', 'norte', false, null ),
( '100020', 'norte', false, null ),
( '100020', 'norte', false, null ),
( '100020', 'norte', false, null ),
( '968585', 'norte', false, null ),
( '968585', 'norte', false, null ),
( '968585', 'norte', false, null ),
( '968585', 'norte', false, null ),
( '552455', 'norte', false, null ),
( '552455', 'norte', false, null ),
( '675868', 'norte', false, null ),
( '675868', 'norte', false, null ),
( '451232', 'norte', false, null ),
( '451232', 'norte', false, null ),

-- Sur - 15 productos
( '458458', 'sur', false, null ),
( '458458', 'sur', false, null ),
( '458458', 'sur', false, null ),
( '458458', 'sur', false, null ),
( '100020', 'sur', false, null ),
( '100020', 'sur', false, null ),
( '100020', 'sur', false, null ),
( '968585', 'sur', false, null ),
( '968585', 'sur', false, null ),
( '968585', 'sur', false, null ),
( '968585', 'sur', false, null ),
( '451232', 'sur', false, null ),
( '451232', 'sur', false, null ),
( '451232', 'sur', false, null ),
( '451232', 'sur', false, null ),

-- Bodega - 30 productos
( '383838', null, true, null ),
( '383838', null, true, null ),
( '383838', null, true, null ),
( '383838', null, true, null ),
( '458458', null, true, null ),
( '458458', null, true, null ),
( '458458', null, true, null ),
( '100020', null, true, null ),
( '100020', null, true, null ),
( '100020', null, true, null ),
( '100020', null, true, null ),
( '786378', null, true, null ),
( '786378', null, true, null ),
( '786378', null, true, null ),
( '786378', null, true, null ),
( '786378', null, true, null ),
( '968585', null, true, null ),
( '968585', null, true, null ),
( '968585', null, true, null ),
( '968585', null, true, null ),
( '552455', null, true, null ),
( '552455', null, true, null ),
( '552455', null, true, null ),
( '552455', null, true, null ),
( '675868', null, true, null ),
( '675868', null, true, null ),
( '675868', null, true, null ),
( '451232', null, true, null ),
( '451232', null, true, null ),
( '451232', null, true, null ),

-- Vendidos
( '383838', null, false, 1 ),
( '383838', null, false, 5 ),
( '383838', null, false, 6 ),
( '383838', null, false, 6 ),
( '458458', null, false, 1 ),
( '458458', null, false, 12 ),
( '458458', null, false, 5 ),
( '458458', null, false, 7 ),
( '100020', null, false, 1 ),
( '100020', null, false, 11 ),
( '100020', null, false, 8 ),
( '100020', null, false, 2 ),
( '100020', null, false, 2 ),
( '786378', null, false, 1 ),
( '786378', null, false, 9 ),
( '786378', null, false, 14 ),
( '786378', null, false, 3 ),
( '786378', null, false, 14 ),
( '968585', null, false, 1 ),
( '968585', null, false, 15 ),
( '968585', null, false, 3 ),
( '968585', null, false, 15 ),
( '552455', null, false, 15 ),
( '552455', null, false, 13 ),
( '552455', null, false, 1 ),
( '552455', null, false, 12 ),
( '675868', null, false, 11 ),
( '675868', null, false, 3 ),
( '675868', null, false, 4 ),
( '451232', null, false, 10 ),
( '451232', null, false, 10 ),
( '451232', null, false, 1 );

--Roles
---Administrador
GRANT USAGE ON SCHEMA control_usuarios TO administrador_electronic_homes;
GRANT USAGE ON SCHEMA control_productos TO administrador_electronic_homes;
GRANT USAGE ON SCHEMA control_sucursales TO administrador_electronic_homes;
GRANT USAGE ON SCHEMA control_clientes TO administrador_electronic_homes;
GRANT USAGE ON SCHEMA control_ventas TO administrador_electronic_homes;

GRANT INSERT,SELECT ON TABLE control_usuarios.usuario TO administrador_electronic_homes;
GRANT SELECT ON TABLE control_productos.producto TO administrador_electronic_homes;
GRANT SELECT ON TABLE control_productos.producto_ingresado TO administrador_electronic_homes;
GRANT SELECT ON TABLE control_sucursales.sucursal TO administrador_electronic_homes;
GRANT SELECT ON TABLE control_clientes.cliente TO administrador_electronic_homes;
GRANT SELECT ON TABLE control_ventas.venta TO administrador_electronic_homes;

CREATE USER administrador01 WITH PASSWORD 'admin_01';
GRANT administrador_electronic_homes TO administrador01;
---Bodega
GRANT USAGE ON SCHEMA control_productos TO gestor_bodega_electronic_homes;

GRANT INSERT,UPDATE,SELECT ON TABLE control_productos.producto_ingresado TO gestor_bodega_electronic_homes;
GRANT INSERT,UPDATE,SELECT ON TABLE control_productos.producto TO gestor_bodega_electronic_homes;
GRANT USAGE, SELECT ON ALL SEQUENCES IN SCHEMA control_productos TO gestor_bodega_electronic_homes;

CREATE USER gestor_bodega01 WITH PASSWORD 'bodega_01';
GRANT gestor_bodega_electronic_homes TO gestor_bodega01;
---Inventario
GRANT USAGE ON SCHEMA control_productos TO gestor_inventario_electronic_homes;

GRANT UPDATE,SELECT ON TABLE control_productos.producto_ingresado TO gestor_inventario_electronic_homes;
GRANT SELECT ON TABLE control_productos.producto TO gestor_inventario_electronic_homes;
GRANT USAGE, SELECT ON ALL SEQUENCES IN SCHEMA control_productos TO gestor_inventario_electronic_homes;

CREATE USER gestor_inventario01 WITH PASSWORD 'inventario01';
GRANT gestor_inventario_electronic_homes TO gestor_inventario01;
---Ventas
GRANT USAGE ON SCHEMA control_clientes TO vendedor_electronic_homes;
GRANT USAGE ON SCHEMA control_productos TO vendedor_electronic_homes;
GRANT USAGE ON SCHEMA control_ventas TO vendedor_electronic_homes;

GRANT INSERT,UPDATE,SELECT ON TABLE control_clientes.cliente TO vendedor_electronic_homes;
GRANT UPDATE,SELECT ON TABLE control_productos.producto_ingresado TO vendedor_electronic_homes;
GRANT SELECT ON TABLE control_productos.producto TO vendedor_electronic_homes;
GRANT INSERT,SELECT ON TABLE control_ventas.venta TO vendedor_electronic_homes;
GRANT USAGE,SELECT ON ALL SEQUENCES IN SCHEMA control_productos TO vendedor_electronic_homes;
GRANT USAGE,SELECT ON ALL SEQUENCES IN SCHEMA control_ventas TO vendedor_electronic_homes;

CREATE USER vendedor01 WITH PASSWORD 'vendedor01';
GRANT vendedor_electronic_homes TO vendedor01;

-------------------ADMINISTRADOR--------------------------------

select codigo_producto, count(codigo_producto) as cantidad 
  from control_productos.producto_ingresado 
  where codigo_venta = 1 
  group by codigo_producto;

-- Select para obtener la cantidad de cada producto y su precio unitario y el total para ese producto
SELECT control_productos.producto_ingresado.codigo_producto, 
  COUNT(control_productos.producto_ingresado.codigo_producto) as cantidad,
  control_productos.producto.precio as precio_unidad,
  control_productos.producto.precio * COUNT(control_productos.producto_ingresado.codigo_producto) as total
  FROM control_productos.producto_ingresado
JOIN control_productos.producto
  ON control_productos.producto.codigo = control_productos.producto_ingresado.codigo_producto
  WHERE control_productos.producto_ingresado.codigo_venta = 15
  GROUP BY control_productos.producto_ingresado.codigo_producto,
    control_productos.producto.codigo
;

-- Select para obtener el total de una venta
SELECT SUM(control_productos.producto.precio)
  FROM control_productos.producto_ingresado
JOIN control_productos.producto
  ON control_productos.producto.codigo = control_productos.producto_ingresado.codigo_producto
  WHERE control_productos.producto_ingresado.codigo_venta = 15
;

-- Top 10 productos mas vendidos
SELECT codigo_producto, 
  nombre,
  COUNT(codigo_producto) as veces_vendido
  FROM control_productos.producto_ingresado
  JOIN control_productos.producto
  ON control_productos.producto_ingresado.codigo_producto = control_productos.producto.codigo
  WHERE control_productos.producto_ingresado.codigo_venta IS NOT NULL
  GROUP BY control_productos.producto_ingresado.codigo_producto, nombre
  ORDER BY veces_vendido DESC
  LIMIT 10
;

-- Top 10 clientes que mas ganancias generan
SELECT cliente, cliente.nombre, SUM(precio) as total_gastado
  FROM control_productos.producto_ingresado
  JOIN control_productos.producto
  ON control_productos.producto_ingresado.codigo_producto = control_productos.producto.codigo
  JOIN control_ventas.venta
  ON control_productos.producto_ingresado.codigo_venta = control_ventas.venta.codigo
  JOIN control_clientes.cliente
  ON control_ventas.venta.cliente = control_clientes.cliente.nit
  WHERE control_productos.producto_ingresado.codigo_venta IS NOT NULL
    AND control_ventas.venta.cliente IS NOT NULL
  GROUP BY cliente, cliente.nombre
  ORDER BY total_gastado DESC
  LIMIT 10
;

-- Top 3 sucursales con mas ventas
SELECT sucursal, COUNT(sucursal) as ventas_sucursal
  FROM control_ventas.venta
  JOIN control_usuarios.usuario
  ON venta.vendedor = usuario.username
  GROUP BY sucursal
  ORDER BY ventas_sucursal DESC
  LIMIT 3
;

-- Top 3 sucursales con mas ingresos
SELECT  control_usuarios.usuario.sucursal, SUM(precio) as ventas_sucursal
  FROM control_productos.producto_ingresado
  JOIN control_productos.producto
  ON control_productos.producto_ingresado.codigo_producto = control_productos.producto.codigo
  JOIN control_ventas.venta
  ON control_productos.producto_ingresado.codigo_venta = control_ventas.venta.codigo
  JOIN control_usuarios.usuario
  ON control_ventas.venta.vendedor = control_usuarios.usuario.username
  WHERE control_productos.producto_ingresado.codigo_venta IS NOT null
  GROUP BY control_usuarios.usuario.sucursal
  ORDER BY ventas_sucursal DESC
  LIMIT 3
;

-- Top 3 vendedores con mas ventas
SELECT username, COUNT(username) as ventas_empleado
  FROM control_ventas.venta
  JOIN control_usuarios.usuario
  ON venta.vendedor = usuario.username
  GROUP BY username
  ORDER BY ventas_empleado DESC
  LIMIT 3
;

-- Top 3 vendedores con mas ingresos
SELECT  control_usuarios.usuario.username, SUM(precio) as ventas_sucursal
  FROM control_productos.producto_ingresado
  JOIN control_productos.producto
  ON control_productos.producto_ingresado.codigo_producto = control_productos.producto.codigo
  JOIN control_ventas.venta
  ON control_productos.producto_ingresado.codigo_venta = control_ventas.venta.codigo
  JOIN control_usuarios.usuario
  ON control_ventas.venta.vendedor = control_usuarios.usuario.username
  WHERE control_productos.producto_ingresado.codigo_venta IS NOT null
  GROUP BY control_usuarios.usuario.username
  ORDER BY ventas_sucursal DESC
  LIMIT 3
;

-- Top 10 productos mas vendidos
SELECT codigo_producto, COUNT(codigo_producto) as ventas_producto
  FROM control_productos.producto_ingresado
  WHERE control_productos.producto_ingresado.codigo_venta IS NOT null
  GROUP BY codigo_producto
  ORDER BY ventas_producto DESC
  LIMIT 10
;

-- Top 10 productos mas ingresos
SELECT codigo_producto, producto.nombre, SUM(precio) as total_producto
  FROM control_productos.producto_ingresado
  JOIN control_productos.producto
  ON codigo_producto = codigo
  WHERE control_productos.producto_ingresado.codigo_venta IS NOT null
  GROUP BY codigo_producto, producto.nombre
  ORDER BY total_producto DESC
  LIMIT 10
;

-- Top 5 productos mas vendidos por sucursal - cambiar sucursal
SELECT codigo_producto, producto.nombre, COUNT(codigo_producto) as ventas_producto
  FROM control_productos.producto_ingresado
  JOIN control_ventas.venta
  ON codigo_venta = codigo
  JOIN control_usuarios.usuario
  ON vendedor = username
  JOIN control_productos.producto
  ON codigo_producto = producto.codigo
  WHERE control_productos.producto_ingresado.codigo_venta IS NOT null
    AND usuario.sucursal = 'sur'
  GROUP BY codigo_producto, producto.nombre
  ORDER BY ventas_producto DESC
  LIMIT 5
;

-- Top 5 productos con mas ingresos por sucursal - cambiar sucursal
SELECT codigo_producto, producto.nombre, SUM(precio) as total_producto
  FROM control_productos.producto_ingresado
  JOIN control_productos.producto
  ON codigo_producto = control_productos.producto.codigo
  JOIN control_ventas.venta
  ON codigo_venta = control_ventas.venta.codigo
  JOIN control_usuarios.usuario
  ON vendedor = username
  WHERE control_productos.producto_ingresado.codigo_venta IS NOT null
    AND usuario.sucursal = 'sur'
  GROUP BY codigo_producto, producto.nombre
  ORDER BY total_producto DESC
  LIMIT 5
;

---------------------BODEGA-------------------------------------

-- Insertar producto (existente) en bodega
INSERT INTO control_productos.producto_ingresado ( 
  codigo_producto, 
  sucursal, 
  en_bodega, 
  codigo_venta 
) VALUES 
( 'codigo', null, true, null );

-- Insertar producto (nuevo) en bodega
INSERT INTO control_productos.producto ( codigo, nombre, precio ) VALUES 
( 'codigo', 'nombre', 0.0 );

INSERT INTO control_productos.producto_ingresado ( 
  codigo_producto, 
  sucursal, 
  en_bodega, 
  codigo_venta 
) VALUES 
( 'codigo', null, true, null );

-- Modificar codigo del producto
UPDATE control_productos.producto
  SET codigo = 'codigo'
  WHERE codigo = 'codigo_original';
-- Modificar nombre del producto
UPDATE control_productos.producto
  SET nombre = 'nombre'
  WHERE codigo = 'codigo_producto';
-- Modificar precio del producto
UPDATE control_productos.producto
  SET precio = 0.0
  WHERE codigo = 'codigo_producto';

-- Select productos
SELECT codigo,nombre,precio 
  FROM control_productos.producto;

-- Select productos de bodega
SELECT id, codigo, nombre 
  FROM control_productos.producto_ingresado
  JOIN control_productos.producto
  ON codigo_producto = codigo
  WHERE en_bodega = true;

--------------------------INVENTARIO-------------------------------

-- Seleccionar productos FUERA de la sucursal
SELECT id, codigo_producto, nombre, sucursal, en_bodega 
  FROM control_productos.producto_ingresado
  JOIN control_productos.producto
  ON codigo_producto = codigo
  WHERE codigo_venta IS NULL AND (sucursal != 'central' OR sucursal IS NULL)
;

-- Seleccionar productos EN la sucursal
SELECT id, codigo_producto, nombre
  FROM control_productos.producto_ingresado
  JOIN control_productos.producto
  ON codigo_producto = codigo
  WHERE codigo_venta IS NULL AND sucursal = 'central'
;

-- Seleccionar producto existe
SELECT *
  FROM control_productos.producto_ingresado
  WHERE codigo_venta IS NULL AND id = 5
;

-- Mover producto a sucursal
UPDATE control_productos.producto_ingresado
  SET sucursal = 'sucursal',
  en_bodega = false
  WHERE id = 'id_producto'
;

-------------------------VENDEDOR----------------------------------

-- Select cliente
SELECT *
  FROM control_clientes.cliente
  WHERE nit = 1551906
;

-- Select ultima venta de cliente
SELECT *
  FROM control_ventas.venta
  WHERE cliente = 1551906
  ORDER BY codigo DESC
  LIMIT 1
;

-- Total ultima venta cliente
SELECT SUM(precio) as total
  FROM control_productos.producto_ingresado
  JOIN control_productos.producto
  ON codigo_producto = codigo
  WHERE codigo_venta = 11
;

-- Crear nueva venta (cliente existente)
INSERT INTO control_ventas.venta ( vendedor, cliente, descuento ) VALUES 
( 'username_vendedor', 000000000, null ) 
;

-- Select ultima venta
SELECT *
  FROM control_ventas.venta
  ORDER BY codigo DESC
  LIMIT 1
;

  -- Para cada producto comprado
UPDATE control_productos.producto_ingresado
  SET codigo_venta = last_venta,
  sucursal = null
  WHERE id = 'id_producto';

-- Crear nueva venta (consumidor final)
INSERT INTO control_ventas.venta ( vendedor, cliente, descuento ) VALUES 
( 'username_vendedor', null, null );

  -- Para cada producto comprado
UPDATE control_productos.producto_ingresado
  SET codigo_venta = 00000000,
  sucursal = null,
  WHERE id = 'id_producto';

-- Crear nueva venta (nuevo cliente)
INSERT INTO control_clientes.cliente ( nit, nombre ) VALUES 
( 000000000, 'nombre' );

INSERT INTO control_ventas.venta ( vendedor, cliente, descuento ) VALUES 
( 'username_vendedor', 00000000, null );

  -- Para cada producto comprado
UPDATE control_productos.producto_ingresado
  SET codigo_venta = 00000000,
  sucursal = null,
  WHERE id = 'id_producto';

-- Select productos en venta de la sucursal
SELECT id, codigo, nombre, precio
  FROM control_productos.producto_ingresado
  JOIN control_productos.producto
  ON codigo_producto = codigo
  WHERE sucursal = 'norte'
;

-- Modificar NIT de comprador
UPDATE control_clientes.cliente
  SET nit = 000000000
  WHERE nit = 111111111
;

-- Modificar nombre de comprador
UPDATE control_clientes.cliente
  SET nombre = 'nuevo_nombre'
  WHERE nit = 000000000;
