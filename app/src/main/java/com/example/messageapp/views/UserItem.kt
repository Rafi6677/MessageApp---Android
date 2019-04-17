package com.example.messageapp.views

import com.example.messageapp.R
import com.example.messageapp.models.User
import com.squareup.picasso.Picasso
import com.xwray.groupie.Item
import com.xwray.groupie.ViewHolder
import kotlinx.android.synthetic.main.user_row_new_message.view.*

class UserItem(val user: User): Item<ViewHolder>() {

    override fun bind(viewHolder: ViewHolder, position: Int) {
        viewHolder.itemView.usernameTextViewNewMessage.text = user.username

        Picasso.get().load(user.profileImageUrl).into(viewHolder.itemView.imageNewMessage)
    }

    override fun getLayout(): Int {
        return R.layout.user_row_new_message
    }
}