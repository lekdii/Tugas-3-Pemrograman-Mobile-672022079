package com.diva.tugas3

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import com.diva.tugas3.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: MyViewModel
    var id_user= 1;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(binding.main) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        binding.textTitle.text = "TUGAS 3 - 672022079"

        viewModel = ViewModelProvider(this).get(MyViewModel::class.java)

        viewModel.users.observe(this) { userList ->
            val textData = userList.joinToString(separator = "\n") { User ->
                "ID: ${User.id} \n${User.firstName} ${User.lastName} - ${User.university}"
            }
            binding.textDetailsData.text = if (textData.isEmpty()) "No Data"
            else textData
        }

        binding.btnGetUsers.setOnClickListener {
            id_user = 1
            viewModel.fetchUsers(id_user)
        }

        binding.btnPrevious.setOnClickListener {
            if (id_user == 1) {
                Toast.makeText(this, "ID User mencapai batas minimal", Toast.LENGTH_SHORT).show()
                id_user = 1
            }else{
                id_user -= 1
            }
            viewModel.fetchUsers(id_user)
        }

        binding.btnNext.setOnClickListener {
            if (id_user == 30) {
                Toast.makeText(this, "ID User mencapai batas maksimal", Toast.LENGTH_SHORT).show()
                id_user = 30
            }else{
                id_user += 1
            }
            viewModel.fetchUsers(id_user)
        }

        viewModel.products.observe(this) { avg ->
            binding.textAveragePrice.text = "Average Price: Rp ${String.format("%,.2f", avg)}"
        }

        binding.btnGetProducts.setOnClickListener {
            viewModel.fetchProducts()
        }

        viewModel.errorMessage.observe(this) { message ->
            Toast.makeText(this, message, Toast.LENGTH_LONG).show()
        }
    }
}