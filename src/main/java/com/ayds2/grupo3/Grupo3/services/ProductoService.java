package com.ayds2.grupo3.Grupo3.services;

import org.springframework.stereotype.Service;
import com.ayds2.grupo3.Grupo3.dao.ProductoDAO;
import com.ayds2.grupo3.Grupo3.models.Producto;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class ProductoService {

    private final ProductoDAO productoDao;

    public Producto getProductoPorId(int productoId) {
        return productoDao.getPorId(productoId);
    }

}
