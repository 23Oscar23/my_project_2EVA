package es.otm.myproject.adapters

import android.content.Context
import android.content.res.ColorStateList
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.card.MaterialCardView
import es.otm.myproject.R
import es.otm.myproject.classes.Chat

class ChatAdapter(private val context: Context,
                  private val listChat: MutableList<Chat>,
                  private val selectedColor: Int):
RecyclerView.Adapter<ChatAdapter.ChatViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChatViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.chat_view, parent, false)
        return ChatViewHolder(view)
    }

    override fun getItemCount(): Int {
        return listChat.size
    }

    override fun onBindViewHolder(holder: ChatViewHolder, position: Int) {
        val mensaje = listChat[position]
        holder.bind(mensaje)

        val colorStateList = ColorStateList.valueOf(selectedColor)
        holder.card.backgroundTintList = colorStateList
    }

    class ChatViewHolder(view: View): RecyclerView.ViewHolder(view){
        private val userTextView = view.findViewById<TextView>(R.id.usuarioMensaje)
        private val contentTextView = view.findViewById<TextView>(R.id.interiorChat)
        val card = itemView.findViewById<MaterialCardView>(R.id.card)
        fun bind(chat: Chat){
            userTextView.text = chat.title
            contentTextView.text = chat.content
        }
    }

}