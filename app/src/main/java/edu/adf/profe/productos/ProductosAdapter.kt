package edu.adf.profe.productos

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import edu.adf.profe.databinding.FilaProductoBinding

class ProductosAdapter(var listaProductos: List<Producto>):RecyclerView.Adapter<ProductoViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductoViewHolder {
        val filaProducto = FilaProductoBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ProductoViewHolder(filaProducto)
    }

    override fun getItemCount(): Int {
       return this.listaProductos.size
    }

    override fun onBindViewHolder(holder: ProductoViewHolder, position: Int) {
        val productoActual = this.listaProductos[position]
        holder.rellenarFilaProducto(productoActual)
    }
}