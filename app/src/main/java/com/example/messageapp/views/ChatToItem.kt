package com.example.messageapp.views

import com.example.messageapp.R
import com.example.messageapp.models.User
import com.squareup.picasso.Picasso
import com.xwray.groupie.Item
import com.xwray.groupie.ViewHolder
import kotlinx.android.synthetic.main.chat_to_row.view.*

class ChatToItem(val text: String, val user: User): Item<ViewHolder>() {
    override fun bind(viewHolder: ViewHolder, position: Int) {
        viewHolder.itemView.textViewTo.text = text

        val uri = user.profileImageUrl
        val image = viewHolder.itemView.imageTo
        Picasso.get().load(uri).into(image)
    }

    override fun getLayout(): Int {
        return R.layout.chat_to_row
    }
}