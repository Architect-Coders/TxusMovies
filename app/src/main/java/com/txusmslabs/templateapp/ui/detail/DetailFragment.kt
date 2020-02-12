package com.txusmslabs.templateapp.ui.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.txusmslabs.templateapp.R
import com.txusmslabs.templateapp.databinding.FragmentDetailBinding
import com.txusmslabs.templateapp.ui.common.app
import com.txusmslabs.templateapp.ui.common.bindingInflate
import com.txusmslabs.templateapp.ui.common.toast
import org.koin.android.scope.currentScope
import org.koin.android.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class DetailFragment : Fragment() {

    private val viewModel: DetailViewModel by currentScope.viewModel(this) {
        parametersOf(args.id)
    }
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

        viewModel.notFound.observe(this, Observer {
            if (it) {
                app.toast(R.string.message_movie_not_found)
                findNavController().popBackStack()
            }
        })
        binding?.viewmodel = viewModel
        binding?.lifecycleOwner = this
    }
}