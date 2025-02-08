create table empleado (
	empleado_id serial not null,
	primer_nombre varchar(50) not null,
	segundo_nombre varchar(50),
	apellido_paterno varchar(50) not null, 
	apellido_materno varchar(50) not null, 
	edad smallint not null, 
	sexo char(1) not null, 
	fecha_nacimiento date not null, 
	puesto varchar(100) not null,
	constraint pk_empleado 
		primary key(empleado_id)
);