package com.example.testapp.ui.main

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.view.GravityCompat
import com.example.testapp.NewsApplication
import com.example.testapp.R

import com.example.testapp.databinding.ActivityMainBinding
import com.example.testapp.ui.books.BooksFragment
import com.example.testapp.ui.base.BaseActivity
import com.google.android.material.navigation.NavigationView



class MainActivity : BaseActivity<ActivityMainBinding, MainViewModel>(),NavigationView.OnNavigationItemSelectedListener {


    lateinit var toggle: ActionBarDrawerToggle

    override fun getViewModelClass(): Class<MainViewModel> = MainViewModel::class.java

    override fun getLayoutId(): Int = R.layout.activity_main

    override fun onCreate(savedInstanceState: Bundle?) {
        (application as NewsApplication).appComponent.inject(this)
        super.onCreate(savedInstanceState)
        setSupportActionBar(binding.toolbar)

        if (savedInstanceState == null){
            supportFragmentManager.beginTransaction()
                .add(R.id.container,NewsFragment(),"news_fragment")
                .commit()
        }

        initDrawerLayout()

    }

    private fun initDrawerLayout(){
        with(binding) {
            toggle = ActionBarDrawerToggle(
                this@MainActivity,
                drawerLayout,toolbar,
                R.string.navigation_drawer_open,
                R.string.navigation_drawer_close
            )
            drawerLayout.addDrawerListener(toggle)
            toggle.syncState()
            supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_menu_white)
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
            navView.setNavigationItemSelectedListener(this@MainActivity)
        }
    }

    override fun onNavigationItemSelected(p0: MenuItem): Boolean {
        binding.drawerLayout.closeDrawer(GravityCompat.START)
        if (p0.itemId == R.id.actionNews){
            supportFragmentManager.beginTransaction()
                .add(R.id.container,NewsFragment(),"news_fragment")
                .commit()
            return true
        }else if (p0.itemId == R.id.actionBooks){
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, BooksFragment(),"books_fragment")
                .commit()
            return true
        }
        return false
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (toggle.onOptionsItemSelected(item)) {
            return true
        }
        return false
    }

}
