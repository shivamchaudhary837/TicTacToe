package com.example.tictactoe

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import android.widget.Toolbar
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_game.*
import kotlinx.coroutines.handleCoroutineException

class GameFragment : Fragment(),View.OnClickListener {

    private var Turn_Count=0
    private var PLAYER=true
    private lateinit var board:Array<Array<Button>>

    private var boardStatus=Array(3){IntArray(3)}


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_game, container, false)
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        board= arrayOf(
            arrayOf(button1,button2,button3),
            arrayOf(button4,button5,button6),
            arrayOf(button7,button8,button9)
        )
        //initialize board
        initializeBoard()

        for(i in board){
            for(button in i){
                button.setOnClickListener(this)
            }
        }

        reset.setOnClickListener{
            PLAYER=true
            Turn_Count=0
            initializeBoard()
        }
    }
    private fun   initializeBoard(){
        player_label.text="PLAYER X TURN"
        for(i in 0..2){
            for(j in 0..2){
                boardStatus[i][j]=-1
                board[i][j].isEnabled=true
                board[i][j].text=""
            }
        }
    }

    override fun onClick(view: View) {

        when(view.id){
            R.id.button1->{
                updateValue(row=0,column=0,player=PLAYER)
            }
            R.id.button2->{
                updateValue(0,1,PLAYER)
            }
            R.id.button3->{
                updateValue(0,2,PLAYER)
            }
            R.id.button4->{
                updateValue(1,0,PLAYER)
            }
            R.id.button5->{
                updateValue(1,1,PLAYER)
            }
            R.id.button6->{
                updateValue(1,2,PLAYER)
            }
            R.id.button7->{
                updateValue(2,0,PLAYER)
            }
            R.id.button8->{
                updateValue(2,1,PLAYER)
            }
            R.id.button9->{
                updateValue(2,2,PLAYER)
            }

        }
        Turn_Count++
        PLAYER= !PLAYER

        if(PLAYER){
            updateDisplay("PLAYER X TURN")
        }else{
            updateDisplay("PLAYER O TURN")
        }
        if(Turn_Count==9){
            updateDisplay("GAME OVER")
        }

        //for check winner
        checkWinner()
    }

    private fun checkWinner() {
        //for  horizontal wins
        for (i in 0..2){
            if(boardStatus[i][0]== boardStatus[i][1] && boardStatus[i][0]== boardStatus[i][2]) {
                if (boardStatus[i][0] == 1) {
                    updateDisplay("PLAYER X WINS")
                    break
                } else {
                    if (boardStatus[i][0] == 0) {
                        updateDisplay("PLAYER O WINS")
                        break
                    }
                }
            }
        }

        //for vertical wins
        for (i in 0..2){
            if(boardStatus[0][i]== boardStatus[1][i] && boardStatus[0][i]== boardStatus[2][i]) {
                if (boardStatus[0][i] == 1) {
                    updateDisplay("PLAYER X WINS")
                    break
                } else {
                    if (boardStatus[0][i] == 0) {
                        updateDisplay("PLAYER O WINS")
                        break
                    }
                }
            }
        }

        //for 1st digonal wins
        if(boardStatus[0][0]== boardStatus[1][1] && boardStatus[0][0]== boardStatus[2][2]) {
            if (boardStatus[0][0] == 1) {
                updateDisplay("PLAYER X WINS")
            } else {
                if (boardStatus[0][0] == 0) {
                    updateDisplay("PLAYER O WINS")
                }
            }
        }

        //fpr 2nd diagonal wins
        if(boardStatus[0][2]== boardStatus[1][1] && boardStatus[0][2]== boardStatus[2][0]) {
            if (boardStatus[0][2] == 1) {
                updateDisplay("PLAYER X WINS")
            } else {
                if (boardStatus[0][2] == 0) {
                    updateDisplay("PLAYER O WINS")
                }
            }
        }


    }

    private fun updateDisplay(text: String) {

        player_label.text=text

        if(text.contains("WINS")){
            disableButtons()
            Toast.makeText(requireContext(),text,Toast.LENGTH_SHORT).show()
        }
    }

    private fun disableButtons() {
        for(i in board){
            for(button in i){
                button.isEnabled=false
            }
        }
    }


    private fun updateValue(row: Int, column: Int, player: Boolean) {
        val text=if(player) "X" else "O"
        val value=if(player) 1 else 0

        board[row][column].apply {
            setText(text)
            isEnabled=false
        }
        boardStatus[row][column]=value
    }


}