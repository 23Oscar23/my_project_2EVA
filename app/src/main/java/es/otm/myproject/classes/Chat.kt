package es.otm.myproject.classes

import java.util.Date

data class Chat(var id: String? = null,
                var userId: String = "",
                var title:String = "",
                var content:String = "",
                var createdAt: Date? = null)
