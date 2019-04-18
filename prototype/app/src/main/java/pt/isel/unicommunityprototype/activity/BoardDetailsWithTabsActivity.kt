package pt.isel.unicommunityprototype.activity

import android.content.Intent
import com.google.android.material.tabs.TabLayout
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.viewpager.widget.ViewPager
import android.os.Bundle
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.tabs.TabItem

import pt.isel.unicommunityprototype.R
import kotlinx.android.synthetic.main.activity_board_details_with_tabs.*
import kotlinx.android.synthetic.main.fragment_board_details_forum_tab.view.*
import kotlinx.android.synthetic.main.fragment_board_details_with_tabs.*
import kotlinx.android.synthetic.main.fragment_board_details_with_tabs.view.*
import pt.isel.unicommunityprototype.adapter.PostsAdapter
import pt.isel.unicommunityprototype.kotlinx.getUniApplication
import pt.isel.unicommunityprototype.model.Board
import pt.isel.unicommunityprototype.model.Post

class BoardDetailsWithTabsActivity : AppCompatActivity() {

    /**
     * The [androidx.viewpager.widget.PagerAdapter] that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * androidx.fragment.app.FragmentStatePagerAdapter.
     */
    private var mSectionsPagerAdapter: SectionsPagerAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_board_details_with_tabs)

        setSupportActionBar(toolbar)

        val app = getUniApplication()
        val boardId = intent.getIntExtra("boardId", 0)
        val board = app.repository.getBoardById(boardId)
        supportActionBar?.title = board?.name

        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = SectionsPagerAdapter(supportFragmentManager, board)

        // Set up the ViewPager with the sections adapter.
        container.adapter = mSectionsPagerAdapter

        container.addOnPageChangeListener(TabLayout.TabLayoutOnPageChangeListener(tabs))
        tabs.addOnTabSelectedListener(TabLayout.ViewPagerOnTabSelectedListener(container))

        fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
        }



    }


    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_board_details_with_tabs, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        val id = item.itemId

        if (id == R.id.action_settings) {
            return true
        }

        return super.onOptionsItemSelected(item)
    }


    /**
     * A [FragmentPagerAdapter] that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    inner class SectionsPagerAdapter(
        fm: FragmentManager,
        val board: Board? //TODO: we need this since getItem() needs to know what tab its in to choose the fragment layout
    ) : FragmentPagerAdapter(fm) {

        override fun getItem(position: Int): Fragment {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).

            return PlaceholderFragment.newInstance(position + 1)
        }

        override fun getCount() = board?.getModulesSize()!! //TODO: how to take out double bang?
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    class PlaceholderFragment : Fragment() {

        override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
        ): View? {
            val rootView = inflater.inflate(R.layout.fragment_board_details_with_tabs, container, false)
            rootView.section_label.text = getString(R.string.section_format, arguments?.getInt(ARG_SECTION_NUMBER))

            /*
            rootView.createTabBtn.setOnClickListener {
                tabs.addTab(tabs.newTab())//TODO: HOW DO I KNOW WHAT I HAVE ACCESS...apparently i cant access tabs here since is null!!!
                //TODO: i know tabs is the id for the TabLayout in BoardDetailsWithTabsActivity so.... maybe since this is its fragment i have access to the activity...but how????
            }
            */

            return rootView
        }

        companion object {
            /**
             * The fragment argument representing the section number for this
             * fragment.
             */
            private val ARG_SECTION_NUMBER = "section_number"

            /**
             * Returns a new instance of this fragment for the given section
             * number.
             */
            fun newInstance(sectionNumber: Int): PlaceholderFragment {
                val fragment = PlaceholderFragment()
                val args = Bundle()
                args.putInt(ARG_SECTION_NUMBER, sectionNumber)
                fragment.arguments = args
                return fragment
            }
        }
    }
/*
    inner class ForumFragment : Fragment() { //TODO: do I have to use the keywork inner for this@BoardDetailsWithTabsActivity to work???

        override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
        ): View? {
            val rootView = inflater.inflate(R.layout.fragment_board_details_forum_tab, container, false)

            rootView.postsRecyclerView.setHasFixedSize(true)
            rootView.postsRecyclerView.layoutManager = LinearLayoutManager(this@BoardDetailsWithTabsActivity)

            val intent = Intent(this@BoardDetailsWithTabsActivity, PostDetailsActivity::class.java)
            val listener = object : PostsAdapter.OnPostClickListener {
                override fun onPostClick(post: Post?) {
                    // Update current board?
                    //app.repository.team = team!!
                    //app.chatBoard.associateTeam(team)
                    intent.putExtra("boardId", post?.id)
                    startActivity(intent)
                }
            }
            //rootView.postsRecyclerView.adapter = PostsAdapter(viewModel, listener)

            return rootView
        }
    }
*/
}