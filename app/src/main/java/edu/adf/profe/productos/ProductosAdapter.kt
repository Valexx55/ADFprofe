package edu.adf.profe.productos

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import edu.adf.profe.R
import edu.adf.profe.databinding.FilaProductoBinding

class ProductosAdapter(var listaProductos: ListaProductos):RecyclerView.Adapter<ProductoViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductoViewHolder {
        val binding = FilaProductoBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ProductoViewHolder(binding)
    }

    override fun getItemCount(): Int {
       return this.listaProductos.size
    }

    override fun onBindViewHolder(holder: ProductoViewHolder, position: Int) {
        val productoActual = this.listaProductos[position]
        holder.rellenarFilaProducto(productoActual)
    }
}