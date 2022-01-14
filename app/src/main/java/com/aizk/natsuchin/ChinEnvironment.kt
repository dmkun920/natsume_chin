package com.aizk.natsuchin

import android.util.Log

/**
 * 四人のプレーヤを生む
 * クリックされたら１回ゲームする
 * ゲームのジャッジ（誰がどんな手で何点買ったか／負けたかの判定）ができる
 */
class ChinEnvironment() {

    // インスタンス変数
    var playDices = ChinDices()
    val players: MutableList<ChinPlayer> = mutableListOf()

    // 初期化メソット（必ず呼ばれる）
    init {
        players.add(ChinPlayer("Player1", playDices))
        players.add(ChinPlayer("Player2", playDices))
        players.add(ChinPlayer("Player3", playDices))
        players.add(ChinPlayer("Me     ", playDices))
    }

    // 地方によってルールが少し違うかもしれない
    fun judging(aBet: Int) {

        val sortedPlayers: List<ChinPlayer> = players.sortedWith(compareBy({ it.myHand.power }, { it.power }))

        // 上位二人が同点ならDrowで、チャラ（ChinPlayerを直接比較できる；参照equals()）
        val top2Players: List<ChinPlayer> = sortedPlayers.takeLast(2)
        if ((top2Players.first()) == (top2Players.last())) {
            top2Players.first().howWon = Judges.Drow
            top2Players.last().howWon = Judges.Drow
            return
        }

        // 普通の順位づけ（得点移動あり）
        val winnerPlayer: ChinPlayer = sortedPlayers.last()
        val winnerPower: Int = winnerPlayer.power

        Log.d("myTAG", "winnerPlayer:$winnerPlayer")     // ToDo: debug-write
        Log.d("myTAG", "winnerPower:$winnerPower")     // ToDo: debug-write


        // 勝者が１２３か判定
        winnerPlayer.howWon = if (winnerPower >= 0) Judges.Win else Judges.Fall

        // 敗者から勝者（１２３含む）にポイントを移動する
        // （勝者以外の人から勝者（１２３含む）への移動のみ）
        for (eachPlayer: ChinPlayer in sortedPlayers.filter { it != winnerPlayer }) {
            eachPlayer.myPoints -= winnerPower * aBet
            winnerPlayer.myPoints += winnerPower * aBet
        }

        return
    }

}