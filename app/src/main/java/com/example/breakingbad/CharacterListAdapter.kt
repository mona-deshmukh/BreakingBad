package com.example.breakingbad

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*


class CharacterListAdapter(var dataSource: List<Character>) :
    RecyclerView.Adapter<CharacterListAdapter.ViewHolder>() {

    var onImageClick: ((Character) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.character_list_item, parent, false)
        val holder = ViewHolder(view)

        holder.container.setOnClickListener {
            onImageClick?.invoke(dataSource[holder.bindingAdapterPosition])
        }
        return holder
    }

    override fun getItemCount() = dataSource.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.characterName.text = dataSource[position].name

        Log.e("Date ","${dataSource[position].birthday}")

        try {
            val format: DateFormat = SimpleDateFormat("mm-dd-yyyy", Locale.ENGLISH)
            val date: Date = format.parse(dataSource[position].birthday)
            val cal = Calendar.getInstance()
            cal.isLenient = false
            cal.time = date
            val pattern = "MMMM d, yyyy"
            val df: DateFormat = SimpleDateFormat(pattern)
            val dateAsString = df.format(date)
            holder.characterAge.text = dateAsString
        } catch (e: Exception) {
            holder.characterAge.text = "--"
            println("Invalid date")
        }

        if (!dataSource[position].img.isNullOrBlank()) {
            Picasso.get().load(dataSource[position].img)
                .placeholder(R.drawable.picture_placeholder)
                .error(R.drawable.picture_placeholder).into(holder.imageView)
        } else {
            Picasso.get().load(R.drawable.picture_placeholder).error(R.drawable.picture_placeholder)
                .into(holder.imageView)
        }
    }

    inner class ViewHolder internal constructor(itemView: View) :
        RecyclerView.ViewHolder(itemView) {
        val characterName: TextView = itemView.findViewById(R.id.characterName)
        val characterAge: TextView = itemView.findViewById(R.id.characterBirthday)
        val imageView: ImageView = itemView.findViewById(R.id.characterImage)
        val container: CardView = itemView.findViewById(R.id.container)
    }
}

