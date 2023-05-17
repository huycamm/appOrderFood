package com.example.apporderfood

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import com.example.apporderfood.model.TaiKhoan
import com.google.firebase.database.DatabaseReference

class FragmentUser : Fragment() {
    lateinit var taiKhoan: TaiKhoan
    lateinit var txtTenTaiKhoan: TextView
    lateinit var btnDangXuat:Button

    lateinit var layoutCaiDat:LinearLayout
    lateinit var layoutThongTin:LinearLayout
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        taiKhoan = arguments?.getSerializable("taikhoan") as TaiKhoan
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_user, container, false)

        btnDangXuat = view.findViewById(R.id.btnDangXuat)
        txtTenTaiKhoan = view.findViewById(R.id.txtTenTaiKhoan)
        layoutThongTin = view.findViewById(R.id.thongTin)
        layoutCaiDat = view.findViewById(R.id.caiDat)

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        txtTenTaiKhoan.setText(taiKhoan.getUserName())
        btnDangXuat.setOnClickListener {
            val diolog = AlertDialog.Builder(requireContext())
            diolog.setTitle("Đăng xuất")
            diolog.setMessage("Bạn có chắc chắn muốn đăng xuất?")
            diolog.setNegativeButton("Hủy"){listener,i ->
                listener.dismiss()
            }
            diolog.setPositiveButton("Đăng xuất"){listener,i ->
                val intent = Intent(activity, Activity_DangNhap::class.java)
                activity?.finish()
                startActivity(intent)
            }
            diolog.show()
        }

        layoutThongTin.setOnClickListener {
            val diolog = AlertDialog.Builder(requireContext())
            val view = layoutInflater.inflate(R.layout.diolog_thong_tin_app, null, false)
            diolog.setView(view)
            diolog.show()
        }

        layoutCaiDat.setOnClickListener {
            val intent = Intent(activity, ActivityTaiKhoan::class.java)
            intent.putExtra("taikhoan", taiKhoan)
            startActivity(intent)
        }
    }

    fun createFragmentUser(taiKhoan: TaiKhoan):FragmentUser{
        val fragment = FragmentUser()
        val bundle = Bundle()
        bundle.putSerializable("taikhoan",taiKhoan)
        fragment.arguments = bundle
        return fragment
    }
}