package pt.isel.unicommunityprototype.kotlinx

import android.support.v7.app.AppCompatActivity
import pt.isel.unicommunityprototype.UniCommunityApplication

inline fun AppCompatActivity.getUniApplication() = this.application as UniCommunityApplication