package com.sizdev.calculatorxdice.calculator

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.sizdev.calculatorxdice.R
import kotlinx.android.synthetic.main.fragment_history.view.*
import kotlinx.android.synthetic.main.fragment_history_item.view.*
import java.util.*
import kotlin.collections.ArrayList


class HistoryFragment : BottomSheetDialogFragment() {
    private var modelListener: Listener? = null

    private lateinit var modelData: java.util.ArrayList<String>

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater!!.inflate(R.layout.fragment_history, container, false)

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        modelData = ArrayList<String>(Arrays.asList(getString(R.string.no_history)))

        val data = arguments?.getStringArrayList(ARG_HISTORY_ACTION)
        if (data != null) {
            if (data.isNotEmpty()) {
                modelData.clear()
                modelData.addAll(data)
            }
        }

        val recyclerView : RecyclerView? = view!!.list
        recyclerView!!.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = ItemAdapter(modelData)

        val buttonClearHistory: ImageButton = view.bt_clearHistory
        buttonClearHistory.setOnClickListener {
            if (data != null) {
                data.clear()
            }
            modelData.clear()
            modelData.add(getString(R.string.no_history))
            (recyclerView.adapter as Any)
            Toast.makeText(activity, getString(R.string.history_cleared), Toast.LENGTH_SHORT).show()
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        val parent = parentFragment
        if (parent != null) {
            modelListener = parent as Listener
        } else {
            modelListener = context as Listener?
        }
    }

    override fun onDetach() {
        modelListener = null
        super.onDetach()
    }

    interface Listener {
        fun onHistoryItemClicked(resultText: String)
    }

    private inner class ViewHolder(inflater: LayoutInflater, parent: ViewGroup) : RecyclerView.ViewHolder(inflater.inflate(R.layout.fragment_history_item, parent, false)) {

         val rowLayout: LinearLayout = itemView.row
         val actionTextView: TextView = itemView.action
         val resultTextView: TextView = itemView.result

        init {
            rowLayout.setOnClickListener {
                if (modelListener != null) {
                    modelListener!!.onHistoryItemClicked(resultTextView.text.toString())
                    dismiss()
                }
            }
        }

    }

    private inner class ItemAdapter internal constructor(private val mHistoryActionList: java.util.ArrayList<String>) : RecyclerView.Adapter<ViewHolder>() {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            return ViewHolder(LayoutInflater.from(parent.context), parent)
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            // Regular expression lookbehind with delimiter "="
            val reg = Regex("(?<=[=])")
            val historyActionList = mHistoryActionList.get(position).split(reg)
            holder.actionTextView.text = if (historyActionList.size == 1) "" else historyActionList.first()
            holder.resultTextView.text = historyActionList.last().trim()
        }

        override fun getItemCount(): Int {
            return mHistoryActionList.count()
        }

    }

    companion object {

        private val ARG_HISTORY_ACTION = "history_action"

        fun newInstance(historyActionList: java.util.ArrayList<String>): HistoryFragment {
            val fragment = HistoryFragment()
            val args = Bundle()
            args.putStringArrayList(ARG_HISTORY_ACTION, historyActionList)
            fragment.arguments = args
            return fragment
        }
    }

}