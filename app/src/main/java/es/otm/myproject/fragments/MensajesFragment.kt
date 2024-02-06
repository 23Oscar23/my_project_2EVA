package es.otm.myproject.fragments

import android.content.Context
import android.content.SharedPreferences
import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import es.otm.myproject.AuthManager
import es.otm.myproject.FirebaseChat
import es.otm.myproject.R
import es.otm.myproject.SettingsActivity
import es.otm.myproject.adapters.ChatAdapter
import es.otm.myproject.classes.Chat
import es.otm.myproject.databinding.FragmentMensajesBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MensajesFragment : Fragment() {

    private lateinit var binding: FragmentMensajesBinding
    private lateinit var mAdapter: ChatAdapter
    private lateinit var pref : SharedPreferences
    private val firestoreManager: FirebaseChat by lazy { FirebaseChat() }
    private lateinit var listMensajes: MutableList<Chat>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentMensajesBinding.inflate(layoutInflater)

        pref = requireContext().getSharedPreferences("es.otm.myproject_preferences", Context.MODE_PRIVATE)

        binding.btnEnviarMensajes.setOnClickListener{
            var user = pref.getString(SettingsActivity.USERNAME, "").toString()
            if (user.isNullOrEmpty()){
                user = AuthManager().getCurrentUser()?.email.toString()
            }
            val mensaje = binding.contenidoMensaje.text.toString()
            mensajesChat(user, mensaje)
        }

        setUpRecyler()
        return binding.root
    }

    fun setUpRecyler(){
        val selectedColor = pref.getInt(SettingsActivity.COLOR, Color.BLACK)
        listMensajes = mutableListOf()
        binding.recViewMensajes.layoutManager = GridLayoutManager(requireContext(), 1)
        mAdapter = ChatAdapter(requireContext(), listMensajes, selectedColor)
        binding.recViewMensajes.adapter = mAdapter

        lifecycleScope.launch(Dispatchers.IO){
            firestoreManager.getNotesFlow()
                .collect{ mensajesUpdated ->
                    listMensajes.clear()
                    listMensajes.addAll(mensajesUpdated)
                    withContext(Dispatchers.Main){
                        mAdapter.notifyDataSetChanged()
                    }
                }
        }
    }

    private fun mensajesChat(usuario: String, contenido: String){
        lifecycleScope.launch(Dispatchers.IO){
            try {
                val newMensaje = Chat(title = usuario, content = contenido)
                val inserted = firestoreManager.add(newMensaje)

                withContext(Dispatchers.Main){
                    if (inserted) {
                        Toast.makeText(requireContext(), "Mensaje enviado", Toast.LENGTH_SHORT)
                            .show()
                    }else{
                        Toast.makeText(requireContext(), "Error al enviar mensaje", Toast.LENGTH_SHORT)
                            .show()
                    }
                }
            }catch (e: Exception){
                withContext(Dispatchers.Main){
                    Toast.makeText(requireContext(), "Mensaje no se ha podido enviar", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }


}