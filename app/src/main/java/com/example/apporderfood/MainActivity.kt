package com.example.apporderfood

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.example.apporderfood.databinding.ActivityMainBinding
import com.example.apporderfood.model.TaiKhoan
import com.google.android.material.bottomnavigation.BottomNavigationView

lateinit var binding: ActivityMainBinding
class MainActivity : AppCompatActivity() {
    lateinit var taiKhoan: TaiKhoan
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        taiKhoan = intent.getSerializableExtra("taikhoan") as TaiKhoan

        binding.viewPager2.adapter = AdapterViewPager2(supportFragmentManager,lifecycle)
        binding.viewPager2.currentItem = 0
        //Ngăn kghoong cho lướt viewPager2
        binding.viewPager2.isUserInputEnabled = false

        actionNav()
    }

    private fun actionNav() {
        binding.bottomNav.setOnNavigationItemSelectedListener {
            when(it.itemId){
                R.id.menu_trang_chu -> {
                    binding.viewPager2.setCurrentItem(0, false)
                    true
                }
                R.id.menu_gio_hang -> {
                    binding.viewPager2.setCurrentItem(1, false)
                    true
                }
                R.id.menu_phan_hoi -> {
                    binding.viewPager2.setCurrentItem(2, false)
                    true
                }
                R.id.menu_lich_su -> {
                    binding.viewPager2.setCurrentItem(3, false)
                    true
                }
                R.id.menu_toi -> {
                    binding.viewPager2.setCurrentItem(4, false)
                    true
                }
                else -> false
            }
        }
    }
    private inner class AdapterViewPager2(fragmentManager: FragmentManager, lifecycle: Lifecycle) : FragmentStateAdapter(fragmentManager, lifecycle) {
        override fun getItemCount() = 5

        override fun createFragment(position: Int):Fragment{
            return when(position){
                0 -> FragmentTrangChu().creatFragmentTT(taiKhoan)
                1 -> FragmentGioHang().creatFragmentGH(taiKhoan)
                2 -> FragmentPhanHoi()
                3 -> FragmentLichSu().createFragmentLS(taiKhoan)
                else -> FragmentUser().createFragmentUser(taiKhoan)
            }
        }
    }

}