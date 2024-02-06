package es.otm.myproject.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import es.otm.myproject.R


class CatsAdapter(private val images: MutableList<String>,
                  private val description: MutableList<String>)
    : RecyclerView.Adapter<CatsAdapter.CatViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CatViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return CatViewHolder(layoutInflater.inflate(R.layout.rv_item_cat, parent, false))
    }

    override fun onBindViewHolder(holder: CatViewHolder, position: Int) {
        val url = images[position]
        val desc = description[position]
        holder.bind(url, desc)
    }

    override fun getItemCount(): Int {
        return images.size
    }

    class CatViewHolder(view: View): RecyclerView.ViewHolder(view){
        private val imageView = view.findViewById<ImageView>(R.id.imageViewCat)
        private val textDescription = view.findViewById<TextView>(R.id.textView12)

        fun bind(url: String, desc: String){
            Picasso.get().load(url).into(imageView)
            textDescription.text = desc
        }
    }

}