package com.example.apporderfood

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import androidx.core.widget.addTextChangedListener
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.appdoan.adapter.AdapterMonAnAll
import com.example.appdoan.adapter.AdapterMonAnMain
import com.example.appdoan.inteface.InterfaceRvFood
import com.example.apporderfood.model.MonAn
import com.example.apporderfood.model.TaiKhoan
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class ListAllFood : AppCompatActivity() {
    lateinit var edtSearch:EditText
    lateinit var btnBack:ImageView
    lateinit var rvMonAn:RecyclerView

    lateinit var dsMonAn:MutableList<MonAn>
    lateinit var adapter:AdapterMonAnAll
    lateinit var dbrf:DatabaseReference

    lateinit var taiKhoan: TaiKhoan

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_all_food)

        taiKhoan = intent.getSerializableExtra("taikhoan") as TaiKhoan

        edtSearch = findViewById(R.id.edtSearchFood)
        rvMonAn = findViewById(R.id.rvFood)
        btnBack = findViewById(R.id.imageBackAll)
        btnBack.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            intent.putExtra("taikhoan",taiKhoan)
            finish()
            startActivity(intent)
        }

        getAllMonAn()

        edtSearch.addTextChangedListener {
            searchMonAN(edtSearch.text.toString())
        }
    }

    private fun searchMonAN(search: String) {
        dbrf = FirebaseDatabase.getInstance().getReference("food")
        dbrf.orderByChild("title").startAt(search)
            .addValueEventListener(object:ValueEventListener{
                override fun onDataChange(snapshot: DataSnapshot) {
                    dsMonAn.clear()
                    if(snapshot.exists()){
                        for(foodSnap in snapshot.children){
                            val food = foodSnap.getValue(MonAn::class.java)
                            dsMonAn.add(food!!)
                        }
                    }
                    adapter = AdapterMonAnAll(dsMonAn,object : InterfaceRvFood {
                        override fun onClick(monAn: MonAn) {
                            val intent = Intent(this@ListAllFood, DetailFood::class.java)
                            intent.putExtra("monAn", monAn)
                            intent.putExtra("taikhoan",taiKhoan)
                            startActivity(intent)
                        }
                    })
                    rvMonAn.adapter = adapter
                    rvMonAn.layoutManager = GridLayoutManager(this@ListAllFood, 1, GridLayoutManager.VERTICAL,false)
                }

                override fun onCancelled(error: DatabaseError) {
                    TODO("Not yet implemented")
                }
            })
    }

    private fun getAllMonAn() {
        dsMonAn = mutableListOf()
        dbrf = FirebaseDatabase.getInstance().getReference("food")
        dbrf.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                dsMonAn.clear()
                if(snapshot.exists()){
                    for(foodSnapshot in snapshot.children){
                        var food = foodSnapshot.getValue(MonAn::class.java)
                        dsMonAn.add(food!!)
                    }
                }
                adapter.notifyDataSetChanged()
            }
            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })
        adapter = AdapterMonAnAll(dsMonAn,object : InterfaceRvFood {
            override fun onClick(monAn: MonAn) {
                val intent = Intent(this@ListAllFood, DetailFood::class.java)
                intent.putExtra("monAn", monAn)
                intent.putExtra("taikhoan",taiKhoan)
                startActivity(intent)
            }
        })
        rvMonAn.adapter = adapter
        rvMonAn.layoutManager = GridLayoutManager(this@ListAllFood, 1, GridLayoutManager.VERTICAL,false)
    }
}