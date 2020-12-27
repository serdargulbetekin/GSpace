package com.example.gspace.modules.createspaceship

import android.os.Bundle
import android.widget.SeekBar
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.gspace.databinding.ActivityCreateSpaceShipBinding

class CreateSpaceShipActivity : AppCompatActivity() {

    private val viewBinding by lazy { ActivityCreateSpaceShipBinding.inflate(layoutInflater) }
    private val viewModel by viewModels<CreateSpaceShipViewModel>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(viewBinding.root)
        viewModel.onClickContinue(viewBinding.editTextName.text.toString())
    }

    private fun handleSeekBar() {
        viewBinding.apply {

            seekBarEndurance.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
                override fun onProgressChanged(seekBar: SeekBar?, progress: Int, p2: Boolean) {
                    viewModel.onEnduranceChange(progress)
                }

                override fun onStartTrackingTouch(p0: SeekBar?) {

                }

                override fun onStopTrackingTouch(p0: SeekBar?) {
                }

            })

            seekBarVelocity.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
                override fun onProgressChanged(seekBar: SeekBar?, progress: Int, p2: Boolean) {
                    viewModel.onVelocityChange(progress)
                }

                override fun onStartTrackingTouch(p0: SeekBar?) {

                }

                override fun onStopTrackingTouch(p0: SeekBar?) {
                }

            })

            seekBarCapacity.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
                override fun onProgressChanged(seekBar: SeekBar?, progress: Int, p2: Boolean) {
                    viewModel.onCapacityChange(progress)
                }

                override fun onStartTrackingTouch(p0: SeekBar?) {

                }

                override fun onStopTrackingTouch(p0: SeekBar?) {
                }

            })
        }
    }
}