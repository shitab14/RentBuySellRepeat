package app.smir.rentbuysellrepeat.presentation.feature.product.adapter

/**
Created by Shitab Mir on 5/8/25.
shitabmir@gmail.com
 **/
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import app.smir.rentbuysellrepeat.presentation.feature.product.create.*

class CreateProductPagerAdapter(fragmentActivity: FragmentActivity) :
 FragmentStateAdapter(fragmentActivity) {

 override fun getItemCount(): Int = 6

 override fun createFragment(position: Int): Fragment {
  return when (position) {
   0 -> TitleFragment()
//   1 -> CategoriesFragment()
//   2 -> DescriptionFragment()
//   3 -> ImageFragment()
//   4 -> PriceFragment()
//   5 -> SummaryFragment()
   else -> throw IllegalArgumentException("Invalid position: $position")
  }
 }
}