package co.edu.poli.examen2.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.edu.poli.examen2.model.Producto;
import co.edu.poli.examen2.repository.ProductoRepository;

@Service
public class ProductoService {
    @Autowired
    private ProductoRepository productoRepository;

    public List<Producto> getAllProductos() {
        return productoRepository.findAll();
    }

    public Producto getProductoById(String id) {
        return productoRepository.findById(id).orElse(null);
    }

    public Producto saveProducto(Producto producto) {
        return productoRepository.save(producto);
    }

    public Producto updateProducto(Producto producto) {
        if (productoRepository.existsById(producto.getId())) {
            return productoRepository.save(producto);
        } else {
            return null;
        }
    }

    public Producto deleteProducto(String id) {
        Producto producto = getProductoById(id);
        if (producto == null) {
            return null;
        }
        productoRepository.deleteById(id);
        return producto;
    }
}
