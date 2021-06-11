package com.txusmslabs.templateapp.ui.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.txusmslabs.domain.Movie
import com.txusmslabs.templateapp.R
import com.txusmslabs.templateapp.databinding.FragmentDetailBinding
import com.txusmslabs.templateapp.ui.common.BaseFragment
import com.txusmslabs.templateapp.ui.common.SharedViewModel
import com.txusmslabs.templateapp.ui.common.app
import com.txusmslabs.templateapp.ui.common.toast
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class DetailFragment : BaseFragment() {

    private val viewModel: DetailViewModel by viewModel {
        parametersOf(args.id)
    }
    private val sharedViewModel: SharedViewModel by sharedViewModel()
    private val args: DetailFragmentArgs by navArgs()
    private lateinit var binding: FragmentDetailBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDetailBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding) {
            viewmodel = viewModel
        }

        viewModel.findMovie()
    }

    private fun onStateChanged(movie: Movie) {
        with(movie) {
            binding.isFavourite = favorite
            binding.title = title
            binding.url = backdropPath
            binding.movieDetailInfo.updateMovieDetails(this)
        }
    }

    override fun initFlows() {
        launch {
            viewModel.uiState.collect {
                if (it.notFound) {
                    app.toast(R.string.message_movie_not_found)
                    findNavController().popBackStack()
                }
                it.movie?.let { m -> onStateChanged(m) }
            }
        }
    }
}