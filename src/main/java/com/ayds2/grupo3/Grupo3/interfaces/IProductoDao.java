package com.ayds2.grupo3.Grupo3.interfaces;

import com.ayds2.grupo3.Grupo3.models.Producto;

public interface IProductoDao {
    Producto getPorId(int id);
}
