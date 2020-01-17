package com.txusmslabs.templateapp.ui.main

import android.Manifest.permission.ACCESS_COARSE_LOCATION
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.findNavController
import com.txusmslabs.templateapp.R
import com.txusmslabs.templateapp.databinding.FragmentMainBinding
import com.txusmslabs.templateapp.ui.common.*
import kotlinx.android.synthetic.main.fragment_main.*
import kotlinx.android.synthetic.main.toolbar.*

class MainFragment : Fragment() {

    private lateinit var adapter: MoviesAdapter
    private val coarsePermissionRequester by lazy {
        PermissionRequester(
            activity!!,
            ACCESS_COARSE_LOCATION
        )
    }
    private lateinit var component: MainFragmentComponent
    private val viewModel: MainViewModel by lazy { getViewModel { component.mainViewModel } }

    private lateinit var navController: NavController
    private var binding: FragmentMainBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = container?.bindingInflate(R.layout.fragment_main, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = view.findNavController()
        toolbar.setTitle(R.string.app_name)
        component = app.component.plus(MainFragmentModule())

        viewModel.navigateToMovie.observe(this, EventObserver { id ->
            val action = MainFragmentDirections.actionMainFragmentToDetailFragment(id)
            navController.navigate(action)
        })

        viewModel.requestLocationPermission.observe(this, EventObserver {
            coarsePermissionRequester.request {
                viewModel.onCoarsePermissionRequested()
            }
        })

        adapter = MoviesAdapter(viewModel::onMovieClicked)
        recycler.adapter = adapter

        binding?.apply {
            viewmodel = viewModel
            lifecycleOwner = this@MainFragment
        }
    }
}