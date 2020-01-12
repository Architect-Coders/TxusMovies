package com.txusmslabs.templateapp.ui.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.txusmslabs.data.repository.MoviesRepository
import com.txusmslabs.data.repository.RegionRepository
import com.txusmslabs.templateapp.R
import com.txusmslabs.templateapp.databinding.FragmentDetailBinding
import com.txusmslabs.templateapp.framework.data.AndroidPermissionChecker
import com.txusmslabs.templateapp.framework.data.PlayServicesLocationDataSource
import com.txusmslabs.templateapp.framework.data.database.RoomDataSource
import com.txusmslabs.templateapp.framework.data.server.TheMovieDbDataSource
import com.txusmslabs.templateapp.ui.common.app
import com.txusmslabs.templateapp.ui.common.bindingInflate
import com.txusmslabs.templateapp.ui.common.getViewModel
import com.txusmslabs.usecases.FindMovieById
import com.txusmslabs.usecases.ToggleMovieFavorite

class DetailFragment : Fragment() {

    private lateinit var viewModel: DetailViewModel
    private val args: DetailFragmentArgs by navArgs()
    private var binding: FragmentDetailBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = container?.bindingInflate(R.layout.fragment_detail, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = getViewModel {
            val moviesRepository = MoviesRepository(
                RoomDataSource(app.db),
                TheMovieDbDataSource(),
                RegionRepository(
                    PlayServicesLocationDataSource(app),
                    AndroidPermissionChecker(app)
                ),
                app.getString(R.string.api_key)
            )

            DetailViewModel(
                args.id,
                FindMovieById(moviesRepository),
                ToggleMovieFavorite(moviesRepository)
            )
        }

        binding?.viewmodel = viewModel
        binding?.lifecycleOwner = this
    }
}