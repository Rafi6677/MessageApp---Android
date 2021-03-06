package com.example.messageapp.views

import com.example.messageapp.R
import com.example.messageapp.models.ChatMessage
import com.example.messageapp.models.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.squareup.picasso.Picasso
import com.xwray.groupie.Item
import com.xwray.groupie.ViewHolder
import kotlinx.android.synthetic.main.latest_messages_row.view.*

class LatestMessageRow(val chatMessage: ChatMessage): Item<ViewHolder>(){
    var chatPartner: User ?= null

    override fun bind(viewHolder: ViewHolder, position: Int) {
        val chatPartnerId: String

        if(chatMessage.fromId == FirebaseAuth.getInstance().uid) {
            chatPartnerId = chatMessage.toId
        }
        else {
            chatPartnerId = chatMessage.fromId
        }

        val ref = FirebaseDatabase.getInstance().getReference("/users/$chatPartnerId")

        ref.addListenerForSingleValueEvent(object: ValueEventListener {
            override fun onDataChange(p0: DataSnapshot) {
                chatPartner = p0.getValue(User::class.java)

                viewHolder.itemView.usernameTextView.text = chatPartner?.username
                Picasso.get().load(chatPartner?.profileImageUrl).into(viewHolder.itemView.imageLatestMessage)
            }

            override fun onCancelled(p0: DatabaseError) {}
        })

        viewHolder.itemView.messageTextView.text = chatMessage.text
    }

    override fun getLayout(): Int {
        return R.layout.latest_messages_row
    }
}