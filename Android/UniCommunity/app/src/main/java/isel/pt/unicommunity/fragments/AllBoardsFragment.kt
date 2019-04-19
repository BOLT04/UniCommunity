package isel.pt.unicommunity.fragments

import android.content.Context
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import isel.pt.unicommunity.MainActivity

import isel.pt.unicommunity.R
import isel.pt.unicommunity.adapters.AllBoardsAdapter
import isel.pt.unicommunity.adapters.BoardClickListener
import isel.pt.unicommunity.kotlinx.getViewModel
import isel.pt.unicommunity.model.SmallBoardItem
import isel.pt.unicommunity.viewmodel.AllBoardsViewModel
import kotlinx.android.synthetic.main.fragment_all_boards.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * [AllBoardsFragment.OnFragmentInteractionListener] interface
 * to handle interaction events.
 * Use the [AllBoardsFragment.newInstance] factory method to
 * create an instance of this fragment.
 *
 */
class AllBoardsFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
   // private var listener: OnFragmentInteractionListener? = null




    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_all_boards, container, false)
    }

    override fun onStart() {
        super.onStart()

        val viewModel = (activity as AppCompatActivity).getViewModel("allBoards"){
            AllBoardsViewModel(/*id!!*/)//TODO double bangs onde e que ha mesmo a verificaçao
        }

        recyclerView.layoutManager = LinearLayoutManager(activity)
        val onBoardClickListener = object : BoardClickListener {
            override fun onClickListener(smallBoardItem: SmallBoardItem?) {

                (activity as MainActivity).navigateTo(BoardMenuFragment())

                Toast.makeText(activity, smallBoardItem?.name ?: "nullsmall board item", Toast.LENGTH_LONG).show()
            }
        }
        recyclerView.adapter = AllBoardsAdapter(viewModel, onBoardClickListener)

        viewModel.liveData.observe(
            this,
            Observer { recyclerView.adapter =  AllBoardsAdapter(viewModel, onBoardClickListener)}
        )
    }



    // TODO: Rename method, update argument and hook method into UI event
    /*fun onButtonPressed(uri: Uri) {
        listener?.onFragmentInteraction(uri)
    }
*/
    /*override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnFragmentInteractionListener) {
            listener = context
        } else {
            throw RuntimeException(context.toString() + " must implement OnFragmentInteractionListener")
        }
    }
*//*
    override fun onDetach() {
        super.onDetach()
        listener = null
    }*/

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     *
     *
     * See the Android Training lesson [Communicating with Other Fragments]
     * (http://developer.android.com/training/basics/fragments/communicating.html)
     * for more information.
     */
   /* interface OnFragmentInteractionListener {
        // TODO: Update argument type and title
        fun onFragmentInteraction(uri: Uri)
    }*/

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment AllBoardsFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            AllBoardsFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}
