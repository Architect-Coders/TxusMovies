package com.txusmslabs.templateapp.ui.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.txusmslabs.templateapp.R
import com.txusmslabs.templateapp.databinding.FragmentDetailBinding
import com.txusmslabs.templateapp.ui.common.SharedViewModel
import com.txusmslabs.templateapp.ui.common.app
import com.txusmslabs.templateapp.ui.common.toast
import org.koin.androidx.scope.ScopeFragment
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import org.koin.core.parameter.parametersOf

class DetailFragment : ScopeFragment() {

    private val viewModel: DetailViewModel by inject {
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

        viewModel.notFound.observe(viewLifecycleOwner, Observer {
            if (it) {
                app.toast(R.string.message_movie_not_found)
                findNavController().popBackStack()
            }
        })

        with(binding) {
            viewmodel = viewModel
            lifecycleOwner = viewLifecycleOwner
        }
    }
}