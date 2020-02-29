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
import com.txusmslabs.templateapp.ui.common.EventObserver
import com.txusmslabs.templateapp.ui.common.PermissionRequester
import com.txusmslabs.templateapp.ui.common.bindingInflate
import kotlinx.android.synthetic.main.fragment_main.*
import kotlinx.android.synthetic.main.toolbar.*
import org.koin.android.scope.currentScope
import org.koin.android.viewmodel.ext.android.viewModel

class MainFragment : Fragment() {

    private lateinit var adapter: MoviesAdapter
    private val coarsePermissionRequester by lazy {
        PermissionRequester(
            activity!!,
            ACCESS_COARSE_LOCATION
        )
    }
    private val viewModel: MainViewModel by currentScope.viewModel(this)

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

        viewModel.navigateToMovie.observe(viewLifecycleOwner, EventObserver { id ->
            val action = MainFragmentDirections.actionMainFragmentToDetailFragment(id)
            navController.navigate(action)
        })

        viewModel.requestLocationPermission.observe(viewLifecycleOwner, EventObserver {
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