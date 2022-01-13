package com.aizk.natsuchin

// ダイス１個
// ダイスを振る（1個）

class Dice() {

    // インスタンス変数
    var points: Int = 0

    fun throwing() {

        points = (1..6).random()

    }

}