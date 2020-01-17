package com.txusmslabs.templateapp.ui.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.txusmslabs.templateapp.R
import com.txusmslabs.templateapp.databinding.FragmentDetailBinding
import com.txusmslabs.templateapp.ui.common.app
import com.txusmslabs.templateapp.ui.common.bindingInflate
import com.txusmslabs.templateapp.ui.common.getViewModel

class DetailFragment : Fragment() {

    private lateinit var component: DetailFragmentComponent
    private val viewModel: DetailViewModel by lazy { getViewModel { component.detailViewModel } }
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
        component = app.component.plus(DetailFragmentModule(args.id))

        binding?.viewmodel = viewModel
        binding?.lifecycleOwner = this
    }
}