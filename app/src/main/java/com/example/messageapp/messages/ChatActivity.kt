package com.example.messageapp.messages

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.example.messageapp.R
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.Item
import com.xwray.groupie.ViewHolder
import kotlinx.android.synthetic.main.activity_chat.*

class ChatActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)

        supportActionBar?.title = "Chat"

        val adapter = GroupAdapter<ViewHolder>()

        adapter.add(ChatItem())

        recyclerViewChat.adapter = adapter
    }
}


class ChatItem: Item<ViewHolder>() {
    override fun bind(viewHolder: ViewHolder, position: Int) {

    }

    override fun getLayout(): Int {
        return R.layout.chat_from_row
    }
}