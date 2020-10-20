package io.realm.androidtodoapp.ui

import android.view.*
import android.widget.CheckBox
import android.widget.PopupMenu
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import io.realm.androidtodoapp.R
import io.realm.OrderedRealmCollection
import io.realm.Realm
import io.realm.RealmRecyclerViewAdapter
import io.realm.androidtodoapp.model.Item
import io.realm.kotlin.where
import org.bson.types.ObjectId

internal class ItemsRecyclerAdapter(data: OrderedRealmCollection<Item>) : RealmRecyclerViewAdapter<Item, ItemsRecyclerAdapter.ItemViewHolder?>(data, true) {
    internal inner class ItemViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var name: TextView = view.findViewById(R.id.body)
        var checked: CheckBox = view.findViewById(R.id.checkbox)
        var data: Item? = null
        var menu: TextView = view.findViewById(R.id.menu)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val itemView: View = LayoutInflater.from(parent.context).inflate(R.layout.item_view, parent, false)
        return ItemViewHolder(itemView)

    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val obj: Item? = getItem(position)
        holder.data = obj
        holder.name.text = obj?.body



        // multiselect popup to control status
        holder.itemView.setOnClickListener {
            run {


                val popup = PopupMenu(holder.itemView.context, holder.menu)
                val menu = popup.menu


                // add a delete button to the menu, identified by the delete code
                val deleteCode = -1
                menu.add(0, deleteCode, Menu.NONE, "Delete Item")

                // handle clicks for each button based on the code the button passes the listener
                popup.setOnMenuItemClickListener { item: MenuItem? ->
                    when (item!!.itemId) {

                        deleteCode -> {
                            removeAt(holder.data?._id!!)
                        }
                    }

                    true
                }
                popup.show()
            }}

    }

    private fun removeAt(id: ObjectId) {
        // need to create a separate instance of realm to issue an update, since this event is
        // handled by a background thread and realm instances cannot be shared across threads
        val bgRealm = Realm.getDefaultInstance()
        // execute Transaction (not async) because remoteAt should execute on a background thread
        bgRealm!!.executeTransaction {
            // using our thread-local new realm instance, query for and delete the task
            val item = it.where<Item>().equalTo("_id", id).findFirst()
            item?.deleteFromRealm()
        }
        // always close realms when you are done with them!
        bgRealm.close()
    }
}