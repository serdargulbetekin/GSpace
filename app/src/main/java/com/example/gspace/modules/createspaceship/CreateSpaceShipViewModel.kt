package com.example.gspace.modules.createspaceship

import android.annotation.SuppressLint
import androidx.lifecycle.ViewModel
import com.example.gspace.modules.createspaceship.room.SpaceShipEntity
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class CreateSpaceShipViewModel @Inject constructor(private val createSpaceShipRepo: CreateSpaceShipRepo) :
    ViewModel() {

    private var endurance: Int = 0
    private var velocity: Int = 0
    private var capacity: Int = 0

    private val totalFeature = 15

    @SuppressLint("CheckResult")
    fun onClickContinue(
        name: String,
        onNameEmptyError: () -> Unit,
        onAddComplete: () -> Unit,
        onAmountExceedError: () -> Unit,
        onFeatureZeroError: () -> Unit,
        onError: () -> Unit
    ) {
        when {
            name.isEmpty() -> {
                onNameEmptyError.invoke()
            }
            isExceedTheMaxTotalFeature() -> {
                onAmountExceedError.invoke()
            }
            isFeatureZero() -> {
                onFeatureZeroError.invoke()
            }
            else -> {
                Single.fromCallable {
                    createSpaceShipRepo.insertSpaceShip(
                        SpaceShipEntity(
                            name = name,
                            endurance = endurance.toString(),
                            velocity = velocity.toString(),
                            capacity = capacity.toString()
                        )
                    )
                }.subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe({
                        onAddComplete.invoke()
                    }, {
                        onError.invoke()
                    })
            }
        }
    }

    fun onEnduranceChange(endurance: Int) {
        this.endurance = endurance
    }

    fun onVelocityChange(velocity: Int) {
        this.velocity = velocity
    }

    fun onCapacityChange(capacity: Int) {
        this.capacity = capacity
    }

    private fun isExceedTheMaxTotalFeature() = (endurance + velocity + capacity) > totalFeature
    private fun isFeatureZero() = (endurance == 0 || velocity == 0 || capacity == 0)


}