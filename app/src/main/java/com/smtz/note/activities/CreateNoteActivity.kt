package com.smtz.note.activities

import android.content.Context
import android.content.Intent
import android.graphics.Rect
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.snackbar.Snackbar
import com.smtz.note.data.vos.NoteVO
import com.smtz.note.databinding.ActivityCreateNoteBinding
import com.smtz.note.presenters.CreateNotePresenter
import com.smtz.note.presenters.CreateNotePresenterImpl
import com.smtz.note.views.CreateNoteView

class CreateNoteActivity : AppCompatActivity(), CreateNoteView {

    lateinit var binding: ActivityCreateNoteBinding
    private lateinit var mPresenter: CreateNotePresenter

    private var mNote: NoteVO? = null
    private var mId: Long = 0

    companion object{

        private const val EXTRA_ID = "EXTRA ID"

        fun newIntent(context: Context, id: Long): Intent {
            val intent = Intent(context, CreateNoteActivity::class.java)

            if (id != 0L)
                intent.putExtra(EXTRA_ID, id)
            return intent
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCreateNoteBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setUpPresenter()

        mId = intent.getLongExtra(EXTRA_ID, 0)

        setUpListeners()

        mPresenter.onUiReady(this, mId)
    }

    private fun setUpPresenter() {
        mPresenter = ViewModelProvider(this)[CreateNotePresenterImpl::class.java]
        mPresenter.initPresenter(this)
    }

    private fun setUpListeners() {
        binding.btnBack.setOnClickListener {
            super.onBackPressed()
        }

        // ဒီ activity ကို‌ရောက်တာနဲ့ etText ကို autoFocus ပြီး keyboard ပေါ်အောင်လုပ်
        binding.etText.requestFocus()
        // .post to run a delayed task and set cursor position to the end
        binding.etText.post {
            binding.etText.setSelection(binding.etText.text.length)
        }
        // SoftKeyboard ပေါ်အောင်လုပ်
        val imm = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY)


        // text မရှိရင် Done button ပျောက်
        textChangedListener(binding.etTitle)
        textChangedListener(binding.etText)

        binding.btnDone.setOnClickListener {
            if (mNote == null) {
                mPresenter.onTapDone(binding.etTitle.text.toString(), binding.etText.text.toString(), 0L, "")
            } else {
                mPresenter.onTapDone(binding.etTitle.text.toString(), binding.etText.text.toString(), mNote!!.id, mNote!!.date?:"")
            }

            imm.hideSoftInputFromWindow(window.decorView.windowToken, 0)
            super.onBackPressed()
        }
    }

    private fun textChangedListener(edt: EditText) {
        edt.addTextChangedListener(object : TextWatcher {

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (s.isNullOrEmpty()) {
                    binding.btnDone.visibility = View.INVISIBLE
                } else {
                    binding.btnDone.visibility = View.VISIBLE
                }
            }
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun afterTextChanged(s: Editable?) {}
        })
    }


    override fun setUpNoteVO(note: NoteVO?) {
        mNote = note

        mNote?.let {
            if (it.title != "title")
                binding.etTitle.setText(it.title)
            binding.etText.setText(it.content)
        }
    }

    override fun showError(message: String) {
        Snackbar.make(window.decorView, message, Snackbar.LENGTH_LONG).show()
        Toast.makeText(applicationContext, message, Toast.LENGTH_LONG).show()
    }
}