package com.example.notesproject.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import com.example.notesproject.R
import com.example.notesproject.Util.setStatusBarColor

abstract class BaseFragment<VB: ViewBinding> : Fragment() {

    protected val binding by lazy { viewBindingInflate() }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return binding.root
    }

    protected abstract fun viewBindingInflate() : VB

    override fun onResume() {
        super.onResume()
        activity?.setStatusBarColor(R.color.white, false)
    }
}