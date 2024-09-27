package com.example.lab_week_05

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.fragment.app.Fragment
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.firestore.FirebaseFirestore

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [InsertFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class InsertFragment : BaseAuthFragment() {
    private val db = FirebaseFirestore.getInstance()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
// Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_insert, container, false)
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val coffeeInput =
            view.findViewById<TextInputEditText>(R.id.coffee_title_input_field)
        val submitButton = view.findViewById<Button>(R.id.submit_button)
        submitButton.setOnClickListener {
            val coffeeTitle = coffeeInput.text.toString()
            Log.d("coffee title",coffeeTitle)
            if (coffeeTitle.isNotEmpty()) {
                val coffeeData = mapOf("title" to coffeeTitle)
                db.collection("coffees").add(coffeeData)
                    .addOnSuccessListener { documentReference ->
                        Log.d(
                            "Firestore",
                            "DocumentSnapshot added with ID: ${documentReference.id}"
                        )
                    }.addOnFailureListener { e ->
                        Log.w("Firestore", "Error adding document", e)
                        Toast.makeText(
                            requireContext(),
                            "Error adding coffee title",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                coffeeInput.text?.clear()
            } else {
                Toast.makeText(requireContext(), "Coffee Title is Empty",
                    Toast.LENGTH_LONG).show()
            }
        }
    }
}
