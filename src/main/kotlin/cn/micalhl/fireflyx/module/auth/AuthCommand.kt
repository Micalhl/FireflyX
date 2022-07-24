package cn.micalhl.fireflyx.module.auth

import cn.micalhl.fireflyx.api.FireflyXAPI
import taboolib.common.platform.ProxyPlayer
import taboolib.common.platform.command.command
import taboolib.module.lang.sendLang

object AuthCommand {

    fun register() {
        command("register", aliases = listOf("reg", "r")) {
            dynamic(commit = "password") {
                dynamic(commit = "confirm") {
                    execute<ProxyPlayer> { user, context, _ ->
                        if (FireflyXAPI.authDatabase.registered(user.uniqueId)) {
                            user.sendLang("auth-register-already")
                            return@execute
                        }
                        val first = context.argument(-1)
                        val second = context.argument(0)
                        if (first == second) {
                            FireflyXAPI.authDatabase.register(user, calculate(first))
                            Auth.login.add(user.uniqueId)
                            user.sendLang("auth-register-success")
                        } else {
                            user.sendLang("auth-register-fail")
                        }
                    }
                }
            }
        }
        command("login", aliases = listOf("l")) {
            dynamic(commit = "password") {
                execute<ProxyPlayer> { user, context, _ ->
                    if (Auth.login.contains(user.uniqueId)) {
                        user.sendLang("auth-login-already")
                        return@execute
                    }
                    val password = calculate(context.argument(0))
                    val data = FireflyXAPI.authDatabase.get(user.uniqueId)
                    if (password == data) {
                        Auth.login.add(user.uniqueId)
                        user.sendLang("auth-login-success")
                    } else {
                        user.sendLang("auth-login-fail")
                    }
                }
            }
        }
        command("changepassword") {
            dynamic(commit = "old") {
                dynamic(commit = "new") {
                    dynamic(commit = "confirm") {
                        execute<ProxyPlayer> { user, context, _ ->
                            val old = context.argument(-2)
                            val new = context.argument(-1)
                            val confirm = context.argument(0)
                            if (FireflyXAPI.authDatabase.get(user.uniqueId) != calculate(old)) {
                                user.sendLang("auth-change-old")
                                return@execute
                            }
                            if (new != confirm) {
                                user.sendLang("auth-change-confirm")
                                return@execute
                            }
                            FireflyXAPI.authDatabase.change(user, calculate(new))
                            user.sendLang("auth-change-success")
                        }
                    }
                }
            }
        }
    }

}