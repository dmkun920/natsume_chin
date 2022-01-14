package com.aizk.natsuchin

/**
 * 3個のダイスがある（ダイスセット；一人分）
 * ダイスを振ることができる（3個）
 * インスタンス同士を比較できる（どちらが強いか）
 * 目と役を印刷する
 */
class ChinDices() {

    // インスタンス変数
    var power = 0
    var kachime = 0
    var myHand = Hand.Butame

    private var diceA = Dice()
    private var diceB = Dice()
    private var diceC = Dice()

    fun throwing() {
        diceA.throwing()
        diceB.throwing()
        diceC.throwing()
        myHand = handAnalyze()
    }

    // printing
    fun myHandMe(): String {
        val myMeA = diceA.points
        val myMeB = diceB.points
        val myMeC = diceC.points

        return ("[$myMeA] [$myMeB] [$myMeC] ")
    }

    // Enumだけで実装できる；enum>Hand参照（これはmap型を使用した例）
    fun myHandName(): String {
        val yakumeiDictionary: Map<Hand, String> =
            mapOf(
                Hand.Butame to "ぶた",
                Hand.Yakume to "役目",
                Hand.Sigoro to "四五六",
                Hand.Zorome to "ぞろ目",
                Hand.Hifumi to "一二三"
            )
        val yakumei: String? = yakumeiDictionary[myHand]

        return (if (yakumei != null) yakumei else "---" )
    }

    // accessing
    fun isButame(): Boolean {
        return (myHand == Hand.Butame)
    }

    // private
    private fun pairDicePoint(): Int {
        if (diceA.points == diceB.points) return (diceC.points)
        if (diceB.points == diceC.points) return (diceA.points)
        if (diceA.points == diceC.points) return (diceB.points)
        return (0)
    }

    private fun handAnalyze(): Hand {

        if ((diceA.points == diceB.points) && (diceA.points == diceC.points)) {
            return (yakuTrio(diceA.points))
        }

        val pairPoints: Int = pairDicePoint()
        val totalPoints: Int = diceA.points + diceB.points + diceC.points
        if (pairPoints > 0) return (yakuPair(pairPoints))
        if (totalPoints == 6) return (yaku123())
        if (totalPoints == 15) return (yaku456())

        return (buta())
    }

    private fun yakuTrio(aNumber: Int): Hand {
        kachime = aNumber
        power = kachime * (if (aNumber == 1) 100 else 10)
        return (Hand.Zorome)
    }

    private fun yakuPair(aNumber: Int): Hand {
        kachime = aNumber
        power = kachime * 1
        return (Hand.Yakume)
    }

    private fun yaku456(): Hand {
        kachime = 1
        power = kachime * 30
        return (Hand.Sigoro)
    }

    private fun yaku123(): Hand {
        kachime = 1
        power = kachime * -20
        return (Hand.Hifumi)
    }

    private fun buta(): Hand {
        kachime = 0
        power = 0
        return (Hand.Butame)
    }

}

