package com.smtz.note.activities

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.animation.ValueAnimator
import android.os.Build
import android.os.Bundle
import android.util.TypedValue
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.view.animation.AccelerateDecelerateInterpolator
import android.widget.LinearLayout
import android.widget.SearchView
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.appbar.CollapsingToolbarLayout
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.snackbar.Snackbar
import com.smtz.note.R
import com.smtz.note.adapters.NoteAdapter
import com.smtz.note.common.NoteSingleton
import com.smtz.note.data.vos.NoteVO
import com.smtz.note.databinding.ActivityHomeBinding
import com.smtz.note.presenters.HomePresenter
import com.smtz.note.presenters.HomePresenterImpl
import com.smtz.note.utils.animateSearchCardViewWidth
import com.smtz.note.views.HomeView

class HomeActivity : AppCompatActivity(), HomeView {

    private lateinit var binding: ActivityHomeBinding
    private lateinit var mPresenter: HomePresenter

    private lateinit var mNoteAdapter: NoteAdapter
    private var mNoteList: MutableList<NoteVO> = mutableListOf()

    private var isSearchCardViewMovedToEnd = false

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setUpPresenter()

        setUpToolbarAnimation()
        setUpListeners()
        setUpAdapters()

        mPresenter.onUiReady(this)
    }

    // searchView is focusing and keyboard is appearing for returning with onBackPressed() from another activity
    override fun onResume() {
        super.onResume()
        binding.searchView.clearFocus()
    }

    private fun setUpPresenter() {
        mPresenter = ViewModelProvider(this)[HomePresenterImpl::class.java]
        mPresenter.initPresenter(this)
    }

    private fun setUpToolbarAnimation() {
        val appBarLayout = binding.appBarLayout
        val searchCardView = binding.searchCardView
        val searchCardViewLayoutParams = searchCardView.layoutParams as CollapsingToolbarLayout.LayoutParams

        val collapseWidth = resources.getDimensionPixelSize(R.dimen.search_card_width_collapsed)

        appBarLayout.addOnOffsetChangedListener { _, verticalOffset ->
            val maxScroll = appBarLayout.totalScrollRange
            val percentage = Math.abs(verticalOffset).toFloat() / maxScroll.toFloat()

            if (percentage >= 0.8) {
                if (!isSearchCardViewMovedToEnd) {
                    // Move searchCardView to the end of toolbar
                    searchCardViewLayoutParams.collapseMode = CollapsingToolbarLayout.LayoutParams.COLLAPSE_MODE_OFF
                    searchCardViewLayoutParams.gravity = Gravity.END or Gravity.TOP
                    searchCardView.layoutParams = searchCardViewLayoutParams

                    // Animate width change to 200dp
                    animateSearchCardViewWidth(searchCardView, collapseWidth)

                    isSearchCardViewMovedToEnd = true
                }
            } else {
                if (isSearchCardViewMovedToEnd) {
                    // Reset the layout parameters of searchCardView
                    searchCardViewLayoutParams.collapseMode = CollapsingToolbarLayout.LayoutParams.COLLAPSE_MODE_PIN
                    searchCardViewLayoutParams.gravity = Gravity.TOP
                    searchCardView.layoutParams = searchCardViewLayoutParams

                    // Set width to match_parent without animation
                    val params = searchCardView.layoutParams
                    params.width = ViewGroup.LayoutParams.MATCH_PARENT
                    searchCardView.layoutParams = params

                    isSearchCardViewMovedToEnd = false
                }
            }
        }
    }

    private fun setUpListeners() {
        binding.fabCreate.setOnClickListener {
            mPresenter.onTapCreateNote()
        }

        binding.btnPin.setOnClickListener {
            mPresenter.onTapPinNote()
        }

        binding.btnDelete.setOnClickListener {
            mPresenter.onTapDeleteNote()

            binding.navAdditionalSetting.visibility = View.INVISIBLE
            binding.fabCreate.visibility = View.VISIBLE

            val layoutParams = binding.rvNoteList.layoutParams as LinearLayout.LayoutParams
            layoutParams.setMargins(0, 0, 0, 0)
            binding.rvNoteList.layoutParams = layoutParams
        }

        val searchedNotes: MutableList<NoteVO> = mutableListOf()
        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener,
            androidx.appcompat.widget.SearchView.OnQueryTextListener {

            override fun onQueryTextChange(newText: String?): Boolean {
                searchedNotes.clear()
                mNoteList.forEach {
                    if (newText != null) {
                        if (it.title.lowercase().contains(newText.lowercase(), true) ||
                            (it.content?.lowercase()?.contains(newText.lowercase(), true) == true)
                        ) {
                            searchedNotes.add(it)
                        }
                    }
                }
                mNoteAdapter.setNewData(searchedNotes)
                return true
            }
            override fun onQueryTextSubmit(query: String?): Boolean {
                return true
            }
        })
    }

    private fun setUpAdapters() {
//        mNoteAdapter = NoteAdapter()
//        binding.rvNoteList.adapter = mNoteAdapter
//        binding.rvNoteList.layoutManager = GridLayoutManager(this, 2, GridLayoutManager.VERTICAL, true)

        binding.rvNoteList.apply {
            mNoteAdapter = NoteAdapter(mPresenter)
            adapter = mNoteAdapter
            layoutManager = StaggeredGridLayoutManager(2, LinearLayout.VERTICAL)
        }
    }

    override fun navigateToCreateNoteActivity(id: Long) {
        startActivity(CreateNoteActivity.newIntent(this, id))
    }

    override fun showNoteList(noteList: List<NoteVO>) {
        mNoteList = noteList as ArrayList
        mNoteAdapter.setNewData(noteList)
    }

    override fun onBackPressed() {
        when (binding.navAdditionalSetting.visibility) {
            View.VISIBLE -> {
                showHideSetting(false)
//                mNoteAdapter.setNewData(mNoteList)
                mNoteAdapter.removedSelectedItems(NoteSingleton.instance.getSelectedNoteList().toList())
                mPresenter.clearSelectedNotes()
            }
            else -> super.onBackPressed()
        }
    }

    override fun showAdditionalSetting() {
        showHideSetting(true)
    }

    override fun hideAdditionalSetting() {
        showHideSetting(false)
    }

    private fun showHideSetting(navShow: Boolean) {
        val layoutParams = binding.rvNoteList.layoutParams as LinearLayout.LayoutParams

        if (navShow) {
            binding.navAdditionalSetting.visibility = View.VISIBLE
            binding.fabCreate.visibility = View.GONE
            layoutParams.bottomMargin = resources.getDimensionPixelSize(R.dimen.bottom_nav_height)
        } else {
            binding.navAdditionalSetting.visibility = View.GONE
            binding.fabCreate.visibility = View.VISIBLE
            layoutParams.setMargins(0, 0, 0, 0)
        }
        binding.rvNoteList.layoutParams = layoutParams
    }

    override fun showError(message: String) {
        Snackbar.make(window.decorView, message, Snackbar.LENGTH_LONG).show()
    }
}