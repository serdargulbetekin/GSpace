package com.example.gspace.modules.createspaceship

import android.annotation.SuppressLint
import androidx.lifecycle.ViewModel
import com.example.gspace.modules.createspaceship.room.SpaceShipEntity
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class CreateSpaceShipViewModel @Inject constructor(private val createSpaceShipRepo: CreateSpaceShipRepo) :
    ViewModel() {

    private var endurance: Int = 0
    private var velocity: Int = 0
    private var capacity: Int = 0

    @SuppressLint("CheckResult")
    fun onClickContinue(name: String) {
        Observable.fromCallable {
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

            }, {

            })
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


}