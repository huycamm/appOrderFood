package com.example.apporderfood

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListView
import com.example.apporderfood.adapter.AdapterLichSu
import com.example.apporderfood.model.LichSu
import com.example.apporderfood.model.TaiKhoan
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class FragmentLichSu : Fragment() {
    lateinit var dbrf:DatabaseReference
    lateinit var lvLichSu:ListView
    lateinit var dsLichSu:MutableList<LichSu>

    lateinit var taiKhoan: TaiKhoan

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        taiKhoan = arguments?.getSerializable("taikhoan") as TaiKhoan
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_lich_su, container, false)
        lvLichSu = view.findViewById(R.id.lvLichSu)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        dsLichSu = mutableListOf()
        dbrf = FirebaseDatabase.getInstance().getReference("history")
        dbrf.orderByChild("idTaiKhoan").equalTo(taiKhoan.getId()).addValueEventListener(object :ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                dsLichSu.clear()
                if(snapshot.exists()){
                    for(lichSuSnap in snapshot.children){
                        val lichSu = lichSuSnap.getValue(LichSu::class.java)
                        dsLichSu.add(lichSu!!)
                    }
                    lvLichSu.adapter = context?.let { AdapterLichSu(it,dsLichSu) }
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })

    }

    fun createFragmentLS(taiKhoan: TaiKhoan):FragmentLichSu{
        val fragment = FragmentLichSu()
        val bundle = Bundle()
        bundle.putSerializable("taikhoan", taiKhoan)
        fragment.arguments = bundle
        return fragment
    }

}