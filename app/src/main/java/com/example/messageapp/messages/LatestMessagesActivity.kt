package com.example.messageapp.messages

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import com.example.messageapp.R
import com.example.messageapp.models.User
import com.example.messageapp.registerlogin.RegisterActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.squareup.picasso.Picasso
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.Item
import com.xwray.groupie.ViewHolder
import kotlinx.android.synthetic.main.activity_latest_messages.*
import kotlinx.android.synthetic.main.latest_messages_profile_row.view.*
import kotlinx.android.synthetic.main.user_row_new_message.view.*

class LatestMessagesActivity : AppCompatActivity() {

    companion object {
        var currentUser: User? = null
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_latest_messages)

        verifyUserIsLoggedIn()

        fetchCurrentUser()

        setupRows()
    }

    private fun verifyUserIsLoggedIn() {
        val uid = FirebaseAuth.getInstance().uid
        if (uid == null) {
            val intent = Intent(this, RegisterActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK.or(Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)
        }
    }


    private fun fetchCurrentUser() {
        val uid = FirebaseAuth.getInstance().uid
        val ref = FirebaseDatabase.getInstance().getReference("/users/$uid")

        ref.addListenerForSingleValueEvent(object: ValueEventListener {
            override fun onDataChange(p0: DataSnapshot) {
                currentUser = p0.getValue(User::class.java)
            }

            override fun onCancelled(p0: DatabaseError) {

            }
        })
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.nav_menu, menu)

        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            R.id.menuNewMessage -> {
                val intent = Intent(this, NewMessageActivity::class.java)
                startActivity(intent)
            }
            R.id.menuSignOut -> {
                FirebaseAuth.getInstance().signOut()
                val intent = Intent(this, RegisterActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK.or(Intent.FLAG_ACTIVITY_NEW_TASK)
                startActivity(intent)
            }
        }

        return super.onOptionsItemSelected(item)
    }

    private fun setupRows() {
        val adapter = GroupAdapter<ViewHolder>()

        adapter.add(LatestMessageProfileRow())
        adapter.add(LatestMessageRow())
        adapter.add(LatestMessageRow())
        adapter.add(LatestMessageRow())

        recyclerViewLatestMessages.adapter = adapter
    }



    class LatestMessageProfileRow: Item<ViewHolder>() {
        override fun bind(viewHolder: ViewHolder, position: Int) {
            viewHolder.itemView.curentUserName.text = currentUser?.username

            Picasso.get().load(currentUser?.profileImageUrl).into(viewHolder.itemView.currentUserImage)
        }

        override fun getLayout(): Int {
            return R.layout.latest_messages_profile_row
        }
    }

    class LatestMessageRow: Item<ViewHolder>(){
        override fun bind(viewHolder: ViewHolder, position: Int) {

        }

        override fun getLayout(): Int {
            return R.layout.latest_messages_row
        }
    }
}
