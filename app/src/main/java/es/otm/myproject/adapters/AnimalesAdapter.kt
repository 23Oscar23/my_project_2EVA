package es.otm.myproject.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import es.otm.myproject.R
import es.otm.myproject.classes.Animales

class AnimalesAdapter(private val context: Context,
                      private val animales: MutableList<Animales>,
                      private val mListener: (Animales) -> Unit):
RecyclerView.Adapter<AnimalesAdapter.AnimalesViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AnimalesViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.rec_item, parent, false)
        return AnimalesViewHolder(view)
    }

    override fun getItemCount(): Int {
       return animales.size
    }

    override fun onBindViewHolder(holder: AnimalesViewHolder, position: Int) {
        val item = animales[position]
        holder.bindItem(item)
        holder.itemView.setOnClickListener{mListener(item)}
    }

    class AnimalesViewHolder(view: View):RecyclerView.ViewHolder(view){
        private val txtName: TextView = view.findViewById(R.id.txtAnimales)
        private val photo: ImageView = view.findViewById(R.id.imageMascotas)

        fun bindItem(animal:Animales){
            photo.setImageResource(animal.image)
            txtName.text = animal.name
        }
    }
}