package com.aizk.natsuchin

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.aizk.natsuchin.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    // インスタンス変数
    private lateinit var binding: ActivityMainBinding
    val thisEnvironment: ChinEnvironment = ChinEnvironment()
    var viewList: MutableList<ViewSet> = mutableListOf<ViewSet>()
    var playCnt = 0

    // "onCreate"イベントを拾う；ここから始まる
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        binding = ActivityMainBinding.inflate(layoutInflater)

        binding.let {
            viewList.add(ViewSet(it.btP1, it.tvP11, it.tvP12, it.tvP13, thisEnvironment.players[0]))
            viewList.add(ViewSet(it.btP2, it.tvP21, it.tvP22, it.tvP23, thisEnvironment.players[1]))
            viewList.add(ViewSet(it.btP3, it.tvP31, it.tvP32, it.tvP33, thisEnvironment.players[2]))
            viewList.add(ViewSet(it.btP4, it.tvP41, it.tvP42, it.tvP43, thisEnvironment.players[3]))
        }

        val myTurn1 = TurnOfPlayer(viewList[0])
        val myTurn2 = TurnOfPlayer(viewList[1])
        val myTurn3 = TurnOfPlayer(viewList[2])
        val myTurn4 = TurnOfPlayer(viewList[3])

        //        Log.d("myTAG", "登録前")     // debug-write

        // 画面のボタンとプログラムを結びつける
        binding.apply {
            btP1.setOnClickListener(myTurn1)          // リスナ関数を登録
            btP2.setOnClickListener(myTurn2)          // リスナ関数を登録
            btP3.setOnClickListener(myTurn3)          // リスナ関数を登録
            btP4.setOnClickListener(myTurn4)          // リスナ関数を登録

            btPN.setOnClickListener(NextListener())          // リスナ関数を登録

//            btPN.isEnabled = false
        }

    }

    // リスナとして登録されるクラス　★クラス名は大文字で始まる
    private inner class NextListener : View.OnClickListener {
        // onClickイベントを拾うメソッドをオーバーライド
        override fun onClick(view: View) {

            for (aView in viewList) {
                aView.tv1.text = ""
                aView.tv2.text = ""
                aView.tv3.text = ""
                aView.bt.isEnabled = true
            }
//            binding.btPN.isEnabled = false

        }
    }

    private inner class TurnOfPlayer(aViewSet: ViewSet) : View.OnClickListener {

        val myButton: Button = aViewSet.bt
        val textView1: TextView = aViewSet.tv1
        val textView2: TextView = aViewSet.tv2
        val aPlayer: ChinPlayer = aViewSet.player

        // onClickイベントを拾うメソッドをオーバーライド
        override fun onClick(v: View?) {

//            myButton.isEnabled = false

//            Log.d("myTAG", "onClick in..." )     // debug-write


            // 指定プレーヤにサイを振らせる
            aPlayer.setThrowing()
            textView1.text = aPlayer.myHandMe
            textView2.text = aPlayer.myHandName + "(" + aPlayer.myHandKachime + ") "

            playCnt += 1

            if (playCnt == 4) { // 4人プレーしたら判定（judging()）へ

                val aBet: Int? = binding.betValue.text.toString().toIntOrNull()
                if (aBet == null) {
                    return  // Betを入力項目からget
                }

                binding.btPN.isEnabled = true
                thisEnvironment.judging(aBet)
                putview()   // 画面への出力

                playCnt = 0
            }
        }

    }

    fun putview() {

        val playersPuts: MutableList<String> = mutableListOf<String>()

        for (eachPlayer in thisEnvironment.players) {
            var bufString: String =
                if (eachPlayer.howWon != Judges.na) " <" + eachPlayer.howWon.toString() + "> " else "　　　　"
            bufString += eachPlayer.myPoints.toString()

            playersPuts.add(bufString)
        }

//            Log.d("myTAG", "After string Set")     // debug-write
        binding.apply {
            tvP13.text = playersPuts[0]
            tvP23.text = playersPuts[1]
            tvP33.text = playersPuts[2]
            tvP43.text = playersPuts[3]
        }

    }
}

// このクラスパッケージのインスタンス全体で使うemun型(グローバルenum）
enum class Hand(val power: Int) {
    Butame(1),
    Yakume(2),
    Sigoro(3),
    Zorome(4),
    Hifumi(99)
}

enum class Judges {
    Win, Fall, Drow, na
}

// ★★★　viewをMainで簡易的につくっているので、、これのList型で表現。　本来は「データバインディング」で実装する
data class ViewSet(
    var bt: Button,
    var tv1: TextView,
    var tv2: TextView,
    var tv3: TextView,
    var player: ChinPlayer
    )

