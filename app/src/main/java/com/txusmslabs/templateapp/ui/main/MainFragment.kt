package com.txusmslabs.templateapp.ui.main

import android.Manifest.permission.ACCESS_COARSE_LOCATION
import android.os.Bundle
import android.view.*
import androidx.navigation.NavController
import androidx.navigation.findNavController
import com.txusmslabs.templateapp.R
import com.txusmslabs.templateapp.databinding.FragmentMainBinding
import com.txusmslabs.templateapp.ui.common.EventObserver
import com.txusmslabs.templateapp.ui.common.PermissionRequester
import com.txusmslabs.templateapp.ui.common.SharedViewModel
import com.txusmslabs.templateapp.ui.dialog.AlertFragmentDirections
import org.koin.androidx.scope.ScopeFragment
import org.koin.androidx.viewmodel.ext.android.sharedViewModel


class MainFragment : ScopeFragment() {

    private lateinit var adapter: MoviesAdapter
    private val coarsePermissionRequester by lazy {
        PermissionRequester(
            requireActivity(),
            ACCESS_COARSE_LOCATION
        )
    }
    private val viewModel: MainViewModel by inject()
    private val sharedViewModel: SharedViewModel by sharedViewModel()

    private lateinit var navController: NavController
    private lateinit var binding: FragmentMainBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        setHasOptionsMenu(true)
        binding = FragmentMainBinding.inflate(layoutInflater)
        return binding.root
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
        binding.recycler.adapter = adapter

        with(binding) {
            viewmodel = viewModel
            lifecycleOwner = viewLifecycleOwner
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_settings -> {
                val action = AlertFragmentDirections.actionToAlertFragment("Hola!!")
                navController.navigate(action)
                true
            }
            R.id.action_test_deeplink -> {

                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_main, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }
}