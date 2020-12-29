package com.example.gspace.modules.createspaceship

import android.content.Intent
import android.os.Bundle
import android.widget.SeekBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.gspace.databinding.ActivityCreateSpaceShipBinding
import com.example.gspace.modules.createspaceship.factory.Factory
import com.example.gspace.modules.main.MainActivity

class CreateSpaceShipActivity : AppCompatActivity() {

    private val binding by lazy { ActivityCreateSpaceShipBinding.inflate(layoutInflater) }
    private val factory by lazy { Factory() }
    private lateinit var viewModel: CreateSpaceShipViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        viewModel = ViewModelProvider(this, factory).get(CreateSpaceShipViewModel::class.java)

        binding.textViewContinue.setOnClickListener {
            viewModel.onClickContinue(binding.editTextName.text.toString(),
                onNameEmptyError = {
                    Toast.makeText(this, "Name field can not be empty!!!", Toast.LENGTH_SHORT).show()
                },
                onAddComplete = {
                    startActivity(Intent(this, MainActivity::class.java))
                },
                onAmountExceedError = {
                    Toast.makeText(this, "Total feature can not exceed 15!!!", Toast.LENGTH_SHORT)
                        .show()
                },
                onFeatureZeroError = {
                    Toast.makeText(this, "One of features can not be 0!!!", Toast.LENGTH_SHORT)
                        .show()

                },
                onError = {
                    Toast.makeText(this, "Something went wrong!!!", Toast.LENGTH_SHORT).show()
                })
        }
        handleSeekBar()
    }

    private fun handleSeekBar() {
        binding.apply {
            seekBarEndurance.max = 13
            seekBarVelocity.max = 13
            seekBarCapacity.max = 13

            seekBarEndurance.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
                override fun onProgressChanged(seekBar: SeekBar?, progress: Int, p2: Boolean) {
                    viewModel.onEnduranceChange(progress)
                    textViewEndurance.text= progress.toString()
                }

                override fun onStartTrackingTouch(p0: SeekBar?) {

                }

                override fun onStopTrackingTouch(p0: SeekBar?) {
                }

            })

            seekBarVelocity.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
                override fun onProgressChanged(seekBar: SeekBar?, progress: Int, p2: Boolean) {
                    viewModel.onVelocityChange(progress)
                    textViewVelocity.text= progress.toString()
                }

                override fun onStartTrackingTouch(p0: SeekBar?) {

                }

                override fun onStopTrackingTouch(p0: SeekBar?) {
                }

            })

            seekBarCapacity.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
                override fun onProgressChanged(seekBar: SeekBar?, progress: Int, p2: Boolean) {
                    viewModel.onCapacityChange(progress)
                    textViewCapacity.text= progress.toString()
                }

                override fun onStartTrackingTouch(p0: SeekBar?) {

                }

                override fun onStopTrackingTouch(p0: SeekBar?) {
                }

            })
        }
    }
}