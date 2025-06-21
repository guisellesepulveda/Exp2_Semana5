/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.proyecto_biblioteca.procesos;

import com.proyecto_biblioteca.objetos.*;
import com.proyecto_biblioteca.excepciones.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.TreeSet;

/**
 * Clase Biblioteca
 * 
 * @author jennifer y guiselle
 */

public class Biblioteca {
    private List<Libro> libros;
    private HashSet<Usuario> usuarios;
    private TreeSet<String> usernames;
    private HashMap<Usuario, List<Libro>> prestamos;

    public Biblioteca() {
        this.libros = new ArrayList<>();
        this.usuarios = new HashSet<>();
        this.usernames = new TreeSet<>();
        this.prestamos = new HashMap<>();
    }

    public List<Libro> getLibros() {
        return libros;
    }

    public void setLibros(List<Libro> libros) {
        this.libros = libros;
    }

    public HashSet<Usuario> getUsuarios() {
        return usuarios;
    }

    public void setUsuarios(HashSet<Usuario> usuarios) {
        this.usuarios = usuarios;
    }

    public HashMap<Usuario, List<Libro>> getPrestamos() {
        return prestamos;
    }

    public void setPrestamos(HashMap<Usuario, List<Libro>> prestamos) {
        this.prestamos = prestamos;
    }

    public TreeSet<String> getUsernames() {
        return usernames;
    }

    public void setUsernames(TreeSet<String> usernames) {
        this.usernames = usernames;
    }

    public void agregarLibro(Libro libro) {
        libros.add(libro);
    }

    public Libro buscarLibro(String titulo) throws LibroNoEncontradoException {
        for (Libro libro : libros) {
            if (libro.getTitulo().equals(titulo)) {
                return libro;
            }
        }
        throw new LibroNoEncontradoException("Lo sentimos. El libro que busca no está en la biblioteca.");
    }

    public boolean libroExiste(String titulo) {
        for (Libro libro : libros) {
            if (libro.getTitulo().equals(titulo)) {
                System.out.println("Este título ya existe en la biblioteca.\nAhora regresará al menú.");
                return true;
            }
        }
        return false;
    }

    public void mostrarLibros() {
        for (Libro libro : libros) {
            System.out.println(libro.toString());
        }
    }

    public boolean bibliotecaVacia() {
        return libros.isEmpty();
    }

    public boolean usuariosVacia() {
        return usuarios.isEmpty();
    }

    public boolean registrarUsuario(Usuario usuario) {
        if (usuarios.contains(usuario)) {
            System.out.println("Error: El usuario ya está registrado en el sistema.");
            return false;
        }
        usuarios.add(usuario);
        prestamos.put(usuario, new ArrayList<>());
        usernames.add(usuario.getUsername());
        return true;
    }

    public Usuario buscarUsuario(String rut) {
        for (Usuario usuario : usuarios) {
            if (usuario.getRut().equals(rut)) {
                return usuario;
            }
        }
        return null;
    }

    public boolean prestamosPorUsuario(Usuario usuario) {
        if (prestamos.get(usuario).isEmpty()) {
            System.out.println("No tiene libros en préstamo.");
            return false;
        } else {
            System.out.println("\nLibros prestados: ");
            for (Libro libro : prestamos.get(usuario)) {
                System.out.println(libro.toString());
            }
            return true;
        }
    }

    public void prestarLibro(String titulo, String rut) throws LibroNoEncontradoException, LibroYaPrestadoException {
        Libro libro = buscarLibro(titulo);
        Usuario usuario = buscarUsuario(rut);

        if (usuario == null) {
            System.out.println("El usuario con el RUT proporcionado no existe.");
            return;
        }

        if (!libro.isDisponible()) {
            throw new LibroYaPrestadoException("El libro no esta disponible para su préstamo.");
        } else {
            libro.setDisponible(false);
            prestamos.get(usuario).add(libro);
            System.out.println("¡Préstamo realizado con éxito!");
        }
    }

    public void devolverLibro(String titulo, String rut) {
        Usuario usuario = buscarUsuario(rut);
        if (usuario == null) {
            System.out.println("El usuario con el RUT proporcionado no existe.");
            System.out.println("Ahora regresará al menú.");
            return;
        }
        Libro libroADevolver = null;

        List<Libro> librosPrestados = prestamos.get(usuario);
        if (librosPrestados == null) {
            System.out.println("El usuario no tiene libros registrados para préstamo.");
            System.out.println("Ahora regresará al menú.");
            return;
        }

        for (Libro libro : librosPrestados) {
            if (libro.getTitulo().equals(titulo)) {
                libroADevolver = libro;
                break;
            }
        }

        if (libroADevolver != null) {
            libroADevolver.setDisponible(true);
            prestamos.get(usuario).remove(libroADevolver);
            System.out.println("¡Libro devuelto con éxito!");
        } else {
            System.out.println("El libro que desea devolver no coincide con los que tiene en préstamo.");
            System.out.println("Ahora regresará al menú.");
        }
    }

    public void mostrarUsernames() {
        if (usernames.isEmpty()) {
            System.out.println("No hay usuarios registrados.");
        } else {
            System.out.println("\nUsernames registrados (ordenados alfabéticamente):");
            for (String username : usernames) {
                System.out.println("- " + username);
            }
        }
    }

}
