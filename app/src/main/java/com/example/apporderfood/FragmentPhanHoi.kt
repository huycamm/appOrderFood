package com.example.apporderfood

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast

class FragmentPhanHoi : Fragment() {
    lateinit var btnPhanHoi:Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_phan_hoi, container, false)
        btnPhanHoi = view.findViewById(R.id.btnPhanHoi)

        val edtHoVaTen = view.findViewById<TextView>(R.id.edtHoTenPH)
        val edtPhanHoi = view.findViewById<EditText>(R.id.edtPhanHoi)
        val edtPhone = view.findViewById<EditText>(R.id.phone)
        val edtEmail = view.findViewById<EditText>(R.id.email)

        btnPhanHoi.setOnClickListener {
            if(edtHoVaTen.text.toString().equals("") || edtPhanHoi.text.toString().equals("")){
                Toast.makeText(requireActivity(), "Nhập đầy đủ thông tin phàn hồi!", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            edtPhanHoi.setText("")
            edtHoVaTen.setText("")
            edtPhone.setText("")
            edtEmail.setText("")
            edtHoVaTen.requestFocus()
            Toast.makeText(requireActivity(), "Gửi phản hồi thành công", Toast.LENGTH_SHORT).show()
        }

        return view
    }

}