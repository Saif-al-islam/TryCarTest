package com.saif.trycartest.presentation.postDetails

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.saif.trycartest.R
import com.saif.trycartest.databinding.FragmentPostDetailsBinding
import com.saif.trycartest.presentation.adapter.CommentAdapter
import com.saif.trycartest.presentation.core.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PostDetailsFragment : BaseFragment() {
    private val viewModel by viewModels<PostDetailsViewModel>()

    private val args: PostDetailsFragmentArgs by navArgs()
    private lateinit var binding: FragmentPostDetailsBinding

    private lateinit var adapter: CommentAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_post_details, container, false)

        setUp()

        return binding.root
    }


    private fun setUp() {
        viewModel.isPostFavoriteUseCase(args.post.id ?: 0)
        viewModel.getPostComments(args.post.id ?: 0)
        binding.item = args.post

        setUi()
        observeData()
    }

    private fun setUi() {
        adapter = CommentAdapter()
        binding.rv.adapter = adapter

        onClicks()
    }

    private fun observeData() {
        observingData(
            binding.root, viewModel.postsFlow, binding.pbLoading, viewModel.loadingFlow
        ) {
            when (it) {
                is SuccessCommentsData -> {
                    adapter.submitList(it.data)
                }
            }
        }

        observingData(
            binding.root,
            viewModel.isPostFavFlow,
            binding.pbLoading,
            viewModel.loadingFlow,
            binding.ivFav
        ) {
            when (it) {
                is SuccessPostFavData -> {
                    checkPostFavorite(it.isFav)
                }
            }
        }
    }

    private fun checkPostFavorite(isFav: Int) {
        binding.ivFav.setImageResource(
            if (isFav == 1) R.drawable.ic_favorite else R.drawable.ic_unfavorite
        )
    }

    private fun onClicks() {
        binding.ivFav.setOnClickListener {
            val isFavoriteNew =
                if ((viewModel.isPostFavFlow.value as? SuccessPostFavData)?.isFav == 1) 0 else 1
            viewModel.changePostFavorite(args.post.copy(isFav = isFavoriteNew))
        }

    }


}