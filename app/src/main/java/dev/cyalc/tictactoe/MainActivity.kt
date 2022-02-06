package dev.cyalc.tictactoe

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.view.WindowCompat
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import dev.cyalc.tictactoe.data.*
import dev.cyalc.tictactoe.ui.theme.BackgroundColor
import dev.cyalc.tictactoe.ui.theme.PawnColor
import dev.cyalc.tictactoe.ui.theme.TicTacToeTheme


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        WindowCompat.setDecorFitsSystemWindows(window, false)

        val viewModel: GameViewModel by viewModels()

        setContent {
            val systemUiController = rememberSystemUiController()
            val useDarkIcons = MaterialTheme.colors.isLight
            SideEffect {
                systemUiController.setSystemBarsColor(Color.Transparent, darkIcons = useDarkIcons)
            }

            TicTacToeTheme {
                MainScreen(viewModel)
            }
        }
    }
}


@Composable
fun MainScreen(viewModel: GameViewModel) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(BackgroundColor)
            .padding(16.dp),
        contentAlignment = Alignment.Center,
    ) {
        GameScreen(
            viewModel.gameState.boardData,
            viewModel.gameState.win,
            viewModel::onPawnClicked,
            viewModel::onResetClicked
        )
    }
}


@Composable
fun GameScreen(
    boardData: Array<Array<Pawn>>,
    win: Win?,
    onPawnClicked: (Pawn) -> Unit,
    onResetClicked: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(BackgroundColor)
            .padding(16.dp),
        contentAlignment = Alignment.Center,
    ) {
        GameBoard(boardData, win) { pawn -> onPawnClicked(pawn) }
        Button(
            onClick = { onResetClicked() },
            modifier = Modifier
                .align(Alignment.TopEnd)
                .padding(top = 16.dp)
        ) {
            Text(text = "Reset")
        }
        if (win != null) {
            Text(
                text = "Player ${win.player.chosenSign.displayValue} won!",
                color = PawnColor,
                fontSize = 36.sp,
                fontFamily = FontFamily.SansSerif,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(bottom = 32.dp),
                )
        }
    }
}

@Preview
@Composable
fun Preview() {
    GameScreen(
        boardData = arrayOf(
            arrayOf(
                Pawn(null, Point(0, 0)),
                Pawn(null, Point(0, 1)),
                Pawn(null, Point(0, 2)),
            ),
            arrayOf(
                Pawn(null, Point(1, 0)),
                Pawn(null, Point(1, 1)),
                Pawn(null, Point(1, 2)),
            ),
            arrayOf(
                Pawn(null, Point(2, 0)),

                Pawn(null, Point(2, 1)),
                Pawn(null, Point(2, 2)),
            )
        ),
        win = Win(
            player = Player(
                chosenSign = PawnType.X,
                turn = Turn.PLAYER_ONE
            ),
            pawns = emptyList()
        ),
        onPawnClicked = {}
    ) {}
}
