# **--LiterAlura--**

**LiterAlura** es un catálogo de libros implementado en Java que interactúa con una API externa para obtener información sobre libros, guarda los datos en una base de datos y permite consultas a través de la consola.

## Descripción

Este proyecto busca ofrecer una forma sencilla de gestionar un catálogo de libros, con soporte para buscar libros, registrar nuevos y consultar la información de los mismos, incluyendo detalles sobre los autores. Utiliza Hibernate para la persistencia de datos y realiza consultas eficientes a la base de datos.

## Funcionalidades

### 1. **Buscar Libro**
   - Si el libro existe, muestra su información completa (título, autor, idioma, número de descargas) y posteriormente se agraga a la base de datos

### 2. **Mostrar Libros Buscados**
   - Muestra una lista de los libros que han sido buscados y almacenados en la base de datos.
   - Cada libro se presenta con su título, autor, idioma y otros detalles relevantes.

### 3. **Buscar Libro por Título**
   - Permite al usuario buscar un libro específico mediante su título.
   - Si el libro se encuentra en la base de datos, se muestra su información, de lo contrario, se indica que el libro no está registrado.

### 4. **Mostrar los Autores**
   - Muestra una lista de todos los autores disponibles en la base de datos.

### 5. **Mostrar Autores Vivos en Determinado Año**
   - Permite consultar si un autor estaba vivo en un año específico.
   - Si un autor nació antes de esa fecha y no ha muerto antes de ella, se considera que estaba vivo en ese año.

### 6. **Buscar Libros por Idioma**
   - Permite filtrar y buscar libros por su idioma.
   - Se muestran todos los libros que coinciden con el idioma seleccionado.

- **Interacción por consola**: El usuario puede interactuar con el sistema a través de opciones en la consola.
