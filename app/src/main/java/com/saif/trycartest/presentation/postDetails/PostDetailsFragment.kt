package com.saif.trycartest.presentation.postDetails

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.saif.trycartest.PostDetailsFragmentArgs
import com.saif.trycartest.R
import com.saif.trycartest.databinding.FragmentFavoritePostsBinding
import com.saif.trycartest.databinding.FragmentPostDetailsBinding
import com.saif.trycartest.presentation.adapter.CommentAdapter
import com.saif.trycartest.presentation.adapter.PostAdapter
import com.saif.trycartest.presentation.core.BaseFragment
import com.saif.trycartest.presentation.favoritePosts.FavoritePostsViewModel
import com.saif.trycartest.presentation.favoritePosts.SuccessFavPostsData
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PostDetailsFragment : BaseFragment() {
    private val viewModel by viewModels<PostDetailsViewModel>()

    private val args: PostDetailsFragmentArgs by navArgs()
    private lateinit var binding: FragmentPostDetailsBinding

    private lateinit var adapter: CommentAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_post_details, container, false)

        setUp()

        return binding.root
    }


    private fun setUp() {
        viewModel.getPostComments(args.post.id ?: 1)

        setUi()
        observeData()
    }

    private fun setUi() {
        adapter = CommentAdapter()
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