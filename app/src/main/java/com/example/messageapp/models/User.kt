package com.example.messageapp.models

import android.os.Parcelable
import com.example.messageapp.R
import com.squareup.picasso.Picasso
import com.xwray.groupie.Item
import com.xwray.groupie.ViewHolder
import kotlinx.android.parcel.Parcelize
import kotlinx.android.synthetic.main.user_row_new_message.view.*

@Parcelize
class User(val uid: String, val username: String, val profileImageUrl: String) : Parcelable {
    constructor() : this("", "", "")
}

class UserItem(val user: User): Item<ViewHolder>() {

    override fun bind(viewHolder: ViewHolder, position: Int) {
        viewHolder.itemView.usernameTextViewNewMessage.text = user.username

        Picasso.get().load(user.profileImageUrl).into(viewHolder.itemView.imageNewMessage)
    }

    override fun getLayout(): Int {
        return R.layout.user_row_new_message
    }
}