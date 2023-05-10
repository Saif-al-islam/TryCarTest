package com.saif.trycartest.presentation.favoritePosts

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.saif.trycartest.R
import com.saif.trycartest.databinding.FragmentFavoritePostsBinding
import com.saif.trycartest.domain.models.Post
import com.saif.trycartest.presentation.adapter.PostAdapter
import com.saif.trycartest.presentation.core.BaseFragment
import com.saif.trycartest.presentation.posts.PostsFragmentDirections
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

    override fun onStart() {
        super.onStart()

        viewModel.getFavoritePosts()
    }


    private fun setUp() {
        setUi()
        observeData()
    }

    private fun setUi() {
        adapter = PostAdapter(::onItemClick)
        binding.rv.adapter = adapter
    }

    private fun onItemClick(post: Post) {
        findNavController().navigate(
            FavoritePostsFragmentDirections.actionFavoritePostsFragmentToPostDetailsFragment(
                post
            )
        )
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

                    binding.tvEmptyList.visibility =
                        if (it.data.isNullOrEmpty()) View.VISIBLE else View.GONE
                }
            }
        }
    }

}