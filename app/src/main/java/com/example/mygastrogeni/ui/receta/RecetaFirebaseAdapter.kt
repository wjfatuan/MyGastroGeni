package com.example.mygastrogeni.ui.receta

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.mygastrogeni.ui.home.DetalleRecetaActivity
import com.example.mygastrogeni.R
import com.example.mygastrogeni.ui.home.Receta
import com.squareup.picasso.Picasso

class RecetaFirebaseAdapter(private var listaRecetas: List<Receta>) :
    RecyclerView.Adapter<RecetaFirebaseAdapter.RecetaViewHolder>() {

    private lateinit var context: Context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecetaViewHolder {
        context = parent.context
        val view = LayoutInflater.from(context).inflate(R.layout.item_receta, parent, false)
        return RecetaViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecetaViewHolder, position: Int) {
        val receta = listaRecetas[position]

        // Vincula los datos con las vistas del layout
        holder.textNombre.text = receta.nombre
        holder.textIngredientes.text = receta.ingredientes

        // Si la receta tiene una URL de imagen, la carga usando Picasso
        if (receta.imagenUri.isNotEmpty()) {
            Picasso.get().load(receta.imagenUri).into(holder.imageReceta)
        } else {
            holder.imageReceta.setImageResource(R.drawable.fav1) // Imagen por defecto
        }

        holder.itemView.setOnClickListener {
            val intent = Intent(context, DetalleRecetaActivity::class.java).apply {
                putExtra("id", receta.id)
                putExtra("nombre", receta.nombre)
                putExtra("descripcion", receta.descripcion)
                putExtra("ingredientes", receta.ingredientes)
                putExtra("pasos", receta.pasos)
                putExtra("imagenUri", receta.imagenUri)
                putExtra("autor", receta.autor) // Añadido el autor
            }
            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return listaRecetas.size
    }

    // Método para actualizar la lista de recetas
    fun actualizarLista(nuevaLista: List<Receta>) {
        listaRecetas = nuevaLista
        notifyDataSetChanged()
    }

    // ViewHolder para cada ítem de la lista
    class RecetaViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textNombre: TextView = itemView.findViewById(R.id.textNombreReceta)
        val textIngredientes: TextView = itemView.findViewById(R.id.textIngredientesReceta)
        val imageReceta: ImageView = itemView.findViewById(R.id.imageReceta)
    }
}