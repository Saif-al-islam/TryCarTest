package com.saif.trycartest.presentation.posts

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import com.saif.trycartest.R
import com.saif.trycartest.databinding.FragmentPostsBinding
import com.saif.trycartest.presentation.adapter.PostAdapter
import com.saif.trycartest.presentation.core.BaseFragment
import com.saif.trycartest.presentation.core.Error
import com.saif.trycartest.presentation.core.checkInternetConnection
import com.saif.trycartest.presentation.core.showSnackMsg
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class PostsFragment : BaseFragment() {

    private lateinit var binding: FragmentPostsBinding
    private val viewModel by viewModels<PostsViewModel>()

    private lateinit var adapter: PostAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_posts, container, false)

        setUp()

        return binding.root
    }

    private fun setUp() {
        val isConnected = checkInternetConnection(requireContext())
        viewModel.getPosts(isConnected)
        if (isConnected)
            Error.ErrorInt(R.string.no_internet_connection).showSnackMsg(binding.root)

        setUi()
        observeData()
    }

    private fun setUi() {
        adapter = PostAdapter()
        binding.rv.adapter = adapter
    }

    private fun observeData() {
        observingData(
            binding.root,
            viewModel.postsFlow,
            binding.pbLoading,
            viewModel.loadingFlow
        ) {
            when (it) {
                is SuccessPostsData -> {
                    adapter.submitList(it.data)
                }
            }
        }
    }

}