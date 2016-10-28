------------------------------------------------------Inserta Permisos-------------------------
INSERT INTO PERMISO VALUES ('accessMain', 'Formulario', 'Acceso al Sistema.');
INSERT INTO PERMISO VALUES ('accessPlantelSelect', 'Formulario', 'Acceso a plantelSelect.jsp.');
INSERT INTO PERMISO VALUES ('accessToAddDataSheet', 'Formulario', 'Acceso a jsp Agregar Ficha Técnica de Plantel.');
INSERT INTO PERMISO VALUES ('addDataSheet', 'Permiso', 'Insertar ficha Técnica de Plantel.');
INSERT INTO PERMISO VALUES ('accessToUpdateDataSheet', 'Formulario', 'Modifica ficha Técnica de Plantel.');
INSERT INTO PERMISO VALUES ('updateDataSheet', 'Formulario', 'Actualiza ficha Tecnica de Plantel.');
INSERT INTO PERMISO VALUES ('accessToAddStage', 'Formulario', 'Acceso a vista agregar Etapa de Desarrollo.');
INSERT INTO PERMISO VALUES ('accessToViewStage', 'Formulario', 'Acceso para ver etapas de Desarrollo.');
INSERT INTO PERMISO VALUES ('addStage', 'Permiso', 'Insertar Etapa de Desarrollo de Plantel.');
INSERT INTO PERMISO VALUES ('accessToViewActivity', 'Formulario', 'Acceso para ver actividades de las Etapas de Plantel.');
INSERT INTO PERMISO VALUES ('accessToAddActivity', 'Formulario', 'Acceso a vista agregar Actividad de Etapas de Plantel.');
INSERT INTO PERMISO VALUES ('addActivity', 'Permiso', 'Insertar actividades de las Etapas de Plantel.');
INSERT INTO ROL_PERMISO VALUES (1,1);
INSERT INTO ROL_PERMISO VALUES (1,2);
INSERT INTO ROL_PERMISO VALUES (1,3);
INSERT INTO ROL_PERMISO VALUES (1,4);
INSERT INTO ROL_PERMISO VALUES (1,5);
INSERT INTO ROL_PERMISO VALUES (1,6);
INSERT INTO ROL_PERMISO VALUES (1,7);
INSERT INTO ROL_PERMISO VALUES (1,8);
INSERT INTO ROL_PERMISO VALUES (1,9);
INSERT INTO ROL_PERMISO VALUES (1,10);
INSERT INTO ROL_PERMISO VALUES (1,11);
INSERT INTO ROL_PERMISO VALUES (1,12);
SELECT * FROM PERMISO;
SELECT * FROM ROL_PERMISO;

	10.- Acceso para ver actividades de las Etapas de Plantel. (accessToViewActivity) (Formulario)
	11.- Acceso a vista agregar Actividad de Etapas de Plantel. (accessToAddActivity) (Formulario)
	12.- Insertar actividades de las Etapas de Plantel. (addActivity) (permiso)



-----------------------------------------------------------Metodo para Obtener la DataSheet-----------------------------------------------
SELECT P.nombre, P.direccion, M.municipio, E.estado, P.claveCentroTrabajo, P.anioCreacion, P.telefono, P.correo
, P.latitud, P.longitud, P.nombreCompletoDirector, A.personalAdmin, A.docentes, A.matricula, A.turno,
A.periodoEscolar, A.carrerasVigentes, A.carrerasLiquidadas, I.superficiePredio, I.superficieConstruida,
I.aulasDidacticas, I.laboratorios, I.biblioteca, I.talleresComputo, I.otrosTalleres, I.areasAdmin, I.cafeteria
,I.salaAudio, I.casetaVigilancia, I.bardaPerimetral, I.areasDeportivas, A.ID_Academico, I.ID_Infraestructura, M.ID_Municipio, E.ID_Estado from PLANTEL P, ACADEMICO A, INFRAESTRUCTURA I, MUNICIPIO M, ESTADO E
 WHERE A.FK_ID_Plantel=P.ID_Plantel 
 AND I.FK_ID_Plantel=P.ID_Plantel
 AND P.FK_ID_Municipio=M.ID_Municipio
 AND P.FK_ID_Estado=E.ID_Estado
 AND A.fechaActualizacion=I.fechaActualizacion
 AND P.ID_Plantel=2
 AND A.fechaActualizacion=I.fechaActualizacion;

 ---------------------------------------------------Reseterar Tabla Academico e Infraestructura----------------------

 DELETE FROM ACADEMICO;
 DELETE FROM INFRAESTRUCTURA;
 DBCC CHECKIDENT (ACADEMICO, RESEED,0);
 DBCC CHECKIDENT (INFRAESTRUCTURA, RESEED,0);
 SELECT * FROM ACADEMICO;
 SELECT * FROM INFRAESTRUCTURA;

 ----------------------------------------------------------prueba para saber los tipos de datos ACADEMICO, INFRAESTRUCTURA-------------------
 select A.fechaActualizacion from ACADEMICO A;


 ---------------------------------------------------------------------Metodo de actualización de tablas Plantel, Academico, Infraestructura----------------

UPDATE  PLANTEL SET
nombre = ?
, direccion = ?
, claveCentroTrabajo = ?
, anioCreacion = ?
, telefono = ?
, correo = ?
, latitud = ?
, longitud = ?
, nombreCompletoDirector = ?
WHERE ID_Plantel = ?;

UPDATE ACADEMICO SET
personalAdmin = ?
, docentes = ?
, matricula = ?
, turno = ?
, periodoEscolar = ?
, carrerasVigentes = ?
, carrerasLiquidadas = ?
, fechaActualizacion = ?
WHERE ID_Academico = ?;

UPDATE INFRAESTRUCTURA SET
superficiePredio = ?
, superficieConstruida = ?
, aulasDidacticas = ?
, laboratorios = ?
, biblioteca = ?
, talleresComputo = ?
, otrosTalleres = ?
, areasAdmin = ?
, cafeteria = ?
, salaAudio = ?
, casetaVigilancia = ?
, bardaPerimetral = ?
, areasDeportivas = ?
, fechaActualizacion = ?
WHERE ID_Infraestructura = ?;


---------------------------------------------------------Metodo para Desplegar Etapas de un plantel----------
------------Insertamos un registro--------------
INSERT INTO ETAPA 
VALUES(10,'Nombre1', 'Descripccion1','F.Ini1', '2016-10-01'
,'Status1','F.Act1','TipoEtapa1',0,4,2);
INSERT INTO ETAPA 
VALUES(36,'Nombre2', 'Descripccion2','F.Ini2', '2015-10-01'
,'Status2','F.Act2','TipoEtapa2',0,4,2);
INSERT INTO ETAPA 
VALUES(23,'Nombre3', 'Descripccion3','F.Ini3', '2014-10-01'
,'Status3','F.Act3','TipoEtapa3',0,4,2);
INSERT INTO ETAPA 
VALUES(12,'Nombre4', 'Descripccion4','F.Ini4', '2014-10-01'
,'Status4','F.Act4','TipoEtapa4',0,4,2);
INSERT INTO ETAPA 
VALUES(2,'Nombre3', 'Descripccion3','F.Ini3', '2014-10-01'
,'Status3','F.Act3','TipoEtapa3',0,4,2);


SELECT * FROM ETAPA E WHERE E.FK_ID_Plantel=2;
DELETE FROM ETAPA;
DBCC CHECKIDENT (ETAPA, RESEED,0);

----------------Metodo para saber cuantas etapas de desarrollo tiene un plantel------------
select numeroEtapa from ETAPA E
Where E.FK_ID_Plantel=2;