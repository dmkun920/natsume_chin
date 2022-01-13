package com.aizk.natsuchin

// プレーヤ ☓一人
// ダイスセット（３個）をEnvironmentから渡される
// １ゲーム分のダイスを振る
// インスタンス同士を比較できる（強さが同じか）

class ChinPlayer(aName: String, dices: ChinDices) {

    // インスタンス変数
    var myName: String = aName
    val myDices: ChinDices = dices
    var myHand: Hand = Hand.Butame
    var power: Int = 0
    var myPoints: Int = 1000
    var howWon: Judges = Judges.na

    var myHandName: String = "buta"
    var myHandMe: String = "[0][0][0]"
    var myHandKachime: Int = 0

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