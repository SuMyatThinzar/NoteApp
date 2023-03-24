package com.smtz.note.activities

import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.Window
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.snackbar.Snackbar
import com.smtz.note.adapters.NoteAdapter
import com.smtz.note.data.vos.NoteVO
import com.smtz.note.databinding.ActivityHomeBinding
import com.smtz.note.presenters.HomePresenter
import com.smtz.note.presenters.HomePresenterImpl
import com.smtz.note.utils.mNoteList
import com.smtz.note.views.HomeView


class HomeActivity : AppCompatActivity(), HomeView {

    private lateinit var binding: ActivityHomeBinding
    private lateinit var mPresenter: HomePresenter

    private lateinit var mNoteAdapter: NoteAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setUpPresenter()

        setUpListeners()
        setUpAdapters()

        mPresenter.onUiReady(this)
    }

    private fun setUpPresenter() {
        mPresenter = ViewModelProvider(this)[HomePresenterImpl::class.java]
        mPresenter.initPresenter(this)
    }

    private fun setUpListeners() {
        binding.fabCreate.setOnClickListener {
            mPresenter.onTapCreateNote()
        }

        binding.btnDelete.setOnClickListener {
            mPresenter.onTapDeleteNote()

            binding.navAdditionalSetting.visibility = View.INVISIBLE
            binding.fabCreate.visibility = View.VISIBLE

            val layoutParams = binding.rvNoteList.layoutParams as LinearLayout.LayoutParams
            layoutParams.setMargins(0, 0, 0, 0)
            binding.rvNoteList.layoutParams = layoutParams
        }
    }

    private fun setUpAdapters() {
//        mNoteAdapter = NoteAdapter()
//        binding.rvNoteList.adapter = mNoteAdapter
//        binding.rvNoteList.layoutManager = GridLayoutManager(this, 2, GridLayoutManager.VERTICAL, true)

        binding.rvNoteList.apply {
            mNoteAdapter = NoteAdapter(mPresenter)
            adapter = mNoteAdapter
            layoutManager =
                GridLayoutManager(applicationContext, 2, GridLayoutManager.VERTICAL, false)

        }
    }

    override fun navigateToCreateNoteActivity(id: Long) {
        startActivity(CreateNoteActivity.newIntent(this, id))
    }

    override fun showNoteList(noteList: List<NoteVO>) {
        mNoteAdapter.setNewData(noteList)
    }

    override fun onBackPressed() {
        when (binding.navAdditionalSetting.visibility) {
            View.VISIBLE -> {
                additionalSetting(false)

                mPresenter.clearSelectedNotes()
                mNoteAdapter.setNewData(mNoteList)
            }
            else -> super.onBackPressed()
        }
    }

    override fun showAdditionalSetting() {

        additionalSetting(true)
    }

    override fun hideAdditionalSetting() {

        additionalSetting(false)
    }

    private fun additionalSetting(navShow: Boolean) {

        if (navShow) {
            binding.navAdditionalSetting.visibility = View.VISIBLE
            binding.fabCreate.visibility = View.INVISIBLE

            val layoutParams = binding.rvNoteList.layoutParams as LinearLayout.LayoutParams
            layoutParams.bottomMargin = resources.getDimensionPixelSize(com.smtz.note.R.dimen.bottom_nav_height)
            binding.rvNoteList.layoutParams = layoutParams

        } else {
            binding.navAdditionalSetting.visibility = View.INVISIBLE
            binding.fabCreate.visibility = View.VISIBLE

            val layoutParams = binding.rvNoteList.layoutParams as LinearLayout.LayoutParams
            layoutParams.setMargins(0, 0, 0, 0)
            binding.rvNoteList.layoutParams = layoutParams

        }
    }


    override fun showError(message: String) {
        Snackbar.make(window.decorView, message, Snackbar.LENGTH_LONG).show()
    }
}