package com.msu.morrison.Geoquiz

import androidx.annotation.StringRes

data class Question(@StringRes val textResID: Int, val answer: Boolean)