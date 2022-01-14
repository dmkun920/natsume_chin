package com.aizk.natsuchin

/**
 * プレーヤ ☓一人
 * ダイスセット（３個）をEnvironmentから渡される
 * １ゲーム分のダイスを振る
 * インスタンス同士を比較できる（強さが同じか）
 */
class ChinPlayer(aName: String, dices: ChinDices) {

    // インスタンス変数
    var myName = aName
    val myDices = dices
    var myHand = Hand.Butame
    var power = 0
    var myPoints = 1000
    var howWon = Judges.na

    var myHandName = "buta"
    var myHandMe = "[0][0][0]"
    var myHandKachime = 0

    init {
        setThrowing()
    }

    // 三回スローイング（ブタでなければそこで終わり）
    fun setThrowing() {
        myDices.throwing()
        if (myDices.isButame()) myDices.throwing()
        if (myDices.isButame()) myDices.throwing()
        power = myDices.power
        myHand = myDices.myHand

        myHandName = myDices.myHandName()
        myHandMe = myDices.myHandMe()
        myHandKachime = myDices.kachime
        howWon = Judges.na
    }

    // comparing

    // このオブジェクトを”＝＝”で比較する
    override fun equals(other: Any?): Boolean {
        val aPlayer: ChinPlayer? = other as? ChinPlayer
        return ((myHand == aPlayer?.myHand) and (power == aPlayer?.power))
    }

}