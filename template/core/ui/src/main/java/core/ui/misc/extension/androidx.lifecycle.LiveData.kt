package core.ui.misc.extension

import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.livedata.observeAsState
import androidx.lifecycle.LiveData

@Composable
fun <T> LiveData<T>.observeAsStateValue() = observeAsState().value

@Composable
fun <T> LiveData<T>.observeAsMutableState() = observeAsState() as MutableState

@Composable
fun <T> LiveData<T>.observeAsMutableState(initial: T) = observeAsState(initial) as MutableState