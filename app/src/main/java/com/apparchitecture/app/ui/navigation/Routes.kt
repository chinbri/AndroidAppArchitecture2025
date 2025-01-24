package com.apparchitecture.app.ui.navigation

sealed class Routes(val route: String) {

    data object Main : Routes("main")

    data object Detail : Routes("detail/{id}") {
        const val id = "id"

        fun createRoute(id: Number) = "detail/$id"
    }

}
