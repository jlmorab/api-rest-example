# README.md

## Pre-Requisitos

* Docker
* Java 17

## Configuracion de base de datos

En la raiz del proyecto se encuentra la carpeta `docker` donde se encuentra un archivo `Dockerfile`. Para ejecutarlo utilizando Powershell nos ubicamos en el directorio y ejecutamos

~~~bash
docker build -t postgres .
~~~

esto creará la imagen. Ahora para ejecutarla ingresamos

~~~bash
docker run -d --name postgres postgres
~~~

con esto se tendrá una instancia de postgres corriendo en local. Por default estará disponible en `http://localhost:5432`

Para cargar la base de datos que se utilizará, en la raiz del proyecto se encuentra la carpeta `sql` que contiene los scripts, estos estan nombrados secuencialmente en el orden que deben ejecutarse. Para ejecutarlo mediante Powershell nos ubicamos en la carpeta que contiene los scripts e ingresamos

~~~bash
Get-Content "001_CDL_CreateDatabase.sql" | docker exec -i postgres psql -U postgres -d postgres
~~~

~~~bash
Get-Content "002_CDL_CreateUser.sql" | docker exec -i postgres psql -U postgres -d invex
~~~

~~~bash
Get-Content "003_DDL_CreateTableEmpleado.sql" | docker exec -i postgres psql -U postgres -d invex
~~~

~~~bash
Get-Content "004_DDL_SP_EliminarEmpleado.sql" | docker exec -i postgres psql -U postgres -d invex
~~~

Con estos scripts se creara la base de datos `invex`, el usuario `appusrtest` que utiliza la API, así como sus permisos, crea la tabla de `empleado` y el store procedured `deleteEmployeeById`.

## Compilacion de API

Para compilar la API es necesario tener `maven` y el `JDK` de java 17, además de tener correctamente configuradas las variables de entorno.

Estando en la ruta raiz del proyecto, para ejecutar mediante Powershell ingresamos

~~~bash
mvn dependency:resolve
~~~

~~~bash
mvn clean package
~~~

esto descargará las dependencias, ejecutara las pruebas automatizadas, generará el informe de pruebas, creará el micrositio con la cobertura de código y compilará la aplicación.

## Ejecución de API

Nos ubicamos en la ruta donde esta el artefacto compilado en `/target`. Para ejecutar la API mediante Powershell se hace ingresando

~~~bash
java -jar example-jlmb-0.0.1-SNAPSHOT.jar
~~~

esto desplegará la API con las configuraciones con que se compilo. En caso de querer modificarlas se tomará de `/src/main/resources` los archivos `application.yml` y `secrets.yml`, se editarán según se requiera y se ingresaria

~~~bash
# Estando el archivo 'application.yml' en la misma ubicación
java -jar example-jlmb-0.0.1-SNAPSHOT.jar --spring.config.location=file:application.yml

# Estando el archivo 'application.yml' en otra ubicación
java -jar example-jlmb-0.0.1-SNAPSHOT.jar --spring.config.location=file:/ruta/completa/application.yml
~~~

## Swagger

La API incluye OpenAPI que tiene embebido Swagger para la documentación del servicio REST, para consultar su dashboard en el explorador web ingresamos `http://localhost:2200/api/v1/example-jlmb/swagger-ui/index.html`

## Informe de pruebas unitarias

Al compilarse la API, tiene configurado el plugin de `surefire` lo que hace que el resultado de las pruebas las genere en `/target/surefire-reports`, en esta ubicación esta en archivos XML el detalle de las pruebas unitarias y en archivos TXT el resumen de su ejecución. 

## Cobertura de código

Al compilarse la API, tiene configurado el plugin de `jacoco` lo que hace que se genere un micrositio web con el resultado de la cobertura de código, para poder acceder a este micrositio se abre el archivo `index.html` que se encuentra en `/target/site/jacoco-ut`. Es éste se mostrará cada uno de los packages del proyecto indicando los diferentes niveles de cobertura que tiene, al ingresar en cada uno de ellos se puede navegar entre las clases que contiene y en ellas esta marcado por colores las líneas de código. El color refleja cuales líneas son comprobadas por las pruebas unitarias que se ejecutaron.

## API testing

En la raíz del proyecto se tiene la carpeta `postman` que contiene la colección de las diferentes peticiones a la API. Las URL de las peticiones contienen variables de entorno de postman que estan en el archivo con sufijo `...environment.json`;
