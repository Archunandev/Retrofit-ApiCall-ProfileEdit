package com.example.retrofit

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.viewpager.widget.ViewPager
import com.google.android.material.tabs.TabLayout

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
     //   val textView = findViewById<TextView>(R.id.textView)
      //  val button = findViewById<Button>(R.id.button)
      //  val number_editText = findViewById<EditText>(R.id.number_editText)
       // setupRecyclerView()

        setUpTabs()
       // val shimmer = findViewById<ShimmerFrameLayout>(R.id.shimmer_view_container)



/*    val repository = Repository()
  val viewModelFactory = MainViewModelFactory(repository)
  val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)

  viewModel = ViewModelProvider(this, viewModelFactory).get(MainViewModel::class.java)

  viewModel.getCustomPost(2,"id","desc")
  viewModel.myCustomPost.observe(this, Observer { response ->


      if (response.isSuccessful){
          response.body()?.let { myAdapter.setData(it) }
      }else
      {

          //Toast()
      }

  }) */

}

private fun setUpTabs() {
  val viewPager = findViewById<ViewPager>(R.id.viewPager)
  val tablayout = findViewById<TabLayout>(R.id.tablayout)
  val adapter  = OrderPagerAdapter(supportFragmentManager)
    adapter.addFragment(GetFragment(),"GET")
    adapter.addFragment(ProfileFragment(),"PROFILE")
    adapter.addFragment(PostFragment(),"UPDATE")
  viewPager.adapter = adapter
  tablayout.setupWithViewPager(viewPager)

  tablayout.getTabAt(0)!!.setIcon(R.drawable.ic_baseline_get_app_24)
  tablayout.getTabAt(1)!!.setIcon(R.drawable.ic_baseline_person_24)
    tablayout.getTabAt(2)!!.setIcon(R.drawable.ic_baseline_edit_24)



}

/*  private fun setupRecyclerView(){
  val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
  recyclerView.adapter = myAdapter
  recyclerView.layoutManager = LinearLayoutManager(this)


} */


}