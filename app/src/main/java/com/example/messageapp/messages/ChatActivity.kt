package com.example.messageapp.messages

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.example.messageapp.R
import com.example.messageapp.models.ChatMessage
import com.example.messageapp.models.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.Item
import com.xwray.groupie.ViewHolder
import kotlinx.android.synthetic.main.activity_chat.*
import kotlinx.android.synthetic.main.chat_from_row.view.*
import kotlinx.android.synthetic.main.chat_to_row.view.*

class ChatActivity : AppCompatActivity() {

    val adapter = GroupAdapter<ViewHolder>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)

        recyclerViewChat.adapter = adapter

        val user = intent.getParcelableExtra<User>(NewMessageActivity.USER_KEY)

        supportActionBar?.title = user.username

        //setupData()
        listenForMessages()

        sendButton.setOnClickListener {
            sendMessage()
        }
    }

    private fun setupData() {
        val adapter = GroupAdapter<ViewHolder>()

        adapter.add(ChatFromItem("From message"))
        adapter.add(ChatToItem("To message"))

        recyclerViewChat.adapter = adapter
    }

    private fun listenForMessages() {
        val ref = FirebaseDatabase.getInstance().getReference("/messages")

        ref.addChildEventListener(object: ChildEventListener {
            override fun onChildAdded(p0: DataSnapshot, p1: String?) {
                val chatMessage = p0.getValue(ChatMessage::class.java)

                if(chatMessage != null) {

                    if(chatMessage.fromId == FirebaseAuth.getInstance().uid) {
                        adapter.add(ChatFromItem(chatMessage.text))
                    }
                    else {
                        adapter.add(ChatToItem(chatMessage.text))
                    }
                }
            }

            override fun onChildMoved(p0: DataSnapshot, p1: String?) {

            }

            override fun onChildChanged(p0: DataSnapshot, p1: String?) {

            }

            override fun onChildRemoved(p0: DataSnapshot) {

            }

            override fun onCancelled(p0: DatabaseError) {

            }

        })
    }

    private fun sendMessage() {
        val text = editTextChat.text.toString()
        val user = intent.getParcelableExtra<User>(NewMessageActivity.USER_KEY)

        val fromId = FirebaseAuth.getInstance().uid
        val toId = user.uid

        if(fromId == null) return

        val ref = FirebaseDatabase.getInstance().getReference("/messages").push()

        val chatMessage = ChatMessage(ref.key!!, text, fromId!!, toId, System.currentTimeMillis() / 1000)

        ref.setValue(chatMessage)
            .addOnSuccessListener {
                println("Message saved: ${ref.key}")
            }
    }
}


class ChatFromItem(val text: String): Item<ViewHolder>() {
    override fun bind(viewHolder: ViewHolder, position: Int) {
        viewHolder.itemView.textViewFrom.text = text
    }

    override fun getLayout(): Int {
        return R.layout.chat_from_row
    }
}

class ChatToItem(val text: String): Item<ViewHolder>() {
    override fun bind(viewHolder: ViewHolder, position: Int) {
        viewHolder.itemView.textViewTo.text = text
    }

    override fun getLayout(): Int {
        return R.layout.chat_to_row
    }
}