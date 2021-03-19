package com.example.firebasedemo

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class MainActivity : AppCompatActivity() {

    val database = FirebaseDatabase.getInstance()
    val myRef = database.getReference("student")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val btnAdd: Button = findViewById(R.id.button_add)
        btnAdd.setOnClickListener() {

            val stdID: String = findViewById<TextView>(R.id.edit_id).text.toString()
            val stdName: String = findViewById<TextView>(R.id.edit_name).text.toString()
            val stdProgramme: String = findViewById<TextView>(R.id.edit_programme).text.toString()

            myRef.child(stdID).child("Name").setValue(stdName)
            myRef.child(stdID).child("Programme").setValue(stdProgramme)
        }

        val getData = object: ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                var sb = StringBuilder()

                for(std in snapshot.children) {
                    var name = std.child("Name").getValue()
                    sb.append("$name \n")
                }

                val result: TextView = findViewById(R.id.text_result)
                result.setText(sb)
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        }

        val btnGet: Button = findViewById(R.id.button_get)
        btnGet.setOnClickListener() {
            myRef.addValueEventListener(getData)
            myRef.addListenerForSingleValueEvent(getData)

//            val qry: Query = myRef.orderByChild("Programme").equalTo("RSD")
//            qry.addValueEventListener(getData)
//            qry.addListenerForSingleValueEvent(getData)
        }
    }
}