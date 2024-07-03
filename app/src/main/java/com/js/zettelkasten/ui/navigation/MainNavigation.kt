package com.js.zettelkasten.ui.navigation

sealed class MainNavigation(val route: String) {
    object StudyScreen: MainNavigation("studyscreen")
    object SeeAllScreen: MainNavigation("seeallscreen")
    object AddScreen : MainNavigation("addscreen")
    object EditScreen: MainNavigation("editscreen")
}