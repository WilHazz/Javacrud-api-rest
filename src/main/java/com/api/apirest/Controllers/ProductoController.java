package com.api.apirest.Controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.api.apirest.Entities.Producto;
import com.api.apirest.Repositories.IproductoRepository;


@RestController
@RequestMapping("/Productos")
public class ProductoController {

    @Autowired
    private IproductoRepository productoRepository;

    /*
     * EL metodo getAllProductos trae todo los producto retornando una lista de productos
     */
    @GetMapping
    public List<Producto> getAllProductos(){
        return productoRepository.findAll();
    }

    /*
     * recuperar un producto por Id si no encuentra el id devuelve un error
     * Para eso se usa orElseThrow para el manejo de excepciones 
     * RuntimeException con un mensaje que incluye el ID que se buscó
     */
    @GetMapping("/{id}")
    public Producto getAllProductoID(@PathVariable long id){
        return productoRepository.findById(id)
        .orElseThrow(() -> new RuntimeException(" No se encontró el producto con el ID " + id));
    }

    /*
     * tenemos el metodo createproducto para crear un nuevo producto usamos RequestBody 
     */
    @PostMapping
    public Producto createProducto(@RequestBody Producto producto){
        return productoRepository.save(producto);
    }
    /*
     * putmapping es la anotacion para actualizar un producto 
     */
    @PutMapping("/{id}")
    public Producto updateProducto(@PathVariable Long id, @RequestBody Producto detalleProducto) {
       Producto producto =  productoRepository.findById(id)
        .orElseThrow(() -> new RuntimeException("No se encontró el producto con el ID " + id));

        producto.setNombre(detalleProducto.getNombre());
        producto.setPrecio(detalleProducto.getPrecio());
        producto.setColor(detalleProducto.getColor());

        return productoRepository.save(producto);
    }

    /*
     * Vamos eliminar un producto por id de la lista de productos
     */

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteProducto(@PathVariable Long id) {
        if (!productoRepository.existsById(id)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                             .body("No se encontró el producto con el ID " + id);
        }

        productoRepository.deleteById(id);
        return ResponseEntity.ok("El producto con el ID: " + id + " fue eliminado correctamente");
    }
}