package sanjarbek.uz.patenttest.ui.screens

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import sanjarbek.uz.patenttest.R
import sanjarbek.uz.patenttest.databinding.FragmentSelectVariantBinding
import sanjarbek.uz.patenttest.domin.moc_data.AppHardCodeData
import sanjarbek.uz.patenttest.utils.Constants

class SelectVariantFragment : Fragment() {

    private var _binding: FragmentSelectVariantBinding?=null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentSelectVariantBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding){
            btnFirstVariant.setOnClickListener {
                val bundle =Bundle().apply {
                    putParcelable(Constants.String.VARIANT_LIST, AppHardCodeData.firstVariant)
                }

                val fragment = WorkTestFragment()
                fragment.arguments = bundle

                parentFragmentManager.beginTransaction()
                    .replace(R.id.container, fragment)
                    .addToBackStack(null)
                    .commit()
            }

        }
    }
    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}