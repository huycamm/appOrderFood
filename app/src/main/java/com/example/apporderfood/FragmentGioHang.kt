package com.example.apporderfood

import android.database.Cursor
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.GridView
import android.widget.ListView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.appdoan.inteface.InterfaceRvFood
import com.example.apporderfood.adapter.AdapterChiTietDonHang
import com.example.apporderfood.adapter.AdapterGioHang
import com.example.apporderfood.database.DatabaseGioHang
import com.example.apporderfood.inteface.InterfaceGioHang
import com.example.apporderfood.model.LichSu
import com.example.apporderfood.model.MonAn
import com.example.apporderfood.model.TaiKhoan
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import java.sql.Date
import java.util.Calendar
import kotlin.math.log

class FragmentGioHang : Fragment() {
    lateinit var db:DatabaseGioHang
    lateinit var rs:Cursor
    lateinit var dbrf:DatabaseReference
    lateinit var ds:MutableList<MonAn>

    lateinit var listMonAn:RecyclerView
    lateinit var adapter:AdapterGioHang

    lateinit var txtTongTien:TextView
    lateinit var btnDatHang:Button

    lateinit var taiKhoan: TaiKhoan

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        taiKhoan = arguments?.getSerializable("taikhoan") as TaiKhoan
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_gio_hang, container, false)

        listMonAn = view.findViewById(R.id.rvMonAnGioHang)
        txtTongTien = view.findViewById(R.id.txtTongTien)
        btnDatHang = view.findViewById(R.id.btnDatHang)

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initView()
        initDatHang(ds)
    }

    private fun initDatHang(dsMonAn:MutableList<MonAn>) {
        btnDatHang.setOnClickListener {
            if (dsMonAn.isEmpty()){
                Toast.makeText(requireActivity(), "Hãy thêm món ăn vào giỏ hàng", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }else{
                val diolog = BottomSheetDialog(requireActivity())
                val view = layoutInflater.inflate(R.layout.custom_bottom_sheet_diolog_cart,null,false)

                val lvCTMonAn = view.findViewById<ListView>(R.id.lvMonAnCT)
                lvCTMonAn.adapter = AdapterChiTietDonHang(requireActivity(),dsMonAn)

                val txtTongTienCT = view.findViewById<TextView>(R.id.txtTongTienCT)
                txtTongTienCT.setText(tinhTongTien(dsMonAn).toString()+" VNĐ")

                val edtHoVaTen = view.findViewById<EditText>(R.id.edtHoVaTen)
                edtHoVaTen.setText(taiKhoan.getUserName())

                val edtSoDienThoai = view.findViewById<EditText>(R.id.edtSoDienThoai)
                edtSoDienThoai.setText(taiKhoan.getPhone())

                val edtDiaChi = view.findViewById<EditText>(R.id.edtDiaChi)
                val edtThanhToan = view.findViewById<EditText>(R.id.edtThanhToan)

                val btnHuy = view.findViewById<Button>(R.id.btnHuyCT)
                btnHuy.setOnClickListener {
                    diolog.dismiss()
                }

                //Thực hiện đặt hàng
                val btnDatHang = view.findViewById<Button>(R.id.btnDatHangCT)
                btnDatHang.setOnClickListener {
                    val diaChi = edtDiaChi.text.toString()
                    if(diaChi.equals("")){
                        Toast.makeText(requireActivity(), "Hãy nhập địa chỉ nhận hàng", Toast.LENGTH_SHORT).show()
                        return@setOnClickListener
                    }
                    var thucDon = ""
                    for (monAn in dsMonAn){
                        thucDon += monAn.getTitle() +"  - Số lượng: "+ monAn.getNumInCart() +"\n"
                        db.deleteMonAn(monAn)
                    }
                    updateAdapter()

                    val idTaiKhoan = taiKhoan.getId()
                    val hoTen = edtHoVaTen.text.toString()
                    val phone = edtSoDienThoai.text.toString()
                    val ngayDat = Date(System.currentTimeMillis()).toString()
                    val tongTien = txtTongTienCT.text.toString()
                    val thanhToan = edtThanhToan.text.toString()
                    dbrf = FirebaseDatabase.getInstance().getReference("history")
                    val id = dbrf.push().key!!

                    val lichSu = LichSu(id, hoTen, phone, diaChi, thucDon, ngayDat, tongTien, thanhToan,idTaiKhoan)
                    dbrf.child(id).setValue(lichSu)

                    Toast.makeText(requireActivity(), "Đặt hàng thành công!", Toast.LENGTH_SHORT).show()
                    txtTongTien.setText("0")
                    diolog.dismiss()
                }

                diolog.setCancelable(false)
                diolog.setContentView(view)
                diolog.show()
            }
        }
    }

    fun initView(){
        ds = mutableListOf()
        db = DatabaseGioHang(requireContext())
        rs = db.getMonAn(taiKhoan)
        while (rs.moveToNext()){
            val monAn = MonAn(rs.getString(0),
                rs.getString(1),
                rs.getString(2),
                rs.getInt(3),
                rs.getInt(4))
            ds.add(monAn)
        }

        adapter = AdapterGioHang(ds, object :InterfaceGioHang{
            override fun updateClick(monAn: MonAn, numInCart: Int) {
                db.updateNumInCart(monAn,numInCart)
                updateAdapter()
                txtTongTien.setText(tinhTongTien(ds).toString())
            }

            override fun xoaClick(monAn: MonAn) {
                db.deleteMonAn(monAn)
                updateAdapter()
                txtTongTien.setText(tinhTongTien(ds).toString())
            }
        })

        listMonAn.adapter = adapter
        listMonAn.layoutManager = LinearLayoutManager(activity,LinearLayoutManager.VERTICAL,false)

        txtTongTien.setText(tinhTongTien(ds).toString())
    }

    fun tinhTongTien(list:MutableList<MonAn>):Int{
        var tongTien = 0
        for (monAn in list){
            tongTien += monAn.getPrice()*monAn.getNumInCart()
        }
        return tongTien
    }

    fun creatFragmentGH(taiKhoan: TaiKhoan):Fragment{
        val fragment = FragmentGioHang()
        val bundle = Bundle()
        bundle.putSerializable("taikhoan",taiKhoan)
        fragment.arguments = bundle
        return fragment
    }

    fun updateAdapter(){
        ds.clear()
        rs = db.getMonAn(taiKhoan)
        while (rs.moveToNext()){
            val monAn = MonAn(rs.getString(0),
                rs.getString(1),
                rs.getString(2),
                rs.getInt(3),
                rs.getInt(4))
            ds.add(monAn)
        }
        adapter.notifyDataSetChanged()
    }
}