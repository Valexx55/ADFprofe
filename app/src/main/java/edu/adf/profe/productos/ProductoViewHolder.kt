package edu.adf.profe.productos

import android.view.View
import androidx.core.net.toUri
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import edu.adf.profe.databinding.FilaProductoBinding

class ProductoViewHolder(val filaProducto: FilaProductoBinding): RecyclerView.ViewHolder(filaProducto.root) {

    val RUTA_FOTO_CHESPIRITO = "https://www.eluniverso.com/resizer/v2/3ZSP5WZAZZG55EX27CJCWUZCDM.jpg?auth=fa020d3b068d8fc1549073ebb5d0d360d1aee522d99a7ae6cc9f692c370be9da&width=622&height=670&quality=75&smart=true"


    fun rellenarFilaProducto(producto: ListaProductosItem)
    {
        this.filaProducto.idProducto.text = producto.id.toString()
        this.filaProducto.nombreProducto.text = producto.name
        this.filaProducto.precioProducto.text = producto.price
        Glide.with(itemView.context)
            .load(RUTA_FOTO_CHESPIRITO.toUri())
            .into(this.filaProducto.imagenProducto)
    }

}