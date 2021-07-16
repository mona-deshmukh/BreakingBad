package com.example.breakingbad

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    private lateinit var viewModel: MainActivityViewModel
    private lateinit var characterAdapter: CharacterListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        init()
    }

    private fun init(){
        viewModel = ViewModelProvider(this).get(MainActivityViewModel::class.java)
        viewModel.getCharacters()

        characterAdapter = CharacterListAdapter(ArrayList())
        characterAdapter.onImageClick = {
            Toast.makeText(this, it.name, Toast.LENGTH_SHORT).show()
        }

        recyclerView.apply {
            layoutManager = GridLayoutManager(this@MainActivity, 2)
            adapter = characterAdapter
        }
        characterAdapter.notifyDataSetChanged()

        viewModel.characters.observe(this, Observer {
            if (it == null)
                return@Observer

            characterAdapter.dataSource = it
            characterAdapter.notifyDataSetChanged()
            progressView.visibility = View.GONE
            progressView.stop()
        })

        viewModel.error.observe(this, Observer {
            if (it == null)
                return@Observer
            progressView.visibility = View.GONE
            progressView.stop()

            Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
        })
    }
}