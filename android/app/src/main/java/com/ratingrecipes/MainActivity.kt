package com.ratingrecipes

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.play.core.review.ReviewManagerFactory
import com.ratingrecipes.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        binding.inAppRating.setOnClickListener {
            val manager = ReviewManagerFactory.create(applicationContext)
            val request = manager.requestReviewFlow()
            request.addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val reviewInfo = task.result
                    val flow = manager.launchReviewFlow(this, reviewInfo)
                    flow.addOnCompleteListener { _ ->
                    }
                } else {
                    Toast.makeText(this, task.exception.toString(), Toast.LENGTH_LONG).show()
                }
            }
        }

        binding.ratingPrompt.setOnClickListener {
            MaterialAlertDialogBuilder(this)
                .setMessage("Do you want to rate this app?")
                .setNegativeButton("No") { dialog, which ->
                }
                .setPositiveButton("Yes") { dialog, which ->
                }
                .show()
        }

        binding.sentimentPrompt.setOnClickListener {
            MaterialAlertDialogBuilder(this)
                .setMessage("Do you want enjoy this app?")
                .setNegativeButton("No") { dialog, which ->
                }
                .setPositiveButton("Yes") { dialog, which ->
                }
                .show()
        }
    }
}