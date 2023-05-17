package com.example.apporderfood

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.EditText
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.example.appdoan.adapter.AdapterMonAnMain
import com.example.appdoan.inteface.InterfaceRvFood
import com.example.apporderfood.adapter.AdapterViewPager2Photo
import com.example.apporderfood.model.MonAn
import com.example.apporderfood.model.TaiKhoan
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import me.relex.circleindicator.CircleIndicator3

class FragmentTrangChu : Fragment() {
    lateinit var dsMonAn:MutableList<MonAn>
    lateinit var dbrf: DatabaseReference

    lateinit var dsPhoto:MutableList<String>
    lateinit var viewPager2Photo:ViewPager2
    lateinit var circle:CircleIndicator3
    lateinit var mhandle:Handler
    lateinit var mRunnable: Runnable

    lateinit var edtSearch:EditText
    lateinit var rvMonAn:RecyclerView
    lateinit var txtUser:TextView

    lateinit var taiKhoan: TaiKhoan

    lateinit var datHang:TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        taiKhoan = arguments?.getSerializable("taikhoan") as TaiKhoan
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view =  inflater.inflate(R.layout.fragment_trang_chu, container, false)
        edtSearch = view.findViewById(R.id.edtSearch)
        rvMonAn = view.findViewById(R.id.rvMonAn)
        txtUser = view.findViewById(R.id.txtUser)
        datHang = view.findViewById(R.id.txtDatHang)
        viewPager2Photo = view.findViewById(R.id.viewPager2Photo)
        circle = view.findViewById(R.id.circle)

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        txtUser.setText(taiKhoan.getUserName())
        datHang.setOnClickListener {
            val intent = Intent(requireActivity(), ListAllFood::class.java)
            intent.putExtra("taikhoan",taiKhoan)
            startActivity(intent)
        }

        actionViewPager2()
        getMonAn()
        searchFood()
    }

    private fun actionViewPager2() {
        dsPhoto = mutableListOf()
        mhandle = Handler()
        mRunnable = Runnable {
            if (viewPager2Photo.currentItem == (dsPhoto.size-1)){
                viewPager2Photo.currentItem = 0
            }else{
                viewPager2Photo.currentItem++
            }
        }
        dbrf = FirebaseDatabase.getInstance().getReference("food")
        dbrf.addValueEventListener(object:ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                if(snapshot.exists()){
                    for(foodSnapshot in snapshot.children){
                        var food = foodSnapshot.getValue(MonAn::class.java)
                        dsPhoto.add(food!!.getPicture())
                    }
                }
                viewPager2Photo.adapter = AdapterViewPager2Photo(dsPhoto)
                circle.setViewPager(viewPager2Photo)
                viewPager2Photo.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
                    override fun onPageSelected(position: Int) {
                        super.onPageSelected(position)
                        mhandle.removeCallbacks(mRunnable)
                        mhandle.postDelayed(mRunnable, 3000)
                    }
                })
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })
    }


    private fun searchFood() {
        edtSearch.setOnClickListener {
            val intent = Intent(requireActivity(), ListAllFood::class.java)
            intent.putExtra("taikhoan",taiKhoan)
            startActivity(intent)
        }
    }

    private fun getMonAn() {
        dsMonAn = mutableListOf()
        dbrf = FirebaseDatabase.getInstance().getReference("food")
        dbrf.limitToFirst(3).addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                dsMonAn.clear()
                if(snapshot.exists()){
                    for(foodSnapshot in snapshot.children){
                        var food = foodSnapshot.getValue(MonAn::class.java)
                        dsMonAn.add(food!!)
                    }
                }
                val adapter = AdapterMonAnMain(dsMonAn,object : InterfaceRvFood {
                    override fun onClick(monAn: MonAn) {
                        val intent = Intent(requireActivity(), DetailFood::class.java)
                        intent.putExtra("monAn", monAn)
                        intent.putExtra("taikhoan",taiKhoan)
                        startActivity(intent)
                    }
                })
                rvMonAn.adapter = adapter
                rvMonAn.layoutManager = LinearLayoutManager(requireActivity(), LinearLayoutManager.HORIZONTAL,false)
            }
            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })
    }

    fun creatFragmentTT(taiKhoan: TaiKhoan):FragmentTrangChu{
        val fragment = FragmentTrangChu()
        val bundle = Bundle()
        bundle.putSerializable("taikhoan", taiKhoan)
        fragment.arguments = bundle
        return fragment
    }

    override fun onPause() {
        super.onPause()
        mhandle.removeCallbacks(mRunnable)
    }

    override fun onResume() {
        super.onResume()
        mhandle.postDelayed(mRunnable,3000)
    }
}