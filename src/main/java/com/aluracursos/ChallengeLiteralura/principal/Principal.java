package com.aluracursos.ChallengeLiteralura.principal;

import com.aluracursos.ChallengeLiteralura.model.Autor;
import com.aluracursos.ChallengeLiteralura.model.DatosLibro;
import com.aluracursos.ChallengeLiteralura.model.Libro;
import com.aluracursos.ChallengeLiteralura.model.LibroAPI;
import com.aluracursos.ChallengeLiteralura.repositorio.AutorRepository;
import com.aluracursos.ChallengeLiteralura.repositorio.LibroRepository;
import com.aluracursos.ChallengeLiteralura.service.ConsumoAPI;
import com.aluracursos.ChallengeLiteralura.service.ConvierteDatos;
import jakarta.transaction.Transactional;
import org.springframework.dao.InvalidDataAccessApiUsageException;

import java.util.*;

public class Principal {
    private Scanner input = new Scanner(System.in);
    private ConsumoAPI consumoApi = new ConsumoAPI();
    private ConvierteDatos convertir = new ConvierteDatos();
    private static String API_BASE = "https://gutendex.com/books/?search=";
    private List<Libro> datosLibro = new ArrayList<>();
    private LibroRepository libroRepository;
    private AutorRepository autorRepository;

    public Principal(LibroRepository libroRepository, AutorRepository autorRepository) {
        this.libroRepository = libroRepository;
        this.autorRepository = autorRepository;
    }

    public void mostrarMenu(){
        var opcion = -1;
        while (opcion != 0){
            var menu = """
                    
                    **--Bienvenido al challenge LiterAlura--**
                    ¿Qué deseas hacer a continuación?
                    
                    1 - Buscar Libro 
                    2 - Mostrar libros buscados
                    3 - Buscar libro por titulo
                    4 - Mostrar los autores
                    5 - Mostrar autores vivos en determinado año
                    6 - Buscar libros por idioma
                    
                    0 - Salir
                    *--------------------------------------------------*
                    Por favor, ingrese una opción: 

                    """;

            try {
                System.out.println(menu);
                opcion = input.nextInt();
                input.nextLine();
            } catch (InputMismatchException e) {

                System.out.println("**--Por favor, ingrese una opcion valida--**");
                input.nextLine();
                continue;
            }



            switch (opcion){
                case 1:
                    buscarLibroEnLaWeb();
                    break;
                case 2:
                    librosBuscados();
                    break;
                case 3:
                    buscarLibroPorTitulo();
                    break;
                case 4:
                    mostrarAutores();
                    break;
                case 5:
                    mostrarAutoresPorFecha();
                    break;
                case 6:
                    buscarLibrosPorIdioma();
                    break;
                case 0:
                    System.out.println("**--Saliendo de la aplicacion--**");
                    break;
                default:
                    System.out.println("**--Ingrese una opcion valida--**");
                    mostrarMenu();
                    break;
            }
        }
    }

    private Libro getDatosLibro(){
        System.out.println("Ingrese el nombre del libro: ");
        var nombreLibro = input.nextLine().toLowerCase();
        var json = consumoApi.obtenerDatos(API_BASE + nombreLibro.replace(" ", "%20"));
        //System.out.println("JSON INICIAL: " + json);
        LibroAPI datos = convertir.obtenerDatos(json, LibroAPI.class);

        if (datos != null && datos.getResultadoLibros() != null && !datos.getResultadoLibros().isEmpty()) {
            DatosLibro primerLibro = datos.getResultadoLibros().get(0); // Obtener el primer libro de la lista
            return new Libro(primerLibro);
        } else {
            System.out.println("No se encontraron resultados.");
            return null;
        }
    }

    private void buscarLibroEnLaWeb() {
        Libro libro = getDatosLibro();
        if (libro == null){
            System.out.println("**--Libro no encontrado--**");
            return;
        }

        try{
            boolean libroExiste= libroRepository.existsByTitulo(libro.getTitulo());
            if (libroExiste){
                System.out.println("El libro ya existe en la base de datos!");
            }else {
                libroRepository.save(libro);
                System.out.println(libro);
            }
        }catch (InvalidDataAccessApiUsageException e){
            System.out.println("No se puede persisitir el libro buscado!");
        }
    }

    private void librosBuscados(){
        List<Libro> libros = libroRepository.findAll();
        if (libros.isEmpty()) {
            System.out.println("**--No se ha buscado ninngun libro--**");
        } else {
            System.out.println("**--Libros buscados: ");
            for (Libro libro : libros) {
                System.out.println(libro.toString());
            }
        }
    }

    private void buscarLibroPorTitulo() {
        System.out.println("**--Ingrese el titulo del libro que quiere buscar: ");
        var titulo = input.nextLine();
        Libro libroBuscado = libroRepository.findByTituloContainsIgnoreCase(titulo);
        if (libroBuscado != null) {
            System.out.println("**--Libro Encontrado: " + libroBuscado);
        } else {
            System.out.println("**--Libro no encontrado--**");
        }
    }

    private  void mostrarAutores(){
        List<Autor> autores = autorRepository.findAll();

        if (autores.isEmpty()) {
            System.out.println("No se encontraron libros en la base de datos. \n");
        } else {
            System.out.println("Libros encontrados en la base de datos: \n");
            Set<String> autoresUnicos;
            autoresUnicos = new HashSet<>();
            for (Autor autor : autores) {
                if (autoresUnicos.add(autor.getNombre())){
                    System.out.println(autor.getNombre()+'\n');
                }
            }
        }
    }

    private void mostrarAutoresPorFecha() {
        System.out.println("**--Ingresa la fecha que deseas consultar--**");

        int fechaBuscada;
        try {
            fechaBuscada = input.nextInt();
            input.nextLine();
        } catch (InputMismatchException e) {
            System.out.println("**--Por favor, ingrese un número válido--**");
            input.nextLine(); // Limpia el buffer del scanner
            return;
        }

        List<Autor> autoresVivos = autorRepository.findByFechaDeNacimientoLessThanOrFechaDeMuerteGreaterThanEqual(fechaBuscada, fechaBuscada);

        if (autoresVivos.isEmpty()) {
            System.out.println("**--No se encontró ningún autor con vida en esta fecha--**");
        } else {
            System.out.println("**--Se encontraron los siguientes autores--**");
            Set<String> autoresUnicos = new HashSet<>();
            for (Autor autor : autoresVivos) {
                if (autor.getFechaDeNacimiento() != null && autor.getFechaDeMuerte() != null) {
                    if (autor.getFechaDeNacimiento() <= fechaBuscada && autor.getFechaDeMuerte() >= fechaBuscada) {
                        if (autoresUnicos.add(autor.getNombre())) {
                            System.out.println("**--Autor: " + autor.getNombre());
                        }
                    }
                }
            }
        }
    }


    private void  buscarLibrosPorIdioma(){
        System.out.println("**--Ingrese Idioma en el que quiere buscar: ");
        System.out.println("es - Español");
        System.out.println("en - Inglés");
        System.out.println("fr - Francés");
        System.out.println("pt - Portugués");

        var idioma = input.nextLine();
        List<Libro> librosPorIdioma = libroRepository.findByIdiomas(idioma);

        if (librosPorIdioma.isEmpty()) {
            System.out.println("**--No se encontró ningun libro--**");
        } else {
            System.out.println("**--Libros encontrados: ");
            for (Libro libro : librosPorIdioma) {
                System.out.println(libro.toString());
            }
        }
    }
}
