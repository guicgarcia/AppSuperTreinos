package com.example.t_gamer.supertreinos.ui.user

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.t_gamer.supertreinos.R
import com.example.t_gamer.supertreinos.entities.User
//import kotlinx.android.synthetic.main.activity_register.view.*
import kotlinx.android.synthetic.main.item_user_edit.view.*
import kotlinx.android.synthetic.main.item_user_show.view.*

class UserAdapter(var users: MutableList<User>, var listener: UserAdapterListener) :
    RecyclerView.Adapter<UserAdapter.UserViewHolder>() {
    private var userEditing: User? = null

    fun createUser(): Int {
        val position = 0
        val user = User("", "", "", "")
        users.add(position, user)
        userEditing = user
        notifyItemInserted(position)
        return position
    }

    override fun getItemCount() = users.size

    override fun getItemViewType(position: Int) =
        if (users[position] === userEditing)
            R.layout.item_user_edit
        else
            R.layout.item_user_show

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        UserViewHolder(
            LayoutInflater
                .from(parent.context)
                .inflate(viewType, parent, false)
        )

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        holder.fillUI(users[position])
    }

    fun notify(user: User, clear: Boolean = false) {
        val position = users.indexOf(user)
        userEditing = if (clear) null else user
        notifyItemChanged(position)
    }

    inner class UserViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun fillUI(user: User) {
            if (user === userEditing) {
                itemView.txtCad.setText(user.name)
                itemView.txtEmail.setText(user.email)
                itemView.txtUserName.setText(user.username)
                itemView.txtPassword.setText(user.password)
                itemView.btSave.setOnClickListener {
                    user.name = itemView.txtCad.text.toString()
                    user.email = itemView.txtEmail.text.toString()
                    user.username = itemView.txtEmail.text.toString()
                    user.password = itemView.txtPassword.text.toString()
                    listener.userSaved(user)
                    notify(user, true)
                }
                itemView.btDelete.setOnClickListener {
                    val position = users.indexOf(user)
                    notifyItemRemoved(position)
                    listener.userRemoved(user)
                    users.remove(user)
                    userEditing = null
                }
            } else {
                itemView.lblName.text = user.name

                itemView.setOnClickListener {
                    notify(user)
                }
                itemView.setOnLongClickListener {
                    listener.userSaved(user)
                    notify(user, true)
                    true
                }
                itemView.btShare.setOnClickListener {
                    val shareIntent = Intent(Intent.ACTION_SEND)
                    with(shareIntent) {
                        type = "text/plain"
                        //val message = "${itemView.context.getString(R.string.share_message)} ${user.nome}"
                        //putExtra(Intent.EXTRA_TEXT, message)
                    }
                    itemView.context.startActivity(shareIntent)
                }
            }

        }

    }

}