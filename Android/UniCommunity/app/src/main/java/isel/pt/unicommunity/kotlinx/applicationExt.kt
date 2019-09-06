package isel.pt.unicommunity.kotlinx

import androidx.appcompat.app.AppCompatActivity
import isel.pt.unicommunity.UniCommunityApp

inline fun AppCompatActivity.getUniCommunityApp() = this.application as UniCommunityApp