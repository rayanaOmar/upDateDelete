package com.example.postrequestupdatedelete

import android.app.ProgressDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UpdateDelete : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_update_delete)
        val userId = findViewById<View>(R.id.editTextTextPersonNameId) as EditText
        val name = findViewById<View>(R.id.editTextTextPersonName) as EditText
        val location = findViewById<View>(R.id.editTextTextPersonName2) as EditText
        val deleteButton = findViewById<View>(R.id.buttonDelete) as Button
        val updateButton = findViewById<View>(R.id.buttonUpdate) as Button

        //onClick
        deleteButton.setOnClickListener {
            val apiInterface = APIClient().getClient()?.create(APIInterface::class.java)
            val progressDialog = ProgressDialog(this@UpdateDelete)
            progressDialog.setMessage("Please wait")
            progressDialog.show()

            if(apiInterface != null){
                apiInterface.deleteUser((userId.text.toString().toInt()))?.enqueue(object: Callback<Void>{
                    override fun onResponse(call: Call<Void>, response: Response<Void>) {
                        Toast.makeText(applicationContext, "Delete Success!", Toast.LENGTH_SHORT).show()
                        progressDialog.dismiss()

                    }

                    override fun onFailure(call: Call<Void>, t: Throwable) {
                        Toast.makeText(applicationContext, "Error", Toast.LENGTH_SHORT).show()
                        progressDialog.dismiss()
                    }
                })
            }
        }
        updateButton.setOnClickListener {
            val apiInterface = APIClient().getClient()?.create(APIInterface::class.java)
            val progressDialog = ProgressDialog(this@UpdateDelete)
            progressDialog.setMessage("Please wait")

            val userInput = userId.text.toString().toInt()
            val userNameLocation = Users.UserDetails(name.text.toString(),location.text.toString(),userId.text.toString().toInt())

            progressDialog.show()

            if(apiInterface != null){
                apiInterface.updateUser(userInput,userNameLocation)?.enqueue(object: Callback<Users.UserDetails>{
                    override fun onResponse(call: Call<Users.UserDetails>, response: Response<Users.UserDetails>) {
                        Toast.makeText(applicationContext, "Update Success!", Toast.LENGTH_SHORT).show()
                        progressDialog.dismiss()

                    }

                    override fun onFailure(call: Call<Users.UserDetails>, t: Throwable) {
                        Toast.makeText(applicationContext, "Error", Toast.LENGTH_SHORT).show()
                        progressDialog.dismiss()
                    }
                })
            }
        }
    }
}