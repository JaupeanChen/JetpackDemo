package com.ylz.jetpackdemo.ui

import android.content.res.Configuration
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.ViewGroup
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import androidx.core.view.forEach
import androidx.lifecycle.ViewModel
import com.ylz.jetpackdemo.R
import com.ylz.jetpackdemo.data.ConversationSampleData
import com.ylz.jetpackdemo.ui.ui.theme.JetpackDemoTheme

class ComposeActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            JetpackDemoTheme {
                // A surface container using the 'background' color from the theme
//                Surface(color = MaterialTheme.colors.background) {
//                    Greeting("Android")
//                }
//                MessageCard(msg = Message("Android", "JetPack Compose"))
//                Conversation(messages = ConversationSampleData.conversationSample)
                MyScaffold(onBackPressed = { finishActivity() })
            }
        }
    }

    private fun finishActivity() {
        this.finish()
    }

    override fun onResume() {
        super.onResume()
        window.decorView.postDelayed(
            {
                transverse((window.decorView as ViewGroup), null, 1)
            }, 1000
        )
    }

    private fun transverse(childView: View, father: View?, index: Int) {
        Log.d("Compose", "第{$index}层:$childView, 父类：$father")
        if (childView is ViewGroup) {
            childView.forEach {
                transverse(it, childView, index + 1)
            }
        }
    }
}

@Composable
fun Greeting(name: String) {
    Text(text = "Hello $name!")
}

//@Preview(showBackground = true)
//@Preview(
//    uiMode = Configuration.UI_MODE_NIGHT_YES,
//    showBackground = true,
//    name = "Dark Mode"
//)
@Composable
fun DefaultPreview() {
    JetpackDemoTheme {
//        Greeting("Android")
//        MessageCard(msg = Message("Android", "JetPack Compose"))
//        Conversation(messages = ConversationSampleData.conversationSample)
        MyScaffold(onBackPressed = {})
    }
}

@Composable
fun MyScaffold(onBackPressed: () -> Unit) {
    val scaffoldState = rememberScaffoldState()
    val scope = rememberCoroutineScope()
    Scaffold(
        scaffoldState = scaffoldState,
        drawerContent = { Text(text = "Drawer Content") },
        topBar = {
//            MyAppTopAppBar(topAppBarText = "主页", onBackPressed)
            CenterTitleAppBar("Hello", onBackPressed)
        },
        floatingActionButton = {
            ExtendedFloatingActionButton(
                text = { Text(text = "Inc") },
                onClick = { /*TODO*/ })
        },
        content = { Conversation(messages = ConversationSampleData.conversationSample) },
    )
}

@Composable
fun Conversation(messages: List<Message>) {
    LazyColumn {
        items(messages) { message ->
            MessageCard(msg = message)
        }
    }
}

@Composable
fun MessageCard(msg: Message) {
    Row(modifier = Modifier.padding(all = 8.dp)) {
        Image(
            painter = painterResource(id = R.drawable.dog),
            contentDescription = "profile photo",
            modifier = Modifier
                .size(40.dp)
                .clip(CircleShape)
                .border(1.5.dp, MaterialTheme.colors.secondary, CircleShape)
        )

        Spacer(modifier = Modifier.width(8.dp))

        // We keep track if the message is expanded or not in this
        // variable
        var isExpanded by remember {
            mutableStateOf(false)
        }

        // surfaceColor will be updated gradually from one color to the other
        val surfaceColor: Color by animateColorAsState(
            if (isExpanded) MaterialTheme.colors.primary else MaterialTheme.colors.surface
        )

        Column(modifier = Modifier.clickable { isExpanded = !isExpanded }) {
            Text(
                text = msg.author,
                color = MaterialTheme.colors.secondaryVariant,
                style = MaterialTheme.typography.subtitle2
            )

            Spacer(modifier = Modifier.height(4.dp))

            Surface(
                shape = MaterialTheme.shapes.medium,
                elevation = 1.dp,
                color = surfaceColor,
                // animateContentSize will change the Surface size gradually
                modifier = Modifier
                    .animateContentSize()
                    .padding(1.dp)
            ) {
                Text(
                    text = msg.body,
                    style = MaterialTheme.typography.body2,
                    modifier = Modifier.padding(all = 4.dp),
                    //If the message is expanded, we display all its content
                    //otherwise the first line
                    maxLines = if (isExpanded) Int.MAX_VALUE else 1
                )
            }

        }
    }
}

@Composable
fun MyAppTopAppBar(topAppBarText: String, onBackPressed: () -> Unit) {
    //can use CenterTopAppBar
    //https://gist.github.com/evansgelist/aadcd633e9b160f9f634c16e99ffe163
    TopAppBar(
        title = {
            Text(
                text = topAppBarText,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxSize()
                    .wrapContentSize(Alignment.Center)
            )
        },

        navigationIcon = {
            IconButton(onClick = onBackPressed) {
                Icon(Icons.Filled.ArrowBack, contentDescription = "back")
            }
        }
    )
}

@Composable
fun CenterTitleAppBar(title: String, onBackPressed: () -> Unit) {
    val appBarHorizontalPadding = 4.dp
    val titleIconModifier = Modifier
        .fillMaxHeight()
        .width(72.dp - appBarHorizontalPadding)

    TopAppBar(
        backgroundColor = MaterialTheme.colors.primary,
        modifier = Modifier.fillMaxWidth(),
        elevation = 0.dp,
    ) {
        Box(Modifier.height(32.dp)) {
            //Navigation Icon
            Row(titleIconModifier, verticalAlignment = Alignment.CenterVertically) {
                //CompositionLocalProvider用于在启用或停用相应按钮时更改内容的alpha值
                CompositionLocalProvider(LocalContentAlpha provides ContentAlpha.high) {
                    IconButton(onClick = onBackPressed ) {
                        Icon(imageVector = Icons.Filled.ArrowBack, contentDescription = "Back")
                    }
                }
            }
            //Title
            Row(Modifier.fillMaxSize(), verticalAlignment = Alignment.CenterVertically) {
                ProvideTextStyle(value = MaterialTheme.typography.h6) {
                    CompositionLocalProvider(LocalContentAlpha provides ContentAlpha.high) {
                        Text(
                            text = title,
                            modifier = Modifier.fillMaxWidth(),
                            textAlign = TextAlign.Center,
                            maxLines = 1
                        )
                    }
                }
            }

            //actions
            CompositionLocalProvider(LocalContentAlpha provides ContentAlpha.medium) {
                Row(
                    Modifier.fillMaxHeight(),
                    horizontalArrangement = Arrangement.End,
                    verticalAlignment = Alignment.CenterVertically,
                ) {

                }
            }

        }
    }
}


data class Message(val author: String, val body: String)

sealed class UiState {
    object SignedOut : UiState()
    object InProgress : UiState()
    object Error : UiState()
    object SignIn : UiState()
}

class ComposeViewModel : ViewModel() {
    private val _uiState = mutableStateOf(UiState.SignedOut)
    val uiState: State<UiState>
        get() = _uiState
}