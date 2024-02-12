package es.otm.myproject.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import android.content.res.ColorStateList
import android.graphics.Color
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.card.MaterialCardView
import es.otm.myproject.AuthManager
import es.otm.myproject.R
import es.otm.myproject.SettingsActivity
import es.otm.myproject.classes.Chat

class ChatAdapter(private val context: Context,
                  private val listChat: MutableList<Chat>):
RecyclerView.Adapter<ChatAdapter.ChatViewHolder>(){

    private lateinit var view: View

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChatViewHolder {
        view = LayoutInflater.from(parent.context).inflate(R.layout.chat_view, parent, false)
        return ChatViewHolder(view)
    }

    override fun getItemCount(): Int {
        return listChat.size
    }

    override fun onBindViewHolder(holder: ChatViewHolder, position: Int) {
        val mensaje = listChat[position]
        holder.bind(mensaje)
    }

    @SuppressLint("NotifyDataSetChanged")
    fun updateList(newList: List<Chat>) {
        listChat.clear()
        listChat.addAll(newList)
        notifyDataSetChanged()
    }

    class ChatViewHolder(view: View): RecyclerView.ViewHolder(view){
        private val userTextView = view.findViewById<TextView>(R.id.usuarioMensaje)
        private val contentTextView = view.findViewById<TextView>(R.id.interiorChat)
        private val card = itemView.findViewById<MaterialCardView>(R.id.card)
        fun bind(chat: Chat) {
            val pref = itemView.context.getSharedPreferences("es.otm.myproject_preferences", Context.MODE_PRIVATE)
            val userName = AuthManager().getCurrentUser()?.email.toString()

            if (chat.userId == userName) {
                card.layoutDirection = View.LAYOUT_DIRECTION_RTL
                userTextView.gravity = Gravity.END
                contentTextView.gravity = Gravity.END
            }
            else {
                card.layoutDirection = View.LAYOUT_DIRECTION_LTR
                userTextView.gravity = Gravity.START
                contentTextView.gravity = Gravity.START
            }

            userTextView.text = chat.title
            contentTextView.text = chat.content
            val colorStateList = ColorStateList.valueOf(pref.getInt(SettingsActivity.COLOR, Color.YELLOW))
            card.backgroundTintList = colorStateList
        }
    }

}