package com.saif.trycartest.presentation.favoritePosts

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import com.saif.trycartest.R
import com.saif.trycartest.databinding.FragmentFavoritePostsBinding
import com.saif.trycartest.presentation.adapter.PostAdapter
import com.saif.trycartest.presentation.core.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FavoritePostsFragment : BaseFragment() {

    private lateinit var binding: FragmentFavoritePostsBinding
    private val viewModel by viewModels<FavoritePostsViewModel>()

    private lateinit var adapter: PostAdapter


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_favorite_posts, container, false)

        setUp()

        return binding.root
    }


    private fun setUp() {
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
                is SuccessFavPostsData -> {
                    adapter.submitList(it.data)
                }
            }
        }
    }

}