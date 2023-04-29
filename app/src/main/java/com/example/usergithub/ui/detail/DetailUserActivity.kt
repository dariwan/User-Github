package com.example.usergithub.ui.detail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.example.usergithub.databinding.ActivityDetailUserBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class DetailUserActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailUserBinding

    companion object {
        const val EXTRA_USERNAME = "extra_username"
        const val EXTRA_ID = "extra_id"
        const val EXTRA_URL = "extra_url"
    }

    private lateinit var viewModel: DetailUserViewMode


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailUserBinding.inflate(layoutInflater)
        setContentView(binding.root)




        val username = intent.getStringExtra(EXTRA_USERNAME)
        showLoading(true)

        val bundle = Bundle()
        bundle.putString(EXTRA_USERNAME, username)


        val id = intent.getIntExtra(EXTRA_ID, 0)

        val avatarUrl = intent.getStringExtra(EXTRA_URL)

        viewModel = ViewModelProvider(this,)[DetailUserViewMode::class.java]


        username?.let { viewModel.setUserDetail(it) }
        showLoading(true)
        viewModel.getUserDetail().observe(this) {
            if (it != null) {

                binding.apply {
                    showLoading(false)
                    tvName.text = it.name
                    tvUsername.text = it.login
                    tvFollowers.text = "${it.followers} Followers"
                    tvFollowing.text = "${it.following} Following"
                    Glide.with(this@DetailUserActivity)
                        .load(it.avatar_url)
                        .transition(DrawableTransitionOptions.withCrossFade())
                        .centerCrop()
                        .into(ivProfile)
                }
            }
        }

        //favorite
        //cek favorite user agar ketika di klik love berubah menjadi merah
        var _isChecked = false
        CoroutineScope(Dispatchers.IO).launch {
            val count = viewModel.checkUser(id)
            withContext(Dispatchers.Main){
                if (count != null){
                    if (count > 0){
                        binding.toggleFavorite.isChecked = true
                        _isChecked = true
                    } else{
                        binding.toggleFavorite.isChecked = false
                        _isChecked = false
                    }
                }
            }
        }

        binding.toggleFavorite.setOnClickListener {
            _isChecked = !_isChecked
            if (_isChecked) {
                viewModel.addToFavorite(username!!, id, avatarUrl!!)
            } else{
                viewModel.removeFromFavorite(id)
            }
            binding.toggleFavorite.isChecked = _isChecked
            }

        val sectionPagerAdapter = SectionPagerAdapter(this, supportFragmentManager, bundle)
        binding.apply {
            viewPager.adapter = sectionPagerAdapter
            tabs.setupWithViewPager(viewPager)
        }

        //merubah tulisan di action bar
        supportActionBar?.title = "GitHub"
    }

    private fun showLoading(state: Boolean) {
        if (state) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
        }
    }

}